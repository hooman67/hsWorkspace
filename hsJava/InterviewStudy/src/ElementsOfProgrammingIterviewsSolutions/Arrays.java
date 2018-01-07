package ElementsOfProgrammingIterviewsSolutions;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class Arrays {
	/************START: return k biggest/smallest elements************/
	static int[] getLargestK_selectionRank(int[] ar, int k){
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
		
		int st = 0, end = ar.length - 1;
		
		while(st <= end){
			//int randomPivot = ((int)Math.random()) % (ar.length);
			Random randomNbGenerator = new Random();
			int randomPivot = randomNbGenerator.nextInt(end - st + 1) + st;
			int pivIndex = helper_partition(ar, st, end, randomPivot);
			
			if(pivIndex == k - 1)
				//return ar[pivIndex]; this is the Kth largest element.
				break;
			else if(pivIndex > k - 1)
				end = pivIndex - 1;
			else
				st = pivIndex + 1;
		}
		
		
		//Since ar is partitioned around the Kth largest element, just return the first k elements of ar.
		int[] out = new int[k];
		for(int i = 0; i < k; i++){
			out[i] = ar[i];
		}
		
		return out;
	}
	static int helper_partition(int[] ar, int st, int end, int pivotIndex){
		/**Returns the index of the pivot in the reordered array. 
		 */
		int pivotValue = ar[pivotIndex], larger = st;
		
		//put pivot at the end for safe keeping
		swapArrayElements(ar, pivotIndex, end);
		
		for(int i = st; i < end; i++){
			if(ar[i] > pivotValue)
				swapArrayElements(ar, i, larger++);
		}
		
		//put pivot back in its place
		swapArrayElements(ar, larger, end);
		
		return larger;
	}
	
	static int[] getK_Elements_sort(int[] in, int k, boolean biggest){
		/**time O(n log n), space O(n)
		 * Returns the k biggest or smallest elements of in
		 */
		
		//Sort small to large
		TreeSet<Integer> m = new TreeSet<>();
		for(int a : in){
			m.add(a);
		}
		
		int[] out = new int[k];
		
		if(biggest){//put k biggest elements in out
			Iterator<Integer> it = m.descendingIterator();

			for(int i =0; i < k && it.hasNext(); i++){
				out[i] = it.next();
			}
		}
		else{//put k smallest elements in out
			Iterator<Integer> it = m.iterator();

			for(int i =0; i < k && it.hasNext(); i++){
				out[i] = it.next();
			}
		}
		
		return out;
	}
	
	static Object[] getK_Elements_heap(int[] in, int k, boolean biggest){
		/**time O(n log k), space O(k)
		 * Returns the k biggest or smallest elements of in
		 */
		
		PriorityQueue<Integer> heap;
		
		if(biggest)//build a min queue to return the k biggest elements
			heap = new PriorityQueue<Integer>();
		else//build a max queue to return the k smallest elements
			heap = new PriorityQueue<Integer>(Collections.reverseOrder());
		
		//heapify first k elements
		for(int i = 0; i < k; i++){
			heap.add(in[i]);
		}
		
		//For the rest, add, and remove head to keep heap size at k		
		for(int i = k; i < in.length; i++){
			heap.add(in[i]);
			heap.remove();
		}
		
		return heap.toArray();
	}
	/**************END: return k biggest/smallest elements************/
	
	
	/************START: Track the median (middle element)************/
	class TrackMedian{
		/**Insert O(log n), getMedian O(1), space O(n)
		 * Keep the smallest half in maxQ, keep the biggest half in minQ.
		 * If we use a sorted array: inserts O(n), getMidian O(1), 
		 * if we use a sorted tree, inserts O(log n), getMedian O(n)
		 */
		PriorityQueue<Integer> minQ = new PriorityQueue<>();
		PriorityQueue<Integer> maxQ = new PriorityQueue<>(Collections.reverseOrder());

		void insert(Integer in){
			if(maxQ.size() == 0){
				maxQ.add(in);
			}
			else if( in <= maxQ.peek() )
				maxQ.add(in);
			else
				minQ.add(in);
			
			//re-balance: make sure sizes of two halves dont defer by more than 1
			if(maxQ.size() + 1 < minQ.size()){
				maxQ.add(minQ.peek());
				minQ.remove();
			}
			else if(minQ.size() + 1 < maxQ.size()){
				minQ.add(maxQ.peek());
				maxQ.remove();
			}
		}
		
		int getMedian(){
			//works because sizes can differ at most 1
			if(minQ.size() < maxQ.size())
				return maxQ.size();
			else if(minQ.size() > maxQ.size())
				return minQ.peek();
			else
				return (minQ.peek() + maxQ.peek()) / 2;
		}
	}
	/**************END: Track the median (middle element)************/
	
	
	/**************START: Dutch Partitioning an array******************/
	static int dutchPartition_myVersion(int[] ar, int pivotIndex){
		/**O(1) space, O(n) time. 
		 * 
		 * Prob: Partition based on 3 key values.Example(below piv, equal piv, above piv).
		 *  
		 * Returns the index of the pivot in the reordered array. Which == larger. 
		 * Fixed the issue with Pivot being the last element with no duplicates present in bookVersion.
		 * 
		 * Sol: We keep the following groups during partitioning:
		 * buttom group: ar[0 : smaller-1],
		 * equal group: ar[smaller : equal -1],
		 * top group: ar[ larger+1 : ar.length -1] 
		 * unclassified group: ar[equal : larger] 
		 */
		
		int smaller = 0, equal = 0, larger = ar.length - 2;
		
		int pivVal = ar[pivotIndex];
		//put pivot at the end for safe keeping
		swapArrayElements(ar, pivotIndex, ar.length - 1);
		
		//While there are unclassified elements. ar[equal] is the incoming unclassified element
		while(equal <= larger){
			if(ar[equal] < pivVal){
				swapArrayElements(ar, smaller, equal);
				smaller++;
				equal++;
			}
			else if(ar[equal] == pivVal){
				equal++;
			}
			else{//ar[equal] > pivVal
				swapArrayElements(ar, equal, larger);
				larger--;
			}
				
		}
		
		//put pivot back in its place
		swapArrayElements(ar, ++larger, ar.length - 1);
		
		return larger;
	}
	static void dutchPartition_bookVersion(int[] ar, int pivotIndex){
		/**O(1) space, O(n) time. 
		 * 
		 * Prob: Partition based on 3 key values.Example(below piv, equal piv, above piv).
		 * 
		 * BookVersion: Has issues if the last element is the pivot and there are no elements 
		 * equal to pivot. myVersion fixes this.
		 * 
		 * Sol: We keep the following groups during partitioning:
		 * buttom group: ar[0 : smaller-1],
		 * equal group: ar[smaller : equal -1],
		 * top group: ar[ larger+1 : ar.length -1] 
		 * unclassified group: ar[equal : larger]
		 * 
		 * Note on understanding the algo: 				
		  		 * smaller always refers to an element we've already processes, (its always <= equal) 
				 * so no need to visit its element after it gets swappes 
				 * (i.e. we can safely increment equal). However, larger refers to an element we havent
				 * seen before, so we cannot increment equal after swapping it with larger, we gotta
				 * visit equal again
		 */
		
		int smaller = 0, equal = 0, larger = ar.length - 1;
		
		//While there are unclassified elements. ar[equal] is the incoming unclassified element
		while(equal <= larger){
			if(ar[equal] < ar[pivotIndex]){
				swapArrayElements(ar, smaller, equal);
				smaller++;
				equal++; 
			}
			else if(ar[equal] == ar[pivotIndex]){
				equal++;
			}
			else{//ar[equal] > ar[pivotIndex]
				swapArrayElements(ar, equal, larger);
				larger--;
			}
				
		}
	}
	static void swapArrayElements(int[] ar, int first, int second){
		int temp = ar[first];
		ar[first] = ar[second];
		ar[second] = temp;
	}
	
	static class SomethingWith4KeyTypes{
		int key;
		SomethingWith4KeyTypes(int k){
			key = k;
		}
	}
	static void dutchPartition4Keys(SomethingWith4KeyTypes[] ar){
		/**O(1) space, O(n) time.
		 * We have 4 keytypes, trick is to assume we have only 3 keys types first, 
		 * use normal duchPartiion above, then do one last round of partitioning 
		 * for the two remaining keys.
		 */
		
		int smaller = 0, equal = 0, larger = ar.length - 1;
		
		while(equal <= larger){
			if(ar[equal].key == 1){
				swapArrayElements(ar, smaller, equal);
				smaller++;
				equal++;
			}
			else if(ar[equal].key == 2 || ar[equal].key == 3){
				equal++;
			}
			else if(ar[equal].key == 4){//ar[equal] > ar[pivotIndex]
				swapArrayElements(ar, equal, larger);
				larger--;
			}	
		}
		
		int newEqual = smaller, newLarger = larger;
		while(newEqual <= newLarger){
			if(ar[newEqual].key == 3){
				swapArrayElements(ar, newEqual, newLarger);
				newLarger--;
			}
			else{
				newEqual++;
			}
		}
	}
	static void swapArrayElements(SomethingWith4KeyTypes[] ar, int first, int second){
		int temp = ar[first].key;
		ar[first].key = ar[second].key;
		ar[second].key = temp;
	}
	/**************END: Dutch Partitioning an array******************/
	
	
	/******************START: MaxDifferences************************/
	static int maxDifference(int[] ar){
		/**Time O(n), space O(1)
		 * Return the maximum possible value for ar[j]-ar[i] such that j>i.
		 */
		int smallestElementSoFar = Integer.MAX_VALUE;
		int largestDifferenceSoFar = Integer.MIN_VALUE;
		
		for(int i = 0; i < ar.length; i++){
			if(ar[i] < smallestElementSoFar)
				smallestElementSoFar = ar[i];
			
			int diff = ar[i] - smallestElementSoFar;
			if(diff > largestDifferenceSoFar)
				largestDifferenceSoFar = diff;
		}
		
		return largestDifferenceSoFar;
	}
	static int maxDifferenceBrute(int[] ar){
		TreeMap<Integer, Integer> map = new TreeMap<Integer, Integer>();
		for(int i =0; i < ar.length; i++)
			map.put(ar[i], i);
		
		int out = Integer.MIN_VALUE;
		for(int i =0; i < ar.length; i++){
			int maxDif = Integer.MIN_VALUE;
			
			Set<Integer> k = map.descendingKeySet();
			
			for(Integer key : k){
				if(map.get(key) > i){
					maxDif = key - ar[i];
					break;
				}
			}
			
			if(maxDif > out)
				out = maxDif;
		}
		
		return out;
	}
	
	static int twoMaxDifferencesSummed(int[] ar){
		/**Time O(n), space O(1)
		 * Return the maximum possible value for (ar[j0]-ar[i0]) + (ar[j1]-ar[i1])
		 * such that i0<j0<i1<j1.
		 */
		int smallestElementSoFar = Integer.MAX_VALUE,
			largestDifference1 = Integer.MIN_VALUE,
			largestDifference2 = Integer.MIN_VALUE,
			indexOfSmallestElement = 0,
			indexOfLargestElement = 0;
		
		for(int i=0; i < ar.length; i++){
			if(ar[i] < smallestElementSoFar){
				smallestElementSoFar = ar[i];
				indexOfSmallestElement = i;
			}
			
			int dif = ar[i] - smallestElementSoFar;
			if(dif > largestDifference1){
				largestDifference1 = dif;
				indexOfLargestElement = i;
			}
		}
		
		//reset smallest Element seen so far
		smallestElementSoFar = Integer.MAX_VALUE;
		
		//look only before the first interval 
		for(int i=0; i < indexOfSmallestElement; i++){
			
			if(ar[i] < smallestElementSoFar)
				smallestElementSoFar = ar[i];
				
				int dif = ar[i] - smallestElementSoFar;
				if(dif > largestDifference2)
					largestDifference2 = dif;
				
		}
		
		//reset smallest Element seen so far
		smallestElementSoFar = Integer.MAX_VALUE;
		
		//look only after the first interval 
		for(int i=indexOfLargestElement + 1; i < ar.length; i++){
			
			if(ar[i] < smallestElementSoFar)
				smallestElementSoFar = ar[i];
				
				int dif = ar[i] - smallestElementSoFar;
				if(dif > largestDifference2)
					largestDifference2 = dif;
				
		}
		
		return largestDifference1 + largestDifference2;
	}
	static int wrongApproach_TwoMaxDifferencesSummed(int[] ar){
		/**This approach only works when the global 
		 * largest difference comes before the 2nd largest difference.
		 */
		int smallestElement1 = Integer.MAX_VALUE,
			smallestElement2 = Integer.MAX_VALUE,
			largestDifference1 = Integer.MIN_VALUE,
			largestDifference2 = Integer.MIN_VALUE,
		    indexOfSmallestElement2 = 0,
		    indexOfLargestElement2 = 0;

		for(int i=0;i<ar.length;i++){
			
			if(ar[i] < smallestElement2){
				smallestElement2 = ar[i];
				indexOfSmallestElement2 = i;
			}
						
			if(i < indexOfSmallestElement2 && ar[i] < smallestElement1)
				smallestElement1 = ar[i];
			
			int tempDif = ar[i] - smallestElement2;
			if(tempDif > largestDifference2){
				largestDifference2 = tempDif;
				indexOfLargestElement2 = i;
			}
			
			tempDif = ar[i] - smallestElement1;
			if(i < indexOfLargestElement2 && tempDif > largestDifference1)
				largestDifference1 = tempDif;
		}
		
		return largestDifference1 + largestDifference2;
	}
	
	static int longApproach_kMaxDifferencesSummed(int[] ar, int k){
		/**Time O(kn), space O(k)
		 * Return the maximum possible value for Sum(ar[j_t]-ar[i_t]) for t=0:k-1
		 * such that i_t<j_t<i_t+1<j_t+1, ...
		 * Solution, at each k, look at all the segments that we havent taken anything from yet.
		 * Wrong because there is a better approach.
		 */
		int sumOfLargestDifferences = 0;
		LinkedList<Integer> lowerBounds = new LinkedList<>();
		LinkedList<Integer> upperBounds = new LinkedList<>();
		int currentSmallestElementSoFar = Integer.MAX_VALUE,
			currentLargestDifference = Integer.MIN_VALUE;
		
		lowerBounds.add(0);
		upperBounds.add(ar.length);

		for(int kk = 0; kk < k; kk++){
			
			int[] difs = new int[kk+1];
			int[] indexOfSmallestElements =  new int[kk+1];
			int[] indexOfLargestElements =  new int[kk+1];
			
			for(int l = 0; l < lowerBounds.size() - 1; l++){

				currentSmallestElementSoFar = Integer.MAX_VALUE;
				
				for(int i = lowerBounds.get(l); i < upperBounds.get(l); i++){
					if(ar[i] < currentSmallestElementSoFar){
						currentSmallestElementSoFar = ar[i];
						indexOfSmallestElements[l] = i;
					}
					
					int dif = ar[i] - currentSmallestElementSoFar;
					if(dif > currentLargestDifference){
						difs[l] = dif;
						indexOfLargestElements[l] = i;
					}
				}
					
			}

		}
		
		return 0;
	}
	static int wrongApproach_kMaxDifferencesSummed(int[] ar, int k){
		/**This approach is wrong, because we cannot merge the intervals that we've already
		 * picked from. We gottach check between individual intervals two. It is not enough
		 * to just chack before and after the merged interval.
		 */
		int currentSmallestElementSoFar = Integer.MAX_VALUE,
			indexOfSmallestElement = Integer.MAX_VALUE,
			indexOfLargestElement = Integer.MIN_VALUE,
			currentLargestDifference = Integer.MIN_VALUE,
			sumOfLargestDifferences = 0;
		
		//To the first pass with no boundaries
		for(int i =0; i<ar.length; i++){
			if(ar[i] < currentSmallestElementSoFar){
				currentSmallestElementSoFar = ar[i];
				indexOfSmallestElement = i;
			}
			
			int dif = ar[i] - currentSmallestElementSoFar;
			if(dif > currentLargestDifference){
				currentLargestDifference = dif;
				indexOfLargestElement = i;
			}
		}
		
		//do k-1 additional passes AND update the boundaries
		for(int kk = 1; kk < k; kk++){
			
			//reset smallest Element seen so far and currentLargestDifference
			currentSmallestElementSoFar = Integer.MAX_VALUE;
			sumOfLargestDifferences += currentLargestDifference;
			currentLargestDifference = Integer.MIN_VALUE;
			
			//look only before the first interval 
			for(int i=0; i < indexOfSmallestElement; i++){
				
				if(ar[i] < currentSmallestElementSoFar){
					currentSmallestElementSoFar = ar[i];
					indexOfSmallestElement = i;
				}
					
					int dif = ar[i] - currentSmallestElementSoFar;
					if(dif > currentLargestDifference)
						currentLargestDifference = dif;
					
			}
			
			//reset smallest Element seen so far
			currentSmallestElementSoFar = Integer.MAX_VALUE;
			
			//look only after the first interval 
			for(int i=indexOfLargestElement + 1; i < ar.length; i++){
				
				if(ar[i] < currentSmallestElementSoFar)
					currentSmallestElementSoFar = ar[i];
					
					int dif = ar[i] - currentSmallestElementSoFar;
					if(dif > currentLargestDifference){
						currentLargestDifference = dif;
						indexOfLargestElement = i;
					}	
			}
		}
		
		return sumOfLargestDifferences;
	}
	/******************END: MaxDifferences************************/
	
	
	/***************START: Sub-array summed to target *****************/
	static int numberOfSubArraysWithElementsLessThanK(int[] ar, int k){
		/**time O(n), space O(1) 
		 * Prob: Return the number of contiguous subArrays in ar, where all elements are < k. 
		 * Note: two adjacent elements < k, form 3 subarrays (two individuals 1 with both of them). 
		 */
		int count = 0;
		int countStep = 0;
		
		for(int i =0; i < ar.length; i++){
			if(ar[i] < k){
				countStep++;
				count += countStep;
			}
			else{
				countStep = 0;
			}
		}
		
		return count;
	}
	
	static int numberThatCannotBeRepresentedAsSum(int[] ar){
		/**time O(n), space O(1)
		 * 
		 * Prob: Given a sorted array, return the smallest integer than cannot be represented as sum of 
		 * any elements of ar. 
		 * 
		 * Sol: a number that is smaller than element i, but bigger than sum of all the elements before i
		 */
		int out = 1; //smallest we can return == sum+1
		
		for(int i =0; i < ar.length && ar[i] <= out; i++)
			out += ar[i];
		
		return out;
	}

	static boolean isThereSubArraySummedToTarget(int[] ar, int targetSum){
		/**Time O(targetSum*n) space O(targetSum). pseudo-polynomial time (i.e.
		 * function of largest integer in ar not size of ar).
		 * NP-Hard problem: return any (not contiguous) subset of ar whose elements 
		 * sum to targetSum. ar is an array of NONE-NEGATIVE integers.
		 */
		
		// The value of subset[i][j] will be true if there 
        // is a subset of ar[0..j-1] with sum equal to i
		boolean subset[][] = new boolean[targetSum+1][ar.length+1];
  
		// If targetSum is 0, then answer is true
		for (int i = 0; i <= ar.length; i++)
			subset[0][i] = true;
  
		// If sum is not 0 and set is empty, then answer is false
		for (int i = 1; i <= targetSum; i++)
			subset[i][0] = false;
  
		// Fill the subset table using a  bottom up manner dynamic programming technique
		for (int i = 1; i <= targetSum; i++) {
			for (int j = 1; j <= ar.length; j++) {
				subset[i][j] = subset[i][j-1];
				if (i >= ar[j-1])
					subset[i][j] = subset[i][j] || subset[i - ar[j-1]][j-1];
			}
		}
  
		// uncomment this code to print all the paths summing to the given value
		for (int i = 0; i <= ar.length; i++){
          		System.out.println(subset[targetSum][i]);
     	}
  
     return subset[targetSum][ar.length];
	}

	static int subArrayOfLengthKWithMaxAverage(int[] ar, int targetLength){
		/**time O(n), space O(1)
		 * 
		 * Prob: Return the starting index of the contiguous subArray of 
		 * length targetLength, that has maximum average. 
		 * 
		 * Sol: use leftPointer and rightPointer tergetLength points apart, slide accross
		 * the array calculating the avg, keeping track of starting index of the one with 
		 * max avg so far
		 */
		//sum of first k elements
		int curSum =0;
		for(int i =0; i< targetLength; i++)
			curSum += ar[i];
		
		int largestSum = curSum;
		int startingIndexOfBestSubArray = 0;
		
		//Remove 1st element, add new element
		for(int i = targetLength; i < ar.length; i++){
			curSum = curSum - ar[i-targetLength] + ar[i];
			
			if(curSum > largestSum){
				largestSum = curSum;
				startingIndexOfBestSubArray = i - targetLength + 1;
			}
		}
		
		return startingIndexOfBestSubArray;
	}
	static int timeButNotSpaceEfficient_subArrayOfLengthKWithMaxAverage(int[] ar, int targetLength){
		/**time O(n), space O(n)
		 */
		int[] sums = new int[ar.length];
		sums[0] = ar[0];
		
		for(int i = 1; i<ar.length; i++)
			sums[i] = sums[i-1]+ar[i];
		
		int largestSum = Integer.MIN_VALUE;
		int startingIndexOfBestSubArray = -1;
		
		for(int i =0; i <= ar.length - targetLength; i++){
			int curSum = sums[i+targetLength-1] - sums[i];
			
			if(curSum > largestSum){
				largestSum = curSum;
				startingIndexOfBestSubArray = i;
			}
		}
		
		return startingIndexOfBestSubArray;
	}	
	static int bruteForce_subArrayOfLengthKWithMaxAverage(int[] ar, int targetLength){
		/**time O(nk), space O(1)
		 */
		int maxSum = Integer.MIN_VALUE;
		int startingIndexOfBestSubArray = -1;
		
		for(int i =0; i <= ar.length - targetLength; i++){
			int sum = 0;
			
			for(int k = 0; k < targetLength; k++){
				sum += ar[i + k];
			}
			
			if(sum > maxSum){
				maxSum = sum;
				startingIndexOfBestSubArray = i;
			}
		}
		
		return startingIndexOfBestSubArray;
	}
	
	static int lenSmallestContiguousSubArrayBiggerEqualSum(int[] ar, int targetSum){
		/**Time O(n) because each element can be visited at most twice, once by the 
		 * rightIndex and (at most) once by the leftIndex. Space O(1)
		 * 
		 * Prob: Return the length of the smallest contiguous subarray whose sum is >= targetSum.
		 * 
		 * Sol: Start with a 0 length segment, grow it (incr rightPointer) till sum >= targetSum, 
		 * then shrink it as much as you can (inc leftPointer) while sum >= tagetSum. 
		 */
		int smallestLength = Integer.MAX_VALUE;
		int sum = 0;
		int leftIndex = 0;
		
		for(int rightIndex = 0; rightIndex < ar.length; rightIndex++){
			sum += ar[rightIndex];
			
	        while (sum >= targetSum) {
	        	smallestLength = Math.min(smallestLength, rightIndex - leftIndex + 1);
	            sum -= ar[leftIndex];
	            leftIndex++;
	        }
		}
	    
	    return smallestLength == Integer.MAX_VALUE ? -1 : smallestLength;
	}
	static int lessEfficient_lenSmallestContinuousSubArrayBiggerEqualSum_V1(int[] ar, int targetSum){
		/**Time O(n^2 worst), space O(n)
		 * Return the length of the SMALLEST, CONTIGUOUS subarray of ar whose sum >= targetSum.
		 */
		int[] sums = new int[ar.length];
		sums[0] = ar[0];
		for(int i = 1; i<ar.length; i++)
			sums[i] = sums[i-1]+ar[i];
		
		if(sums[sums.length-1] < targetSum)
			return -1;
		
		int smallestLength = Integer.MAX_VALUE;
		
		for(int i =0; i < sums.length; i++){
			
			if(sums[i] >= targetSum){
				int currentSum = sums[i];	
				int j = 0;
				
				while(currentSum >= targetSum){
					currentSum -= ar[j++];
				}
				
				int newLength = ((i+1) - (j - 1));
				if( newLength < smallestLength)
					smallestLength = newLength;		
			}
		}
		
		return smallestLength; 
	}
	static int lessEfficient_lenSmallestContinuousSubArrayBiggerEqualSum_V2(int[] ar, int targetSum){
		/**Time O(n^2 worst), space O(n)*/
		int[] prefixSum = new int[ar.length];
		int sum = 0;
		
		for(int i = 0; i < ar.length; i++){
			sum+= ar[i];
			prefixSum[i] = sum;
		}
		
		int smallestLengthSofar = Integer.MAX_VALUE;
		
		int i = ar.length - 1;
		
		while(i >= 0){
			
			if(prefixSum[i] >= targetSum){
				int j = i - 1;
				while(j >= 0 && (prefixSum[i]-prefixSum[j]) < targetSum){
					j--;
				}

				if((i - j) < smallestLengthSofar)
					smallestLengthSofar = i -j;
			}

			i--;
		}
		
		return smallestLengthSofar;
	}
	static int bruteForce_lenSmallestContinuousSubArrayBiggerEqualSum(int[] ar, int targetSum){
		int smallestLength = Integer.MAX_VALUE;
		
		for(int i =0; i< ar.length; i++){
			int sum = 0;
			int j = i;
			while(j< ar.length && sum < targetSum){
				sum += ar[j++];
			}
			
			int curLen = (j-1) - i;
			if(curLen < smallestLength)
				smallestLength = curLen;
		}
		
		return smallestLength;
	}
	
	static ArrayList<Integer> zeroModNSumSubset(int[] ar){
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
		int sum = 0;
		int[] sumsMod = new int[ar.length];
		for(int i = 0; i<ar.length; i++){
			sum += ar[i];
			sumsMod[i] = sum % ar.length;
		}
		
		ArrayList<Integer> out = new ArrayList<>();
		HashMap<Integer, Integer> map = new HashMap<>();
		
		for(int i =0; i < sumsMod.length; i++){
			if(sumsMod[i] == 0){
				for(int j = 0; j<= i; j++){
					out.add(j);
				}
				return out;
			}
			else if(map.containsKey(sumsMod[i])){
				for(int j =map.get(sumsMod[i])+1 ; j<= i; j++){
					out.add(j);
				}
				return out;
			}
			else{
				map.put(sumsMod[i], i);
			}
		}
		
		return out;
	}
	/*****************END: Sub-array summed to target *****************/
	
	
	/***************START:Longest Increasing Sub-array***********/
	static ArrayList<Integer> slightlyMoreEfficient_longestIncreasingSubArray(int[] ar){
		/**O(max(n/L,L)) time where L is the length of longest chain, O(1) space
		 * Return the start and end indices of longset increasing subArray in ar
		 */
		int maxLengthSoFar = 1;
		ArrayList<Integer> output = new ArrayList<>();
		output.add(0); //add temp final left and right indices
		output.add(0); 
		
		int i = 0;
		while( i < ar.length){
			boolean isSkippable = false;
			
			//check backwardly and skip if ar[j] >= ar[j+1]
			for(int j = i + maxLengthSoFar - 1; j >= i; --j){
				if(ar[j] >= ar[j+1]){
					i = j + 1;
					isSkippable = true;
					break;
				}
			}
			
			
			//check forwardly is not isSkippable
			if(!isSkippable){
				i += maxLengthSoFar + 1;
				while(i + 1 < ar.length && ar[i] < ar[i+1] ){
					++i;
					++maxLengthSoFar;
				}
				output.set(0, i - maxLengthSoFar);
				output.set(1, i);
			}
		}	
		
		return output; 
	}
	static ArrayList<Integer> longestIncreasingSubArray_v1(int[] ar){
		/**O(n) time, O(1) space
		 * Return the start and end indices of longset increasing subArray in ar
		 */
		int curLen = 1, curStartInd = 0, longestSoFar = 0;
		ArrayList<Integer> out = new ArrayList<Integer>();
		out.add(0);
		out.add(0);
		
		for(int i = 1; i < ar.length; i++){
			if(ar[i] > ar[i-1]){
				curLen++;
				
				if(curLen > longestSoFar){
					longestSoFar = curLen;
					out.set(0, curStartInd);
					out.set(1,i);
				}
					
			}
			else{
				curLen = 1;
				curStartInd = i;
			}
		}
		
		return out;
	} 
	static ArrayList<Integer> longestIncreasingSubArray_v2(int[] ar){
		/**O(n) time, O(1) space
		 * Return the start and end indices of longset increasing subArray in ar
		 */
		int maxLengthSoFar = Integer.MIN_VALUE;
		ArrayList<Integer> output = new ArrayList<>();
		output.add(0); //add temp final left and right indices
		output.add(0); 
		
		int leftIndex = 0;
		while( leftIndex < ar.length - 1){
		    int rightIndex = leftIndex + 1;
			
		    while(rightIndex < ar.length && ar[rightIndex-1] < ar[rightIndex])
				rightIndex++;
		    
		    int tempLength = rightIndex - leftIndex;
		    if(tempLength > maxLengthSoFar){
		    	maxLengthSoFar = tempLength;
		    	output.set(0, leftIndex);
		    	output.set(1, rightIndex - 1);
		    }
		    
		    leftIndex = rightIndex;
		}	
		
		return output; 
	}
	/***************END:Longest Increasing Sub-array*************/
	
	
	/***************START:ReOrder Array by Permutation***********/
	static void reOrder_spaceEfficient(int[] ar, int[] perm){
		/**space O(1), time O(n^2)
		 * Re order elements of A according to the indexes given in permutation.
		 * Ex: A[1,2,3], perm=[1,0,2], result=[2,1,3].
		 * 
		 * Sol: Go through the perms, as you do each one, save the element of the original array you are
		 * replacing (and its index) in temp. Then loop through your perms to find the location of temp.
		 * 
		 * This is mySol and has a bug with 	int[] ar = {0,1,2,3,4} and int[] perm = {4,2,3,0,1};
		 */
		//do the first element to initialize things
		int tempIn = 0;
		int temp = ar[0];
		ar[0] = ar[perm[0]];
		perm[0] = -1;
		
		for(int permInd = 0; permInd < perm.length; permInd++){
			if(perm[permInd] >= 0){ //If we havent done this one yet, do whats in temp first, then this one.
				for(int i = 0; i < perm.length; i++){
					if(perm[i] == tempIn){
						int tempIn2 = i;
						int temp2 = ar[i];
						ar[i] = temp;
						
						tempIn = tempIn2;
						temp = temp2;
						
						perm[i] = -1;
						break;
					}
				}
			}
		}
	}

	static int[] reOrder_timeEfficient(int[] A, int[] permutation){
		/**space O(n), time O(n)
		 * Re order elements of A according to the indexes given in permutation.
		 * Ex: A[1,2,3], perm=[1,0,2], result=[2,1,3].
		 */
		int[] out = new int[A.length];
		for(int i = 0; i < permutation.length; i++){
			out[i] = A[permutation[i]];
		}
		return out;
	}
	/***************END:ReOrder Array by Permutation*************/
	
	
	/******************START: Rotate An Array********************/
	static void rotate_inPlace(int[] A, int pos){
		/**time: O(n), space O(1)
		 * Prob: Rotate A by "pos" positions i.e Put the first pos elements at the end of the array
		 * 
		 * Didnt do this one 
		 */
		for(int i=0; i < A.length; i++){
			int temp = A[i];
			A[i] = A[(i+pos) % A.length];
			//A[] = temp;
			//TODO
		}
	}
	
	static int[] rotate_notInPlace(int[] A, int pos){
		/**time: O(n), space O(n)
		 * Rotate A by "pos" positions. i.e Put the first pos elements at the end of the array
		 */
		int[] out = new int[A.length];
		
		for(int i=0; i < A.length; i++)
			out[i] = A[(i+pos) % A.length];
		
		return out;
	}
	/******************END: Rotate An Array**********************/


	
	public static void main(String[] args) {
		int[] a = {0,1,2,3,4};
		int[] p = {4,2,3,0,1};
		
		reOrder_spaceEfficient(a, p);
		
		for(int i = 0; i < a.length; i++)
		System.out.println(a[i]);
	}
}