#include <bitset> 
//general:
//1.	u access a bit by & it with 1
//2.	you clear a bit by & it with 0.
//3.	You set a bit by | it with 1;
//4.	Shift left by n to multiply by 2 ^ n
//5.	Shift right by n to divide by 2 ^ n
//6.	Negs are stored as second compl of their positive counter parts
//7.	To get the compl (negative) of a positive number: flip all its bits (~ negate it) and then add 1 to it.
//bit numbers start from 0
int updateBit(int num, int i, int v){
	num & ~(1 << i);  //this clears the bit at i
	return num | (v << i);  // v<<i  has the value v at i  and 0 everywhere else
}
bool getBit(int num, int i){
	int mask = 1;
	mask = mask < i;
	int result = num & mask;
	return (result != 0);
}
/ToClear the most sig bits to ith bit(including bit i) : A & ((1 << I) - 1);
/To clear bits i through 0 (inclusive) : A & ~((1 << (i + 1)) – 1);
/ X & (~0 << n) clears the n right most bits in x

/a ^ ~a = all ones
/ X ^ 1s = ~x
/ X ^ 0s = X;
/X^X = 0;

/IsPowerOf2: ((n & (n-1)) == 0) Returns 1 if n is a power of 2 or 0  and returns o otherwise
/(a==NULL) != (b==NULL) does logical XOR we do not have ^^ only bitwise ^

int putB_inA( int a, int b, int i, int j){//insert number B into A at the bit starting at j and ending at i 
	short int mask = ~(((1 << 3)-1) << i);
	short int out = (b & mask ) | (a << i);
	return out;
}
int swapOddEvenBits(int x) { //swap the odd bits of a number with its even bits
	return ( ((x & Oxaaaaaaaa) » 1) | ((x & 0x55555555) « 1) );
}
bool isLittleEndian(){//In a big-endian machine the MSB has the lowest address; in a little-endian machine the LSB has the
lowest address.
int testNum;
char *ptr;                  
testNum = 1;              // casting an integer pointer into char changes the size of the data it points to from an 
ptr = (char *) &testNum; //int(several bytes ) to a char which is only one byte.
return (*ptr); /* Returns the byte at the lowest address */
}
int numOnesInBinary( int number ) {//return the number of bits that are = 1 in the bin representation of a number O(n), n nb of bits
	int numOnes = 0;
	while( number != 0 ){
		if( ( number & 1 ) == 1 ) {
			numOnes++;
		}
		number = number >>> 1;
	}
	return numOnes;
}
int numOnesInBinary1( int number ){ //O(m)  m: = number of ones
	int numOnes = 0;
	while( number != 0 ){
		number = number & (number – 1);
		numOnes++;
	}
	return numOnes;
}
string printBinary(double num){
	if (num >= 1 || num <= 0) {
		return "ERROR";
	}
	string binary;
	binary += '.';
	while (num > 0) {
		/* Setting a limit on length: 32 characters */
		//if (binary.size() >= 32) {
		//	return "ERROR";
	//	}
		double r = num * 2;
		if (r >= 1) {
			binary += '1';
			num = r - 1;
		} else {
			binary += '0';
			num = r;
		}
	}
 return binary;
}
int bitsRequired(int a, int b) { //Number of bits required to convert a  to b 
	int count = 0;
	for (int c = a ^ b ; c != 0 ; c = c & (c-1)) {
		count++;
	}
	return count;
}

//int findMissingInt pg 264
//void DrawLine pg266

//use bitset<a>m(b) to do binary operations. a is the number of bits u wanna use to represent b.
// cout << m;  prints b in binary.   use m[i] to access individual bits.
short int a = 5;
bitset<10> m(a);    //m takes the binary representation of a using 10bit
cout << m << "\n";  //use m[i] to access individual bits




