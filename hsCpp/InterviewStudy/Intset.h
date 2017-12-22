#include <iostream>
using namespace std;
#include <string>

class IntSet{
public:
	IntSet();
	~IntSet();
	IntSet& operator=(const IntSet &other);
	// above is the same as IntSet& operator=(const IntSet& other);
	//however you cannot write &IntSet operator=...
	//the & always comes after the type of the variable unless it is dereferencing the variable its self in which case comes before the variable.
	IntSet(const IntSet& other);  //this is a copy constructor big 3
	IntSet(int cap, int count, int*p); //parametric constructor
    void insert( int entry );
    void remove( int target );
	int size() const;
	int get( int index ) const;
	bool find( int target ) const;
	virtual void print() const;
protected:
	void copy(const IntSet &other); //this is a copy helper function which is used to define the copy constructor and the overloaded assignment operator.
	// Helper functions are always private
	// the return is void because this is a class method which is always called on an alteady created object.
	int count;
	int capacity;
//	int*data = new int[capacity]; This is just the decleration so we cannot initialized anything we can just creat a pointer field;
	int * data;
	void cleanUp(); //this is another helper function used in the destructor
};
