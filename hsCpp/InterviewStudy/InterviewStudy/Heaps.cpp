#include <iostream>
#include<vector>
#include<queue>
#include<functional>
#include <algorithm>

using namespace std;

class hsComparator {
public:
	bool operator() (pair<int, int> a, pair<int, int> b) {
		return a.first > b.first;
	}
};



/**********************START: Merge K sorted files**********************/
vector<int> mergeSortedFiles(vector<vector<int>>& v){
	vector<int> merged;
	priority_queue<pair<int, int>, vector<pair<int, int>>, hsComparator>   q;
	int* locs = new int[v.size()];

	for (int i = 0; i < v.size(); i++){
		q.push(make_pair(v[i][0], i));
		locs[i] = 1;
	}

	while (!q.empty()){
		pair<int, int> curEl = q.top();
		q.pop();

		merged.push_back(curEl.first);

		if (locs[curEl.second] < v[curEl.second].size())
			q.push(make_pair(v[curEl.second][locs[curEl.second]++], curEl.second));
	}

	delete locs;

	return merged;
}
/************************END: Merge K sorted files**********************/




int main(){
	vector < vector<int> > v;

	vector<int> a, b, c;

	a.push_back(3);
	a.push_back(5);
	a.push_back(7);

	b.push_back(6);
	b.push_back(9);
	b.push_back(12);

	c.push_back(1);
	c.push_back(8);
	c.push_back(11);

	v.push_back(a);
	v.push_back(b);
	v.push_back(c);


	vector<int> out = mergeSortedFiles(v);

	for (int i : out){
		cout << i << "\n";
	}

}