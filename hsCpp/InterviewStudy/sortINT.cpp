#include "hsGlobolHeader.h"

void merge(list<int>& s1, list<int>& s2, list<int>& s){ //ALL CASES O(nlogn) mem: O(n) 
	auto p1 = s1.begin();                        // this perform is for doublylinked lists or arrays not singly linked
	auto p2 = s2.begin();
	while( p1 != s1.end() && p2 != s2.end() ){  //if lists are sorted in decreasing order, then we have to do if(p1 > p2) first
		if( *p1 < *p2 )                        
			s.push_back(*p1++);
		else
			s.push_back(*p2++);
	}
	while( p1 != s1.end() )
		s.push_back(*p1++);
	while( p2 != s2.end() )
		s.push_back(*p2++);
} 
void mergeSort( list<int>& s ){//ALL CASES O(nlogn) mem: O(n) 
	int n = s.size();              // this perform is for doublylinked lists or arrays not singly linked
	if( n <= 1)
		return;
	list<int> s1;
	list<int> s2;
	auto p = s.begin();
	for( int i = 0; i < n/2; i++)
		s1.push_back(*p++);
	for( int i = n/2; i < n; i++)
		s2.push_back(*p++);
	s.clear();
	mergeSort(s1);
	mergeSort(s2);
	merge( s1, s2, s );
}
void merge(vector<int>& in, vector<int>& out, int b, int m) { //ALL CASES O(nlogn) mem: O(n) 
	int i = b; // index into first part of vector in
	int j = b + m; // index into 2nd part of in
	int n = in.size();
	int e1 = std::min(b + m, n); // end 1st part of vector in
	int e2 = std::min(b + 2*m, n); // end of 2nd part of vector in
	int k = b; //output index
	while ((i < e1) && (j < e2)) { 
		if(!(in[j] < in[i])) // append smaller to S
			out[k++] = in[i++];
		else
			out[k++] = in[j++];
	}while (i < e1) // copy rest of run 1 to S
		out[k++] = in[i++];
	while (j < e2) // copy rest of run 2 to S
		out[k++] = in[j++];
}
void mergeSort(vector<int>& S){//ALL CASES O(nlogn) mem: O(n) 
	int n = S.size();               // this perform is for doublylinked lists or arrays not singly linked
	vector<int> out(n); 
	for (int m = 1; m < n; m *= 2) { // list sizes doubling
		for (int b = 0; b < n; b += 2*m) { // beginning of list
			merge(S, out, b, m); // merge sublists
		}
		swap(S, out); // swap input with output
	}
}
//merge sort for stacks
void merge(stack<int>& s, stack<int>& s1, stack<int>& s2, bool flag){
	while( !s1.empty() && !s2.empty() ){
		if( flag ){
			if( s1.top() > s2.top() ){
				s.push(s1.top() );
				s1.pop();
			}
			else{
				s.push(s2.top() );
				s2.pop();
			}
		}
		else{
			if( s1.top() < s2.top() ){
				s.push(s1.top() );
				s1.pop();
			}
			else{
				s.push(s2.top() );
				s2.pop();
			}
		}
	}
	while( !s1.empty() ){
			s.push(s1.top() );
			s1.pop();
	}

	while( !s2.empty() ){
			s.push(s2.top() );
			s2.pop();
	}
}
void mergeSort(stack<int>& s, bool flag){ //call with flag == true for smallest element on top  and with flag == false for largest element on top
	int size = s.size();
	if( size <= 1 )
		return;
	stack<int> s1,s2;
	for(int i =0; i < size/2;i++){
		s1.push(s.top());
		s.pop();
	}
	for( int i = size/2; i < size;i++){
	    s2.push(s.top());
		s.pop();
	}
	mergeSort(s1, !flag);
	mergeSort(s2, !flag);
	merge(s,s1,s2, flag);
}


void quickSortHelper(vector<int>& v, int a, int b) { //worst case O(n^2) av,best O(nlogn) MEM O(1) this is inplace els O(n)
	if (a >= b) return; // 0 or 1 left? done
	int pivot = v[b]; // select last element  as pivot
	int l = a; // left edge
	int r = b-1; // start from the element to the left of the last element
	while (l <= r) { //the two have not crossed yet
		while (l <= r && !(pivot < v[l]) )  // scan right to find sth that is larger than pivot
			l++; // you must put the =  in <= or the whole thing doesnt work
		while (r >= l && !(v[r] < pivot)) // scan left to find sth that is smaller than pivot
			 r--;// you must put the =  in <= or the whole thing doesnt work
		if (l < r) // the two havent crossed yet
			swap(v[l],v[r]);
	} // the two indices have crossed and we have exited the loop
	swap(v[l],v[b]); // swap with pivot to put the pivot almost at the middle of the list ( l )
	quickSortHelper(v, a, l-1); // recur on both sides
	quickSortHelper(v, l+1, b);
}
void quickSort(vector<int>& v) { //worst case O(n^2) happends when arr is already sorted best O(nlogn)
	if (v.size() <= 1)       //ave= best if pivot selected at random or to be mean  MEM: O(1) this is inplace else O(n)
		return; // already sorted
	quickSortHelper(v, 0, v.size() -1 ); // call sort utility
}
//Quick sort is usually not good for linked lists (but it dst need random access bydef so it is possible (nodes)
template<class Node> 
void qs(Node * hd, Node * tl, Node ** rtn) //worst case O(n^2) happends when arr is already sorted best O(nlogn)
{                          //ave= best if pivot selected at random or to be mean  MEM: O(1) this is inplace else O(n)
    int nlo, nhi;
    Node *lo, *hi, *q, *p;

    /* Invariant:  Return head sorted with `tl' appended. */
    while (hd != NULL) {

        nlo = nhi = 0;
        lo = hi = NULL;
        q = hd;
        p = hd->next;

        /* Start optimization for O(n) behavior on sorted and reverse-of-sorted lists */
        while (p != NULL && LEQ(p, hd)) {
            hd->next = hi;
            hi = hd;
            ++nhi;
            hd = p;
            p = p->next;
        }

        /* If entire list was ascending, we're done. */
        if (p == NULL) {
            *rtn = hd;
            hd->next = hi;
            q->next = tl;
            return;
        }
        /* End optimization.  Can be deleted if desired. */

        /* Partition and count sizes. */
        while (p != NULL) {
            q = p->next;
            if (LEQ(p, hd)) {
                p->next = lo;
                lo = p;
                ++nlo;
            } else {
                p->next = hi;
                hi = p;
                ++nhi;
            }
            p = q;
        }

        /* Recur to establish invariant for sublists of hd, 
           choosing shortest list first to limit stack. */
        if (nlo < nhi) {
            qs(lo, hd, rtn);
            rtn = &hd->next;
            hd = hi;        /* Eliminated tail-recursive call. */
        } else {
            qs(hi, tl, &hd->next);
            tl = hd;
            hd = lo;        /* Eliminated tail-recursive call. */
        }
    }
    /* Base case of recurrence. Invariant is easy here. */
    *rtn = tl;
}

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















