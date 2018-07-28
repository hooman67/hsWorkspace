#include "hsGlobolHeader.h"
//trick for this is that the main for loop should go to the lenght of the longest chain.
//the second for loop (or while loop) traverses the length of the list.
//and IMPORT the chain's pointer or index increaes ONLY if the length of the chain is more than the current index
//of the main loop

void print( list<list<int>>& l ){
	int maxSize = 0;
	for( auto p = l.begin(); p != l.end(); p++){
		if( p->size() > maxSize )
			maxSize = p->size();
	}
	for( int i =0; i< maxSize; i++){
		for( auto p = l.begin(); p!= l.end(); p++ ){
			auto pp= p->begin();
			for(int j =0; j < i; j ++ ){
				if( i < p->size())
				pp++;
			}
			if( i < p->size() )
				cout << *pp << "   ";
			else
				cout << "    ";
		}
		cout << "\n";
	}
}

int main() {
list<list<int>> my;
list<int> a;
list<int> b;
list<int> c;
list<int> d;
list<int> e;

a.push_front(4);
a.push_front(3);
a.push_front(2);
a.push_front(1);
b.push_front(7);
b.push_front(6);
b.push_front(5);
c.push_front(8);
d.push_front(9);
e.push_front(12);
e.push_front(11);
e.push_front(10);

my.push_front(e);
my.push_front(d);
my.push_front(c);
my.push_front(b);
my.push_front(a);

}