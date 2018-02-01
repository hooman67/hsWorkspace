package ElementsOfProgrammingIterviewsSolutions;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class GraphNode<T>{
	T data;
	ArrayList<GraphNode<T>> childs = new ArrayList<GraphNode<T>>();
	
	boolean isVisited = false;
	
	GraphNode(T data){
		this.data = data;
	}
	void addChild(T data){
		childs.add(new GraphNode<T>(data));
	}
	void addChild(GraphNode<T> child){
		childs.add(child);
	}
}


public class Graphs {
	
	static void PrintBreadthFirst(GraphNode<Integer> root){
		Queue<GraphNode<Integer>> q = new LinkedList();
		q.add(root);
		
		while(!q.isEmpty()){
			GraphNode<Integer> cur = q.poll();
			System.out.println(cur.data);
			cur.isVisited = true;
			
			for(GraphNode<Integer> n : cur.childs){
				if(!n.isVisited)
					q.add(n);
			}
		}
	}
	
	
	static void PrintDepthFirst(GraphNode<Integer> root){
		Stack<GraphNode<Integer>> st = new Stack();
		st.push(root);
		
		while(!st.isEmpty()){
			GraphNode<Integer> cur = st.pop();
			System.out.println(cur.data);
			cur.isVisited = true;
			
			for(GraphNode<Integer> n : cur.childs){
				if(!n.isVisited)
					st.push(n);
			}
		}
	}
	
	




	
	public static void main(String[] args) {
		/*BinaryTreeNode<Integer> root = new BinaryTreeNode(4, null, 9);
		
		root.leftChild = new BinaryTreeNode(2, root, 3);
		root.leftChild.leftChild = new BinaryTreeNode(1, root.leftChild, 1);
		root.leftChild.rightChild = new BinaryTreeNode(3, root.leftChild, 1);
		
		root.rightChild = new BinaryTreeNode(6, root, 5);
		root.rightChild.leftChild = new BinaryTreeNode(5, root.rightChild, 1);
		root.rightChild.rightChild = new BinaryTreeNode(8, root.rightChild, 3);
		
		root.rightChild.rightChild.leftChild = new BinaryTreeNode(7, root.rightChild.rightChild, 1);
		root.rightChild.rightChild.rightChild = new BinaryTreeNode(9, root.rightChild.rightChild, 1);*/
		
		//System.out.println(getLCA_generalCase(root, root.rightChild.rightChild.rightChild, root.rightChild).data);
		
		
		BinaryTreeNode<Integer> root = new BinaryTreeNode(4);
		
		root.leftChild = new BinaryTreeNode(2);
		root.leftChild.leftChild = new BinaryTreeNode(1);
		root.leftChild.rightChild = new BinaryTreeNode(3);
		
		root.rightChild = new BinaryTreeNode(6);
		root.rightChild.leftChild = new BinaryTreeNode(5);
		root.rightChild.rightChild = new BinaryTreeNode(8);
		
		root.rightChild.rightChild.leftChild = new BinaryTreeNode(7);
		root.rightChild.rightChild.rightChild = new BinaryTreeNode(9);
		
		
		GraphNode<Integer> groot = new GraphNode(1);
		
		GraphNode<Integer> a2 = new GraphNode(2);
		a2.addChild(5);
		
		groot.addChild(a2);
		groot.addChild(4);
		
		GraphNode<Integer> a3 = new GraphNode(3);
		GraphNode<Integer> a6 = new GraphNode(6);
		GraphNode<Integer> a10 = new GraphNode(10);
		a10.addChild(11);
		a6.addChild(a10);
		a3.addChild(a6);
		
		GraphNode<Integer> a7 = new GraphNode(7);
		a7.addChild(8);
		a7.addChild(12);
		a7.addChild(9);
		
		a3.addChild(a7);
		
		groot.addChild(a3);
		
		PrintDepthFirst(groot);

	}
}
