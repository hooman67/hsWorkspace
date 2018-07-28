#include "Intset.h"

class ChildInt : public IntSet {
public:
	ChildInt( int k = 0, int j = 1); //par names are the same as data field names so we must use initialization lists
	int getk() const;
	int getj() const;
	void insert( int entry ); //this is also in the base
	void print() const;

private:
	int fieldk;
	int fieldj;
};