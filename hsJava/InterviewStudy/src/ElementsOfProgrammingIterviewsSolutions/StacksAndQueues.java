package ElementsOfProgrammingIterviewsSolutions;
import java.util.*;

import ElementsOfProgrammingIterviewsSolutions.BinaryTreeNode;
import ElementsOfProgrammingIterviewsSolutions.ListNode;

public class StacksAndQueues {

	/****START: design a stack that also track the max element in the stack****/
	static class MaxStack{
		/**time O(1) all ops, space O(n)
		 * Prob: Return the max element in a stack. As well as regular stack functionality.
		 *
		 * Sol: In the stack store a pair. Where the first element is the value, and second element
		 * is the maximum value in the stack after the value was added. So the maxof(new value, old max).
		 */
		class Pair{
			Integer first, second;
			Pair(int val1, int val2){
				this.first = val1;
				this.second = val2;
			}
		}

		Stack<Pair> stack;
		
		MaxStack(){
			stack = new Stack<Pair>();
		}
		
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
	}
	
	static class MaxStack_MoreSpaceEfficient{
		/**time O(1) all ops, space O(n) worst case (each new elment is max) O(1) best case.
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
		class Pair{
			Integer first, second;
			Pair(int val1, int val2){
				this.first = val1;
				this.second = val2;
			}
		}

		Stack<Integer> stack;
		Stack<Pair> auxStack;
		
		MaxStack_MoreSpaceEfficient(){
			this.stack = new Stack<Integer>();
			this.auxStack = new Stack<Pair>();
		}
		
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
	
	
	/***START: depthFirst traversal of BST (recursion NOT allowed so must use Stack)***/
	static void traverseDepthFirst_Stack(BinaryTreeNode<Integer> node){
		/**time O(n), space O(log(n)==h), but no visited field needed.
		 * Problem: Print the elements in a BST in sorted order. So get inorder traversal of bst.
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
	
	static void traverseInOrder_Stack(BinaryTreeNode<Integer> root){
		/**time O(n), space O(log(n)==h), but no visited field needed.
		 * Problem: Print the lements in a BST in sorted order. So get inorder traversal of bst.
		 * 
		 * Sol: no recursion is allowed, so we need to use a stack to implement depth first traversal.
		 * 
		 * Note for graph traversal: Here we are doing it like this so that we don't need a
		 * visited field, (and because we only) have two childs. For many childs, we add all 
		 * childs in the first if statement, and at else just print. We also change the if to 
		 * if(cur != null && !cur.visited).
		 */
		Stack<BinaryTreeNode<Integer>> st = new Stack<>();
		BinaryTreeNode<Integer> cur = root;
		
		while(!st.empty() || cur != null){
			if(cur != null){
				st.push(cur);
				cur = cur.leftChild;
			}
			else{
				cur = st.pop();
				System.out.println(cur.data);
				cur = cur.rightChild;
			}
		}
	}
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
	
	
	
	public static void main(String[] args) {
		Stack<Integer> st = new Stack<>();
		
		st.push(5);
		st.push(1);
		st.push(4);
		st.push(3);
		st.push(2);
		
		Stack<Integer> st2 = sortTheStack_NonRecursive(st);
		
		while(!st2.empty())
			System.out.println(st2.pop());
	}
}
