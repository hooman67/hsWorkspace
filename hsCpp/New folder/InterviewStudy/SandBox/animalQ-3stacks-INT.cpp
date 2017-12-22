class animal{
public:
	friend class animalQ;
	string name;
	int order;
	animal(string n):name(n){}
};
class cat : public animal{
public:
	cat(animal a):animal(a.name){}
	cat(string n):animal(n){}
};
class dog: public animal{
public:
	dog(animal a):animal(a.name){}
	dog(string n):animal(n){}
};
class animalQ{
	int univOrder;
	queue<cat> qc;
	queue<dog> qd;

public:
	animalQ():univOrder(0){}
	void addAnimal(animal a){
		univOrder++;
		a.order = univOrder;
		if( typeid(a)==typeid(cat) )
			qc.push(cat(a));
		else
			qd.push(dog(a));
	}

	animal deAny(){
		dog d = qd.front();
		cat c = qc.front();

		if( d.order < c.order ){
			qd.pop();
			return d;
		}
		else{
			qc.pop();
			return c;
		}
	}
};


//three stacks on 1 array. variable size
class st{
public:
	int Cap, cap1, cap2, cap3, ind1, ind2, ind3, start1, start2, start3, size1, size2, size3;
	int* arr;
	st(int capa = 9) :Cap(capa){
		cap1 = cap2 = cap3 = Cap / 3;
		start1 = ind1 = 0;
		start2 = ind2 = Cap / 3;
		start3 = ind3 = Cap - 1;
		size1 = size2 = size3 = 0;
		arr = new int[Cap];
		for (int i = 0; i < Cap; i++)
			arr[i] = 0;
	};
	~st(){
		delete[] arr;
	};
	void push(int nb, int item){
		if (nb == 0){
			if (size1 == 0){
				arr[ind1] = item;
				size1++;
			}
			else{
				if (++ind1 >= start1 + cap1)
					grow(0);
				arr[ind1] = item;
				size1++;
			}
		}
		else if (nb == 1){
			if (size2 == 0){
				arr[ind2] = item;
				size2++;
			}
			else{
				if (++ind2 >= start2 + cap2)
					grow(1);
				arr[ind2] = item;
				size2++;
			}
		}
		else{
			if (size3 == 0){
				arr[ind3] = item;
				size3++;
			}
			else{
				if (--ind3 <= start3 - cap3)
					grow(2);
				arr[ind3] = item;
				size3++;
			}
		}
	};

	void grow(int nb){
		if (nb == 0){
			if (size2 >= cap2 && size3 >= cap3)
				cerr << "full";
			else{
				shiftR(start2, ind2, start3 - size3);
				cap3 = size3;
				start2 = Cap - size3 - size2;
				cap2 = size2;
				cap1 = start2;
			}
		}
		else if (nb == 1){
			if (ind1 >= cap1 - 1 && size3 >= cap3)
				cerr << "full";
			else{
				shiftL(start2, ind1 + 1, ind2);
				start2 = ind1 + 1;
				ind2 = start2 + size2;
				cap2 = Cap - size1 - size3;
				cap1 = size1;
				cap3 = size3;
			}
		}
		else{
			if (ind1 >= cap1 - 1 && size2 >= cap2)
				cerr << "full";
			else{
				shiftL(start2, ind1 + 1, ind2);
				start2 = ind1 + 1;
				ind2 = start2 + size2;
				cap2 = size2;
				cap1 = size1;
				cap3 = Cap - size1 - size2;
			}
		}
	};

	void shiftR(int start, int ind, int end){
		for (int i = ind; i >= start; i--)
			arr[end--] = arr[i--];
	};

	void shiftL(int start, int startn, int ind){
		for (int i = start; i <= ind; i++){
			arr[startn++] = arr[i];
		}
	};
};
int main(){
	st my;
	my.push(0, 3);
	my.push(0, 4);
	my.push(1, 1);
	my.push(1, 2);
	my.push(2, 8);
	my.push(2, 9);
	my.push(2, 10);
	my.push(2, 11);

	for (int i = 0; i <9; i++)
		cout << my.arr[i] << "   ";
	cout << "\n";
	//cout << my.cap1 << "\n";
}





