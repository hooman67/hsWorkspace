



//extras 

struct Node{
	int data;
	Node* next;
	Node(int it):data(it),next(NULL){};
};

bool isPolindrome(Node* head){
	Node* slow = head;
	Node* fast = head;
	stack<int> s;
	while(fast!= NULL && fast->next != NULL){
		s.push(slow->data);
		slow = slow->next;
		fast = fast->next->next;
	}
	if( fast != NULL )
		slow = slow->next;
	while( !s.empty() ){
		if( slow->data != s.top() )
			return false;
		else{
			s.pop();
			slow = slow->next;
		}
	}
	return true;
}

void des(Node* a){
	while( a!=NULL){
		Node* temp = a->next;
		delete a;
		a = temp;
	}
}

int main(){
	Node* a = new Node(1);
	a->next = new Node(2);
	a->next->next = new Node(3);
	a->next->next->next = new Node(4);
	a->next->next->next->next = new Node(3);
	a->next->next->next->next->next = new Node(2);
	a->next->next->next->next->next->next = new Node(1);

	cout << isPolindrome(a) << "\n";

	des(a);

}







struct Node{
	int data;
	Node* next;
	Node(int it):data(it),next(NULL){};
};


int f(Node*& out, Node* a, Node* b){
	if( a == NULL || b==NULL || out ==NULL )
		return 0;
	int i = f(out->next,a->next,b->next);
	int res = b->data + a->data + i;
	i = 0;
	if( res >=10 ){
		res -= 10;
		i = 1;
	}
	out->data = res;
	return i;
}


Node* f(Node* a, Node* b, int sa, int sb){
	int s = sa - sb;
	Node* out = new Node(0);
	Node* p = out;
	while( sa > 1 ){
		p->next = new Node(0);
		p = p->next;
		sa--;
	}
	while(s>0){
		Node* n = new Node(0);
		n->next = b;
		b = n;
		s--;
	}
	f(out,a,b);
	return out;
}


void des(Node* a){
	while( a!=NULL){
		Node* temp = a->next;
		delete a;
		a = temp;
	}
}

int main(){
	Node* a = new Node(6);
	a->next = new Node(1);
	a->next->next = new Node(7);
	a->next->next->next = new Node(0);

	Node* b = new Node(9);
	b->next = new Node(9);
	b->next->next = new Node(5);


	Node* k = f(a,b,4,3);
	Node* p = k;
	while(p != NULL ){
		cout << p->data << "  ";
		p = p->next;
	}

	des(k);
	des(a);
	des(b);
}









#include <cstdlib>
#include <iostream>
#include <unordered_map>
#include <string>
#include <list>
using namespace std;

struct Node{
	int data;
	Node* next;
	Node(int it):data(it),next(NULL){};
};

void f(Node*& out, Node* a, Node* b, int car){
	if( a == NULL && b == NULL){
		if( car == 1 )
			out = new Node(1);
		return;
	}

	int res;

	if( a != NULL && b!= NULL)
		res = a->data + b->data + car;
	else if( b != NULL )
		res = b->data+car;
	else if( a!=NULL)
		res = a->data+car;
	car = 0;
	if( res >= 10 ){
		res -= 10;
		car = 1;
	}
	
		out = new Node(res);
		if( a != NULL && b!= NULL )
			f(out->next,a->next,b->next,car);
		else if( a!= NULL )
			f(out->next,a->next,NULL,car);
		else if( b!= NULL )
			f(out->next,NULL,b->next,car);
		else
			f(out->next,NULL,NULL,car);
}

void des(Node* a){
	while( a!=NULL){
		Node* temp = a->next;
		delete a;
		a = temp;
	}
}

int main(){
	Node* out = NULL;
	Node* a = new Node(9);
	a->next = new Node(9);
	a->next->next = new Node(9);
	a->next->next->next = new Node(9);

	Node* b = new Node(5);
	b->next = new Node(6);
	b->next->next = new Node(7);

	f(out,a,b,0);
	Node* p = out;
	while(p != NULL ){
		cout << p->data << "  ";
		p = p->next;
	}

	des(out);
	des(a);
	des(b);
}







//determine if a string has all unique characters  O(n) and O(1)
bool f(string s){
	if( s.size() > 128 )
		return false;
	bool* a = new bool[128];
	for( int i=0; i < 128;i++)
		a[i] = false;
	for( int i=0; i < s.size();i++){
		if( !a[int(s[i])] )
			a[int(s[i])] = true;
		else{
	delete [] a;
	return false;
		}
	}
	delete [] a;
	return true;
}
//string compression O(n) and O(n);
string f(string s){
	int* a = new int[s.size()];
	for( int i=0; i < 128;i++)
		a[i] = 0;
	char car = s[0];
	int c = 0;
	for( int i=0; i < s.size();i++){
		if( s[i] == car )
			a[c]++;
		else{
			c++;
			car = s[i];
			a[c]++;
		}
	}
	string temp;
	c = 0;
	int i =0;
	while(i < s.size()){
		temp += s[i];
		temp += char(48+a[c]);
		char cur = s[i];
		while(i<s.size() && s[i] == cur )
			i++;
		c++;
	}
	delete [] a;
	return temp;
}