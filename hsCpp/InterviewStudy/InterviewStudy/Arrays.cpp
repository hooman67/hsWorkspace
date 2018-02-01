#include <iostream>
#include <vector>
#include <string>

using namespace std;

/***START: Interleave categories *********/
void mergeCategories(vector<string>& vect){
	/*time O(n) space O(1)
	* problem: convert ["a1","a2","a3","b1", "b2", "b3", "c1","c2", "c3"] to ["a1","b1","c1","a2", "b2", "c2", "a3","b3", "c3"].
	* should work for any number of ppl but with only 3 categories.
	*/
	int nbPeople = vect.size() / 3;

	int ap = 0, bp = nbPeople, cp = nbPeople * 2;
	int fa = 0, fb = 1, fc = 2;

	while (cp < vect.size() - 1){

		//swap
		string temp = vect[fa];
		vect[fa] = vect[ap];
		vect[ap] = temp;


		if (fa == bp)
			bp = ap;
		else if (fa == cp)
			cp = ap;

		//swap
		temp = vect[fb];
		vect[fb] = vect[bp];
		vect[bp] = temp;

		//swap
		temp = vect[fc];
		vect[fc] = vect[cp];
		vect[cp] = temp;


		//update pointers
		fa += 3;
		fb += 3;
		fc += 3;

		ap += nbPeople;
		bp++;
		cp++;
	}
}
/*****END: Interleave categories *********/

int main(){
	vector<string> vec;
	vec.push_back("a1"); vec.push_back("a2"); vec.push_back("a3"); vec.push_back("a4");
	vec.push_back("b1"); vec.push_back("b2"); vec.push_back("b3"); vec.push_back("b4");
	vec.push_back("c1"); vec.push_back("c2"); vec.push_back("c3"); vec.push_back("c4");

	mergeCategories(vec);

	for (string s : vec)
		cout << s << "\n";
}