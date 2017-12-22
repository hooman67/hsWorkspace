#include "hsGlobolHeader.h"
//linear probing(clusters): if |k| % N is full try |k+i|/N next i=1,2,3..   flag the empty spots for eas of find and insert
//quadratic probing(secondary clusters) if full try |k+i^2| % N  next.    double hashing : next try |k+h'(k)| % N   no clusters
//mad compression avoids repeated patters  |aK+b| % N  (N prime and large, a and b>0 and a%N != 0)  
//load factor(size of each list) = n/N < 0.9 for seperate chaining  and < 0.5 for open addressing  (prob of collision 1/N for a good hashfun)
size_t Polynomial(const string& k, int N) { //polynomical hash funct
	 unsigned int hashVal = 0;
	 for( int i = 0; i < k.length(); i++)
	 hashVal = 37 * hashVal + k[i];

 return hashVal % N;
}
size_t CyclingShift(string& key, int N) { //cyclic shift hash function for a 32bit 
	unsigned int h = 0;
	for (int i = 0; i < key.size(); i++) { 
		h = (h << 5) | (h >> 27); // cyclic shift of a 32bit by 5 to the left
		h += (unsigned int) key[i]; // add in next character
	}
	return h % N;
}
size_t integer( const int key, int N ){
	return key % N;
}
size_t summationint( const string& key, int tableSize ) {//NOT GOOD  summation hash function for a string
 	int hashVal = 0;
	for( int i=0; i < key.length(); i++ )
		hashVal += key[i];
	return hashVal % tableSize;
 }

//below is implementation of hash table with a seperate chaining.
typedef string key;
typedef int val;
typedef pair<key,val> PT;
class HashTable{
public://private:
    size_t hash(const key& k ) const;  //complete hash function		
	list<PT>* arr;
	int currentSize;
	int capacity;
	void rehash();
public:
	HashTable( int size = 101 ):capacity(size), currentSize(0){ arr = new list<PT>[capacity];};  
	~HashTable(){
		for(int i =0; i < capacity;i++)
			arr[i].clear();
		delete [] arr;
	};
	bool count( const key& k )const;
	bool insert( const key& k, const val& v );
	bool erase( const key& k );
	val& operator[](const key& k);
};
size_t HashTable::hash(const key& k) const { //polynomical hash funct
	 unsigned int hashVal = 0;
	 for( int i = 0; i < k.length(); i++)
	 hashVal = 37 * hashVal + k[i];

 return hashVal % capacity;

}
bool HashTable::count(const key& k) const{
	for( auto p=arr[hash(k)].begin(); p!=arr[hash(k)].end();p++){
		if( p->first == k )
			return true;
	}
	return false;
}
bool HashTable::insert(const key& k, const val& v ){
	if( count(k) )
		return false;
	PT out = make_pair(k,v);  
	if( ++currentSize >= capacity )   //rehash()                            
			cerr<< "table full";                  
	else                                       
		arr[hash(k)].push_back(out);
		return true;
}
bool HashTable::erase(const key& k ){
		for( auto p= arr[hash(k)].begin();p!=arr[hash(k)].end();p++){
			if( p->first == k ){
				arr[hash(k)].remove(*p);
				return true;
			}
		}
		return false;
	}
val& HashTable::operator[](const key& k){
	for(auto p = arr[hash(k)].begin(); p!=arr[hash(k)].end();p++){
		if( p->first == k )
			return p->second;
	}
	val i = -1;
	arr[hash(k)].push_back(make_pair(k,i));
	return i;
}

// key: find returns pair<int,bool>  first is the location
//of the item if it exists or the location of the next empty cell.  second is a bool showing if item was there or not
//for quad probing, u only change the find to probe in quad manner. The rest including offsets in shift and insert do not change. 
//so the offset u hold is the real offset and is had nothing to do with the way u probe.

//implementation with open addressing and linear probing 
typedef pair<pair<string,int>,int> t;
class ht{
private:
	int cap,size;
	t* ar;
	size_t h(const string& k){
		unsigned hh = 0;
		for( int i=0; i < k.size();i++){
			hh = (hh>>27) | (hh<<5);
			hh += unsigned(k[i]);
		}
		return hh%cap;
	}
public:
	ht(int Cap = 7 ):cap(Cap),size(0),ar(new t[cap]){
		for( int i=0; i < cap; i++)
			ar[i].second = -1;
	}
	~ht(){delete [] ar;}
	pair<unsigned,bool> find(const string& k){
		unsigned hash = h(k);
		while( ar[hash].second != -1 ){
			if( ar[hash].first.first == k )
				return make_pair(hash,true);
			hash = ++hash%cap;
		}
		return make_pair(hash,false);
	}
	void res(){  //its ok to use insert
		t* old = ar;
		int oldCap = cap;
		cap = 13;
		size = 0;
		ar = new t[cap];
		for( int i=0; i < cap; i++)
			ar[i].second = -1;
		for( int i=0; i < oldCap; i++){
			if( old[i].second != -1 )
				insert(old[i].first.first,old[i].first.second);
		}
	}
	bool insert(const string& k, int val){
		auto a = find(k);
		if( a.second ){
			cout << "item already exists\n";
			return false;
		}
		else{
			if( ++size >= cap ){ //adding the new element fills up the array. whic u cant have because find never stops if that happens
				res();
				a = find(k);
				int off = a.first - h(k);
				if( off < 0 )
					off+=cap;
				ar[a.first] = make_pair(make_pair(k,val),off);
				size++;
				return true;
			}
			else{
			int off = a.first - h(k);
			if( off < 0 )
				off+=cap;
			ar[a.first] = make_pair(make_pair(k,val),off);
			return true;
			}
		}
	}
	void shift(size_t index){
		size_t ind = (index+1)%cap; //this has changed from before
		int off = 1;
		while( ar[ind].second != -1){
			if( ar[ind].second >= off ){
				ar[index].first = ar[ind].first;
				ar[index].second = ar[ind].second - off;  //this has changed from before too. before u used the hash funct to calculate the offset again from scratch
				ar[ind].second = -1;
				off = 0;
				index = ind;
			}
			off++;
			ind = ++ind%cap;
		}
	}
	bool del(const string& k){
		auto a = find(k);
		if( !a.second ){
			cout << "item not found\n";
			return false;
		}
		ar[a.first].second = -1;
		shift(a.first);
		return true;
	}

	//accessors:
	int getSize()const{return size;};
	int getCap()const{return cap;};
};

int main(){
	ht my;
	my.insert("d",7);
	my.insert("b",1);
	my.insert("i",2);
	my.insert("p",3);
	my.insert("w",4);
	my.insert("af",5);
	my.insert("am",6);

}







class HT{  //uses open addressing with quadratic probing (in vectorized form: f(i) == f(i-1) + 2i-1)    // open addressing fails for load factor >= 0.5
	enum infoType{ full, empty, deleted };
public:
	pair<pair<string, int>, infoType>* v;
	int Cap, size;

public:
	HT(int capac = 13) :Cap(capac), size(0){  //change to 7
		v = new pair<pair<string, int>, infoType>[Cap];
		for (int p = 0; p < Cap; p++)
			v[p].second = empty;
	};

	~HT(){
		delete[] v;
	};

	size_t hf(const string& k) {
		unsigned int hashVal = 0;
		for (int i = 0; i < k.length(); i++)
			hashVal = 37 * hashVal + k[i];
		return hashVal % Cap;
	};

	int findPos(string key){ //helper method, returns -1 if element not found
		int offset = 1;
		int currPos = hf(key);

		while (v[currPos].second != empty && v[currPos].first.first != key){
			currPos += offset; //for linear probing we just do currPos++  and dont need the offset var. for linear probing we can use a for loop. but we cannot use for(...;...; currPos++) because this way we will never start searching the table from its begining. Instead, we have to do currPos = (currPos + 1) % Cap. 
			offset += 2;

			if (currPos >= Cap) // this is so that once we reach the end of the vector, we start the search from the begining. Alternative is given in above comment
				currPos -= Cap;
		}
		return currPos;
	};

	bool insert(string key, int val){
		int ind = findPos(key);
		if (v[ind].second == full)
			return false;
		v[ind] = make_pair(make_pair(key, val), full);

		if (++size >= Cap / 2){ //this is because open addressing fails if load factor is >= 0.5 (#elems >= arr size)
			cout << "rehashed\n";
			rehash();
		}
		return true;
	};

	bool erase(string key){
		int ind = findPos(key);
		if (v[ind].second != full)
			return false;
		v[ind].second = deleted;
		return true;
	};
	void rehash(){
		//this 17 is the next prime number twise 7. it should be replaced with a method that calculates prime #s by itself
		auto old = v;
		int oldCap = Cap;
		v = new pair<pair<string, int>, infoType>[17];
		Cap = 17;
		size = 0;
		for (int i = 0; i < Cap; i++)
			v[i].second = empty;
		for (int i = 0; i < oldCap; i++){
			if (old[i].second == full)
				insert(v[i].first.first, v[i].first.second);
		}
	};

	bool contains(string key){
		return (v[findPos(key)].second == full);
	};
};