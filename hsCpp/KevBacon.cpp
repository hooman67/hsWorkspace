#include <cstdlib>
#include <iostream>
#include <stack>
#include <unordered_set>
#include <queue>
#include <string>
using namespace std;

class Node{
public:
	string name;
	unordered_set<Node*>* conn;
	bool visited;
	string par;  
	int bacon;  //using bacon number we do not need the visited or par fields anymore
	Node(string a):name(a),visited(false),par(""),bacon(-1) {
		conn = new unordered_set<Node*>;
	};

	void addEdge(Node* n){ //we create a link from each node to the other because we want an undirected graph.
		this->conn->insert(n);  //undirected graph
		n->conn->insert(this);
	};

	//This does not work.
	int BFS(Node* kev){
		queue<Node*> q;
		q.push(kev);
		int i = 0;
		string temp = "rand";
		while( !q.empty() ){
			Node* p = q.front();
			q.pop();
			p->visited = true;
	
			if( p->par != temp ){ //counts the length of the shortes path
				temp = p->par;
				i++;
			}
			cout << p->name << "\n";   //just chekcing 

			for(unordered_set<Node*>::iterator pp = p->conn->begin();(!p->conn->empty() && pp!= p->conn->end()); pp++){
					if( (*pp)->visited == false){
						(*pp)->par = p->name;              //a counter here will give u the number of edges in the graph
						(*pp)->visited = true;
						q.push(*pp);
						if( (*pp)->name == this->name )
							return i;
					}
				}
		}
		return -1;
	};

	//pre processing using bacon number. we can traverse the entire graph reachable from keving bacon node once and store for each 
	//node its bacon number . once this preprocessing is done we can return the bacon number of each node in O(1)

	//this method must only be called on the keving bacon node because it calculates the distance of every node from the node it is called on
	void bfsTravers(){
		queue<Node*> q;
		q.push(this);
		this->bacon = 0;
		while( !q.empty() ){
			Node* p = q.front();
			q.pop();
			int parBac = p->bacon;
			for( unordered_set<Node*>::iterator pp = p->conn->begin(); pp != p->conn->end();pp++){
				if( (*pp)->bacon == -1){
					q.push(*pp);
					(*pp)->visited = true;
					(*pp)->bacon = parBac +1;
				}
			}
		}
	};
	int LengthOfShortestPathFromKevBacon(){return this->bacon;}; 

  
	~Node(){
		conn->empty();
		delete conn;
	};
};

int main(){
	Node* k = new Node("kevin");
	Node* n0 = new Node("mak");
	Node* n2 = new Node("nak");
	Node* n1 = new Node("pak");
	Node* n3 = new Node("jak");
	Node* n4 = new Node("lak");
	Node* n5 = new Node("sak");
	Node* n6 = new Node("po");
	Node* n7 = new Node("bo");

	k->addEdge(n0);
	n0->addEdge(n2);
	n2->addEdge(n1);
	n2->addEdge(n3);
	n3->addEdge(n4);
	n1->addEdge(n5);
	n0->addEdge(n6);
	n0->addEdge(n7);

	cout << n4->BFS(k) << "\n";

	delete k;
	delete n0;
	delete n1;
	delete n2;
	delete n3;
	delete n4;
	delete n5;
	delete n6;
	delete n7;
}



	int DFS(){  
		stack<int> s;
		int* dep = new int[nn];
		for( int i=0; i < nn; i++)
			dep[i] = -1;
		s.push(0);
		dep[0] = -2;
		int cycleCount = 0;
		while( !s.empty() ){
			int p = s.top();
			s.pop();
			for( auto pp = adj[p].begin(); pp!=adj[p].end();pp++){
				if(dep[*pp] == -1 ){
					dep[*pp] = p;
					s.push(*pp);
				}
				else if( dep[p] != *pp && dep[p] != -3){
					cycleCount++;
					dep[*pp] = -3;
					cout << *pp << "par=   "<< p << "\n";
				}
			}
		}
		delete [] dep;
		return cycleCount;
	}







