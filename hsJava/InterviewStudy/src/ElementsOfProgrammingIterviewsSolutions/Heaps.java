package ElementsOfProgrammingIterviewsSolutions;

import java.util.*;

import ElementsOfProgrammingIterviewsSolutions.StacksAndQueues.Pair;

public class Heaps {
	static class Pair implements Comparable<Pair>{//used in merge and findMaxElement
		Integer first, second;
		
		Pair(int val1, int val2){
			this.first = val1;
			this.second = val2;
		}
		
		@Override
		public int compareTo(Pair arg0) {
			//NOTsE: whatever you use here, your maxQueue will operate based on.
			return first.compareTo(arg0.first);
		}
	}
	
	/**********************START: Merge K sorted files**********************/
	static ArrayList<Integer> merge(ArrayList<ArrayList<Integer>> files){
		/**time O(n log k), space O(k) with k being #OfFiles and n being size of each. 
		 * 
		 * Prob: Given many small sorted files, merge them all into 1 big sorted file. 
		 * Also, files dont need to have the same length.
		 * 
		 * Sol: keep a minHeap of size k, take 1st row of all k files. Then, pop the heap
		 * (add it to output) then enqueue the next element in the same file whose element you 
		 * popped. So you need to store pairs in the heap, to know which file it belongs to, and 
		 * also need a way to keep track of where you are in each file (fileBookmark).  
		 */
		ArrayList<Integer> out = new ArrayList<>();
		PriorityQueue<Pair> minHeap = new PriorityQueue<Pair>();
		int[] fileBookmark = new int[files.size()];
		
		//Add the first row of all files to initialize things.
		for(int i = 0; i < files.size(); i++){
			minHeap.add( new Pair(files.get(i).get(0), i) );
			fileBookmark[i] = 1;
		}
		
		while(!minHeap.isEmpty()){
			Pair cur = minHeap.poll();
			out.add(cur.first);
			
			//If we haven't reached the end of this file, add its next element
			if(fileBookmark[cur.second] < files.get(cur.second).size())
				minHeap.add(new Pair(files.get(cur.second).get(fileBookmark[cur.second]++), cur.second) );
		}
		
		return out;
	}
	
	static ArrayList<Integer> merge_Wrong(ArrayList<ArrayList<Integer>> files){
		/**time O(n log k), space O(k) with k being #OfFiles and n being size of each. 
		 * 
		 * Prob: Given many small sorted files, merge them all into 1 big sorted file.
		 * 
		 * Sol: allocate a minHeap of size k, take one row of all k files, and load it into heap, 
		 * load the min from heap to output.
		 */
		ArrayList<Integer> out = new ArrayList<>();
		
		PriorityQueue<Integer> minHeap = new PriorityQueue<>();
		
		for(int i = 0; i < files.get(0).size(); i++){
			
			for(ArrayList<Integer> file : files)
				minHeap.add(file.get(i));
			
			while(!minHeap.isEmpty())
				out.add(minHeap.poll());
		}
		
		return out;
	}
	/************************END: Merge K sorted files**********************/
	
	
	/**************START: Sort a K-Increasing-Decreasing Array**************/
	static ArrayList<Integer> sortIncDecArray(int[] ar){
		/**time O(n log k), space O(k) aside from the O(n) needed for output.
		 * 
		 * Prob: you have k increasing or decreasing segments in ar. Assumes the first 
		 * segment is increasing (initializes isIncreasing to true), but doesn't care 
		 * about the last (specifically checks before reversing the last). 
		 * 
		 * Sol: break down ar into k individual segments, reverse the decreasing ones,
		 * merge the k sorted arrays according to above (Merge K sorted files). If you check
		 * for size < 2, you can initialize isIncreasing depending on the first two elements, 
		 * and then you don't need to assume that the first segment is increasing.
		 */
		ArrayList<ArrayList<Integer>> files = new ArrayList<>();
		boolean isIncreasing = true;
		
		files.add(new ArrayList<Integer>());
		files.get(0).add(ar[0]);
		
		for(int i = 1; i < ar.length; i++){
			if(ar[i-1] > ar[i] && isIncreasing){
				files.add(new ArrayList<Integer>()); //add this element to a new array
				isIncreasing = false;
			}
			else if(ar[i-1] <= ar[i] && !isIncreasing){
				reverseArrayList_helper(files.get(files.size()-1)); //reverse this array
				files.add(new ArrayList<Integer>()); //add this element to a new array
				isIncreasing = true;
			}
			
			files.get(files.size()-1).add(ar[i]);
		}
		
		//Reverse the last array if it was decreasing
		if(!isIncreasing)
			reverseArrayList_helper(files.get(files.size()-1));
		
		System.out.println(files);
		return merge(files); //Merge the k files into 1
	}
	static void reverseArrayList_helper(ArrayList<Integer> ar){
		for(int i = 0; i < ar.size()/2; i++){
			int temp = ar.get(i);
			ar.set(i, ar.get(ar.size() - 1 - i));
			ar.set(ar.size() - 1 - i, temp);
		}
	}
	/****************END: Sort a K-Increasing-Decreasing Array**************/
	
	
	/************START: Track the median (middle element)************/
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
	/**************END: Track the median (middle element)************/
	
	
	/*****START: Generate the first k numbers of the form a+bSqrt(2)*****/
	static class Num implements Comparable<Num>{
		int a, b;
		Double val;
		Num(int a, int b){
			this.a = a;
			this.b = b;
			this.val = a + b*Math.sqrt(2);
		}
		
		@Override
		public int compareTo(Num arg0) {//So minQ.poll() returns smallest based on val
			return this.val.compareTo(arg0.val);
		}
		
		@Override
		public boolean equals(Object arg0) {//So minQ.contains() returns based on val
			return val.equals( ((Num)arg0).val );
		}
	}
	static ArrayList<Double> generateFirstKnbs(int k){
		ArrayList<Double> out = new ArrayList<Double>();
		
		PriorityQueue<Num> minQ = new PriorityQueue<Num>();
		minQ.add(new Num(0,0));
		
		while(out.size() < k){
			Num cur = minQ.poll();
			out.add(cur.val);
			
			Num firstNumFromCur = new Num(cur.a+1, cur.b);
			Num secondNumFromCur = new Num(cur.a, cur.b+1);
			
			if(!minQ.contains(firstNumFromCur))
				minQ.add(firstNumFromCur);
			
			if(!minQ.contains(secondNumFromCur))
				minQ.add(secondNumFromCur);
		}
		
		return out;
	}
	/*******END: Generate the first k numbers of the form a+bSqrt(2)*****/

	
	/*****START: Compare Kth largest element in maxHeap with x*****/
	static int compareKthWithX(PriorityQueue<Integer> heap, Integer x, int k){
		/**time O(k), space O(k) due to recursion.
		 * 
		 * Prob: Assumes k = 1, refers to the top of the heap. So k >= 1 always.
		 * 
		 * Sol: We do things recursively as to not change the heap thats why we need O(k) space.
		 * Otherwise, we could just pop the heap k times and compare with top, using O(1) space. 
		 */
		return compareKthWithX_helper(heap, x, k, 0);
	}
	static int compareKthWithX_helper(PriorityQueue<Integer> heap, Integer x, int k, int curK){
		if(curK == k-1){
			System.out.println(heap.peek());
			return heap.peek().compareTo(x);
		}
		
		Integer temp = heap.poll();
		int out = compareKthWithX_helper(heap, x, k, curK+1);
		heap.add(temp);
		
		return out;
	}
	/*****START: Compare Kth largest element in maxHeap with x*****/
	
	
	public static void main(String[] args){
		PriorityQueue<Integer> minQ = new PriorityQueue<>();
		
		minQ.add(7);
		minQ.add(6);
		minQ.add(5);
		minQ.add(4);
		minQ.add(3);
		minQ.add(2);
		minQ.add(1);

		System.out.println(compareKthWithX(minQ,3 , 3));
		 

	}
}
