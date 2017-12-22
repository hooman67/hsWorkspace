#include "hsGlobolHeader.h"
//this is a much quicker implementation:
void move2(stack<int>& a, stack<int>& b){
	if( !a.empty() && (b.empty() || a.top() < b.top())){
	b.push(a.top());
	a.pop();
	}
}
void solve(stack<int>& orig,stack<int>& buff, stack<int>& dest, int n){
	if( n > 0 ){
		solve(orig,dest,buff,n-1);
		move2(orig,dest);
		solve(buff,orig,dest,n-1);
	}
}


class Tower {
public:
	stack<int> disks;
	int index; //simply identifies which tower we are talking about

	Tower(int i = 0 ) :index(i){}

    void add(int d) {
		if( !disks.empty() && disks.top() <= d)
			cerr << "couldnt place disk" <<"\n";
		else 
			disks.push(d);
	}

	void moveTop2(Tower& t) {  
		if( !(disks.empty())){
		int top = disks.top();
		disks.pop();
		t.add(top);
		}
		else cerr << "coult not moveTop2" << "\n";
	}

	void moveDisks(int n, Tower& destination, Tower& buffer) { //doesnt matter what n is as long as its >= to number of lists
		if( n>0 ){
			moveDisks(n-1, buffer, destination);
			moveTop2(destination);
			buffer.moveDisks(n-1,destination,*this);
		}
	}
};

/*Driver, Main():
Tower t1;
	Tower t2;
	Tower t3;
	t1.add(3);
	t1.add(2);
	t1.add(1);
	t1.moveDisks(5,t3,t2);
	while( !t3.disks.empty() ){
		cout << t3.disks.top() << "\n";
		t3.disks.pop();
	} */
