package ElementsOfProgrammingIterviewsSolutions;
import java.util.*;

import ElementsOfProgrammingIterviewsSolutions.BinaryTreeNode;
import ElementsOfProgrammingIterviewsSolutions.ListNode;

public class StacksAndQueues {
	static class Pair implements Comparable<Pair>{//used in maxStack and findMaxElement
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

	
	/***************START: Find Max element in a sliding window***************/
	static int[] findMaxElementInWindow(Pair[] ar, int windowSize){
		/**time O(n), space O(m) extra. where m is the number of elements that fit in a given window.
		 * 
		 * Prob: the first element in ar is a timestamp the second is a volum. Ar is sorted 
		 * according to timestamp. given a windowSize (w), output[i] should give the maxVolume 
		 * in ar[i-w to w]. So size of out is the same as ar.
		 * 
		 * Sol: put each element in a MaxQueue, pop the queue till the difference btw timeStamp of current
		 * element and front of queue is <= w. For a given i, the q.getMax() gives you the answer.
		 */
		int[] out = new int[ar.length];
		
		MaxQueue_WithDeque<Pair> q = new MaxQueue_WithDeque<Pair>();

		for(int i =0 ; i < ar.length; i++){
			q.enqueue(ar[i]);

			try {
				while((ar[i].first - q.peak().first) > windowSize)
					q.dequeue();

				out[i] = q.getMax().second;
			} catch (Exception e) {e.printStackTrace();}
		}

		return out;
	}
	/*****************END: Find Max element in a sliding window***************/
	
	
	/***START: Views Of the Sunset***/
	static Stack<ListNode<Integer>> canSeeSunset(ListNode<Integer> node){
		/**Time O(n) each node is pushed and popped at most ones, space O(#OfBuildingsWithView)
		 * 
		 * Problem: given a list of buildings heights, from east to west. If building A is
		 * to the east of building B (comes before it in the list) it can view the sunset 
		 * only if its height is bigger than height of building B. Return the buildings that 
		 * can see the sunset, with O(#OfBuildingsWithView) being the maximum amount of 
		 * additional space you can use.
		 *	 
		 * Sol: Keep a stack with all the buildings that can see the sunset. For each new building, 
		 * pop the stack, as long as the peak is smaller than the new building.
		 */
		Stack<ListNode<Integer>> out = new Stack<>();
		out.push(node);
		ListNode<Integer> cur = node.next;
		
		while(cur != null){
			while(!out.empty() && out.peek().data < cur.data)
				out.pop();
			out.push(cur);
		}
		
		return out;
	}
	
	/**If the list of buildings was given in west to east order, aside from our list/stack of 
	 * buildings with a view, we would keep track of the largest element seen so far. When 
	 * processing a new building, if it is smaller thatn the largestElementSoFar, we wont
	 * add it (just move on). If it is bigger than (or equal) the largestElementSoFar, 
	 * we add it to our stack, and update the largestElementSoFar if needed.
	 */
	/*****END: Views Of the Sunset***/
	

	/****START: design a stack that also track the max element in the stack****/
	static class MaxStack{
		/**time O(1) all ops, space O(n) extra space. So O(2n) in total.
		 * Prob: Return the max element in a stack. As well as regular stack functionality.
		 *
		 * Sol: In the stack store a pair. Where the first element is the value, and second element
		 * is the maximum value in the stack after the value was added. So the maxof(new value, old max).
		 */

		Stack<Pair> stack = new Stack<Pair>();
		
		void push(int val){
			if(stack.empty())
				stack.push(new Pair(val, val));
			else
				stack.push(new Pair(val, Math.max(val, stack.peek().second)));

			return;
		}
		
		Integer pop() throws Exception{
			if(stack.empty())
				throw new Exception("stack is empty");
			else
				return stack.pop().first;
		}
		
		Integer getMax() throws Exception{
			if(stack.empty())
				throw new Exception("stack is empty");
			else{
				return stack.peek().second;
			}
		}
		
		int size(){
			return stack.size();
		}
	}
	
	static class MaxStack_SlightlyMoreSpaceEfficient{
		/**time O(1) all ops, space O(n) extra space worst case (each new elment is max) 
		 * O(1) extra space best case.
		 *
		 * Prob: Return the max element in a stack. As well as regular stack functionality.
		 *
		 * Sol: Use two stacks, the first one is just a normal stack storing values. 
		 * The second (auxilary) stack stores pairs where the 1st element is the max element in stack
		 * and the second element is the count of how many copies of that element we have in the stack.
		 * 
		 * In add, if new element is smaller than top of aux_stack do nothing, otherwise, update the 
		 * count (if equal) or add the element with count of 1 if bigger. In remove, if removing the
		 * (max) element at the top of the aux_stack, update its count.
		 */

		Stack<Integer> stack = new Stack<Integer>();
		Stack<Pair> auxStack = new Stack<Pair>();
	
		Integer getMax() throws Exception{
			if(stack.empty())
				throw new Exception("stack is empty");
			else
				return auxStack.peek().first;
		}
		
		void push(int val){
			stack.push(val);
			
			if(auxStack.empty() || val > auxStack.peek().first)
				auxStack.push(new Pair(val,1));
			
			else if(val == auxStack.peek().first)
				auxStack.peek().second++;
			
			return;
		}
		
		Integer pop() throws Exception{
			if(stack.empty())
				throw new Exception("stack is empty");
			else{
				Integer out = stack.pop();
				
				if(out == auxStack.peek().first){
					if(--auxStack.peek().second <= 0)
						auxStack.pop();
				}
				
				return out;
			}
		}
	}
	/******END: design a stack that also track the max element in the stack****/
	
	
	/****START: design a queue that also track the max element in the queue****/
	static class MaxQueue_WithDeque<T extends Comparable<T>>{
		/**time O(1) all ops, space O(n) extra (so O(2*n) in total)
		 * 
		 * Prob: Return the max element in a queue. As well as regular queue functionality.
		 *
		 * Sol: Keep the elements that can be MaxElement in a Deque. MaxElement is always
		 * the head of the Deque. On popping an element, if it is the element at the head 
		 * of the Deque, pop the Deque. Otherwise, do nothing. In adding an element, if it 
		 * is bigger than the tail of the queue (==smallest element in the queu) then pop the tail
		 * of the queue, for as long as it is smaller, then add the new element to the tail. 
		 * 
		 * Rational: If for an element i, there is an element j where  j>i and ar[j] > ar[i]. Then 
		 * element i can never be the maxElement because there is a bigger element (j) that gets 
		 * dequeue after i. So in the Deque, each element is larger than all the elements that come 
		 * after it (in both deque and queue). 
		 */
		Deque<T> deq = new LinkedList<T>();
		Queue<T> q = new LinkedList<T>();
		
		T getMax() throws Exception{
			if(q.isEmpty())
				throw new Exception();
			
			return deq.getFirst();
		}
		
		void enqueue(T el){
			while(!deq.isEmpty() && deq.getLast().compareTo(el) < 0)
				deq.pollLast();

			deq.addLast(el);
			q.add(el);				
		}
		
		T dequeue() throws Exception{
			if(q.isEmpty())
				throw new Exception();
			
			if(q.peek() == deq.getFirst())
				deq.pollFirst();
			
			return q.poll();
		}
		
		int size(){
			return q.size();
		}
		
		T peak(){
			return q.peek();
		}
	}
	
	static class MaxQueue_WithTwoMaxStacks{
		/**time O(1) all ops, space O(n) extra (so O(2*n) in total)
		 * 
		 * Prob: Return the max element in a queue. As well as regular queue functionality.
		 *
		 * Sol: Exactly the same as QueueWithTwoStacks except use maxStacks so that to return
		 * max element in queue you just return max btw two stacks.
		 * 
		 * Rational: We wanna keep our maxElements in a stack, because when one pops, we wanna default
		 * to the latest maxElement before the one that was just poped. This suggests that the 
		 * best data structure for this would be implementing the queue using two stacks, both capable 
		 * of returning max element. This can do enqueue, dequeue, and getMax
		 * all in O(1) ammortized time.
		 */
		MaxStack st1 = new MaxStack(), st2 = new MaxStack();
		
		int getMax() throws Exception{
			if(st1.size() == 0)
				return st2.getMax();
			else if(st2.size() == 0)
				return st1.getMax();
			else
				return Math.max(st1.getMax(), st2.getMax());
		}
		
		void enqueue(int el){
			st1.push(el);				
		}
		
		int dequeue() throws Exception{
			if(st1.size() == 0 && st2.size() == 0)
				throw new Exception();
			
			if(st2.size() != 0)
				return st2.pop();
			
			while(st1.size() != 0)
				st2.push(st1.pop());
			
			return st2.pop();
		}
		
		int size(){
			return st1.size() + st2.size();
		}
		
	}
	/******END: design a queue that also track the max element in the queue****/
	
	
	/***START: Sort a stack with largest element on top using only stack ops***/
	static Stack<Integer> sortTheStack_NonRecursive(Stack<Integer> st){
		/**O(n^2), space O(n)
		 * Sol: use an additional stack (hs solution)
		 */
		Stack<Integer> sorted = new Stack<>();
		
		while(!st.empty()){
			Integer cur = st.pop();
			
			if(sorted.empty() || cur >= sorted.peek())
				sorted.push(cur);
			else{
				while(!sorted.empty() && cur < sorted.peek())
					st.push(sorted.pop());
				
				sorted.push(cur);
			}
		}
		
		return sorted;
	}
	
	static void sortTheStack(Stack<Integer> st){
		/**time O(n^2), space O(n) due to recursion.
		 * 
		 * Sol: we need two extra stacks, so we use two recursive methods. 
		 * Pop the stack and store the element, sort the rest of the stack, then insert 
		 * the element back (base case, when stack is empty). 
		 * InsertIntoStack is cursive too, if stack is empty or peak is smaller than element 
		 * (base case) just push the element in. Otherwise, pop the current peak, store it, call
		 * the method again to insert the element now that the peak is poped. Then push the element
		 * you poped (now that you know the previous element is in the stack).
		 * 
		 */
		if(!st.empty()){//st.empty()  is base case which just returns
			Integer element = st.pop();
			sortTheStack(st);
			insertIntoStack_helper(st, element);
		}
	}
	static void insertIntoStack_helper(Stack<Integer> st, Integer element){
		if(st.empty() || element >= st.peek())//This is the base case
			st.push(element);
		else{
			Integer tempElement = st.pop();
			insertIntoStack_helper(st, element);
			st.push(tempElement);
		}
	}
	/*****END: Sort a stack with largest element on top using only stack ops***/
	
		
	/***START: Jump first traversal of PostingsList (with visited fields in nodes)***/
	static void traverseJumpFirst_bookVer(ListNode<Integer> node){
		/**time O(n), space O(n) due to stack
		 * Prob: Traverse the PostingsLinkedList JumpFirstOrder: if the jump node leads to a node
		 * we've'nt seen before, or the next node if we've seen the jump node already.
		 */
		Stack<ListNode<Integer>> st = new Stack<>();
		st.push(node);
		
		while(!st.empty()){
			ListNode<Integer> cur = st.pop();
			
			if(cur.visited == -1){
				System.out.println(cur.data);
				
				cur.visited = 0;
				
				st.push(cur.next);
				st.push(cur.jump);
			}
		}
	}
	
	static void traverseJumpFirst_myVer(ListNode<Integer> node){
		/**time O(n), space O(n) due to stack
		 * Prob: Traverse the PostingsLinkedList JumpFirstOrder: if the jump node leads to a node
		 * we've'nt seen before, or the next node if we've seen the jump node already.
		 * 
		 * MyVersion is just using the similar approach to traverseInOrder above for 
		 * easier comparison otherwise Book's solution. is better.
		 */
		Stack<ListNode<Integer>> st = new Stack<>();
		ListNode<Integer> cur = node;
		
		while(!st.empty() || cur != null){
			if(cur != null && cur.visited == -1){
				System.out.println(node.data);
				
				node.visited = 0;
				
				st.push(node.next);
				
				cur = node.jump;
			}{
				cur = st.pop();
			}
		}
	}
	
	static void traverseJumpFirst_Recursive(ListNode<Integer> node){
		/**time O(n), space O(n) due to recursion
		 * Prob: Traverse the PostingsLinkedList JumpFirstOrder: if the jump node leads to a node
		 * we've'nt seen before, or the next node if we've seen the jump node already.
		 */
		if(node != null && node.visited == -1){//This is the first time we see this node
			System.out.println(node.data);
			
			node.visited = 0; //Don't be here again, in case some other node jumps here
			
			traverseJumpFirst_Recursive(node.jump); //If visited, won't get printed anyway
			
			traverseJumpFirst_Recursive(node.next);
		}
	}
	/*****END: Jump first traversal of PostingsList (with visited fields in nodes)***/
	
	
	/***START: depthFirst traversal of BST (recursion NOT allowed so must use Stack) ***/
	static void traverseDepthFirst_Stack(BinaryTreeNode<Integer> node){
		/**time O(n), space O(log(n)==h), but no visited field needed.
		 * Problem: Print the elements in a BST in depth first order
		 * 
		 * Sol: no recursion is allowed, so we need to use a stack to implement depth first traversal.
		 */
		Stack<BinaryTreeNode<Integer>> st = new Stack<>();
		st.push(node);
		
		while(!st.empty()){
			BinaryTreeNode<Integer> cur = st.pop();
			
			System.out.println(cur.data);
			
			if(cur.leftChild != null)
				st.push(cur.leftChild);
			
			if(cur.rightChild != null)
				st.push(cur.rightChild);
		}
	}
	
	/**InOrder, PreOrder, and PostOrder tree traversals are All variation of depthFirst traversals
	 * (Look at BinaryTree traversals for code). All depthFirst traversals need stacks when recursion isn't allowed.*/
	/*****END: depthFirst traversal of BST (recursion NOT allowed so must use Stack)***/
	
	
	/***START: BreadthFirst traversal of BST (recursion NOT allowed so must use Queue)***/
	static void traverseBreadthFirst_Queue(BinaryTreeNode<Integer> root){
		/**
		 * This is not the same as PreOrder traversal.
		 */
		if(root == null)
			return;
		
		Queue<BinaryTreeNode<Integer>> q = new LinkedList<BinaryTreeNode<Integer>>();
		q.add(root);
		
		while(!q.isEmpty()){
			BinaryTreeNode<Integer> cur = q.poll();
			
			System.out.println(cur.data);
			
			if(cur.leftChild != null)
				q.add(cur.leftChild);
			if(cur.rightChild != null)
				q.add(cur.rightChild);
		}
	}
	/*****END: BreadthFirst traversal of BST (recursion NOT allowed so must use Queue)***/
	
	
	/******************START: Different Queue Implementations******************/
	static class CircularQueueFromArray{
		/**All ops are O(1) with no resizing. With resizing, total time for m ops is O(m).*/
		
		int[] ar;
		int count, frontIndex, backIndex;
		
		CircularQueueFromArray(int cap){
			ar = new int[cap];
			count = frontIndex = backIndex = 0;
		}
		
		int size(){
			return count;
		}
		
		int dequeue() throws Exception{
			if(size() == 0)
				throw new Exception();
			
			count--; 
			int out = ar[frontIndex];
			frontIndex = (frontIndex + 1) % ar.length;
			
			return out;
		}
		
		void enqueue(int el){
			if(count == ar.length)
				resize();
			
			ar[backIndex] = el;
			backIndex = (backIndex + 1) % ar.length;
			count++;
		}
		
		void resize(){
			int[] newAr = new int[ar.length*2];
			int j = 0;
			
			if(backIndex > frontIndex){
				for(int i = frontIndex; i < backIndex; i++)
					newAr[j++] = ar[i];
			}else{
				for(int i = frontIndex; i < ar.length; i++)
					newAr[j++] = ar[i];
				for(int i = 0; i < backIndex; i++)
					newAr[j++] = ar[i];
			}
			
			ar = newAr;
			frontIndex = 0;
			backIndex = j;
		}
	};
	
	static class QueueWithTwoInts{
		/**Prob: Implement a queue (to store 0<=Integers<=9) using two unsigned integers
		 * 
		 * Sol: Since we are only storing single digits, the queue can be thought of as a number (i.e. 
		 * string of digits). 
		 * 
		 * So if val is a k-bit integer, the queue can have 
		 * logBase10(2^k) elements at most. 
		 */
		int count = 0, val = 0;
		
		void enqueue(int el){
			count++;
			val = val * 10 + el;
		}
		
		int dequeue() throws Exception{
			if(count == 0)
				throw new Exception();
			
			count--;
		
			int out = (int) (val / Math.pow(10, count));
			val -= out*Math.pow(10, count);
		
			return out;
		}
	}
	
	static class QueueWithTwoStacks{
		/**All ops: time O(1) because we put each element, in each of our two stacks at most ones,
		 *space O(1) aside from the O(n) for second stack.
		 *
		 *Sol: enqueue in first stack, but put everything from 1st into 2nd before dequeuing. 
		 * */
		
		Stack<Integer> st1, st2;
		
		QueueWithTwoStacks(){
			st1 = new Stack<>();
			st2 = new Stack<>();
		}
		
		void enqueue(int el){
			st1.push(el);				
		}
		
		int dequeue() throws Exception{
			if(st1.empty() && st2.empty())
				throw new Exception();
			
			if(!st2.empty())
				return st2.pop();
			
			while(!st1.empty())
				st2.push(st1.pop());
			
			return st2.pop();
		}
		
		int size(){
			return st1.size() + st2.size();
		}
	}
	/********************END: Different Queue Implementations******************/

	
	
	public static void main(String[] args) {
		Pair[] ar = {new Pair(1, 20), new Pair(2, 16), new Pair(3, 24), new Pair(4, 12), new Pair(5, 4), new Pair(6, 8)};
		
		int[] out = findMaxElementInWindow(ar , 1);
			System.out.println(out[4]);
	}
}
