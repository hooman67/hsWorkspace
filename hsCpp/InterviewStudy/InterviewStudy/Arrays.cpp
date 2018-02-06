#include <iostream>
#include <vector>
#include <string>
#include<unordered_map>
#include<unordered_set>
#include<string>
#include<list>
#include <algorithm>

using namespace std;

/***************START: Array Partitioning *****************/
void partSm(vector<int>& v, int pivInd){
	int piv = v[pivInd];
	swap(v[v.size() - 1], v[pivInd]);

	int smaller = 0, p = 0;

	while (p < v.size() - 1 && smaller < v.size() - 1){
		if (v[p] < piv){
			swap(v[p++], v[smaller++]);
		}
		else
			p++;
	}

	swap(v[smaller], v[v.size() - 1]);
}

void duthcPar(vector<int>& v, int pivInd){
	int piv = v[pivInd];
	swap(v[v.size() - 1], v[pivInd]);

	int smaller = 0, equal = 0, larger = v.size() - 2;

	while (equal <= larger){
		if (v[equal] < piv){
			swap(v[equal++], v[smaller++]);
		}
		else if (v[equal] == piv){
			equal++;
		}
		else if (v[equal] > piv){
			swap(v[equal], v[larger--]);
		}
	}

	swap(v[v.size() - 1], v[equal]);
}

void quickSort(vector<int>& v, int st, int end){
	if (end <= st)
		return;

	int pivInd = st;

	int newPivInd = partLg(v, pivInd, st, end);

	quickSort(v, st, newPivInd);
	quickSort(v, newPivInd + 1, end);
}
/*****************END: Array Partitioning *****************/



/***************START: Sub-array summed to target *****************/
void findSubArrayWithMaxSum(int arr[], int size){
	/**Time O(n)  Space O(1)
	*
	* Given an array of Pos and Neg integers. Return the subArray with largest sum.
	*/
	int max_ending_here, best_max_so_far, curStart, best_start, best_end;
	curStart = best_start = best_end = 0;
	max_ending_here = arr[0];
	best_max_so_far = arr[0];
	for (int i = 1; i<size; i++){

		/*
		//If you wanna just return the max, replace everything in for loop with these two lines
		curMax = max(arr[i], max_ending_here + arr[i]);
		gMax = max(max_ending_here, best_max_so_far);
		*/

		if (arr[i] >(max_ending_here + arr[i])){
			//this subArray is not what we are looking for, set start to the start of next subarray
			curStart = i;
			max_ending_here = arr[i];
		}
		else{
			max_ending_here = max_ending_here + arr[i];
		}

		if (max_ending_here > best_max_so_far){
			best_max_so_far = max_ending_here;
			best_start = curStart;
			best_end = i;
		}
	}

	cout << best_max_so_far << "  start: " << best_start << "  end: " << best_end << "\n";
}

void smallestSubArraySummedToTarget(int ar[], int arLen, int target){
	/**Time O(n) because each element can be visited at most twice, once by the
	* rightIndex and (at most) once by the leftIndex. Space O(1)
	*
	* Prob: Given an array of Pos intgers
	* Return the length of the smallest contiguous subarray whose sum is >= targetSum.
	*
	* Sol: Start with a 0 length segment, grow it (incr rightPointer) till sum >= targetSum,
	* then shrink it as much as you can (inc leftPointer) while sum >= tagetSum.
	*/
	int smallestSoFar = numeric_limits<int>::max();
	int sum = 0;
	int curStart = 0;
	for (int i = 0; i < arLen; i++){
		sum += ar[i];
		while (sum >= target){
			smallestSoFar = min(smallestSoFar, i - curStart + 1);
			sum -= ar[curStart++];
		}
	}

	cout << smallestSoFar << "\n";
}

void subArrayOfLengthKWithMaxSum(int ar[], int arLen, int L){
	/**time O(n), space O(1)
	*
	* Prob: Return the starting index of the contiguous subArray of
	* length targetLength, that has maximum average.
	*
	* Sol: use leftPointer and rightPointer tergetLength points apart, slide accross
	* the array calculating the avg, keeping track of starting index of the one with
	* max avg so far
	*/
	int bestSum, sum, bestStart, curStart, curEnd;
	sum = bestStart = curStart = curEnd = 0;

	for (; curEnd < L; curEnd++)
		sum += ar[curEnd];

	bestSum = sum;

	while (curEnd < arLen){
		sum -= ar[curStart++];
		sum += ar[curEnd++];

		if (sum > bestSum){
			bestSum = sum;
			bestStart = curStart;
		}
	}

	cout << bestStart << "\n";
}

void isThereSubSetSummedToTarget(int ar[], int arLen, int targetSum){}

int numberThatCannotBeRepresentedAsSum(int ar[], int arLen){
	/**time O(n), space O(1)
	*
	* Prob: Given a sorted array, return the smallest integer than cannot be represented as sum of
	* any elements of ar.
	*
	* Sol: a number that is smaller than element i, but bigger than sum of all the elements before i
	*/
	int sum = 1;
	for (int i = 0; i < arLen && sum >= ar[i]; i++){
		sum += ar[i];
	}

	return sum;
}

int numberOfSubArraysWithElementsLessThanK(int ar[], int arLen, int k){
	/**time O(n), space O(1)
	* Prob: Return the number of contiguous subArrays in ar, where all elements are < k.
	* Note: two adjacent elements < k, form 3 subarrays (two individuals 1 with both of them).
	*/
	int count = 0, counter = 0;
	for (int i = 0; i < arLen; i++){
		if (ar[i] < k){
			counter++;
			count += counter;
		}
		else{
			counter = 0;
		}
	}

	return count;
}

vector<int> zeroModNSumSubset(int ar[], int arLen){
	/**Time O(n), space O(n)
	*
	* Prob: Find any subset of indices of ar where their sum % ar.length = 0. The
	* indices DO NOT NEED to be continous. And you just need to return one of such subsets.
	* Ex: For int[] a = {429,334,62,711,704, 763, 98, 733,721, 995}; return {3 ,4 ,9}
	* because ( ar[3] + ar[4]+ ar[9] ) % 10 = 0
	* Finding an array whose sum mod k is 0, is an np-hard problem.
	* But this has an O(n) solution when k = n.
	*
	* Sol:If there is a subset like this, there is a continuous one too. Make an array of
	* size n, where elements are prevElementsSum % n. Either you have an element in this
	* array that is 0 (you return 1st element, till (and including) that element), or you have two elements
	* with the same value, in which case you return the segment that fals between the two
	* (not including the first but including the last) because that segment has sum % n = 0.
	*/
	vector<int> out;
	int* preSums = new int[arLen];
	int sum = 0;
	for (int i = 0; i < arLen; i++){
		sum += ar[i];
		preSums[i] = sum % arLen;
	}

	for (int i = 0; i < arLen; i++){
		if (preSums[i] == 0){
			for (int j = 0; j <= i; j++){
				out.push_back(ar[j]);
			}
			delete preSums;
			return out;
		}
	}

	unordered_map<int, int>* map = new unordered_map<int, int>();
	for (int i = 0; i < arLen; i++){
		if (!map->insert(make_pair(preSums[i], i)).second){
			for (int j = (*map)[preSums[i]] + 1; j <= i; j++){
				out.push_back(ar[j]);
			}
		}
	}

	delete preSums;
	delete map;
	return out;
}
/***************END: Sub-array summed to target *****************/



/***************START:Longest Increasing Sub-array***********/
int longestIncreasingSubArray(vector<int>& v){
	int longestSoFar = numeric_limits<int>::min();

	int curLen = 0;
	for (int i = 1; i < v.size(); i++){
		if (v[i] > v[i - 1]){
			curLen++;
			longestSoFar = max(longestSoFar, curLen);
		}
		else{
			curLen = 0;
		}
	}
}
/*****************END:Longest Increasing Sub-array***********/



/******************START: MaxDifferences************************/
int maxDifference(int ar[], int arLen){
	int largestDif = numeric_limits<int>::min();
	int smallestElement = numeric_limits<int>::max();

	for (int i = 0; i < arLen; i++){
		if (ar[i] < smallestElement)
			smallestElement = ar[i];

		int dif = smallestElement - ar[i];

		if (dif > largestDif)
			largestDif = dif;
	}

	return largestDif;
}
/********************END: MaxDifferences************************/


/*******************START: return k biggest elements*******************/
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
int getKLargest(vector<int>& v, int k){
	/**time O(n), space O(1) aside from few recursions.
	*
	* Prob: return the largest K elements. where k = 1 means max element.
	*
	* Sol: Find the kth largest using selection rank and then partition the array around it.
	* Selection rank: Partition around a random pivot, if index of pivot == k-1, then
	* pivot is Kth largest element. If IndexOfPivot > k-1, then we are looking for the kth
	* largest in ar[0:IndexOfPivot-1] if IndexOfPivot < k - 1, then we look
	* in ar[IndexOfPivot+1, n-1]
	*
	* The version that returns Kth largest element is in Searching.java
	*/
	int st = 0, end = v.size() - 1;

	while (st <= end){
		int pivInd = rand() % (end - st + 1) + st;

		int newPivInd = partLg(v, pivInd, st, end);

		if ((newPivInd + 1) == k){
			return v[newPivInd];
		}
		else if ((newPivInd + 1) > k){
			end = newPivInd - 1;
		}
		else{
			st = newPivInd + 1;
			k -= newPivInd;
		}

	}

}

vector<int> getK_Elements_sort(vector<int>& ar, int k){
	/**time O(n log n), space O(n)
	* Returns the k biggest or smallest elements of in
	*/
}

vector<int> getK_Elements_heap(vector<int>& ar, int k){
	/**time O(n log k), space O(k)
	* Returns the k biggest or smallest elements of in
	*/
}
/*********************END: return k biggest elements*******************/


/***START: Interleave categories *********/
void mergeCategories(vector<string>& vect){
	/*time O(n) space O(1)
	* problem: convert ["a1","a2","a3","b1", "b2", "b3", "c1","c2", "c3"] to ["a1","b1","c1","a2", "b2", "c2", "a3","b3", "c3"].
	* should work for any number of ppl but with only 3 categories.
	*/
	int nbPeople = vect.size() / 3;

	int ap = 0, bp = nbPeople, cp = nbPeople * 2;
	int fa = 0, fb = 1, fc = 2;

	while (cp < vect.size() - 1){

		//swap
		string temp = vect[fa];
		vect[fa] = vect[ap];
		vect[ap] = temp;


		if (fa == bp)
			bp = ap;
		else if (fa == cp)
			cp = ap;

		//swap
		temp = vect[fb];
		vect[fb] = vect[bp];
		vect[bp] = temp;

		//swap
		temp = vect[fc];
		vect[fc] = vect[cp];
		vect[cp] = temp;


		//update pointers
		fa += 3;
		fb += 3;
		fc += 3;

		ap += nbPeople;
		bp++;
		cp++;
	}
}
/*****END: Interleave categories *********/



int main(){
	vector<string> vec;
	vec.push_back("a1"); vec.push_back("a2"); vec.push_back("a3"); vec.push_back("a4");
	vec.push_back("b1"); vec.push_back("b2"); vec.push_back("b3"); vec.push_back("b4");
	vec.push_back("c1"); vec.push_back("c2"); vec.push_back("c3"); vec.push_back("c4");

	mergeCategories(vec);

	for (string s : vec)
		cout << s << "\n";
}