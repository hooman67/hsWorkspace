#include <sstream>
#include <fstream>
#include <iostream>
#include <string>
#include <vector>
#include <queue>
#include <functional>
#include <stack>
#include <memory>
#include <list>
#include <map>


using namespace std;

template <class T>
class CandyCrush{
public:
	list<T>* cursh(T* ar, int len, int crushingThreshold);
};

template <class T>
list<T>*  CandyCrush<T>::cursh(T* ar, int len, int crushingThreshold){
	stack<pair<T, int>>* st = new stack<pair<T, int>>();
	int i = 0;
	while (i < len){
		if (st->empty()){
			st->push(pair<char, int>(ar[i], 1));
			i++;
		}
		else if (st->top().first == ar[i]){
			st->top().second++;
			i++;
		}
		else{
			if (st->top().second >= crushingThreshold){
				st->pop();
			}
			else{
				st->push(pair<char, int>(ar[i], 1));
				i++;
			}
		}
	}

	list<T>* ls = new list<T>();
	while (!st->empty()){
		ls->push_back(st->top().first);
		st->pop();
	}

	delete st;

	return ls;
}


int main(){
	CandyCrush<char>* cc = new CandyCrush<char>();

	char ar[] = { 'a', 'b', 'b', 'c', 'c', 'c', 'b', 'd', 'd', 'd', 'c' };
	list<char>* ls = cc->cursh(ar, 11, 3);

	list<char>::reverse_iterator itr = ls->rbegin();
	while (itr != ls->rend()){
		cout << *itr << "\n";
		itr++;
	}


	delete cc;
	delete ls;
}
