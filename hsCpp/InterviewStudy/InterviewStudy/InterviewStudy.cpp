#include <iostream>
#include<vector>
#include<algorithm>
#include<limits>


using namespace std;

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



int main(){
	vector<int> v;
	v.push_back(1); v.push_back(4); v.push_back(6); v.push_back(2); v.push_back(8); v.push_back(3); v.push_back(10); v.push_back(14);
	//v.push_back(1); v.push_back(3); v.push_back(7); v.push_back(5); v.push_back(10); v.push_back(3);
	//v.push_back(1); v.push_back(7); v.push_back(5); v.push_back(10);
	
	//int k = maxProfit(v, 0);
	int k = maxProfitFromAnyNumberOfTxs(v, 9);
	cout << k << "\n";
}