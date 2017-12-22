#include "hsGlobolHeader.h"
//this is a trie (prefix tree); has an add function that makes it a sufixTree (adds the sufixes of each string as well)
class Node{
public:
	char value;             // the character value (a-z)
	bool end;               // indicates whether this node completes a word
	Node * children[26];    // represents the 26 letters in the alphabet
	Node(char val):value(val),end(false){
		for (int i = 0; i < 26; ++i) 		// Initializes all of the children with NULL value
			children[i] = NULL;
	}
};
class Trie{
private:
	Node * root;
public:
	Trie():root(new Node(' ')){ root->end = true;}
	~Trie(){
		clear(root);
	}
	static void clear(Node* n){
		if (n == NULL)
			return;
		for (int i = 0; i < 26; i++)
			clear(n->children[i]);
		delete n;
	}
	void addWord(string word){
		Node * currentNode = root;
		for (int i = 0; i < word.size(); i++){
			char currentChar = tolower(word[i]);
			int index = currentChar - 'a';
			assert(index >= 0);     // Makes sure the character is between a-z
			if (currentNode->children[index] != NULL)// check if the current node has the current character as one of its decendants
				currentNode = currentNode->children[index];
			else{// the current node doesn't have the current character as one of its decendants
				Node * newNode = new Node(currentChar);
				currentNode->children[index] = newNode;
				currentNode = newNode;
			}
			if (i == word.size() - 1)				// the last character of the word has been reached
				currentNode->end = true;
		}
	}
	void addSuffix(string word, bool parse = false){ //using this will create a suffix tree. If the string has spaces in it, you have to call with parse = 1.
		if (parse){
			for (int i = 0; i <word.size(); i++){
				int k = i;
				while (i < word.size() && word[i] != ' ')
					i++;
				addSuffix(word.substr(k, i - k));
			}
		}
		else{
			for (int i = 0; i < word.size(); i++) //an attempt to put a none alph character (ie ' ') will cause an assertion failure
				addWord(word.substr(i));
		}
	}
	bool searchForWord(string word){ //if u wanna have spaces in your word, you have to parse it and search for key words seperately
		Node * currentNode = root;
		for (int i = 0; i < word.size(); ++i){
			char currentChar = tolower(word.at(i));
			int index = currentChar - 'a';
			assert(index >= 0);
			// if the current node has the current character as one of its decendants
			if (currentNode->children[index] != NULL)
				currentNode = currentNode->children[index];
			else
				return false;
			// makes sure the last node is marked as an end for a word
			if (i == word.size() - 1 && !currentNode->end)
				return false;
		}
		return true;
	}
	bool searchForPrefix(string word){ //return true if word is a prefix of (or the same as) a word in trie
		Node * currentNode = root;
		for (int i = 0; i < word.size(); ++i){
			char currentChar = tolower(word.at(i));
			int index = currentChar - 'a';
			assert(index >= 0);
			// if the current node has the current character as one of its decendants
			if (currentNode->children[index] != NULL)
				currentNode = currentNode->children[index];
			else
				return false;
		}
		return true;
	}
	void deleteWord(string word){
		Node * currentNode = root;
		Node * lastWord = root;
		for (int i = 0; i < word.size(); ++i){
			char currentChar = tolower(word.at(i));
			int index = currentChar - 'a';
			assert(index >= 0);
			// if the current node has the current character as one of its decendants
			if (currentNode->children[index] != NULL)
				currentNode = currentNode->children[index];
			// the current node doesn't have the current character which means the word is not in the trie
			else
				return;
			if (i == word.size() - 1 && currentNode->end)
				currentNode->end = false;
		}
	}
	Node * getRoot(){ return root; }
};
//Traverse the tree in (alphabetical) order.  Call without a prefix so the default is called
void InOrder(Node * node, string prefix = ""){
	if (node->end)
		cout << prefix << "\n";  //replace with appropriate function
	for (int i = 0; i < 26; ++i){
		if (node->children[i] != NULL){
			string currentString = prefix + node->children[i]->value;
			InOrder(node->children[i], currentString);
		}
	}
}

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


