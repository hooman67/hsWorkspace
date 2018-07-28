#include "iostream"
#include<vector>
#include<list>
#include<stack>
#include<algorithm>

using namespace std;

/************ START: Make a Contiguous chaing of pairs ************/
/**
* Given a jumbled collection of segments, each of which is represented as
* a Pair(startPoint, endPoint), this function sorts the segments to
* make a continuous path.
*
* A few assumptions you can make:
* 1. Each particular segment goes in one direction only, i.e.: if you
* see (1, 2), you will not see (2, 1).
* 2. Each starting point only have one way to the end point, i.e.: if
* you see (6, 5), you will not see (6, 10), (6, 3), etc.
*
* For example, if you're passed a list containing the following int arrays:
*      [(4, 5), (9, 4), (5, 1), (11, 9)]
* Then your implementation should sort it such:
*      [(11, 9), (9, 4), (4, 5), (5, 1)]
*/
void mergePairs(vector<pair<int, int>>& a, vector<pair<int, int>>& b, vector<pair<int, int>>& c){
	if (a.size() == 0 || b.size() == 0)
		return;
	if (a.back().second == b.front().first){
		for (auto ap = a.begin(); ap != a.end(); ap++)
			c.push_back(*ap);

		for (auto bp = b.begin(); bp != b.end(); bp++)
			c.push_back(*bp);
	}
	else if (b.front().second == a.back().first){
		for (auto bp = b.begin(); bp != b.end(); bp++)
			c.push_back(*bp);

		for (auto ap = a.begin(); ap != a.end(); ap++)
			c.push_back(*ap);
	}
	else{
		return;
	}
}
void divideAndMergePairs(vector<pair<int, int>>& v){
	if (v.size() <= 1)
		return;

	vector<pair<int, int>> a, b;

	for (int i = 0; i < v.size() / 2; i++)
		a.push_back(v[i]);

	for (int i = v.size() / 2; i < v.size(); i++)
		b.push_back(v[i]);

	v.clear();
	divideAndMergePairs(a);
	divideAndMergePairs(b);
	mergePairs(a, b, v);
}
void connectPairs(vector<pair<int, int>>& in){
	vector<pair<int, int>> v;

	while (v.size() == 0){
		random_shuffle(in.begin(), in.end());

		for (pair<int, int> i : in)
			v.push_back(i);

		divideAndMergePairs(v);
	}

	in.clear();
	for (pair<int, int> i : v)
		in.push_back(i);
}
/************** END: Make a Contiguous chaing of pairs ************/


/************ START: Quick Sort, b is vector.size() ************/
int partLg(vector<int>& v, int pivInd, int beg, int end){
	int piv = v[pivInd];
	swap(v[pivInd], v[end]);

	int larger = 0, p = 0;
	while (p < end){
		if (v[p] > piv)
			swap(v[p++], v[larger++]);
		else
			p++;
	}

	swap(v[end], v[larger]);

	return larger;
}
void quickSort_myVer(vector<int>& v, int st, int end){
	if (end <= st)
		return;

	int pivInd = st;

	int newPivInd = partLg(v, pivInd, st, end);

	quickSort_myVer(v, st, newPivInd);
	quickSort_myVer(v, newPivInd + 1, end);
}
void quickSort_OldVersion(vector<int>& v, int a, int b) { //worst case O(n^2) av,best O(nlogn) MEM O(1) this is inplace els O(n)
	if (a >= b) return; // 0 or 1 left? done
	int pivot = v[b]; // select last element  as pivot
	int l = a; // left edge
	int r = b - 1; // start from the element to the left of the last element
	while (l <= r) { //the two have not crossed yet
		while (l <= r && !(pivot < v[l]))  // scan right to find sth that is larger than pivot
			l++; // you must put the =  in <= or the whole thing doesnt work
		while (r >= l && !(v[r] < pivot)) // scan left to find sth that is smaller than pivot
			r--;// you must put the =  in <= or the whole thing doesnt work
		if (l < r) // the two havent crossed yet
			swap(v[l], v[r]);
	} // the two indices have crossed and we have exited the loop
	swap(v[l], v[b]); // swap with pivot to put the pivot almost at the middle of the list ( l )
	quickSortHelper(v, a, l - 1); // recur on both sides
	quickSortHelper(v, l + 1, b);
}
/************** END: Quick Sort, b is vector.size() ************/


/****************** START: Merge Sort ******************/
template<class T>
void merge(list<T>& a, list<T>& b, list<T>& c){
	auto ap = a.begin(), bp = b.begin();

	while (ap != a.end() && bp != b.end()){
		if ((*ap) < (*bp))
			c.push_back(*ap++);
		else
			c.push_back(*bp++);
	}

	while (ap != a.end())
		c.push_back(*ap++);

	while (bp != b.end())
		c.push_back(*bp++);
}
template<class T>
void mergeSort(list<T>& in){
	if (in.size() <= 1)
		return;

	list<T> a, b;
	auto p = in.begin();

	for (int i = 0; i < in.size() / 2; i++)
		a.push_back(*p++);

	for (int i = in.size() / 2; i < in.size(); i++)
		b.push_back(*p++);


	in.clear();
	mergeSort(a);
	mergeSort(b);
	return merge(a, b, in);
}
/******************** END: Merge Sort ******************/


void insertionSort(vector<int>& v ){ //best O(n) array already sorted almost. worst,av O(n^2), inplace mem O(1)
	for(int i = 1; i < v.size(); i++){
		int temp = v[i];
		int j = i-1;
		while( j >= 0 && v[j] > temp ){
			v[j+1] = v[j];   //move v[j] (element before temp ) right
			j--;
		}
		v[j+1] = temp;
	}
}

void radixSort( int* arr, int n){ //n is size of the array.kpasses O(nk) (faststs) mem O(kn).no compar(why bett than nlog n possible)
	int bucket[10][20], buck_count[10], b[10];
    int i,j,k,r,no_of_passes=0,divisor=1,largest,pass_no;

    largest=arr[0];

    for(i=1;i<n;i++){  //find the largst number
		if(arr[i] > largest)
            largest=arr[i];
	}

    while(largest > 0)  //Find number of digits in largest number
    {
        no_of_passes++;
        largest /= 10;
    }

    for(pass_no=0; pass_no < no_of_passes; pass_no++){
        for(k=0; k<10; k++){
            buck_count[k]=0; //Initialize bucket countfor(i=0;i<n;i++){
            r=(arr[i]/divisor) % 10;
            bucket[r][buck_count[r]++]=arr[i];
        }
        i=0; //collect elements from bucket
		for(k=0; k<10; k++){
            for(j=0; j<buck_count[k]; j++)
                arr[i++] = bucket[k][j];
        }

        divisor *= 10;
    }
}

int findMinIndex( int arr[], int start, int ArrLength) {
	int min = start;
	for(int i = start +1; i<ArrLength; i++){
		if( arr[i] < arr[min])
			min = i;
	}
	return min;
}
void selection(int arr[], int index, int length) {//   Sort from after a given index O(n^2) mem:O(1) ALL CASES
	int key = arr[index];
	swap(arr[index], arr[findMinIndex(arr,index,length)]);
	if( index <length-1)
	selection( arr, (index+1), length);
	else 
		return;
}
int findMinIndexVect( vector<int>& v, int index ){
	int temp = v[index];
	int out = index;
	for( int i = index+1; i< v.size(); i++ ){
		if( v[i] < temp ){
			temp = v[i];
			out = i;
		}
	}
	return out;
}
void selection( vector<int>& v, int index ){////   Sort from after a given index O(n^2) mem:O(1) ALL CASES
	if( index >= v.size() )
		return;
	swap(v[index], v[findMinIndexVect(v,index)]);
	selection(v, ++index);
}

void bubbleSort( vector<int>& v){ //all cases O(n^2)   mem O(1)
	for (int i = 0; i < v.size(); i++){
		for (int j = 0; j < v.size() -1; j++){
			if (v[j] > v[j + 1])
				swap( v[j], v[j+1]);
		}    
	}
}















