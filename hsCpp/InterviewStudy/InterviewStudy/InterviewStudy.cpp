#include <iostream>
#include<vector>
#include<algorithm>
#include<limits>


using namespace std;


int getLargestDif(vector<int>& prices){
	int largestDif = numeric_limits<int>::min();
	int smallestElement = numeric_limits<int>::max();

	for (int i : prices){
		smallestElement = min(smallestElement, i);
		largestDif = max(largestDif, i - smallestElement);
	}

	return largestDif;
}

int get2LargestDif(vector<int>& prices){
	int largestDif1 = 0; //we are not stupid to sell at a loss so this can never be below 0.
	int largestDif2 = 0;
	int smallestElement1 = numeric_limits<int>::max();
	int smallestElement2 = numeric_limits<int>::max();

	for (int i : prices){
		smallestElement1 = min(smallestElement1, i);
		largestDif1 = max(largestDif1, i - smallestElement1);
		
		//the smaller the current price, and the larger the previous profits, the likelier we are to buy at this price. 
		smallestElement2 = min(smallestElement2, i - largestDif1);
		largestDif2 = max(largestDif2, i - smallestElement2);
	}

	return largestDif2;
}




int MaxProfitDpCompact2(vector<int>& prices) {
	if (prices.empty())  return 0;

	int n = prices.size();//there are at most n-1 transactions
	vector<int> largestDifAr(n, 0);
	vector<int> minElementAr(n, numeric_limits<int>::max());

	for (int i = 0; i < n; i++)  {
		for (int j = 1; j < n; j++) {
			minElementAr[j] = min(minElementAr[j], prices[i] - largestDifAr[j - 1]);
			largestDifAr[j] = max(largestDifAr[j], prices[i] - minElementAr[j]);
		}
	}

	return largestDifAr[n-1];
}

int main(){
	vector<int> v;
	v.push_back(1); v.push_back(4); v.push_back(6); v.push_back(2); v.push_back(8); v.push_back(3); v.push_back(10); v.push_back(14);
	//v.push_back(1); v.push_back(3); v.push_back(7); v.push_back(5); v.push_back(10); v.push_back(3);
	//v.push_back(1); v.push_back(7); v.push_back(5); v.push_back(10);
	
	//int k = maxProfit(v, 0);
	int k = MaxProfitDpCompact2(v);
	cout << k << "\n";
}