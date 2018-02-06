#include<iostream>
#include<stack>
#include<unordered_set>

using namespace std;

template<class T>
class Node{
public:
	T data;
	Node* left, *right;
	int childs = -1;;

	Node(T data, Node* left, Node* right){
		this->data = data;
		this->left = left;
		this->right = right;
	}

	/*Node(T data){  FAILS TO SET THE data FIELD IN RUNTIME
		Node(data, NULL, NULL);
	}*/

	Node(T data):data(data), left(NULL), right(NULL){}
};
template<class T>
void deleteNodes(Node<T>* root){
	if (root->right == NULL && root->left == NULL){
		delete root;
		return;
	}

	deleteNodes(root->right);
	deleteNodes(root->left);
}


template<class T>
void printPre(Node<T>* root){
	if (root == NULL){
		return;
	}
		

	cout << root->data << "  ";
	printPre(root->left);
	printPre(root->right);
}
template<class T>
void printIn(Node<T>* root){
	if (root == NULL){
		return;
	}

	
	printIn(root->left);
	cout << root->data << "  ";
	printIn(root->right);
}
template<class T>
void printPost(Node<T>* root){
	if (root == NULL){
		return;
	}


	printPost(root->left);
	printPost(root->right);
	cout << root->data << "  ";
}
void prChildNbs(Node<int>* root){
	if (root == NULL)
		return;

	prChildNbs(root->left);
	cout << root->childs << "  ";
	prChildNbs(root->right);
}


class LockNode{
	Node<int>* root;


};


int addChildsNbs(Node<int>* root){
	if (root == NULL)
		return 0;
	
	int leftChilds = addChildsNbs(root->left);
	int rightChilds = addChildsNbs(root->right);

	root->childs = leftChilds + rightChilds;

	return (leftChilds + rightChilds + 1);
}

int main(){
	Node<int>* root = new Node<int>(4);

	root->left = new Node<int>(2);
	root->left->left = new Node<int>(1);
	root->left->right = new Node<int>(3);

	root->right = new Node<int>(6);
	root->right->left = new Node<int>(5);
	root->right->right = new Node<int>(8);

	root->right->right->left = new Node<int>(7);
	root->right->right->right = new Node<int>(9);


	addChildsNbs(root);

	printIn(root);
	cout << "\n\n";
	prChildNbs(root);
	cout << "\n\n";


	deleteNodes(root);
}


