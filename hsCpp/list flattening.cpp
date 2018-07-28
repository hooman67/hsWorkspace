#include <iostream>
#include <cstdlib>
using namespace std; 

class list;
struct Node{
	int data;
	Node* next, *prev;
	list* child;
	Node(int item):next(NULL),data(item),child(NULL),prev(NULL){};
};
class list{
public:
	Node* head, *tail;
	list():head(NULL), tail(NULL){};
	~list(){
		Node* p;
		while( head != NULL ){
			p = head->next;
			delete head;
			head = p;
		}
	};
	void pushB(int item){
		if( head == NULL ){
			head = new Node(item);
			tail = head;
		}
		else{
		Node* p = head;
		while( p->next != NULL)
			p = p->next;
		p->next = new Node(item);
		p->next->prev = p;
		tail = p->next;
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

	list* mch(int item1){
		Node* p = head;
		while( p != NULL && p->data != item1 )
			p = p->next;
		p->child = new list();
		return p->child;
	};

	Node* find(int item){
		Node* p = head;
		while( p!=NULL && p->data != item)
			p = p->next;
		if( p == NULL )
			cerr << "not found";
		else
		return p;
	}
};

void flatten(list& l){
	for( Node* n = l.head; n != NULL; n = n->next){
		if( n->child != NULL ){
				Node* p = n->child->head;
				n->child->tail->next = n->next;
				n->next->prev = n->child->tail;
				n->next = p;
				p->prev = n;
		}
		n->child = NULL;
	}
}

int main(){
	list l;
	l.pushB(5);
	l.pushB(33);
	l.pushB(17);
	l.pushB(2);
	l.pushB(1);
	list* a = l.mch(5);
	list* b = l.mch(2);

	a->pushB(16);
	a->pushB(25);
	a->pushB(6);

	b->pushB(3);
	b->pushB(7);

	list* c = a->mch(25);
	c->pushB(8);

	c = a->mch(16);
	c->pushB(9);

	list* d = c->mch(9);
	d->pushB(7);

	c = b->mch(7);
	c->pushB(5);
	c->pushB(3);

	flatten(l);

	for( Node* p = l.head; p!=NULL;p=p->next)
		cout << p->data << "\n";
}



