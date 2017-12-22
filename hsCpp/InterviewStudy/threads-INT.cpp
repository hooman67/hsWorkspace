
//consumter/producer with an int array as buffer
class BoundedBuffer {
	int* buffer;
	int capacity;

	int front;
	int rear;
	int count;

	std::mutex lock;

	std::condition_variable not_full;
	std::condition_variable not_empty;

	BoundedBuffer(int capacity) : capacity(capacity), front(0), rear(0), count(0) {
		buffer = new int[capacity];
	}

	~BoundedBuffer(){
		delete[] buffer;
	}

	void deposit(int data){
		std::unique_lock<std::mutex> l(lock);

		not_full.wait(l, [this](){return count != capacity; });

		buffer[rear] = data;
		rear = (rear + 1) % capacity;
		++count;

		not_empty.notify_one();
	}

	int fetch(){
		std::unique_lock<std::mutex> l(lock);

		not_empty.wait(l, [this](){return count != 0; });

		int result = buffer[front];
		front = (front + 1) % capacity;
		--count;

		not_full.notify_one();

		return result;
	}
};
void consumer(int id, BoundedBuffer& buffer){
	for (int i = 0; i < 50; ++i){
		int value = buffer.fetch();
		std::cout << "Consumer " << id << " fetched " << value << std::endl;
		std::this_thread::sleep_for(std::chrono::milliseconds(250));
	}
}
void producer(int id, BoundedBuffer& buffer){
	for (int i = 0; i < 75; ++i){
		buffer.deposit(i);
		std::cout << "Produced " << id << " produced " << i << std::endl;
		std::this_thread::sleep_for(std::chrono::milliseconds(100));
	}
}
int main(){
	BoundedBuffer buffer(200);

	std::thread c1(consumer, 0, std::ref(buffer));
	std::thread c2(consumer, 1, std::ref(buffer));
	std::thread c3(consumer, 2, std::ref(buffer));
	std::thread p1(producer, 0, std::ref(buffer));
	std::thread p2(producer, 1, std::ref(buffer));

	c1.join();
	c2.join();
	c3.join();
	p1.join();
	p2.join();

	return 0;
}

//consumer/producer with just a single string as buffer (which is either empty of has a massage)
class ms{
	mutex mt;
	condition_variable empt, ful;
	string mas;
public:
	ms() :mas(""){}
	static bool isEmpt(string a){ return a.empty(); }
	static bool isFul(string a){ return !a.empty(); }
	void put(string a){
		unique_lock<mutex> lk(mt);
		while (!mas.empty())
			ful.wait(lk);
		mas = a;
		empt.notify_one();
	}
	void rec(){
		unique_lock<mutex> lk(mt);
		while (mas.empty())
			empt.wait(lk);
		cout << mas << "\n";
		mas.clear();
		ful.notify_one();
	}
};
void senders(int id, ms& ser){
	for (int i = 1; i < 11; i++){
		string temp = "put# " + to_string(i) + " by thread " + to_string(id);
		ser.put(temp);
	}
}
void receivers(int id, ms& ser, mutex& mm){
	for (int i = 1; i < 11; i++){
		mm.lock();
		cout << "reciever" << id << " got ";
		ser.rec();
		mm.unlock();
	}
}
int main(){
	vector<thread> v;
	mutex mm;
	ms server;
	for (int i = 1; i < 6; i++)
		v.push_back(thread(senders, i, ref(server)));
	for (int i = 1; i < 6; i++)
		v.push_back(thread(receivers, i, ref(server), ref(mm)));
	for (int i = 0; i < v.size(); i++)
		v[i].join();
}


//DINING PHYLOSOPHERS:
/pick up odd first
//sol3:number the chopsticks CCW : phils pick up the odd nb chpst first, then the even bnrd, if unavailabe they dont drop the chpst
//this is completely fair for even nb of phils and gets fairere as we have more phils for odd nb of phils
void f1(string name, mutex& a, mutex& c){
	while (1){
		a.lock();
		c.lock();
		cout << name << " is eating\n";
		//		_sleep(100);
		c.unlock();
		a.unlock();
	}
}
void f2(string name, mutex& b, mutex& a){
	while (1){
		a.lock();
		b.lock();
		cout << name << " is eating\n";
		//		_sleep(100);
		b.unlock();
		a.unlock();
	}
}
void f3(string name, mutex& c, mutex& b){
	while (1){
		c.lock();
		b.lock();
		cout << name << " is eating\n";
		//		_sleep(100);
		b.unlock();
		c.unlock();
	}
}
int main(){
	mutex a, b, c;
	thread t1(f1, "phil 1", ref(a), ref(c)), t2(f2, "phil 2", ref(b), ref(a)), t3(f3, "phil 3", ref(c), ref(b));
	t1.join();
	t2.join();
	t3.join();
}

/pick up lower number first
//sol2: number the chopsticks in CCW direc : pick up lower numbered chp first, 
//then try for the otherone. Do not drop the first one if the second one is unavailabe.  This has no locks but is not that fair.
void f1(string name, mutex& a, mutex& c){
	while (1){
		a.lock();
		c.lock();
		cout << name << " is eating\n";
		//		_sleep(100);
		c.unlock();
		a.unlock();
	}
}
void f2(string name, mutex& b, mutex& a){
	while (1){
		a.lock();
		b.lock();
		cout << name << " is eating\n";
		//		_sleep(100);
		b.unlock();
		a.unlock();
	}
}
void f3(string name, mutex& c, mutex& b){
	while (1){
		b.lock();
		c.lock();
		cout << name << " is eating\n";
		//		_sleep(100);
		c.unlock();
		b.unlock();
	}
}
int main(){
	mutex a, b, c;
	thread t1(f1, "phil 1", ref(a), ref(c)), t2(f2, "phil 2", ref(b), ref(a)), t3(f3, "phil 3", ref(c), ref(b));
	t1.join();
	t2.join();
	t3.join();
}

//sol1 : puck up right, if left unavailable, drop right and start again. This will create a livelock and is unfair.
void f1(string name, mutex& a, mutex& c){
	while (1){
		a.lock();
		if (!c.try_lock())
			a.unlock();
		else{
			cout << name << " is eating\n";
			//		_sleep(100);
			c.unlock();
			a.unlock();
		}
	}
}
void f2(string name, mutex& b, mutex& a){
	while (1){
		b.lock();
		if (!a.try_lock())
			b.unlock();
		else{
			cout << name << " is eating\n";
			//		_sleep(100);
			a.unlock();
			b.unlock();
		}
	}
}
void f3(string name, mutex& c, mutex& b){
	while (1){
		c.lock();
		if (!b.try_lock())
			c.unlock();
		else{
			cout << name << " is eating\n";
			//		_sleep(100);
			b.unlock();
			c.unlock();
		}
	}
}
int main(){
	mutex a, b, c;
	thread t1(f1, "phil 1", ref(a), ref(c)), t2(f2, "phil 2", ref(b), ref(a)), t3(f3, "phil 3", ref(c), ref(b));
	t1.join();
	t2.join();
	t3.join();
}

//problem statement impl: pick up right then left
void f1(string name, mutex& a, mutex& c){
	while (1){
		a.lock();
		c.lock();
		cout << name << " is eating\n";
		//		_sleep(100);
		c.unlock();
		a.unlock();
	}
}
void f2(string name, mutex& b, mutex& a){
	while (1){
		b.lock();
		a.lock();
		cout << name << " is eating\n";
		//		_sleep(100);
		a.unlock();
		b.unlock();
	}
}
void f3(string name, mutex& c, mutex& b){
	while (1){
		c.lock();
		b.lock();
		cout << name << " is eating\n";
		//		_sleep(100);
		b.unlock();
		c.unlock();
	}
}
int main(){
	mutex a, b, c;
	thread t1(f1, "phil 1", ref(a), ref(c)), t2(f2, "phil 2", ref(b), ref(a)), t3(f3, "phil 3", ref(c), ref(b));
	t1.join();
	t2.join();
	t3.join();
}