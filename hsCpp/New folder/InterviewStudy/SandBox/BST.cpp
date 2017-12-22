#include "First.h"
typedef int data_type;
class TreeNode 
{
friend class BST;
public:      //chane it to private later but turnArr2BST wont work
	data_type data;
	TreeNode* right;
	TreeNode* left;
	TreeNode* parent;
public:
	TreeNode(data_type item = 0);
	void insert_node(TreeNode* node);
	bool find(data_type item ) const;   
	TreeNode* findRec( data_type item); //same as find() except returns a pointer
	void print_preorder() const;
	void print_inorder() const;
	void print_postorder() const;
	void print_preorder_norecur();
    int nodesCount( int count);
	void TurnToHeap();
	void storeInArray(data_type a[], int& i);
	void storeInHeap(data_type a[], int size, int& i);
	data_type getData() const;
	void restructAVL(TreeNode* x, TreeNode* y, TreeNode* z);
	bool operator==( const TreeNode t) const;
	TreeNode* getParent();
	int nodeHeight(); 
	int nodeDepth();
	
};

class BST
{
private:
	TreeNode* root;
public:
	BST();
	void insert( data_type data);
	void erase(data_type data);
	TreeNode* getRoot() const;
	void setRoot(TreeNode* t);
	bool empty() const;
	int size() const;
	TreeNode* rotateRight();  //this is used to balance the tree
	int TreeHeight();
	//int NodeDepth(TreeNode* n); //You cant find the depth of a node from its height you need to have its parents
};

TreeNode::TreeNode( data_type item ) { 
	data = item;
	right = NULL;
	left = NULL;
	parent = NULL;
}

int TreeNode::nodeDepth(){
	if(this->parent == NULL)
		return 0;
	else 
		return 1 + (this->parent)->nodeDepth();
}



BST::BST()
{
	root = NULL;
}

int BST::TreeHeight() {
	return root->nodeHeight();
}

//THIS IS WRONG
//int BST::NodeDepth(TreeNode* n){  //WRONG: Depth of each node is height of the tree - height of the node
	//return (this->TreeHeight() - n->nodeHeight());//TREE WITH HEIGHT 4 CAN BE A NODE WITH HEIGHT 0 AND DEPTH 2

//}

void BST::setRoot( TreeNode* t)
{
	root = t;
	root->parent = NULL;
}

void BST::insert(data_type data)
{
		TreeNode* newnode = new TreeNode(data);
        if(root == NULL ) {
			root = newnode;
		}
		else
			root->insert_node( newnode);
}

int BST::size() const {
	return root->nodesCount(0);
}


TreeNode* BST::getRoot() const
{
	return root;
}

TreeNode* BST::rotateRight() 
{
	TreeNode* newnode = getRoot()->left;
	getRoot()->left = newnode->right;
	newnode->right = getRoot();
	return newnode;
}

bool BST::empty() const { 
	return (root == NULL);
}

void BST::erase(data_type data)
{
  bool found = false;
  TreeNode* iter = root;
  TreeNode* parent = NULL;
  while( iter && !found != NULL)  //to find the node and its parrents
  {
	  if(data > iter->data)
		  {
			  parent = iter;
			  iter = iter->right;
		  }
	     else if( data < iter->data)
		 {
			 parent = iter;
			  iter = iter->left;
		  }
		 else 
			 found = true;
	  }
  if( !found)   //return if didnt find the node
	  return;

  if( iter->left == NULL || iter->right == NULL)  //if the node to be deleted has only one child or no child
  {
	  TreeNode* newchild;
	  if( iter->left == NULL)
		  newchild = iter->right;
	  else
		  newchild = iter->left;

	  if(parent==NULL)  //found item in the root
		  root = newchild;
	  else if(parent->left == iter)
		  parent->left = newchild;
	  else
		  parent->right = newchild;
	  return;
  }

  TreeNode* smallpar = iter;
  TreeNode* small = iter->right;
  while( small->left != NULL)
  {
	  smallpar = small;
	  small = small->left;
  }
  iter->data = small->data;
  if( smallpar == iter)
	  smallpar->right = small->right;
  else 
	  smallpar->left = small->right;
}   //change this it can be made much more easier and it should update the parent of a node as well

void TreeNode::insert_node(TreeNode* node)
{
	if(data > node->data )
	{
		if( left == NULL ){
			left = node;
			left->parent = this;
		}
		else 
			left->insert_node(node);
	}
	else if ( data < node->data )
	{
		if(right == NULL) {
			right = node;
			right->parent = this;
		}
		else{
				cerr << "Node" << node->data << " already exists\n";
		}
			
	}
}

bool TreeNode::operator==(const TreeNode t) const {
	return (this->data == t.data) && (this->right == t.right) && (this->left == t.left) && (this->parent == t.parent); 
}

bool TreeNode::find(data_type item) const
{
	if(item < data)
	{
		if( left == NULL)
			return false;
		else 
			return left->find(item);
	}
	else if ( data < item)
	{
		if( right == NULL ) 
			return false;
		else 
			return right->find(item);
	}
	else
		return true;
}

TreeNode* TreeNode::findRec( data_type item) {
	if( this == NULL )
		return this;
	else if( item > this->data && this->right != NULL ) 
		this->right->findRec(item);
	else if( item < this->data && this->left != NULL )
		this->left->findRec(item);
	else if ( this->data == item )
		return this;
	else
		cerr << "Item Not Found" << "\n";
}

void TreeNode::print_preorder() const
{
	if( this == NULL)
		return;
	else
	{
		cout << data << "\n";
		if( left != NULL)
			left->print_preorder();
		if( right != NULL)
			right->print_preorder();
	}
}

void TreeNode::print_inorder() const
{
	if( this == NULL)
		return;
	else
	{
		if( left != NULL)
			left->print_inorder();
		cout << data << "\n";
		if( right != NULL)
			right->print_inorder();
	}
}

void TreeNode::print_postorder() const
{
	if( this == NULL)
		return;
	else
	{
		if( left != NULL)
			left->print_postorder();
		if( right != NULL)
			right->print_postorder();
		cout << data << "\n";
	}
}

void TreeNode::print_preorder_norecur() 
{
	TreeNode* iterator;
	stack<TreeNode*> s;
	s.push(this);
	while ( s.size() > 0)
	{
	  iterator = s.top();
	  cout << iterator->data << "\n";
	  s.pop();
	  if( iterator->right != NULL)
		  s.push(iterator->right);
	  if(iterator->left != NULL)
		  s.push(iterator->left);
	}
}

int TreeNode::nodeHeight() {
	if( this == NULL || (this->right == NULL && this->left == NULL))
		return 0;
	//if( this->right == NULL && this->left == NULL) //This is saying that our tree cannot have external nodes that are NULL and this is bullshit
		//return 0;
	return 1 + max((this->left)->nodeHeight() , (this->right)->nodeHeight());
}

void TreeNode::storeInArray(data_type a[], int& i)
{
	if( this == NULL )
		return;
	if(a != NULL)
		a[i] = this->data;
	i++;
	this->left->storeInArray(a,i);
	this->right->storeInArray(a,i);	
	return;
}

TreeNode* TreeNode::getParent() {
	return this->parent;
}

int TreeNode::nodesCount( int count) 
{
	if( this == NULL)
		return count;
	count++;
	count = left->nodesCount(count);
	count = right->nodesCount( count);
	return count;
}

void TreeNode::storeInHeap(data_type a[], int size, int& i)
{
	if( this == NULL || !(i<size) )
		return;
	if(a != NULL)
		this->data = a[i];
	i++;
	right->storeInHeap(a,size,i);	
	left->storeInHeap(a,size,i);
	//right->storeInHeap(a,size,i);	
	return;
}

data_type TreeNode::getData() const {
	return this->data;
}

void TreeNode::restructAVL(TreeNode* x, TreeNode* y, TreeNode* z) {
	TreeNode* a = new TreeNode();
    *a = *z;
	a->left = x->right;
	y->right = x->left;
	z->data = x->data;
	z->right = a;
}








  




 



	  
