
#include <iostream>
#include <cstdlib>
#include <vector>
#include <iterator>
using namespace std;
//Binary Heap: change heapifyUp and heapifyDown to change this to max binary heap (now its min binary heap)  
class BinaryHeap{
private:
	vector <int> heap;//start from pos 0 so left child of i is at 2*i +1; right child of i is at 2*i+2; par is at (i-1)/2;
	int left(int parent){//return left child. We start from pos =0; so left child of i is at  2*i +1
		int l = 2 * parent + 1;
		if (l < heap.size())
			return l;
		else
			return -1;
	}
	int right(int parent){//return right child
		int r = 2 * parent + 2;
		if (r < heap.size())
			return r;
		else
			return -1;
	}
	int parent(int child){ //return parent
		int p = (child - 1) / 2;
		if (child == 0)
			return -1;
		else
			return p;
	}
	void heapifyup(int in){//need this when we add sth to the bottom that violates heap property
		if (in >= 0 && parent(in) >= 0 && heap[parent(in)] > heap[in]){ //reverse last part of this to change to max heap
			int temp = heap[in];
			heap[in] = heap[parent(in)];
			heap[parent(in)] = temp;
			heapifyup(parent(in));
		}
	}
	void heapifydown(int in){//need this when we modify (ex delete) something from the top that violates heap property
		int child = left(in);
		int child1 = right(in);
		if (child >= 0 && child1 >= 0 && heap[child] > heap[child1]) //reverse last part of this to change to max heap	
			child = child1;
		if (child > 0){
			int temp = heap[in];
			heap[in] = heap[child];
			heap[child] = temp;
			heapifydown(child);
		}
	}
public:
	BinaryHeap(){}
	void Insert(int element){
		heap.push_back(element);
		heapifyup(heap.size() - 1);
	}
	void DeleteMin(){
		if (heap.size() == 0){
			cout << "Heap is Empty" << endl;
			return;
		}
		heap[0] = heap.at(heap.size() - 1);
		heap.pop_back();
		heapifydown(0);
		cout << "Element Deleted" << endl;
	}
	int ExtractMin(){
		if (heap.size() == 0)
			return -1;
		else
			return heap.front();
	}
	void DisplayHeap(){
		vector <int>::iterator pos = heap.begin();
		cout << "Heap -->  ";
		while (pos != heap.end()){
			cout << *pos << " ";
			pos++;
		}
		cout << endl;
	}
	int Size(){ return heap.size(); }
};


