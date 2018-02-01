#include <iostream>
#include<list>

using namespace std;

template<class T>
class Node{
public:
	T data;
	Node* next;
	Node(T data) : data(data){
		//prev = NULL;
		next = NULL;
	}
	Node(T data, Node* next){
		this->data = data;
		this->next = next;
	}

	bool operator<(const Node n) const {
		return data < n.data
	}
};


/***START: Merge two sorted lists*********/
template<class T>
Node<T>* mergeLists(Node<T>* a, Node<T>* b){
	/**time O(n), space O(1) */
	if (a == NULL && b == NULL)
		return NULL;

	Node<T>* pa = a, *pb = b, *outHead, *out;

	if (pa->data < pb->data){
		out = pa;
		pa = pa->next;
	}
	else{
		out = pb;
		pb = pb->next;
	}
	
	outHead = out;
	
	while (pa != NULL && pb != NULL){
		if (pa->data < pb->data){
			out->next = pa;
			pa = pa->next;
		}
		else{
			out->next = pb;
			pb = pb->next;
		}

		out = out->next;
	}

	while (pa != NULL){
		out->next = pa;
		pa = pa->next;
		out = out->next;
	}

	while (pb != NULL){
		out->next = pb;
		pb = pb->next;
		out = out->next;
	}


	return outHead;
}
/*****END: Merge two sorted lists*********/


/***START: Reverse Singly LinkedList*********/
template<class T>
Node<T>* reverseList(Node<T>* root){
	/**time O(n), space O(1) */

	Node<T>* cur = root, *pre = NULL;

	while (cur != NULL){
		Node<T>* rest = cur->next;
		cur->next = pre;

		pre = cur;
		cur = rest;
	}

	return pre;
}
/*****END: Reverse Singly LinkedList*********/



/*********START: Detect if singlyLinkedLists is Palindrome*********/
template<class T>
bool isPolindrome(Node<T>* root){
	//1.find length
	int len = 0;
	Node<T>* p = root;
	while (p != NULL){
		p = p->next;
		len++;
	}

	//2.Reverse the second half
	Node<T>* cur = root, *pre = NULL;
	for (int i = 0; i < len / 2; i++){
		cur = cur->next;
	}

	while (cur != NULL){
		Node<T>* rest = cur->next;
		cur->next = pre;

		pre = cur;
		cur = rest;
	}

	//3. Compare
	p = root;
	while (pre != NULL){
		if (p->data != pre->data)
			return false;
		p = p->next;
		pre = pre->next;
	}

	return true;
}
/***********END: Detect if singlyLinkedLists is Palindrome*********/


/****START: Reorder a singlyLinkedLists so that even indexed elements come before odd****/
template<class T>
void evenOddMerge(Node<T>* root){
	Node<T>* even = root, *odd = root->next, *headOfOdd = root->next;

	while (even != NULL && odd != NULL && even->next != NULL && odd->next != NULL){

		even->next = even->next->next;
		odd->next = odd->next->next;

		even = even->next;
		odd = odd->next;
	}

	even->next = headOfOdd;
}
/******END: Reorder a singlyLinkedLists so that even indexed elements come before odd****/


int main(){

	Node<int>* n0 = new Node<int>(0);
	Node<int>* n1 = new Node<int>(1);
	Node<int>* n2 = new Node<int>(2);
	Node<int>* n3 = new Node<int>(3);
	Node<int>* n4 = new Node<int>(4);
	Node<int>* n5 = new Node<int>(5);
	Node<int>* n6 = new Node<int>(6);
	Node<int>* n7 = new Node<int>(7);
	Node<int>* n8 = new Node<int>(8);
	Node<int>* n9 = new Node<int>(9);

	Node<int>* m1 = new Node<int>(1);
	Node<int>* m2 = new Node<int>(2);
	Node<int>* m3 = new Node<int>(3);

	n0->next = n1;
	n1->next = n2;
	n2->next = n3;
	n3->next = n4;


	evenOddMerge(n0);

	Node<int>* p = n0;
	while (p != NULL){
		cout << p->data << "\n";
		p = p->next;
	}


	delete n0, n1, n2, n3, n4, n5, n6, n7, n8, n9, m1, m3, m2;

}