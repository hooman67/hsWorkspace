//rotations: if left of unbalanced node n has more childs left becomes n (right rot)    if right has more childs, right becomes n (left rot)
//a node is unbalanced if the height of its children differe by more than one. and this node is passed to rota()
//all rotations are O(1)


//regular BST implementation
typedef int t;
class Node {
	friend class BST;
public:
	Node(t data) : data(data), right(NULL), left(NULL), parent(NULL)t.insert(){};
	void insertNode( Node* node ) {
		if( node->data > data ){
			if( right == NULL ){
				right = node;
				node->parent = this;
			}
			else
				right->insertNode(node);
		}
		else if( node->data < data ){
			if( left == NULL ){
				left = node;
				node->parent = this;
			}
			else
				left->insertNode(node);
		}
		else
			cerr << "Node" << node->data << " already exists\n";
	//	this->makeBalanced();  //this is for AVL tree 
	};
	void eraseNode(t item ){ //this impl will not work with AVL because it uses the findNode() instead of recursively traversing the tree. For AVL impl this except built into it the findNode() method as well
		if( this == NULL )
			return;
		else{
			if( item == data ){
				if( left != NULL ){
				Node* p = left;
				if( p->right == NULL ){
					data = p->data;
					p->parent->left = p->left;
				}
				else{
					while( p->right != NULL )
						p = p->right;
					data = p->data;
					p->parent->right = p->left;
				}
				}
				else if( right != NULL ){
					Node* p = right;
					if( p->left == NULL){
						data = p->data;
						this->right = p->right;
					}
					else
						parent->right = p;
					
				}
				else{
					//*this = NULL;
					if( (parent->right != NULL) && (parent->right->data = data) )
						parent->right = NULL;
					else
						parent->left = NULL;
				}
			}
			else{
				findNode(item)->eraseNode(item);
			}
		}
		//this->makeBalanced();  //this is for AVL but it will not work here because erase uses findNode() instead of recursively traversing the tree so that makeBlanced() is called on all the nodes in the path. to use with AVL implement this with findNode() built into it.
	};

	t heightNode(){
		if( this == NULL || (right == NULL && left == NULL) )
			return 0;
		else{
			return 1 + max(right->heightNode(), left->heightNode());
		}
	};	
	t depthNode(){ //Can do without parent field using the height function (Nd'sDepth = TreeHeight - NdHeight)
		if( this == NULL || parent == NULL )
			return 0;
		else
			return 1 + parent->depthNode();
	}; 
	t countNode(t& i){
		if(this == NULL )
			return i;
		else{
			i++;
			i = left->countNode(i);
			i = right->countNode(i);
		}
	};
	int isBal(Node* n){ //O(n) and mem O(H), most Efficient,(not bal if returns -1) else returns H = height 
		if( n == NULL )
			return 0;
			int hl = isBal(n->left);
			if( hl == -1 )
				return -1;
			int hr = isBal(n->right);
			if(hr == -1 )
				return -1;
			if(abs(hr-hl) > 1)
				return -1;
			else
				return max(hr,hl)+1;		
	};
	bool isBST(int min, int max){
		if( this == NULL )
			return true;
		else{
			if( data > max || data < min )
				return false;
			else{
				return ( right->isBST(data,max) && left->isBST(min,data) );
			}
		}
	};

	//DFS uses less memory than BFS.  BFS always takes the SHORTEST PATH
	//BFS uses a Queue, DFS and all order traversals use stacks. Thats why they are all easily recursive. Pop(),process,push all children on
	//stack or queue  DFS is recursive version   none rec preorder traversal  is the none rec version of DFS
	//FBS moves to right first (push right on q first), dfs moves to left first so push right on stack first.
	void BFS(void (*funct)(Node*)){   //for trees (not graphs) you do not need the visited field
		queue<Node*> q;
		funct(this);
		q.push(this);
		while( !q.empty() ){
			auto r = q.front();
			q.pop();  
			if( r->right != NULL){
				funct(r->right); //do this and the following two lines for each child of r
				q.push(r->right);
			}
			if( r->left != NULL){
				funct(r->left);
				q.push(r->left);
			}
		}
	};
	void DFS(void (*funct)(Node*)){  //do not need the visited field
		if(this == NULL)
			return;
		else{  //do this for each child of this
			funct(this);
			if( right != NULL){
					right->DFS(funct);
			}
			if( left != NULL ){
					left->DFS(funct);
			}
		}
	};
	void NoneRecursivePreorder() {
	stack<Node*> s;
	s.push(this);
	while ( s.size() > 0)	{
	  auto iterator = s.top();
	  cout << iterator->data << "\n";
	  s.pop();
	  if( iterator->right != NULL)
		  s.push(iterator->right);
	  if(iterator->left != NULL)
		  s.push(iterator->left);
	}
}
	void InorderTrav(void (*function)(Node*)){
		if( this == NULL)
			return;
		else{
		left->InorderTrav(function);
		function(this);
	    right->InorderTrav(function);
		}
};

	Node* findNode( t item ){
		if( this == NULL ){
			cerr << "item not found\n";
			return NULL;
		}
		else{
			if( item > data )
				right->findNode(item);
			else if( item < data )
				left->findNode(item);
			else
				return this;
		}
	};
 
	t data;
	Node* right;
	Node* left;
	Node* parent;
	//enum status {unvisited, visited};
	//status state;
};
class BST{
public:
	Node* root;
public:
	BST():root(NULL){};
	void insert(t item){
		Node* newnode = new Node(item);
		if( root == NULL)
			root = newnode;
		else 
			root->insertNode(newnode);
	};
	~BST(){if(root != NULL)clear(root);}
	void clear(Node* n){
		if (n == NULL)
			return;
		if( n->right != NULL)
			clear(n->right);
		if( n->left != NULL)
			clear(n->left);
		delete n;
		n = NULL;
	}
	void erase( t item ){root->eraseNode(item);};
	t size(){int i=0; return root->countNode(i);};
	Node* find( t item ){return root->findNode(item);};
};
//returns the i-th largest number in the BST: O(i) so the worst case is O(n) and space is log(N) for recursion
int trav(Node* n, int target, int& count){ //call with  count = 0;
	if (n == NULL || count > target)
		return INT_MIN;
	trav(n->right, target, count);
	if (++count == target)
		return n->data;
	trav(n->left, target, count);
}

//create a sorted doubly linked list from a BST, inplace.  Comp: O(n) spcace O(1)
//Convert list to a circular linked list, and then break the circular connection. 
Node* convert(Node* root){
	Node* head = convertToCircular(root);
	head->left->right = NULL;
	head->left = NULL;
	return head;
}
void concat(Node* a, Node* b){
	if (a != NULL && b != NULL){
		a->right = b;
		b->left = a;
	}
}
Node* convertToCircular(Node* root){
	if (root == NULL)
		return NULL;
	Node* part1 = convertToCircular(root->left);
	Node* part3 = convertToCircular(root->right);
	if (part1 == NULL && part3 == NULL) {
		root->left = root;
		root->right = root;
		return root;
	}
	Node* tails = (part3 == NULL) ? NULL : part3->left;

	/* join left to root */
	if (part1 == NULL)
		concat(part3->left, root);
	else
		concat(part1->left, root);

	/* join right to root */
	if (part3 == NULL)
		concat(root, part1);
	else
		concat(root, part3);

	/* join right to left */
	if (part1 != NULL && part3 != NULL)
		concat(tails, part1);
	return part1 == NULL ? root : part1;
}


//AVL full and interview ready implementation: del doesnt work though it has a small bug
class node{
public:
	friend class bst;

	int data, height;
	node* left, *right;
public:
	node(int item) :data(item), left(NULL), right(NULL), height(0){}
	int gData()const{ return data; };
	node* gRight()const{ return right; }
	node* gLeft()const{ return left; }
	int h(){
		if (this == NULL)
			return -1;
		return height;
	}
};
class bst{
public:
	node* root;
	static void clear(node* n){
		if (n == NULL)
			return;
		clear(n->right);
		clear(n->left);
		delete n;
	}
	static void makeBalance(node*& n){
		if (n == NULL)
			return;
		n->height = 1 + max(n->right->h(), n->left->h());
		if (abs(n->right->h() - n->left->h()) > 1){
			if (n->right->h() > n->left->h()){
				if (n->right->right->h() > n->right->left->h())
					singleLeft(n);
				else
					doubleLeft(n);
			}
			else{
				if (n->left->left->h() > n->left->right->h())
					singleRight(n);
				else
					doubleRight(n);
			}
		}
	}
	static void singleRight(node*& n){
		if (n == NULL)
			return;
		node* temp = n->left;
		n->left = n->left->right;
		n->height = 1 + max(n->right->h(), n->left->h());
		temp->right = n;
		n = temp;
		n->height = 1 + max(n->right->h(), n->left->h());
	}
	static void singleLeft(node*& n){
		node* temp = n->right;
		n->right = n->right->left;
		n->height = 1 + max(n->right->h(), n->left->h());
		temp->left = n;
		n = temp;
		n->height = 1 + max(n->right->h(), n->left->h());
	}
	static void doubleRight(node*& n){
		singleLeft(n->left);
		singleRight(n);
	}
	static void doubleLeft(node*& n){
		singleRight(n->right);
		singleLeft(n);
	}
	static void insertHelp(int item, node*& n){
		if (item >= n->data){
			if (n->right == NULL)
				n->right = new node(item);
			else
				insertHelp(item, n->right);
		}
		else{
			if (n->left == NULL)
				n->left = new node(item);
			else
				insertHelp(item, n->left);
		}
		makeBalance(n);
	}
	static void delHelp(int item, node*& n){
		if (n == NULL)
			cerr << "item Not Found\n";
		if (item >= n->data){
			if (n->right->data == item){
				if (n->right->left == NULL){
					node* temp = n->right->right;
					delete n->right;
					n->right = temp;
				}
				else{
					node* p = n->right->left;
					while (p->right != NULL)
						p = p->right;
					p->right = n->right->right;
					node* q = p;
					while (q->left != NULL)
						q = q->left;
					q->left = n->right->left;
					delete n->right;
					n->right = p;
				}
			}
			else
				delHelp(item, n->right);
		}
		else{
			if (n->left->data == item){
				if (n->left->left == NULL){
					node* temp = n->left->right;
					delete n->left;
					n->left = temp;
				}
				else{
					node* p = n->left->left;
					while (p->right != NULL)
						p = p->right;
					p->right = n->left->right;
					node* q = p;
					while (q->left != NULL)
						q = q->left;
					q->left = n->left->left;
					delete n->left;
					n->left = p;
				}
			}
			else
				delHelp(item, n->left);
		}
		makeBalance(n);
	}
public:
	bst() :root(NULL){}
	~bst(){ clear(root); }
	node* gRoot()const{ return root; }
	void insert(int item){
		if (root == NULL)
			root = new node(item);
		else
			insertHelp(item, root);
	}
	void del(int item){
		if (root->data == item){
			if (root->right == NULL){
				node* temp = root->right;
				delete root;
				root = temp;
				makeBalance(root);
			}
			else{
				node* p = root->right;
				while (p->left != NULL)
					p = p->left;
				p->left = root->left;
				node* q = p;
				while (q->right != NULL)
					q = q->right;
				q->right = root->right;
				delete root;
				root = p;
				makeBalance(root);
			}
		}
		else
			delHelp(item, root);
	}
	node* find(int item)const{
		if (root->data == item)
			return root;
		node* p = root;
		while (p != NULL && item > p->data)
			p = p->right;
		while (p != NULL && item < p->data)
			p = p->left;
		if (p == NULL){
			cerr << "item not found\n";
			return NULL;
		}
		if (p->data == item)
			return p;
	}
};




	