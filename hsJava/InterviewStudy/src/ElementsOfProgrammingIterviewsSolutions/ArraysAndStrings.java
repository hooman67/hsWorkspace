package ElementsOfProgrammingIterviewsSolutions;

import java.util.*;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;

public class ArraysAndStrings {
	/************START: return k biggest/smallest elements************/
	static int[] getK_Elements_selectionRank(int[] in, int k, boolean biggest){
		/**time O(n), space O(log n) due to recursion
		 * Returns the k biggest or smallest elements of in
		 */
		//TODO
		return null;
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
	
	
	/************START: return the median (middle element)************/
	class KeepMidian{
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
	/**************END: return the median (middle element)************/
	
	
	/**************START: Dutch Partitioning an array******************/
	static void dutchPartition(int[] ar, int pivotIndex){
		/**O(1) space, O(n) time. 
		 * 
		 * Prob: Partition based on 3 key values.Example(below piv, equal piv, above piv)
		 * 
		 * Sol: We keep the following groups during partitioning:
		 * buttom group: ar[0 : smaller-1],
		 * equal group: ar[smaller : equal -1],
		 * top group: ar[ larger+1 : ar.length -1] 
		 * unclassified group: ar[equal : larger] 
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
	
	
	/******************START: Sum of Sub-array********************/
	static int numberOfSubArraysWithElementsLessThanK(int[] ar, int k){
		/**time O(n), space O(1) 
		 * Return the number of contiguous subArrays of ar, where all elements are < k
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
		 * return the smallest integer than cannot be represented as sum of any elements of ar. 
		 * Solution: a number that is smaller than element i, but is bigger than sum of all the 
		 * elements before i
		 */
		//smallest we can return
		int out = 1;
		
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
		 * Sol: Start with a 0 length segment, grow it (incr rightPointer) till sum > targetSum, 
		 * then shrink it as much as you can (inc leftPointer) while sum > tagetSum. 
		 */
		int smallestLength = Integer.MAX_VALUE;
		int sum = 0;
		int leftIndex = 0;
		
		for(int rightIndex = 0; rightIndex < ar.length; rightIndex++){
			sum += ar[rightIndex];
			
	        while (sum >= targetSum) {
	        	smallestLength = Math.min(smallestLength, rightIndex - leftIndex + 1);
	            sum -= ar[leftIndex++];
	        }
		}
	    
	    return smallestLength == Integer.MAX_VALUE ? -1 : smallestLength;
	}
	static int lessEfficient_lenSmallestContinuousSubArrayBiggerEqualSum(int[] ar, int targetSum){
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
	/******************END: Sum of Sub-array**********************/
	
	
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
	static ArrayList<Integer> longestIncreasingSubArray(int[] ar){
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
	static void reOrder_spaceEfficient(int[] A, int[] permutation){
		/**space O(1), time O(n^2)
		 * Re order elements of A according to the indexes given in permutation.
		 * Ex: A[1,2,3], perm=[1,0,2], result=[2,1,3].
		 */
		//do the first element to initialize things
		int tempInd = 0;
		int temp = A[tempInd];
		A[tempInd] = A[permutation[0]];
		permutation[0] -= permutation.length;
		
		//do the rest
		int permInd = 1;
		while(permInd < permutation.length){
			if(permutation[permInd] < 0)//already visited this permutation
				permInd++;
			else{
				
				for(int i = 0; i < permutation.length; i++){
					if(permutation[i] == tempInd){
						int tempInd2 = i;
						//set to visited (denoted by  negetive value)
						permutation[i] -= permutation.length;

						int temp2 = A[tempInd2];
						A[tempInd2] = temp;

						tempInd = tempInd2;
						temp = temp2;
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
		 * Rotate A by "position" positions: 
		 * Put the first pos elements at the end of the array
		 */
		for(int i=0; i < A.length; i++){
			int temp = A[i];
			A[i] = A[(i+pos) % A.length];
			//A[] = temp;
		}
	}
	static int[] rotate_notInPlace(int[] A, int pos){
		/**time: O(n), space O(n)
		 * Rotate A by "position" positions: 
		 * Put the first pos elements at the end of the array
		 */
		int[] out = new int[A.length];
		
		for(int i=0; i < A.length; i++)
			out[i] = A[(i+pos) % A.length];
		
		return out;
	}
	/******************END: Rotate An Array**********************/
	
	
	/******************START: RunLength Encoding*****************/
	static void runLengthEncode(StringBuffer s){
		/**O(n) time, O(1) space
		 */
		int count = 1;
		int outInd = 0;
		for(int i = 1; i < s.length(); i++){
			if(s.charAt(i-1) == s.charAt(i))
				count++;
			else{
				s.setCharAt(outInd++, s.charAt(i-1));
				while(count > 1){
					int temp = count % 10;
					s.setCharAt(outInd++, (char)(temp+'0'));
					count -= temp;
					count /= 10;
				}
				count = 1;
			}
		}
		s.setCharAt(outInd++, s.charAt(s.length()-1));
		while(count > 1){
			int temp = count % 10;
			s.setCharAt(outInd++, (char)(temp+'0'));
			count -= temp;
			count /= 10;
		}
		
		s.setLength(outInd);
	}
	
	static String runLengthEncode(String s){
		/**O(n) time, O(n) space. Can do in C++ in O(1) space but not java unless input is StringBuffer
		 */
		int count = 1;
		StringBuffer buf = new StringBuffer();
		for(int i =1; i < s.length(); i++){
			if(s.charAt(i) == s.charAt(i-1)){
				count++;
			}
			else{
				buf.append(s.charAt(i-1));
				if(count > 1)
					buf.append(Integer.toString(count));
				count = 1;
			}
		}
		
		buf.append(s.charAt(s.length()-1));
		if(count > 1)
			buf.append(Integer.toString(count));
		
		return buf.toString();
	}

	static String runLengthDecode(String s){
		StringBuffer out = new StringBuffer();
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) >= '0' && s.charAt(i) <= '9'){
				int count = ((int)(s.charAt(i)-'0')) - 1;
				while(count-- > 0 ){
					out.append(s.charAt(i-1));
				}
			}
			else{
				out.append(s.charAt(i));
			}
		}

		return out.toString();
	}
	/******************END: RunLength Encoding*******************/
	
	
	/******************START: Find SubString*********************/
	/******************END: Find SubString***********************/
	
	
	/*********START: Reverse the order of words******************/
	static void reverseWords(StringBuffer s){
		/**time O(n), space O(1)
		 */
		reverseString(s, 0, s.length());
		int start = 0;
		
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == ' '){
				reverseString(s, start, i);
				start = i+1;
			}
		}
		
		reverseString(s, start, s.length());
	}
	static void reverseString(StringBuffer s, int start, int end){
		/**time O(n), space O(1)
		 */
		for(int i = 0; i < (end-start)/2; i++){
			char temp = s.charAt(i+start);
			s.setCharAt(i+start, s.charAt(end-1 - i));
			s.setCharAt(end-1 - i, temp);
		}
	}
	/***********END: Reverse the order of words******************/
	
	
	/***START: in sorted string replace 'a' with "dd" and delete 'b'***/	
	static void subst(StringBuffer s){
		/**Time O(n), space O(1), uses reverseString(s, start, end) above */
		int acount = 0;

		while(s.charAt(acount) == 'a' && acount < s.length())
			acount++;

		int dcount = acount*2;

		int endInd = s.length();
		for(int i =0; i < dcount; i++){
			if(s.charAt(i) == 'a' || s.charAt(i) == 'b')
				s.setCharAt(i, 'd');
			else{
				s.setCharAt(--endInd, s.charAt(i));
				s.setCharAt(i, 'd');
			}
		}
		
		for(int i = dcount; i < s.length(); i++){
			if(s.charAt(i) == 'b')
				s.delete(i, i+1);
		}
		
		if(endInd != s.length())
			reverseString(s, dcount, s.length());
	}
	/*****END: in sorted string replace 'a' with "dd" and delete 'b'***/
	
	
	/*********START: String Matching******************/
	static int rabin_karp(String text, String s){
		/**time O(len.text + len.s), space O(1), 
		 * Return: Index of first match of s in text, -1 if not found
		 * matches the hashcode of s to hashcode of len.s size segments of text.
		 */
		if(s.length() > text.length())
			return -1;
		
		final int BASE = 26, MOD = 997;
		int s_hash = 0, text_hash = 0;
		
		//calc s_hash, and first text_hash segment
		for(int i =0; i < s.length(); i++){
			s_hash = (s_hash*BASE + s.charAt(i)) % MOD;
			text_hash = (text_hash*BASE + text.charAt(i)) % MOD;
		}
		
		//calc the rest of text_hash segments
		for(int i = s.length(); i < text.length(); i++){
			//if collision match the strings to make sure 
			if(s_hash == text_hash && s.compareTo( text.substring(i - s.length(), i) ) == 0){
				return i - s.length(); //found match
			}
			
			//take the first char out of hashcode for text
			text_hash -= ( text.charAt(i - s.length()) * (int)(Math.pow(BASE, s.length()-1)) )  % MOD;
			
			if(text_hash < 0)
				text_hash += MOD;
			
			//add the next char's hashcode 
			text_hash = (text_hash*BASE + text.charAt(i)) % MOD;
		}
		
		//match the last piece
		if(s_hash == text_hash && s.compareTo( text.substring(text.length() - s.length(), s.length()) ) == 0){
			return text.length() - s.length(); //found match
		}
		
		return -1; //s is not a substr in text
	}
	/***********END: String Matching******************/

	
	/***START: Print all posble phone nemonic representations of a nb***/
	static void phone_mnemonic(String num){
		String[] map = {"0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"};
		StringBuffer ans = new StringBuffer(num);
		phone_mnemonic_helper(num, map, ans, 0);
	}
	static void phone_mnemonic_helper(String num, String[] map, StringBuffer ans, int d){
		if(d >= num.length())
			System.out.println(ans);
		else{
			for(char c : map[num.charAt(d) - '0'].toCharArray()){
				ans.setCharAt(d, c);
				phone_mnemonic_helper(num, map, ans, d+1);
			}
		}
	}
	/***end: Print all posble phone nemonic representations of a nb***/
	
	
	/***************START: String Multiplication of BigInts *************/
	class BigInt{
		int sign = 1; //1 or -1
		public char[] digits;
		
		BigInt(int capacity){
			digits = new char[capacity];
		}
		
		BigInt(String nb){
			sign = nb.charAt(0) == '-' ? -1 : 1;
			int tempSign = nb.charAt(0) == '-' ? 1 : 0;
			digits = new char[nb.length() - tempSign];
			
			int j = 0;
			for(int i = nb.length() - 1; i >= (tempSign); i--){
				if( nb.charAt(i) >= '0' && nb.charAt(i) <= '9')
					digits[j++] = (char)(nb.charAt(i) - '0');
			}
		}
		
		BigInt multiply(BigInt nb2){
			//If one nb is 0 out should be 0
			if(digits.length == 1 && digits[0] == (char)('0' - '0') || 
					nb2.digits.length == 1 && nb2.digits[0] == (char)('0' - '0')) {
				return new BigInt("0");
			}
			
			BigInt out= new BigInt(digits.length + nb2.digits.length);
			out.sign = sign*nb2.sign;
			
			int i,j;
			for(i = 0; i < nb2.digits.length; i++){
				int carry = 0;
				for(j=0; j < digits.length || (carry > 0); j++){
					int n_digit = out.digits[i+j] + 
							(j < digits.length ? nb2.digits[i] * digits[j] : 0) + carry;
					out.digits[i+j] =  (char)(n_digit%10);
					carry = n_digit / 10;
				}
			}
					
			return out;
		}
	}
	/*****************END: String Multiplication of BigInts *************/
	
	
	public static void main(String[] args) {

	}
}