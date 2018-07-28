#include "hsGlobolHeader.h"

//allocate a block of memory. with the starting address being devisable by al which is a power of 2
void* aligned_malloc(size_t size, size_t al){
	void* p1; //addr of where the original block begins
	void** p2;  //addr of where the alligned block begins
	int offset = al - 1 + sizeof(void*); //say all =16  if we alloc size+15  we know that we can always have an addr dev by 16
	//we need to store the addr of where the data origianlly begun. 
	//in the location directly before where the allign block begins. So we need additional space
	if ((p1 = malloc(size + offset)) == NULL) //if malloc fails
		return NULL;
	p2 = (void**)(((size_t)(p1)+offset) & ~(al - 1)); //say all = 16, an AND with ~15 (which has the last 4 locs 0) garantees that the result is devisable by 16
	p2[-1] = p1;  //storing the orig adr in loc -1 of p2
	return p2;
}
void aligned_release(void* p2){
	void* p1 = ((void**)p2)[-1];
	delete[] p1;
}
//allocating a 2D array of integers in a continuous bloc:  advantage:  a single call to delete is enough to relaese the entire memory. (min calls to malloc)
int** my2DAlloc(int rows, int cols) {
	int i;
	int header = rows * sizeof(int*);
	int data = rows * cols * sizeof(int);
	int** rowptr = (int**)malloc(header + data);
	if (rowptr == NULL) {
		return NULL;
	}
	int* buf = (int*)(rowptr + rows);
	for (i = 0; i < rows; i++) {
		rowptr[i] = buf + i * cols;
	}
	return rowptr;
}

//with reference counting and ownership.avoids both mem leaks and dangling pointers. it does not delete the pt unless nothing is refring it
class refCount{
	int count;
public:
	refCount() :count(0){}
	void inc(){ count++; }
	int dec(){ return --count; }
};
template<class T>
class smPt{
	T* pt;
	refCount* ref;
public:
	smPt(){
		pt = NULL;
		ref = new refCount();
		ref->inc();
	}
	smPt(T* p) :pt(p){
		ref = new refCount();
		ref->inc();
	}
	smPt(const smPt<T>& other){
		pt = other.pt;
		ref = other.ref;
		ref->inc();
	}
	~smPt(){
		if (ref->dec() == 0){
			delete pt;
			delete ref;
		}
	}
	T& operator*(){ return *pt; }
	T* operator->(){ return pt; }
	smPt& operator=(const smPt& other){
		if (this != &other){
			//first decr the old ref because you are no longer pointing to the old obj. and if ref = 0. u must deleted it to avoid mem leak
			if (ref->dec() == 0){
				delete pt;
				delete ref;
			}
			//copy in the new pointer
			pt = other.pt;
			ref = other.ref;
			ref->inc();
		}
		return *this;
	}
};

//sm pntr without ref counting: avoids only mem leaks ,  can cause dangling pointers
template<class T>
class smPt{
	T* pt;
public:
	smPt(T* p = NULL) :pt(p){}
	~smPt(){ delete pt; }
	T& operator*(){ return *pt;}
	T* operator->{return pt;}
	//opr = is not neccessary
	smPt& operator=(const smPt& other){
		if (this != &other){
			pt = other.pt;
		}
		return *this;
	}
};


class Person{
public:
	string name;
	int age;
	Person(string name, int age){
		this->name = name;
		this->age = age;
	}

	void Shout(){
		std::cout << "my name is: " << this->name << "\n";
	}
};


//application example using exceptions:
void MakeNoise()
{
	Person* p = new Person("Scott", 25);
	p->Shout();
	delete p;
}
//if shout() throw an exception, delete p; will not be executed which leads to mem leak.
//fix:
void MakeNoise(){
	Person* p = new Person("Scott", 25);
	try	{
		p->Shout();
	}
	catch (...)	{
		delete p;
		throw;
	}
	delete p;
}
//better fix: nothing else is requoired here
void MakeNoise(){
	smPt<Person> pt(new Person("scott", 25));
	pt->Shout();
}


//C++ built in smart pointer types:  in   #include<memory>.  They all provide automatic garbage collection
//has no ref counting only ownership. Exactly the same as second impl above.
	std::unique_ptr<int> p1(new int(5));
	std::unique_ptr<int> p2 = p1; //Compile error.
	std::unique_ptr<int> p3 = std::move(p1); //Transfers ownership. p3 now owns the memory and p1 is rendered invalid.
	p3.reset(); //Deletes the memory.
	p1.reset(); //Does nothing.

//has ref counting and ownership. Exactly the same as first impl above
std::shared_ptr<int> p1(new int(5));
std::shared_ptr<int> p2 = p1; //Both now own the memory.
p1.reset(); //Memory still exists, due to p2.   
cout << *p2; // so this line is allowed
p2.reset(); //Deletes the memory, since no one else owns the memory.

//shared_ptr uses reference counting, so circular references are potentially a problem.
//To break up cycles, weak_ptr can be used to access the stored object.
//The stored object will be deleted if the only references to the object are weak_ptr references.
//weak_ptr therefore does not ensure that the object will continue to exist, but it can ask for the resource.
shared_ptr<int> p1(new int(5));
weak_ptr<int> wp1 = p1; //p1 owns the memory.
shared_ptr<int> p2 = wp1.lock(); //Now p1 and p2 own the memory.
if (p2){ ... } // As p2 is initialized from a weak pointer, you have to check if the memory still exists!
p1.reset(); //Memory is deleted.
shared_ptr<int> p3 = wp1.lock(); //Memory is gone, so we get an empty shared_ptr.
