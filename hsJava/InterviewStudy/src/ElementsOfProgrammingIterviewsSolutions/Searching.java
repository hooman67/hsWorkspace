package ElementsOfProgrammingIterviewsSolutions;

import java.util.Arrays;
import java.util.Random;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.Callable;

import ElementsOfProgrammingIterviewsSolutions.StacksAndQueues.Pair;

public class Searching {
	static class Pair implements Comparable<Pair>{//
		Integer first, second;
		
		Pair(int val1, int val2){
			this.first = val1;
			this.second = val2;
		}
		
		@Override
		public int compareTo(Pair arg0) {
			//Whatever you use here, your maxQueue will operate based on.
			return second.compareTo(arg0.second);
		}
	}

	
	/***************START: Given Sorted array Find index of k***************/
	static int FindK_withBinSearch(int[] ar, int k){
		/**time O(log n), space O(1)
		 * 
		 * Prob: Find index of any occurance of target value k in sorted array ar. Return -1 if not found.
		 */
		int st = 0, end = ar.length-1;
		
		while(st <= end){
			//int mid = (st+end)/2; //this might over flow if st+end is too large.
			int mid = st + (end - st)/2;
			
			if(ar[mid] == k)
				return mid;
			else if(ar[mid] < k)//search right half
				st = mid + 1;
			else//search left half
				end = mid - 1;
		}
		
		//not found
		return -1;
	}
	
	static int binSearch_withUnknownLength(int[] ar, int k){
		/**time O(log n), space O(1)
		 * 
		 * Prob: Given a sorted array of unknown length, find any element = k.
		 * 
		 * Sol: Find the range containing k or the end of array. Do binarySearch in that range.
		 */
		int p = 0;
		while(true){
			try{
				int val = ar[(int)Math.pow(2, p) - 1];
				
				if(val == k) //found k. Return index of val
					return (int)Math.pow(2, p) - 1;
				else if(val > k) //found the range containing k
					break;
			}catch(Exception e){ //found end of array
				break;
			}
			
			p++; //if val <= k, expand the range
		}
		
		//Do binarySearch for k in the range containing it.
		int st =  (int)Math.pow(2, p-1) + 1;
		int end = (int)Math.pow(2, p) - 2;
		
		while(st <= end){
			int mid = st + (end - st)/2;
			
			if(ar[mid] == k)
				return mid;
			else if(ar[mid] > k) // search left half
				end = mid - 1;
			else
				st = mid + 1;
		}
		
		return -1;
	}
	/*****************END: Given Sorted array Find index of k***************/
	
	
	/***************START: Find first occurrence of k ***************/
	static int findFirstOccuranceOfK_Betterversion(int[] ar, int k){
		/**time O(log n), space O(1)
		 * 
		 * Prob: Find index of first occurance of target value k. In sorted array ar.
		 * 
		 * Sol: since array is sorted duplicated values are next to each other, 
		 * just find any occurance of k, then look to its left.
		 */
		int st = 0, end = ar.length-1, returnVal = -1;
		
		while(st <= end){
			//int mid = (st+end)/2; //this might over flow if st+end is too large.
			int mid = st + (end-st)/2;
			
			if(ar[mid] == k){
				returnVal = mid;
				end--; //go to first occurrence
			}
			else if(ar[mid] < k)
				st = mid + 1;
			else
				end = mid - 1;
		}
		
		return returnVal;
	}
	static int findFirstOccuranceOfK_MyVersion(int[] ar, int k){
		/**time O(log n), space O(1)
		 * 
		 * Prob: Find index of first occurance of target value k.
		 * 
		 * Sol: since array is sorted duplicated values are next to each other, 
		 * just find any occurance of k, then look to its left.
		 */
		int st = 0, end = ar.length-1;
		
		while(st <= end){
			//int mid = (st+end)/2; //this might over flow if st+end is too large.
			int mid = st + (end-st)/2;
			
			if(ar[mid] == k){
				while(ar[mid] == k)//go to first occurrence
					mid--;
				return mid+1;
			}
			else if(ar[mid] < k)
				st = mid + 1;
			else
				end = mid - 1; 
		}
		
		//not found
		return -1;
	}
	/*****************END: Find first occurrence of k ***************/
	
	
	/******START: Find first occurrence of an element larger than k ******/
	static int findFirstElementLargerThanK(int[] ar, int k){
		/**time O(log n), space O(1)
		 * 
		 * Prob: Find first occurrence of an element larger than k. In sorted array ar.
		 */
		int st = 0, end = ar.length-1, returnVal = -1;
		
		while(st <= end){
			int mid = st + (end - st)/2;
			
			if(ar[mid] > k){//go to first occurrence
				returnVal = mid;
				end = mid - 1;
			}else
				st = mid + 1;
		}
		
		return returnVal;
	}
	/********END: Find first occurrence of an element larger than k ******/
	
	
	/****START: Find maxElement in an Ascending then descending array ****/
	static int findMaxInAscDesc(int[] ar){
		/**time O(log n), space O(1)
		 * 
		 * Prob: Find the max value in an array that is first ascending then descending.
		 */
		int st = 0, end = ar.length-1, maxVal = Integer.MIN_VALUE;
		
		while(st <= end){
			//int mid = (st+end)/2; //this might over flow if st+end is too large.
			int mid = st + (end - st)/2;
			
			if(ar[mid] > maxVal)
				maxVal = ar[mid];
			
			if(ar[mid] <= ar[mid+1]){//ascending
				st = mid + 1;
			}
			else if(ar[mid] > ar[mid+1]){//descending
				end = mid - 1;
			}
		}

		return maxVal;
	}
	/******END: Find maxElement in an Ascending then descending array ****/
	
	
	/***START: Find any index i such that ar[i] = i, given sorted aray ***/
	static int findIndexWithSameValue(int[] ar){
		/**
		 * Prob: Given a sorted array with unique elements, find any index i where ar[i]==i. 
		 * Return -1 if no such element.
		 * 
		 * Sol: Implicitly form the array B where B[i] = ar[i] - i. Then look for an element
		 * k = 0. B is sorted as longs as A is sorted (because array indices are sorted).
		 * This sol won't work if we have duplicated elements, Ex:
		 *  int[] ar = {-2,-1,2,4,4,4,5,5,6,7,9} doesn't work but ar = {-2,-1,2,4,4,6,5,5,6,7,9}; does.
		 */
		int st = 0, end = ar.length-1;
		
		while(st <= end){
			int mid = st + (end - st)/2;
			int val = ar[mid] - mid;
			
			if(val == 0)
				return mid;
			else if(val > 0)
				end = mid - 1;
			else
				st = mid + 1;
		}
		
		return -1;
	}
	static int findIndexWithSameValue_WrongApproach(int[] ar){
		/**
		 * Prob: Given a sorted array, find any index i where ar[i]==i. Return -1 if no such element.
		 * 
		 * Sol: array is sorted, so if ar[mid] > mid, then ar[mid+1] is definately bigger than (mid+1).
		 * This approach is wrong because if  ar[mid] < mid, the solution can be in either half. So
		 * splitting to the left half is wrong, Ex: int[] ar = {-2,-1,2,4,4,4,5,5,6,7,9};
		 */
		int st = 0, end = ar.length-1;
		
		while(st <= end){
			int mid = st + (end - st)/2;
			
			if(ar[mid] == mid)
				return mid;
			else if(ar[mid] > mid)
				end = mid - 1;
			else
				st = mid + 1;
		}
		
		return -1;
	}
	/*****END: Find any index i such that ar[i] = i, given sorted aray ***/

	
	/***********START: Find a pair of elements that sum to k ***********/
	Pair findPairThatSumToK_UnorderedArray(int[] ar, int k){
		/**Time and space O(n).
		 * 
		 * Prob: In an unordered array, find a pair of elements that sum to k. Return (-1,-1) if 
		 * no such pair
		 * 
		 * Sol: put everything in a hashTable, and for each element x in the array 
		 * then look for element (k-x) in hashtable.
		 */
		return null;
	}
	
	static Pair findPairThatSumToK_fullySorted(int[] ar, int k){
		/**Time O(n) and space O(1).
		 * 
		 * Prob: In a fully sorted array, find a pair of elements that sum to k. Return (-1,-1) if 
		 * no such pair
		 * 
		 * Sol: start with (ar[0], ar[n-1]) if sum < k, increase it by using ar[1] (we never need
		 * to look at ar[0] again). If sum > k, decrease it by using ar[n-2] (we never need to 
		 * look at ar[n-1] again). 
		 */
		int st = 0, end = ar.length-1;
		
		while(st <= end){
			int curSum = ar[st]+ar[end];
			
			if(curSum == k)
				//return new Pair(ar[st], ar[end]); //to return elements
				return new Pair(st, end);//to return indices
			else if(curSum > k)
				end--;
			else
				st++;
		}
		
		return new Pair(-1,-1);
	}
	
	static Pair findPairThatSumToK_absSorted(int[] ar, int k){
		/**Time O(n) and space O(1).
		 * 
		 * Prob: In a abs sorted array (abs of elements is sorted), find a pair of 
		 * elements that sum to k. Return (-1,-1) if no such pair
		 * 
		 * Sol: If the correct pair is not one with one posEl and one NegEl, then it has
		 * both elements positive for pos k, and both neg for neg k. Do these cases seperately,
		 * by iterating over negative vs positive elements seperately. 
		 */
		
		Pair tempResults = helper_findPosNegPair(ar, k);
		if(tempResults.first == -1 && tempResults.second == -1){
			return k >= 0 ? helper_findBothPos(ar,k) : helper_findBothNeg(ar,k);
		}
		
		return tempResults;
	}
	static Pair helper_findPosNegPair(int[] ar, int k){
		//let out.first be pos and out.second be neg
		Pair out = new Pair(ar.length-1, ar.length-1);
		
		//Find last positive element
		while(out.first >= 0 && ar[out.first] < 0)
			out.first--;
		
		//Find last negetive element
		while(out.second >= 0 && ar[out.second] >= 0)
			out.second--;
		
		while(out.first >= 0 && out.second >= 0){
			if( (ar[out.first] + ar[out.second]) == k )
				return out;
			else if( (ar[out.first] + ar[out.second]) > k ){
				do{
					out.first--;
				}while(out.first >= 0 && ar[out.first] < 0);
			}
			else{
				do{
					out.second--;
				}while(out.second >= 0 && ar[out.second] >= 0);	
			}
		}
		
		return new Pair(-1,-1);
	}
	static Pair helper_findBothPos(int[] ar, int k){
		Pair out = new Pair(0, ar.length-1);
		
		while(out.first < out.second && ar[out.first] < 0)
			out.first++;
		
		while(out.first < out.second && ar[out.second] < 0)
			out.second--;
		
		while(out.first < out.second){
			if( (ar[out.first] + ar[out.second]) == k )
				return out;
			else if( (ar[out.first] + ar[out.second]) < k ){
				do{
					out.first++;
				}while(out.first < out.second && ar[out.first] < 0);	
			}
			else{
				do{
					out.second--;
				}while(out.first < out.second && ar[out.second] < 0);	
			}
				
		}
		
		return new Pair(-1,-1);
	}
	static Pair helper_findBothNeg(int[] ar, int k){
		Pair out = new Pair(0, ar.length-1);
		
		while(out.first < out.second && ar[out.first] >= 0)
			out.first++;
		
		while(out.first < out.second && ar[out.second] >= 0)
			out.second--;
		
		while(out.first < out.second){
			if( (ar[out.first] + ar[out.second]) == k )
				return out;
			else if( (ar[out.first] + ar[out.second]) >= k ){
				do{
					out.first++;
				}while(out.first < out.second && ar[out.first] < 0);		
			}
			else{
				do{
					out.second--;
				}while(out.first < out.second && ar[out.second] < 0);
					
			}
		}
		
		return new Pair(-1,-1);
	}
	/*************END: Find a pair of elements that sum to k ***********/
	
	
	/****START: Find smallest element=pivot in a cyclically sorted array ****/
	static int findSmallestElement_uniqueElements(int[] ar){
		/**time O(log n), space O(1)
		 * 
		 * Prob: Given a cyclically sorted array with unique elements, find 
		 * the index of the pivot == the minimum element in the array.
		 * 
		 * Sol: find the 1st element smaller than the 1st element in array. This cannot be solved in 
		 * less than linear time if elements are not unique. 
		 */
		int st = 1, end = ar.length - 1, out = -1;
		
		while(st <= end){
			int mid = st + (end - st)/2;
			
			if(ar[mid] <= ar[0]){
				out = mid;
				end = mid - 1;
			}
			else
				st = mid+1;
		}
		
		return out;
	}
	
	static int findSmallestElement_duplicatesAllowed(int[] ar){
		/**time O(n) worst case, space O( log n ) due to recursion
		 * 
		 * Prob: Given a cyclically sorted array with duplicated elements, find 
		 * the index of the pivot == the minimum element in the array.
		 * 
		 * Sol: do binary search, but if you find two equal elements do linear search on both halves.
		 * Since duplicated element cannot do better than linear time.
		 */
		return helper_findSmallestElement_duplicatesAllowed(ar, 0, ar.length - 1);
	}
	static int helper_findSmallestElement_duplicatesAllowed(int[] ar, int st, int end){
		if(st == end)
			return st;
		
		int mid = st + (end - st)/2;
		
		if(ar[mid] > ar[end])
			return helper_findSmallestElement_duplicatesAllowed(ar, mid+1, end);
		else if(ar[mid] > ar[end])
			return helper_findSmallestElement_duplicatesAllowed(ar, st, mid);
		else{// ar[mid] == ar[end]
			int st_result =  helper_findSmallestElement_duplicatesAllowed(ar, st, mid);
			int end_result = helper_findSmallestElement_duplicatesAllowed(ar, mid+1, end);
			
			return ar[end_result] < ar[st_result] ? end_result : st_result;
		}
		
	}
	/******END: Find smallest element=pivot in a cyclically sorted array ****/

	
	/****START: Find a cap, such that the sumOverI(min(ar[i],cap)) = budget ****/
	static int completionSearch(int[] ar, int budget){
		Arrays.sort(ar); //here we sort the array if not sorted.
		
		
		int[] prefixSums = new int[ar.length];// each location, holds the sum of previous elements
		
		int sum = 0;
		for(int i =0; i < ar.length; i++){
			sum += ar[i];
			prefixSums[i] = sum;
		}
		
		
		int[] costs = new int[ar.length];//totalSum if the rest of elements are all capped at ar[i]. 
		
		for(int i =0; i < ar.length; i++){
			costs[i] = prefixSums[i] + (ar.length - 1 - i) * ar[i];
		}
		
		
		//Do a linear search for the given budget in our costs.
		int foundVal = 0;
		
		while(foundVal < ar.length && costs[foundVal] < budget)
			foundVal++;
			

		
		if(foundVal >= ar.length) // no solution because the budget is too large (no need to cap anything)
			return -1; 
		
		if(foundVal == 0) // gotta cap every salary
			return budget/ ar.length;
		
		int index = foundVal - 1; //calculate the cap giving us the appropriate budget now that we know its feasable
		return ar[index] + (budget - costs[index]) / (ar.length - 1 - index);
	}
	/******END: Find a cap, such that the sumOverI(min(ar[i],cap)) = budget ****/
	
	
	/************START: return Kth largest element ************/
	static int findKthLargest(int[] ar, int k){
		/**time O(n), space O(1) aside from few recursions.
		 * 
		 * Prob: return the Kth largest element in an array where k = 1 means max element.
		 * 
		 * Sol: Partition around a random pivot, if index of pivot == k-1, then pivot is Kth largest 
		 * element. If IndexOfPivot > k-1, then we are looking for the kth largest in ar[0:IndexOfPivot-1]
		 * if IndexOfPivot < k - 1, then we look in ar[IndexOfPivot+1, n-1]
		 */
		
		int st = 0, end = ar.length - 1;
		
		while(st <= end){
			//int randomPivot = ((int)Math.random()) % (ar.length);
			Random randomNbGenerator = new Random();
			int randomPivot = randomNbGenerator.nextInt(end - st + 1) + st;
			int pivIndex = helper_partition(ar, st, end, randomPivot);
			
			if(pivIndex == k - 1)
				return ar[pivIndex];
			else if(pivIndex > k - 1)
				end = pivIndex - 1;
			else
				st = pivIndex + 1;
		}
		
		return Integer.MIN_VALUE;
	}
	static int helper_partition(int[] ar, int st, int end, int pivotIndex){
		/**Returns the index of the pivot in the reordered array. 
		 */
		int pivotValue = ar[pivotIndex], larger = st;
		
		//put pivot at the end for safe keeping
		helper_swapArrayElements(ar, pivotIndex, end);
		
		for(int i = st; i < end; i++){
			if(ar[i] > pivotValue)
				helper_swapArrayElements(ar, i, larger++);
		}
		
		//put pivot back in its place
		helper_swapArrayElements(ar, larger, end);
		
		return larger;
	}
	static void helper_swapArrayElements(int[] ar, int first, int second){
		int temp = ar[first];
		ar[first] = ar[second];
		ar[second] = temp;
	}
	/**************END: return Kth largest element ************/
	
	
	/**********START: Find missing and duplicated integers **********/
	static int[] findMissingAndDuplicatedInts_UsingSum(int[] ar){
		/**time O(n), space O(n). But this is prone to overflow. Use XOR to resolve.
		 *  
		 * Prob: Given an array of integers from 0 to n-1, find the missing and duplicated integers.
		 * Sequence must start at 0 and there must be only 1 duplicate and 1 missing value. 
		 * 
		 * Sol: Let Zn be the sequence of integers from 0 to n-1. Then: Sum(A) = Sum(Zn) + t - m.
		 * And the sumOfSquaresOfElements(A) =  sumOfSquaresOfElements(Zn) + t^2 - m^2.
		 * Find t-m and t^2 - m^2 directly by calculating sum(A)-sum(Zn) directly. Then Divide the two
		 * do find t+m (i.e. factorize t^2 - m^2) then add and subtract to find m and t.
		 */
		int sum = 0, sqrSum = 0;
		
		for(int i = 0; i < ar.length; i++){
			sum += i - ar[i];
			sqrSum += i*i - ar[i]*ar[i];
		}
		
		// 2m = (t+m)-(t-m)  = (t^2-m^2)/(t-m) - (t-m).   2t = (t+m) + (t-m)
		return new int[]{(sqrSum/sum - sum)/2, (sqrSum/sum + sum)/2};
	}
	
	static int[] findMissingAndDuplicatedInts_UsingXOR(int[] ar){
		/**time O(n), space O(n). But this is prone to overflow. Use XOR to resolve. 
		 * Prob: Given an array of integers, find the missing and duplicated ints. 
		 * 
		 * Sol: 
		 */
		//TODO
		return null;
	}
	/************END: Find missing and duplicated integers **********/
	
	
	
	public static void main(String[] args){
		int[] ar = {1,3,4,4,5,6,7};

		int[] b = findMissingAndDuplicatedInts_UsingSum(ar);
		
		for(int a : b)
		System.out.println(a);

	}
}
