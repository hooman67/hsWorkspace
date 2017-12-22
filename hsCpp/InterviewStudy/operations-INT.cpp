
//Using only add: implement subtraction, multiplications and devision of integers 
int negate(int a){ //returns the negative of a
	int p, neg = 0;
	p = a < 0 ? 1 : -1;
	while (a != 0){
		neg += p;
		a += p;
	}
	return neg;
}
int subt(int a, int b){ //returns a - b
	return a + negate(b);
}
int mult(int a, int b){ //return a*b
	int p = 0;
	int flag = true;
	if (b < 0){
		b = negate(b);
		flag = false;
	}
	for (int i = 0; i < b; i++)
		p += a;
	return flag ? p : negate(p);
}
int dev(int a, int b){//returns a/b as long as a>b because this is integer divsions
	bool flag = true;
	if (a < 0){
		a = negate(a);
		flag = !flag;
	}
	if (b < 0){
		b = negate(b);
		flag = !flag;
	}

	if (a >= b){
		int p = 0, i = 0;
		while ((p += b) <= a)
			i++;
		return flag ? i : negate(i);
	}
}

//implement the arithmatic operators using bitManipulation
int Add(int a, int b){
	while (b){
		int carry = a & b;
		a = a ^ b;
		b = carry << 1;
	}
	return a;
}
int neg(int x) {
	return Add(~x, 1);
}
int subtract(int x, int y){
	return Add(x, neg(y));
}
int is_even(int n) {
	return !(n & 1);
}
int divide_by_two(int n) {
	return n >> 1;
}
int multiply_by_two(int n) {
	return n << 1;
}
int multiply(int x, int y) {
	int result = 0;
	if (x < 0 && y < 0) {
		return multiply(neg(x), neg(y));
	}
	if (x >= 0 && y < 0) {
		return multiply(y, x);
	}
	while (y > 0) {
		if (is_even(y)) {
			x = multiply_by_two(x);
			y = divide_by_two(y);
		}
		else {
			result = Add(result, x);
			y = Add(y, -1);
		}
	}

	return result;
}
