#include "ChildInt.h"

ChildInt::ChildInt(int k, int j) : IntSet(0,0,&k) {  //this is how we would define the constructor if 
	this->fieldk = k;                               //Inset had no default constructor.
    this->fieldj = j;
}

int ChildInt::getk() const {
	return fieldk;
}

int ChildInt::getj() const {
	return fieldj;
}

void ChildInt::insert( int entry) {
	this->IntSet::insert( entry);
	this->fieldj = 9;
	this->fieldk = 99;
}

void ChildInt::print() const {
	for(int i=0;i<count;i++)
			cout << data[i] <<"\n";
	cout << fieldk << "      " << fieldj << "\n";
}