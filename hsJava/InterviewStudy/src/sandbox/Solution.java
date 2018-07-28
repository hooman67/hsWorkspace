package sandbox;

import java.util.*;
// 1. min/ max proiority queus. How to specify your own comparator. 
// 2. Sorting and binary search using Collection for lists.  and Arrays. for arrays. ArrayList has sort too.
// 3. Methods for the data structures
// 4. Iterators, removing elements with them too.
// 5. Template classes


class Pair<K>{
	Integer first;
	K second;
	
	public Pair(Integer first, K second){
		this.first =first;
		this.second = second;
	}

	public void print(){
		System.out.println(first + "  " + second);
	}

}




public class Solution{
	
		
	public static void main(String[] args){
		
		PriorityQueue<Pair<Integer>> minQ = new  PriorityQueue<Pair<Integer>>(new Comparator<Pair<Integer>>(){
			@Override
			public int compare(Pair<Integer> arg0, Pair<Integer> arg1) {
				return arg0.first - arg1.first;
			}
		});
		
		minQ.add( new Pair<Integer>(11, 9));
		minQ.add( new Pair<Integer>(4, 5));
		minQ.add( new Pair<Integer>(9, 4));
		minQ.add( new Pair<Integer>(5, 1));
		
		
		while(!minQ.isEmpty()){
			minQ.peek().print();
			minQ.poll();
		}

	}
}