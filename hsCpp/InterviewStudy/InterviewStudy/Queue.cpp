#include <iostream>
#include<queue>
#include<deque>

using namespace std;



template<class T>
class maxQ{
	queue<T> q;
	deque<T> deq;

public:
	void push(T data){
		q.push(data);

		while (!deq.empty() && data > deq.back()){
			deq.pop_back();
		}
		deq.push_back(data);
	}

	T pop(){
		T ret = q.front();
		q.pop();

		if (ret == deq.front())
			deq.pop_front();

		return ret;
	}

	T getMax(){
		return deq.front();
	}

	int size(){
		return q.size();
	}

};



template<class T>
vector<T>* maxInSlidingWindow(T ar[], int arLen, int windowSize){
	maxQ<T> q;
	vector<T>* out = new vector<T>();

	for (int i = 0; i < arLen; i++){
		q.push(ar[i]);

		if (q.size() > windowSize)
			q.pop();

		if (q.size() == windowSize)
			out->push_back(q.getMax());
	}

	return out;
}

int main(){
	int ar[] = { 1, 2, -5, 10, 1, 7, 4, 6, 5, 8 };
	//int ar[] = { 2, 2,3, 8,9, 10};

	vector<int>* p = maxInSlidingWindow(ar, 10, 3);

	for (int i : (*p)){
		cout << i << "\n";
	}

	delete p;

}