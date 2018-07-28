#include "hsGlobolHeader.h"



/*************START: Detecting Friends Circles*************/
//for each student in the class you have a string “YYNNYN” that says whether that person is friends with each other students or not.
class Gn {
public:
	int id;
	vector<Gn*> friends;

	Gn(int id) {
		this->id = id;
	}

	bool visited = false;
};

bool isCycle(Gn* root) {
	root->visited = true;
	for (Gn* n : root->friends) {
		if (n->visited)
			return true;
		else {
			if (isCycle(n))
				return true;
		}
	}

	return false;
}

int main() {
	Gn* n0 = new Gn(0);
	Gn* n1 = new Gn(1);
	Gn* n2 = new Gn(2);
	Gn* n3 = new Gn(3);
	Gn* n4 = new Gn(4);

	n0->friends.push_back(n1);
	n0->friends.push_back(n2);
	n0->friends.push_back(n3);

	n3->friends.push_back(n4);
	n4->friends.push_back(n2);
	n2->friends.push_back(n0);

	cout << isCycle(n0) << "\n";
}
/***************END: Detecting Friends Circles*************/










//all using adj list:
bool isthreCycleDFS(int v, int par){ //return true if there is a cycle in the graph
	for (auto p = adj[v].begin(); p != adj[v].end(); p++){
		if (!vis[*p]){
			vis[*p] = true;
			if (isthereCycleDFS(*p, v))
				return true;
		}
		else if (*p != par)
			return true;
	}
	return false;
}
void cycleCountDFS(int par, int v, int& count){ //both par and v are called with the node u wanna start from
	for (auto p = adj[v].begin(); p != adj[v].end(); p++){
		if (!vis[*p]){
			vis[*p] = true;
			cycleCountDFS(v, *p, count);
		}
		else if (*p != par)  //This is only needed for undirected graphs
			count++;        
	}
}
bool NoneRecursive-istherePathDFS(int s, int k){ //find vertix k starting from vertix s
	bool* visited = new bool[V];  
	for (int i = 0; i < V; i++)
		visited[i] = false;
	stack<int> st;
	visited[s] = true;
	st.push(s);
	while (!st.empty()){
		int current = st.top();
		st.pop();

		if (current == k) //this is where we process each vertix
			return true;

		for (auto p = adj[current].begin(); p != adj[current].end(); p++){
			if (visited[*p] == false){
				st.push(*p);
				visited[*p] = true;
			}
		}
	}
	return false;
}
int shortestPathBFS(int f, int t){
	queue<int> q;
	int* dep = new int[nn];
	for (int i = 0; i < nn; i++)
		dep[i] = -1;
	dep[f] = 0;  //this is where we are starting from
	q.push(f);
	while (!q.empty()){
		int p = q.front();
		q.pop();
		for (auto pp = adj[p].begin(); pp != adj[p].end(); pp++){
			if (dep[*pp] == -1){
				dep[*pp] = dep[p] + 1;
				q.push(*pp);
				if (*pp == t){
					int temp = dep[*pp];
					delete[] dep;
					return temp;
				}
			}
		}
	}
}
bool istherePathBFS(int s, int d){  // returns true if there is a path from s to d
	if (s == d)
		return true;

	// vertices are integers so create a bool array whose indices represent vertices and whos value is visited or not
	bool* visited = new bool[V];
	for (int i = 0; i < V; i++)
		visited[i] = false;

	list<int> queue;
	visited[s] = true;
	queue.push_back(s);

	while (!queue.empty()){
		s = queue.front();
		queue.pop_front();

		// Get all adjacent vertices of the dequeued vertex s
		for (list<int>::iterator i = adj[s].begin(); i != adj[s].end(); ++i){
			if (*i == d) // this adjacent node is the destination node
				return true;
			if (!visited[*i]){ //else continue BFS
				visited[*i] = true;
				queue.push_back(*i);
			}
		}
	}
	return false;
}

//adj matrix: element i,j represents the number of edges extending from i to j.
class Graph{
	int s;
	bool**adj;
public:
	Graph(int v):s(v){
		adj = new bool*[s];
		for( int i= 0; i < s;i++)
			adj[i] = new bool[s];
		for( int i=0; i < s;i++){
			for(int j=0; j<s;j++)
				adj[i][j] = false;
		}
	}
	~Graph(){
		for( int i=0; i < s;i++)
			delete [] adj[i];
		delete [] adj;
	}
	void edg(int a, int b){
		adj[a][b] = true;
		adj[b][a] = true;
	}
	bool istherePathBFS(int a, int b){
		bool* vis = new bool[s];
		for (int i = 0; i < s; i++)
			vis[i] = false;
		queue<int> q;
		q.push(a);
		vis[a] = true;
		while (!q.empty()){
			int p = q.front();
			q.pop();
			for (int ed = 0; ed < s; ed++){
				if (adj[p][ed]){
					if (!vis[ed]){
						vis[ed] = true;
						if (ed == b)
							return true;
						q.push(ed);
					}
				}
			}
		}
		return false;
	}
	int shortestBFS(int a, int b){//BFS
		int* dist = new int[s];
		for( int i=0; i < s;i++)
			dist[i] = -1;
		dist[a] = 0;
		queue<int> q;
		q.push(a);
		while(!q.empty()){
			int p = q.front();
			q.pop();
			for( int ed = 0; ed < s; ed++){
				if( adj[p][ed] ){
					if( dist[ed] == -1 ){
						dist[ed] = 1 + dist[p];
						if( ed == b ){
							int temp = dist[ed];
							delete [] dist;
							return temp;
						}
						q.push(ed);
					}
				}
			}
		}
		return -1;
	}
	bool isCycleWrapper(int v){
		bool* vis = new bool[s];
		for( int i=0; i < s; i++)
			vis[i] = false;
		bool out = isCycle(v,v,vis);
		delete [] vis;
		return out;
	}
	bool isCycle(int par, int v, bool* vis){
		for (int ed = 0; ed < s; ed++){
			if (adj[v][ed]){
				if (!vis[ed]){
					vis[ed] = true;
					isCycle(v, ed, vis);
				}
				else if (ed != par)
					return true;
			}
		}
		return false;
	}
	int countCycleWrapper(int v){
		bool* vis = new bool[s];
		for( int i=0; i < s; i++)
			vis[i] = false;
		int count = 0;
		countCycle(v,v,vis, count);
		delete [] vis;
		return count;
	}
	void countCycle(int par, int v, bool* vis, int& count){
		for( int ed =0; ed < s; ed++){
			if( adj[v][ed] ){
				if( !vis[ed] ){
					vis[ed] = true;
					countCycle(v,ed,vis,count);
				}
				else if( ed != par )
					count++;
			}
		}
	}
};

//to do the O(1) kev # (by storing kev #s) we have to do a different graph structure:
struct node{
	list<node> adj;
	string name;
	int kevnb;
	node(int a) :name(" "), kevnb(-1){}
};
void addEdg(node n1, node n2){
	n1.adj.push_back(n2);
	n2.adj.push_back(n1);
}
void BFS(node kev, node act){  //this sets the kevnb for all nodes
	kev.kevnb = 0;
	queue<node> s;
	s.push(kev);
	while (!s.empty()){
		auto p = s.front();
		s.pop();
		for (auto pp = p.adj.begin(); pp != p.adj.end(); pp++){
			if (pp->kevnb == -1){
				pp->kevnb = p.kevnb + 1;
				s.push(*pp);
			}
		}
	}
}
int getkev(node n){	return n.kevnb;}

int main(){
	Graph g(6);
	g.edg(0, 1);
	//	g.edg(4,0);
	g.edg(1, 2);
	g.edg(1, 4);
	g.edg(2, 3);
	g.edg(3, 5);
	g.edg(5, 4);
	//	g.edg(5,0);

	cout << g.shortest(0, 5);;
}