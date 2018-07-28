#include "hsGlobolHeader.h"

#include <iostream>
#include <cstdlib>


//fasterst possible implementation of alist with a tail pointer
struct node{
	int data;
	node* next;
	node(int item):data(item),next(NULL){}
};
class list{
public:
	node* head, *tail;
	list():head(NULL),tail(NULL){};
	~list(){
		while( head != NULL ){
		node* temp = head->next;
		delete head;
		head = temp;
		}
	}
	void insertB(int item){
		if( head == NULL ){
			head = new node(item);
			tail = head;
		}
		else{
			tail->next = new node(item);
			tail = tail->next;
		}
	}
	void insertF(int item){
		if( head == NULL ){
			head = new node(item);
			tail = head;
		}
		else{
			node* n = new node(item);
			n->next = head;
			head = n;
		}
	}
};

//a bit slower but doesnt have tail
struct Node{
public:
	int data;
	Node* next;
	Node(int item):next(NULL),data(item){};
};
class list{
public:
	Node* head;
	list():head(NULL){};
	~list(){
		Node* p;
		while( head != NULL ){
			p = head->next;
			delete head;
			head = p;
		}
	};
	void pushB(int item){
		if( head == NULL )
			head = new Node(item);
		else{
		Node* p = head;
		while( p->next != NULL)
			p = p->next;
		p->next = new Node(item);
		}
	};
	void pushF(int item){
		if( head == NULL)
			head = new Node(item);
		else{
			Node* n = new Node(item);
			n->next = head;
			head = n;
		}
	};
	void erase(int item){
		if( head->data == item ){
			Node* p = head;
			head = head->next;
			delete p;   //dont fortget to release the memory
		}
		else{
			Node* p = head;
			Node* par;
			while( p != NULL && p->data != item ){
				par = p;
				p = p->next;
			}
			if( p == NULL )
				cerr << "item not found\n";
			else{
				par->next = p->next;
				delete p;
			}
		}
	};
};


class tree;
class Node{
	friend class tree;
public:
	int data;
	Node* right,*left;
	Node(int data):data(data),right(NULL),left(NULL){};
	void insertN(int item){
		if( item > data ){
			if( right == NULL )
				right = new Node(item);
			else
				right->insertN(item);
		}
		else{
			if( left == NULL )
				left = new Node(item);
			else
				left->insertN(item);
		}
	};
	void eraseN(){};
		Node* rot(){
		Node* k = left;
		left = k->right;
		k->right = this;
		return k;
	};
};
class tree{
public:
	Node* root;
	tree():root(NULL){};
	void insert(int item){
		if( root == NULL)
			root = new Node(item);
		else
			root->insertN(item);
	}
	void erase(int item){
		if( root->data == item ){
			if( root->right != NULL ){
				Node* p = root->right;
				while( p->left != NULL )
					p= p->left;
				p->left = root->left;
				root = p;
			}
			else
				root = root->left;
		}
		else{
			Node* pp = root;
			while( pp!=NULL && pp->data != item ){
				if( item > pp->data )
					pp = pp->right;
				else
					pp = pp->left;
			}
			if( pp == NULL )
				cerr << "Not Found";
			else{
				if( pp->right != NULL ){
					Node* q = pp->right;
					if( q->left == NULL ){
						pp->data = q->data;
						pp->right = q->right;
					}
					else{
						while( q->left->left != NULL )
							q= q->left;
						pp->data = q->left->data;
						q->left = q->left->right;
					}
				}
			}
		}
	};
};