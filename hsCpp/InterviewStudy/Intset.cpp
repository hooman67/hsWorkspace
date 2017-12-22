#include "Intset.h"

IntSet::IntSet(){
	count = 0;
	capacity = 10;
	data = new int[capacity];
}

IntSet::~IntSet(){
	cleanUp();
}

void IntSet::cleanUp(){
	delete [] data;
}

IntSet& IntSet::operator=(const IntSet& other){
	if( &other != this){
		cleanUp();
		copy(other);
	}
	return *this;
}

IntSet::IntSet(int cap, int count, int*p){  //parametric constructor
	this->count = count;
	capacity = cap;
	for(int i=0;i<count;i++)
	data[i] = p[i];
}

void IntSet::copy(const IntSet& other){ //this is the copy helper function. Helper functions are always private
	count = other.count;
	capacity = other.capacity;
	data = new int[capacity];
	for(int i=0;i<count;i++)
		data[i] = other.data[i];
}

IntSet::IntSet(const IntSet &other){
	copy(other);
}

void IntSet::insert( int entry ){
	 data[count] = entry;
	 count++;
 }

int IntSet::size() const{
	 return count;
 }

int IntSet::get( int index ) const{
		return data[index];
	}

void IntSet::print() const{
		for(int i=0;i<count;i++)
			cout << data[i] <<"\n";
	}

bool IntSet::find( int target ) const{
	for( int i = 0; i < this->count; i++ ){
	if ( this->data[ i ] == target )
	{
	return true;
	}
}
return false;
}

void IntSet::remove( int target )
{
for( int i = 0; i < this->count; i++ )
{
if ( this->data[ i ] == target )
{
this->data[ i ] = this->data[ --(this->count) ];
return;
}
}
return;
}