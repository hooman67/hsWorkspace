#include "hsGlobolHeader.h"

class HT{  //uses open addressing with quadratic probing (in vectorized form: f(i) == f(i-1) + 2i-1)    // open addressing fails for load factor >= 0.5
	enum infoType{full,empty,deleted};
public:
	pair<pair<string,int>,infoType>* v;
	int Cap, size;

public:
	HT(int capac = 13):Cap(capac),size(0){  //change to 7
		v = new pair<pair<string,int>,infoType>[Cap];
		for( int p = 0; p < Cap;p++)
			v[p].second = empty;
	};

	~HT(){
		delete [] v;
	};

	size_t hf(const string& k) { 
		unsigned int hashVal = 0;
		for( int i = 0; i < k.length(); i++)
			hashVal = 37 * hashVal + k[i];
		return hashVal % Cap;
	};

	int findPos(string key){ //helper method, returns -1 if element not found
		int offset = 1;
		int currPos = hf(key);
		
		while( v[currPos].second != empty && v[currPos].first.first != key ){
			currPos += offset; //for linear probing we just do currPos++  and dont need the offset var. for linear probing we can use a for loop. but we cannot use for(...;...; currPos++) because this way we will never start searching the table from its begining. Instead, we have to do currPos = (currPos + 1) % Cap. 
			offset += 2; 

			if( currPos >= Cap ) // this is so that once we reach the end of the vector, we start the search from the begining. Alternative is given in above comment
				currPos -= Cap;
		}
		return currPos;
	};

	bool insert(string key, int val){
		int ind = findPos(key);
		if( v[ind].second == full )
			return false;
		v[ind] = make_pair(make_pair(key,val),full);
		
		if( ++size >= Cap /2 ){ //this is because open addressing fails if load factor is >= 0.5 (#elems >= arr size)
			cout << "rehashed\n";
			rehash();
		}
		return true;
	};

	bool erase(string key){
		int ind = findPos(key);
		if(v[ind].second != full )
			return false;
		v[ind].second = deleted;
		return true;
	};
	void rehash(){
		  //this 17 is the next prime number twise 7. it should be replaced with a method that calculates prime #s by itself
		auto old = v;
		int oldCap = Cap;
		v = new pair<pair<string,int>,infoType>[17]; 
		Cap = 17;
		size = 0;
		for( int i =0; i < Cap; i++)
			v[i].second = empty;
		for( int i = 0; i < oldCap; i++){
			if( old[i].second == full )
				insert(v[i].first.first,v[i].first.second);
		}
	};

	bool contains(string key){
		return (v[findPos(key)].second == full );
	};
};