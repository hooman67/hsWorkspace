#include "hsGlobolHeader.h"
struct node {
	int data;
	node* next;
};
class List{
public://change to private
	node* head;
public:
	List():head(NULL){};
	~List();
	void insert(int item);
	bool erase(int item);
	void traverse(void (*function)(node*));
};
List::~List(){
	while( head != NULL ){
		node* temp = head->next;
		delete head;
		head = temp;
	}
}
void List::insert(int item){//this inserts anywhere because list is sorted
	node* n = new node;
	n->data = item;
	n->next = NULL;
	if( head == NULL )
		head = n;
	else if( head->data > n->data ){ // insert front
		n->next = head;
		head = n;
	}
	else{ //insert in the middle  or  last
		node* p = head;
		node* par;
		while( p!=NULL && p->data < n->data ){
			par = p;
			p = p->next;
		}
		if( p == NULL ) //insert last
			par->next = n;
		else{ //insert in the middle
			n->next = p;
			par->next = n;
		}
	}
}
bool List::erase(int item){
	node* p = head;
	node* par;
	if( p->data == item ){//delete front
		head = head->next;
		delete p;
	}
	else{
		while( p!= NULL && p->data != item ){
			par = p;
			p = p->next;
		}
		if( p==NULL) //item not found
			return 0;
		else{  //delete from the middle
			par->next = p->next;
			delete p;
		}
	}
}
void List::traverse(void (*function)(node*)){
	node* p = head;
	while( p != NULL ){
		function(p);
		p = p->next;
	}
}



