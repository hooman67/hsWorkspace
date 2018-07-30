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

/************START: Get Median of unsorted array in ************/
/*Time O(n), space O(1)*/
template<class T>
int partition(vector<T>& v, int st, int end, int pivIndex){
	int p = st, smaller = st;
	int pivVal = v[pivIndex];
	swap(v[pivIndex], v[end]);

	while (p < end){
		if (v[p] < pivVal)
			swap(v[p++], v[smaller++]);
		else
			p++;
	}

	swap(v[smaller], v[end]);
	return smaller;
}
template<class T>
int getMed(vector<T>& v){
	int st = 0, end = v.size() - 1;
	int k = v.size() / 2;
	while (st <= end){
		int pivInd = rand() % (end - st + 1) + st;
		int newPivInd = partition(v, st, end, pivInd);
		if (newPivInd == k)
			return v[newPivInd];
		else if (newPivInd > k)
			end = newPivInd - 1;
		else{
			st = newPivInd + 1;
		}
	}
	return -1;
}
/**************END: Get Median of unsorted array in ************/



/***************START: Sub-array summed to target *****************/
//First one is important:
void findSubArrayWithMaxSum(int arr[], int size){
	/**Time O(n)  Space O(1)
	*
	* Prob: Given an array of Pos and Neg integers. Return the subArray with largest sum.
	* Sol: The only time you wanna change the start of your subArray, is when the current element, is bigger that the sum so far.
	*  So you can keep adding elements regarless of their values (you wanna visit each element at least ones), and you only have to decide when you wanna restart your subarray.
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



/***************START:shortest Unordered Sub-array***********/
int getMinLengthUnorderedSubArray(vector<int>& v) {
	int minLen = numeric_limits<int>::max();
	int direction = 0;
	int lenCounter = 2;

	for (int i = 0; i < v.size() - 1; i++) {
		if (v[i] < v[i + 1]) {

			if (direction == -1) {
				direction = 1;
				lenCounter++;

				if (lenCounter < minLen)
					minLen = lenCounter;

				lenCounter = 2;
			}

			else {
				direction = 1;
				lenCounter++;
			}
		}
		else {

			if (direction == 1) {
				direction = -1;
				lenCounter++;

				if (lenCounter < minLen)
					minLen = lenCounter;

				lenCounter = 2;
			}

			else {
				direction = -1;
				lenCounter++;
			}
		}
	}

	return minLen == numeric_limits<int>::max() ? 0 : minLen;
}
/*****************END:Longest Increasing Sub-array***********/



/******************START: MaxDifferences************************/
int maxProfitFrom1Tx_OrLargestDif(vector<int>& prices){
	/*Problem: return the maximum profit you can make with 
	a single transaction. Or find the largest difference
	between any subsequent elements.
	*/
	int largestDif = 0;
	int smallestElement = numeric_limits<int>::max();

	for (int i : prices){
		smallestElement = min(smallestElement, i);
		largestDif = max(largestDif, i - smallestElement);
	}

	return largestDif;
}

int maxProfitFrom2Txs(vector<int>& v){
	/*Problem: return the maximum profit you can make with
	two transaction if you can only hold on to a single
	stock at a time. This is not the same as find the
	2 largest differences between any subsequent elements.
	*/
	if (v.empty()) return 0;

	int leastBuy1CostSoFar = numeric_limits<int>::max(), leastBuy2CostSoFar = numeric_limits<int>::max();
	int maxTx1ProfitSoFar = 0, maxFinalProfitSoFar = 0;

	for (int todaysPrice : v){

		//How much do I have to pay to make buy 1 today ?
		int costToMakeBuy1Today = todaysPrice;
		if (costToMakeBuy1Today < leastBuy1CostSoFar)
			leastBuy1CostSoFar = costToMakeBuy1Today;  //making a buy today is better than all the days before

		//How much would I make if I make sell 1 today ?
		int profitIfSell1Today = todaysPrice - leastBuy1CostSoFar;
		if (profitIfSell1Today > maxTx1ProfitSoFar)
			maxTx1ProfitSoFar = profitIfSell1Today; //selling today gives beter profit than all the days before

		//How much do I have to pay to make buy 2 today, knowing that my maxTx1ProfitSoFar will be locked now. 
		//since I must have sold my buy 1 at some previously seen price (which created max profit) to buy2 today.
		int costToMakeBuy2Today = todaysPrice - maxTx1ProfitSoFar;
		if (costToMakeBuy2Today < leastBuy2CostSoFar)
			leastBuy2CostSoFar = costToMakeBuy2Today;

		//What is the total profit I have made from both my transactions if I make sell 2 today ?
		//Profits from first Tx are subtracted from the cost of buy2 so they are implicitly added here.
		int totalProfitIfSell2Today = todaysPrice - leastBuy2CostSoFar;
		if (totalProfitIfSell2Today > maxFinalProfitSoFar)
			maxFinalProfitSoFar = totalProfitIfSell2Today;
	}

	return maxFinalProfitSoFar;
}

int maxProfitFromAnyNumberOfTxs(vector<int>& v, int maxNumberOfPossibleTxs){
	/*Problem: return the maximum profit you can make with any number of transactions
	less than or equal to the maxNumberOfPossibleTxs specified.
	Note that the minimum number of txs is 1. (i.e. 1 buy and sell). The maxNumberOfPossibleTxs
	given n prices is n-1 (in practice we don't both buy and sell on the first day) or n (in theory).
	But specifying any number above this has no effect, so thre is no upper limit on maxNumberOfPossibleTxs
	*/
	if (v.empty()) return 0;

	/*The only reason we do the + 1 stuff is to make the same code work for both
	the case with only 1 transaction (buy and sell) and higher number of transactions.
	i.e to add the previous profits, we have to start our indexing of txs from 1. For the case,
	of only 1 transaction, the indexing will break.
	*/
	int totalTxsPlusHistory = maxNumberOfPossibleTxs + 1;

	vector<int> leastBuyCostAtEachNumberOfTxsSoFar(totalTxsPlusHistory, numeric_limits<int>::max());
	vector<int> maxTotalProfitAtEachNumberOfTxsSoFar(totalTxsPlusHistory, 0);

	for (int todaysPrice : v){
		for (int tx = 1; tx < totalTxsPlusHistory; tx++){

			//I have to subtract all the profits that I have made from all previous transactions 
			//from the cost of making a buy today. 
			int costToMakeABuyToday = todaysPrice - maxTotalProfitAtEachNumberOfTxsSoFar[tx - 1];
			if (costToMakeABuyToday < leastBuyCostAtEachNumberOfTxsSoFar[tx])
				leastBuyCostAtEachNumberOfTxsSoFar[tx] = costToMakeABuyToday;


			//What is the total profit I have made from all my previous transactions if I make a sell today ?
			//Profits from previous Txs are subtracted from the cost of making last buy so they are implicitly added here.
			int totalProfitIfMakeASellToday = todaysPrice - leastBuyCostAtEachNumberOfTxsSoFar[tx];
			if (totalProfitIfMakeASellToday > maxTotalProfitAtEachNumberOfTxsSoFar[tx])
				maxTotalProfitAtEachNumberOfTxsSoFar[tx] = totalProfitIfMakeASellToday;
		}

	}

	return maxTotalProfitAtEachNumberOfTxsSoFar[maxNumberOfPossibleTxs];
}

int maxProfitFromAnyNumberOfTxs_justShorterCode(vector<int>& prices) {
	/*Problem: return the maximum profit you can make with
	any number of transactions if you can only hold on to a single
	stock at a time. Note that given n prices the maximum number of
	transactions is n. 
	*/
	if (prices.empty())  return 0;

	int n = prices.size();//there are at most n transactions
	vector<int> largestDifAr(n, 0);
	vector<int> minElementAr(n, numeric_limits<int>::max());

	for (int i = 0; i < n; i++)  {
		for (int j = 1; j < n; j++) {
			minElementAr[j] = min(minElementAr[j], prices[i] - largestDifAr[j - 1]);
			largestDifAr[j] = max(largestDifAr[j], prices[i] - minElementAr[j]);
		}
	}

	return largestDifAr[n - 1];
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



/********START: Get volume of trapped water given cell heights ********/
/*Time O(n), space O(1)*/
int trapRainWater1D(vector<int>& heights) {
	int left = 0, right = heights.size() - 1, leftMax = 0, rightMax = 0;
	int rainWater = 0;

	while (left < right) {
		if (heights[left] < heights[right]) {//care only about left.  Same result if condition on  maxLeft<maxRight 
			if (heights[left] >= leftMax)
				leftMax = heights[left];
			else
				rainWater += leftMax - heights[left];

			left++;
		}
		else {//care only about right
			if (heights[right] >= rightMax)
				rightMax = heights[right];
			else
				rainWater += rightMax - heights[right];

			right--;
		}
	}

	return rainWater;
}

int trapRainWater2D_hsVer(vector<vector<int>>& heightMap) {
	/*for n*d input, runTime O(n*d), space O(n*d)
	*/
	vector<vector<int>> colMaxTop, colMaxBotom;
	for (int i = 0; i < heightMap.size(); i++) {
		vector<int> temp;
		colMaxTop.push_back(temp);
		colMaxBotom.push_back(temp);
	}

	for (int i = 0; i < heightMap[0].size(); i++) {
		int colMax = 0;
		for (int j = 0; j < heightMap.size(); j++) {
			if (heightMap[j][i] > colMax)
				colMax = heightMap[j][i];
			colMaxTop[j].push_back(colMax);
		}

		colMax = 0;
		for (int j = heightMap.size() - 1; j >= 0; j--) {
			if (heightMap[j][i] > colMax)
				colMax = heightMap[j][i];
			colMaxBotom[j].push_back(colMax);
		}
	}

	int rainWater = 0;
	for (int i = 0; i < heightMap.size(); i++) {
		int left = 0, right = heightMap[i].size() - 1, maxLeft = 0, maxRight = 0;

		while (left < right) {
			if (heightMap[i][left] < heightMap[i][right]) {
				if (heightMap[i][left] >= maxLeft)
					maxLeft = heightMap[i][left];
				else {
					int difLeft = maxLeft - heightMap[i][left];
					int difTop = colMaxTop[i][left] - heightMap[i][left];
					int difBot = colMaxBotom[i][left] - heightMap[i][left];

					if (difTop > 0 && difBot > 0) {
						rainWater += min(difLeft, min(difTop, difBot));
					}
				}

				left++;
			}
			else {
				if (heightMap[i][right] >= maxRight)
					maxLeft = heightMap[i][right];
				else {
					int difRight = maxRight - heightMap[i][right];
					int difTop = colMaxTop[i][right] - heightMap[i][right];
					int difBot = colMaxBotom[i][right] - heightMap[i][right];

					if (difTop > 0 && difBot > 0) {
						rainWater += min(difRight, min(difTop, difBot));
					}
				}

				right--;
			}
		}
	}


	return rainWater;
}
/**********END: Get volume of trapped water given cell heights ********/



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



/*************START: Fill a napsack with rocks that have weights and values ***************/
vector<int> values;
vector<int> weights;

bool comp(int a, int b) {
	return values[a] < values[b];
}

int getTotalPossibleValueInNapSack(int maxWeight) {
	vector<int> perm;
	for (int i = 0; i < values.size(); i++)
		perm.push_back(i);

	sort(perm.begin(), perm.end(), comp);

	int totalVal = 0;
	int totalWeight = 0;
	int i = values.size() - 1;
	while (totalWeight < maxWeight && i > 0) {
		if ((weights[i] + totalWeight) <= maxWeight) {
			totalVal += values[i];
			totalWeight += weights[i];
		}

		i--;
	}

	return totalVal;
}
/***************END: Fill a napsack with rocks that have weights and values ***************/



/*************START: Search a sorted Matrix in Linear time ***************/
/*Time: O(m+n), space O(1)*/
int binSearch(vector<int> v, int t, int start, int end) {
	if (end < start)
		return -1;

	int mid = (end - start) / 2 + start;

	if (v[mid] == t)
		return mid;
	else if (v[mid] < t) {
		return binSearch(v, t, mid + 1, end);
	}
	else {
		return binSearch(v, t, start, mid - 1);
	}
}
int searchMatrix(vector<vector<int>> v, int t) {
	int targetRow = 0;
	while (v[targetRow][v[0].size() - 1] < t && targetRow < v.size() - 1)
		targetRow++;

	cout << "target's row: " << targetRow << "\n";
	return binSearch(v[targetRow], t, 0, v[targetRow].size() - 1);
}
/***************END: Search a sorted Matrix in Linear time ***************/



int main(){
	vector<string> vec;
	vec.push_back("a1"); vec.push_back("a2"); vec.push_back("a3"); vec.push_back("a4");
	vec.push_back("b1"); vec.push_back("b2"); vec.push_back("b3"); vec.push_back("b4");
	vec.push_back("c1"); vec.push_back("c2"); vec.push_back("c3"); vec.push_back("c4");

	mergeCategories(vec);

	for (string s : vec)
		cout << s << "\n";
}
