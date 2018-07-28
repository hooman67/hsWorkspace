#include "hsGlobolHeader.h"

//this generates a bool array shwoing which numbers from 0 to max are prime.  call with bool* of length max+1 (to include 0)
void crossOff(bool* flags, int prime, int fLength) {
	//we use prime*prime because any # less than this has either been prime or a multiple of a prime number lass than current prime.
	//so any number less than prime*prime has already been crossed off
	for (int i = prime * prime; i < fLength; i += prime) {
		flags[i] = false;
	}
}
int getNextPrime(bool* flags, int prime, int fLength) {
	int next = prime + 1;
	while (next < fLength && !flags[next]){
		next++;
	}
	return next;
}
void sieveOfEratosthenes(double max, bool* flags) {  //had to change max to double to make it work with sqrt
	int count = 0;
	flags[0] = flags[1] = false;
	for (int i = 2; i < max + 1; i++)  //say max is 20 from 0 to 20 there are 21 #s
		flags[i] = true;

	int prime = 2;

	while (prime <= sqrt(max)) {
		crossOff(flags, prime, max + 1); //cross off the multiples of the current integer 
		prime = getNextPrime(flags, prime, max + 1); //find the next element that is true
		if (prime >= max + 1) {
			break;
		}
	}
}

//returns true if n is a prime nb
bool isPrimeEfficient(int n){
	if (n < 2)
		return false;
	for (int i = 2; i <= int(sqrt(double(n))); i++){  //dont forget that i is <=
		if (n%i == 0)
			return false;
	}
	return true;
}

//find the kth number that has only 3,5,7 prime factors
int f(int k){
	if (k == 0)
		return 0;
	int val = 0;
	queue<int> q3, q5, q7;
	q3.push(3);
	q5.push(5);
	q7.push(7);
	for (int i = 1; i <= k; i++){
		val = min(min(q3.front(), q5.front()), q7.front());
		if (val % 3 == 0){
			int temp = q3.front();
			q3.pop();
			q3.push(temp * 3);
			q3.push(temp * 5);
			q3.push(temp * 7);
		}
		if (val % 5 == 0){
			int temp = q5.front();
			q5.pop();
			q5.push(temp * 3);
			q5.push(temp * 5);
			q5.push(temp * 7);
		}
		if (val % 7 == 0){
			int temp = q7.front();
			q7.pop();
			q7.push(temp * 3);
			q7.push(temp * 5);
			q7.push(temp * 7);
		}
	}
	return val;
}

//pick the line that passes through the largest nb of points 
#define eps 0.0001
typedef pair<double, double> t;
class line{
public:
#define eps 0.0001
	double slope, inter;
	bool ver;
	line(t a, t b){
		if (abs(a.first - b.first) > eps){
			slope = (b.second - a.second) / (b.first - a.first);
			inter = a.second - a.first*slope;
			ver = false;
		}
		else{
			ver = true;
			slope = inter = a.first;
		}
	}
	//floor basically chops off the decimal places we dont want
	double floorToNearestEps(){//keeps only as many decimal places int the input as eps. 
		int r = int(slope / eps); //say eps = 0.001  then  a/0.001 means that the first 3 decmila points are moved into the none decimal part
		return double(r)*eps;   //then you int the number which basically drops all decimals (which is not the orig - the first 3)
	}                           //when u multiply by eps this returns the 3 decimals back into deciamsl
};
int countHelp(vector<double>* v, double a){
	int count = 0;
	for (int i = 0; i < v->size(); i++){
		if (abs((*v)[i] - a) <= eps)
			count++;
	}
	return count;
}
int countEq(line l, unordered_map<double, vector<double>>* my){
	int c1 = 0, c2 = 0, c3 = 0;
	double key = l.floorToNearestEps();
	if (my->count(key - eps)){
		c1 = countHelp(&(*my)[key - eps], l.inter);
	}
	if (my->count(key)){
		c2 = countHelp(&(*my)[key], l.inter);
	}
	if (my->count(key + eps)){
		c3 = countHelp(&(*my)[key + eps], l.inter);
	}
	return (c1 + c2 + c3);
}
line best(vector<t> v){
	unordered_map<double, vector<double>> my;
	t cc, ccc;
	line out(cc, ccc);
	int bestcount = 0;
	for (int i = 0; i < v.size(); i++){
		for (int j = i + 1; j < v.size(); j++){
			line l(v[i], v[j]);
			vector<double> v;
			v.push_back(l.inter);
			double key = l.floorToNearestEps();
			if (!my.insert(make_pair(key, v)).second)
				my[key].push_back(l.inter);
			int count = countEq(l, &my);
			if (count > bestcount){
				out = l;
				bestcount = count;
			}
		}
	}
	return out;
}






