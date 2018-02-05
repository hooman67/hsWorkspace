#include <iostream>
#include<stack>

using namespace std;





template<class T>
class maxStack{
	stack<T> st;
	stack<pair<T, int>> max;

public:
	void push(T data){
		st.push(data);
		if (max.empty() || max.top().first < data)
			max.push(make_pair(data, 1));
		else if (max.top().first == data)
			max.top().second++;
	}

	T pop(){
		T ret = st.top();
		st.pop();
		if (max.top().first == ret){
			max.top().second--;

			if (max.top().second <= 0)
				max.pop();
		}

		return ret;
	}

	T getMax(){
		return max.top().first;
	}
};

template<class T>
stack<T> sortWithStack(stack<T> st){
	stack<T> ns;

	while (!st.empty()){
		if (ns.empty() || st.top() >= ns.top()){
			ns.push(st.top());
			st.pop();
		}
		else{
			T temp = st.top();
			st.pop();

			while (!ns.empty() && ns.top() > temp){
				st.push(ns.top());
				ns.pop();
			}

			ns.push(temp);
		}
	}

	return ns;
}




int main(){
	stack<int> s;
	s.push(5);
	s.push(4);
	s.push(1);
	s.push(3);
	s.push(2);

	s = sortWithStack(s);

	while (!s.empty()){
		cout << s.top() << "\n";
		s.pop();
	}

}