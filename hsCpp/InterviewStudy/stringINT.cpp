#include "hsGlobolHeader.h"
//s.size() doestn count the NULL. So u have to use char*. to even see the null. Then to not flip it u have to use 
// i < (length of char array - 1 )/2   and index [length-2-i]
void revChar( string& s, int st, int en ){  //Reverse the chars in a string from st to en (en is length not index (its index of the last elem + 1) )
	for( int i = st; i < (st+en)/2; i++ ){
		char temp = s[i];
		s[i] = s[(en+st-1)-i];
		s[(en+st-1)-i] = temp;
	}
}
void revWord(string& s){//Reverse the words in a string.First reverse the characters then reverse the characters in each word.USES FUNC ABOVE  
	revChar(s,0,s.length());
	for( int i =0; i < s.size(); i++ ){
		int j = i;
		while(i < s.size() && s[i] != ' ' )
			i++;
		revChar(s,j,i);
	}
}

int Str2Int(string s){
	int out = 0;
	int a = 0;
	if( s[0] == '-' )
		a = 1;
	for(int i = a;i<s.size();i++){
		out += pow(long double(10),int(s.size()-1-i))*(int(s[i])-48);
	}
	if( a==1 )
		return -1*out;
	else
	return out;
}
string Int2Str(int a ){  //this had a bug, i fixed it if its  a > 1  then u cant do 10 or 100 or 1000; it has to be a>= 1;  also case a ==0 was not included before so i added it
	string out;
	if (a == 0){
		out = '0';
		return out;
	}
	else if( a > 0 ){
		while( a >= 1 ){
			out += char(a%10+48);
			a = (a - a%10)/10;
		}
		for( int i =0; i < out.length()/2; i++ ){
			char temp = out[i];
			out[i] = out[out.length()-1-i];
			out[out.length()-1-i] = temp;
		}
	}
	else{
		a = a*-1;
		while( a >= 1 ){
			out += char(a%10+48);
			a = (a - a%10)/10;
		}
		out += '-';
		for( int i =0; i < out.length()/2; i++ ){
			char temp = out[i];
			out[i] = out[out.length()-1-i];
			out[out.length()-1-i] = temp;
		}
	}
	return out;
}
string RemCharsInBfromA( string a, string b){ // O(n) runt time  and memory (in place) so O(1)
	int des = 0;                             //Remove the chars given in b from a
	unordered_map<char,int> my;
	for( int i =0; i < b.length();i++)
		my.insert(make_pair(b[i],1));
	for( int i=0; i < a.length(); i++ ){
		if(my[a[i]]!=1)
			a[des++]= a[i];			
			
	}
	return a.substr(0,des);
}
char FirstUniqChar(string s){ //O(n)  Returns the first none repeated char in a string 
	unordered_map<char,int> my;   //can use arrays for this too if we use the ascii code of each char as index. 
	for( int i = 0; i<s.length(); i ++ ){  // but we have to allocated a huge array that can take every char.
		if( !(my.insert(make_pair(s[i],0)).second))
			my[s[i]]++;
	}
	for( int i = 0; i<s.length();i++ ){
		if( my[s[i]] == 0 )
			return s[i];
	}
}

//print all the permutations (any length) of a given string: uses myf() which is similar to perm and nChoseK
void allPermutations(string in){
	for (int i = 1; i < in.size() + 1; i++){
		string out;
		myf(0, i, in, out);
	}
}
//print permutations of string of the same length
void perm(string in, string& out, bool* used){
	if (out.size() == in.size()){
		cout << out << "\n";
		return;
	}
	for (int i = 0; i < in.size(); i++){
		if (!used[i]){
			out += in[i];
			used[i] = true;
			perm(in, out, used);
			used[i] = false;
			out = out.substr(0, out.size() - 1);
		}
	}
}
void myf(int start, int k, string in, string out){ //print selections of k chars from the string
	for (int i = start; i < in.size(); ++i){
		out += in[i];
		if (out.size() == k){
			bool a[5];
			for (int i = 0; i < 5; i++)
				a[i] = false;
			string out1;
			//	cout << out << "\n";
			perm(out, out1, a);
		}
		if (i < in.size())
			myf(i + 1, k, in, out);
		out = out.substr(0, out.size() - 1);
	}
}

//exact same function as above
void permV2(string s, int curr){//call the func with curr == 0
	if (curr == s.size() - 1)
		cout << s << "\n";
	else{
		for (int i = curr; i<s.size(); i++){
			swap(s[curr], s[i]);
			permV2(s, curr + 1);
			swap(s[curr], s[i]);
		}
	}
}
//print choices of length k
void nChoseK(int start, int k, string in, string out){ //print selections of k chars from the string
	for (int i = start; i < in.size(); ++i){
		out += in[i];
		if (out.size() == k)
			cout << out << "\n";
		if (i < in.size())
			nChoseK(i + 1, k, in, out);
		out = out.substr(0, out.size() - 1);
	}
}
//print choices of all lengths
void select(int start, string in, string out){ //select any # of chars from string in
	for (int i = start; i < in.size(); ++i){
		out += in[i];
		cout << out << "\n";
		if (i < in.size())
			select(i + 1, in, out);
		out = out.substr(0, out.size() - 1);
	}
}
