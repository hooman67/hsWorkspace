#include <iostream>
#include<vector>
#include<stdlib.h>
#include <list>
#include <algorithm>

using namespace std;

template<class T>
void merge(list<T>& a, list<T>& b, list<T>& c){
	auto ap = a.begin(), bp = b.begin();
	
	while (ap != a.end() && bp != b.end()){
		if ((*ap) < (*bp))
			c.push_back(*ap++);
		else
			c.push_back(*bp++);
	}

	while (ap != a.end())
		c.push_back(*ap++);

	while (bp != b.end())
		c.push_back(*bp++);
}
template<class T>
void mergeSort(list<T>& in){
	if (in.size() <= 1)
		return;

	list<T> a, b;
	auto p = in.begin();
	
	for (int i = 0; i < in.size() / 2; i++)
		a.push_back(*p++);

	for (int i = in.size() / 2; i < in.size(); i++)
		b.push_back(*p++);


	in.clear();
	mergeSort(a);
	mergeSort(b);
	return merge(a, b, in);
}


/**
* Given a jumbled collection of segments, each of which is represented as
* a Pair(startPoint, endPoint), this function sorts the segments to
* make a continuous path.
*
* A few assumptions you can make:
* 1. Each particular segment goes in one direction only, i.e.: if you
* see (1, 2), you will not see (2, 1).
* 2. Each starting point only have one way to the end point, i.e.: if
* you see (6, 5), you will not see (6, 10), (6, 3), etc.
*
* For example, if you're passed a list containing the following int arrays:
*      [(4, 5), (9, 4), (5, 1), (11, 9)]
* Then your implementation should sort it such:
*      [(11, 9), (9, 4), (4, 5), (5, 1)]
*/
void mergePairs(vector<pair<int, int>>& a, vector<pair<int, int>>& b, vector<pair<int, int>>& c){
	if (a.size() == 0 || b.size() == 0)
		return;
	if (a.back().second == b.front().first){
		for (auto ap = a.begin(); ap != a.end(); ap++)
			c.push_back(*ap);

		for (auto bp = b.begin(); bp != b.end(); bp++)
			c.push_back(*bp);
	}
	else if (b.front().second == a.back().first){
		for (auto bp = b.begin(); bp != b.end(); bp++)
			c.push_back(*bp);

		for (auto ap = a.begin(); ap != a.end(); ap++)
			c.push_back(*ap);
	}
	else{
		return;
	}
}
void divideAndMergePairs(vector<pair<int, int>>& v){
	if (v.size() <= 1)
		return;

	vector<pair<int, int>> a, b;

	for (int i = 0; i < v.size() / 2; i++)
		a.push_back(v[i]);

	for (int i = v.size() / 2; i < v.size(); i++)
		b.push_back(v[i]);

	v.clear();
	divideAndMergePairs(a);
	divideAndMergePairs(b);
	mergePairs(a, b, v);
}
void connectPairs(vector<pair<int, int>>& in){
	vector<pair<int, int>> v;

	while (v.size() == 0){
		random_shuffle(in.begin(), in.end());

		for (pair<int, int> i : in)
			v.push_back(i);

		divideAndMergePairs(v);
	}

	in.clear();
	for (pair<int, int> i : v)
		in.push_back(i);
}

int main(){
	vector<pair<int, int>> v;
	v.push_back(make_pair(4, 5));
	v.push_back(make_pair(9, 4));
	v.push_back(make_pair(5, 1));
	v.push_back(make_pair(11, 9));
	
	connectPairs(v);

	for (pair<int,int> i : v){
		cout << i.first << "   " << i.second << "\n";
	}
}