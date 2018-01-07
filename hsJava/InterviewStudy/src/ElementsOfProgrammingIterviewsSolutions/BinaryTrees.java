package ElementsOfProgrammingIterviewsSolutions;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

class BinaryTreeNode<T>{
	T data;
	BinaryTreeNode<T> leftChild, rightChild, parent;
	
	int sizeOfSubTree;
//	int visited = -1;
	int numberOfChilds = -1;

	BinaryTreeNode(T val){
		data = val;
		leftChild = rightChild = parent = null;
	}

	BinaryTreeNode(T val, BinaryTreeNode par){
		data = val;
		parent = par;
		leftChild = rightChild;
	}
	
	BinaryTreeNode(T val, BinaryTreeNode par, int subTreeSize){
		data = val;
		parent = par;
		leftChild = rightChild;
		sizeOfSubTree = subTreeSize;
	}
	
	BinaryTreeNode(T val, BinaryTreeNode<T> left, BinaryTreeNode<T> right, BinaryTreeNode<T> par){
		data = val;
		leftChild = left;
		rightChild = right;
		parent = par;
	}
}


public class BinaryTrees {
	/**************START: Locking A tree node******************/
	static class LockTreeNode{
		BinaryTreeNode<Integer> tree;
		static HashSet<BinaryTreeNode<Integer>> lockedNodes = new HashSet<BinaryTreeNode<Integer>>();
		
		boolean isLocked(BinaryTreeNode<Integer> node){
			return lockedNodes.contains(node);
		}
		
		boolean lock(BinaryTreeNode<Integer> node){
			if(canLock(node)){
				lockedNodes.add(node);
				return true;
			}
			else
				return false;
		}
		
		boolean canLock(BinaryTreeNode<Integer> node){
			if(node == null)
				return true;
			
			return canLock(node.leftChild) && lockedNodes.contains(node) && canLock(node.rightChild);
		}
		
		boolean unlock(BinaryTreeNode<Integer> node){
			return lockedNodes.remove(node);
		}
	}
	/**************END: Locking A tree node********************/
	
	
	/**************START: Tree Traversals**********************/
	static void printInOrder_Recursive(BinaryTreeNode<Integer> node){
		/**Time O(n), space O(h)*/
		if(node == null)
			return;
		
		printInOrder_Recursive(node.leftChild);
		System.out.println(node.data);
		printInOrder_Recursive(node.rightChild);
	}
	static void printInOrder_Stack(BinaryTreeNode<Integer> node){
		/**time O(n), space O(log(n)==h), but no visited field needed.
		 * Problem: Print the elements in a BST in sorted order. So get inorder traversal of bst.
		 * 
		 * Sol: no recursion is allowed, so we need to use a stack to implement depth first traversal.
		 */
		Stack<BinaryTreeNode<Integer>> st = new Stack<>();
		BinaryTreeNode<Integer> cur = node;
		
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
	static void printInOrder_LinkedToParent(BinaryTreeNode<Integer> node){
		/**Time O(n), space O(1)
		 * This requires link to parent
		 */
		
		BinaryTreeNode prev = node;
		
		while(node != null){
			
			while(prev != node.leftChild && prev != node.rightChild && node.leftChild != null)
				node = node.leftChild;
			
			if(prev != node.rightChild)
				System.out.println(node.data);
			
			if(prev != node.rightChild && node.rightChild != null){
				node = node.rightChild;
			}
			else{
				prev = node;
				node = node.parent;
			}
		}		
	}

	static void printPreOrder_Recursive(BinaryTreeNode<Integer> node){
		/**Time O(n), space O(h)*/
		if(node == null)
			return;
		
		System.out.println(node.data);
		printPreOrder_Recursive(node.leftChild);
		printPreOrder_Recursive(node.rightChild);
	}
	static void printPreOrder_Stack(BinaryTreeNode<Integer> node){
		/**time O(n), space O(log(n)==h), but no visited field needed.
		 * Problem: Print the elements in a BST in sorted order. So get inorder traversal of bst.
		 * 
		 * Sol: no recursion is allowed, so we need to use a stack to implement depth first traversal.
		 */
		Stack<BinaryTreeNode<Integer>> st = new Stack<>();
		BinaryTreeNode<Integer> cur = node;
		
		while(!st.empty() || cur != null){
			if(cur != null){
				System.out.println(cur.data);
				st.push(cur);
				cur = cur.leftChild;
			}
			else{
				cur = st.pop();
				cur = cur.rightChild;
			}
		}
	}
	static void printPreOrder_LinkedToParent(BinaryTreeNode<Integer> node){
		/**Time O(n), space O(1)
		 * This requires link to parent
		 */
		
		BinaryTreeNode prev = null;

		while(node != null){
			
			if(prev != node.leftChild && prev != node.rightChild)
				System.out.println(node.data);
			
			if(prev != node.leftChild && prev != node.rightChild && node.leftChild != null){
				prev = node;
				node = node.leftChild;
			}
			else if(prev != node.rightChild && node.rightChild != null){
				prev = node;
				node = node.rightChild;
			}
			else{
				prev = node;
				node = node.parent;
			}
		}		
	}

	static void printPostOrder_Recursive(BinaryTreeNode<Integer> node){
		/**Time O(n), space O(h)*/
		if(node == null)
			return;

		printPostOrder_Recursive(node.leftChild);
		printPostOrder_Recursive(node.rightChild);
		System.out.println(node.data);
	}	
	static void printPostOrder_Stack(BinaryTreeNode<Integer> node) {
		/**time O(n), space O(log(n)==h), but no visited field needed.
		 * Problem: Print the elements in a BST in sorted order. So get inorder traversal of bst.
		 * 
		 * Sol: Uses two pointers, current is the peak of the stack, prev is the previously
		 * visited node. At each node if not null, put the right child (if not null) in the stack, 
		 * then the node itself, then move on to the left child of node. When node becomes null 
		 * (sth doesnt have left child), pop the stack.
		 */
		// Check for empty tree
        if (node == null)
            return;
        
        Stack<BinaryTreeNode<Integer>> S = new Stack<BinaryTreeNode<Integer>>();
        S.push(node);
        
        BinaryTreeNode<Integer> prev = null;
        while (!S.isEmpty()) {
        	BinaryTreeNode<Integer> current = S.peek();
  
            /* go down the tree in search of a leaf an if so process it 
            and pop stack otherwise move down */
            if (prev == null || prev.leftChild == current || prev.rightChild == current) {
                if (current.leftChild != null)
                    S.push(current.leftChild);
                else if (current.rightChild != null)
                    S.push(current.rightChild);
                else{
                    S.pop();
                    System.out.println(current.data);
                }
  
                /* go up the tree from left node, if the child is right 
                   push it onto stack otherwise process parent and pop 
                   stack */
            } 
            else if (current.leftChild == prev) {
                if (current.rightChild != null)
                    S.push(current.rightChild);
                else{
                    S.pop();
                    System.out.println(current.data);
                }
                  
                /* go up the tree from right node and after coming back
                 from right node process parent and pop stack */
            } 
            else if (current.rightChild == prev) {
                S.pop();
                System.out.println(current.data);
            }
  
            prev = current;
        }
    }
	static void printPostOrder_LinkedToParent(BinaryTreeNode<Integer> node){
		/**Time O(n), space O(1)
		 * This requires link to parent
		 */
		
		BinaryTreeNode prev = node;
		
		while(node != null){
			
			while(prev != node.leftChild && prev != node.rightChild && node.leftChild != null)
				node = node.leftChild;
			
			if(prev != node.leftChild)
				System.out.println(node.data);
			
			if(prev != node.rightChild && node.rightChild != null){
				prev = node;
				node = node.rightChild;
			}
			else{
				prev = node;
				node = node.parent;
			}
		}
	}
	
	static void printBreadthFirst_Queue(BinaryTreeNode<Integer> root){
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
	static void printDepthFirst_Stack(BinaryTreeNode<Integer> node){
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
	
	//copied from some other file, might be duplicated
	public static void traverseDepthFirstAndStorePath(BinaryTreeNode<Integer> n){
		if(n == null)
			return;
		
		if(n.leftChild!=null || n.rightChild!=null){
			//traverse depth first
			traverseDepthFirstAndStorePath(n.leftChild);
			traverseDepthFirstAndStorePath(n.rightChild);
		}else{
			//This node is a leaf so calculate the sum going up the parents
			LinkedList<Integer> pathFromLeaf = new LinkedList<Integer>();
			storePath(n, pathFromLeaf);
			
			for(Integer a: pathFromLeaf)
				System.out.println(a);
			System.out.println("\n\n");
		}
	}
	public static void storePath(BinaryTreeNode<Integer> n, LinkedList<Integer> curPath){
		if(n == null)
			return;
		
		curPath.addLast(n.data);		
		storePath(n.parent, curPath);
	}
	/****************END: Tree Traversals************************/
	
	
	/*********START: Kth element of inOrder traversal***********/
	static BinaryTreeNode<Integer> getKthElementOfInOrderTraversal_WithSizeOfSubTree(BinaryTreeNode<Integer> node, int k){
		/**Time O(h), space O(1)
		 * This requires each node to store the number of nodes below it + 1 (iteself).
		 */
		while(node != null && k > 0){
			
			int leftSize = node.leftChild != null ? node.leftChild.sizeOfSubTree : 0;
			
			if(leftSize < k - 1){
				k -= (leftSize + 1);
				node = node.rightChild;
			}
			else if(leftSize == k - 1)
				return node;
			else{
				node = node.leftChild;
			}
		}
		
		return null;
	}
	
	static BinaryTreeNode<Integer> getKthElementOfInOrderTraversal_WithLinkToParent(BinaryTreeNode<Integer> node, int k){
		/**Time O(n), space O(1)
		 * This requires link to parent
		 */
		
		BinaryTreeNode prev = node;
		int traversed = 0;
		
		while(node != null){
			
			while(node.leftChild != null && prev != node.leftChild && prev != node.rightChild)
				node = node.leftChild;
			
			if(prev != node.rightChild){
				//System.out.println(node.value);
				traversed++;
				if(traversed == k)
					return node;
			}
				
			if(prev != node.leftChild){
				prev = node;
				node = node.parent;
			}
			else if(node.rightChild != null && prev != node.rightChild){
				node = node.rightChild;
			}
			else{
				prev = node;
				node = node.parent;
			}
		}
		
		return null;		
	}
	
	static BinaryTreeNode<Integer> getKthElementOfInOrderTraversal_NoLinkToParents(BinaryTreeNode<Integer> node, int k){
		/**Time O(n), space O(h). (with link to parents use printInOrderLinkedToParent
		 * traversal to get space O(1)). There is a more efficient algorithm than this (above). 
		 */
		int[] currentK = {0};
		BinaryTreeNode<Integer> out = new BinaryTreeNode<Integer>(0);
		
		helper_getKthElementOfInOrderTraversal_NoLinkToParents(node, k, currentK, out);
		
		return out;
	}
	static void helper_getKthElementOfInOrderTraversal_NoLinkToParents(BinaryTreeNode<Integer> node, int k, int[] currentK, BinaryTreeNode<Integer> foundNode){
		if(node == null)
			return;
		
		helper_getKthElementOfInOrderTraversal_NoLinkToParents(node.leftChild, k, currentK, foundNode);
		
		currentK[0]++;
		if(currentK[0] == k){
			foundNode.data = node.data;
			foundNode.leftChild = node.leftChild;
			foundNode.rightChild = node.rightChild;
		}
			
		helper_getKthElementOfInOrderTraversal_NoLinkToParents(node.rightChild, k, currentK, foundNode);
	}
	/*********END: Kth element of inOrder traversal************/
	
	
	/*********START: Printing Paths in a Tree***********/
	//copied from some other file, might be duplicated
	class PrintPathThatSumToTarget {
		public void traverse(BinaryTreeNode<Integer> n, int target){
			if(n == null)
				return;
			
			LinkedList<Integer> curPath1 = new LinkedList();
			
			printSum(n, curPath1, target);
			traverse(n.leftChild, target);
			traverse(n.rightChild, target);
		}

		public void printSum(BinaryTreeNode<Integer> n, LinkedList<Integer> curPath, int target){
			if(n == null)
				return;
			
			curPath.addLast(n.data);
			
			int sum = 0;
			for(int a : curPath)
				sum+=a;
			
			if(sum == target){
				for(int a : curPath)
					System.out.println(a);
				System.out.println("\n\n");
			}
			
			
			printSum(n.parent, curPath, target);
		}
		
		public void printTree(BinaryTreeNode<Integer> n){
			if(n == null)
				return;
			System.out.println(n.data);
			printTree(n.leftChild);
			printTree(n.rightChild);
		}
		
		public void main(String[] args) {
			
			BinaryTreeNode<Integer> root = new BinaryTreeNode<Integer>(1, null, null, null);
			
			BinaryTreeNode<Integer> n1 =  new BinaryTreeNode<Integer>(1, null, null, null);
			BinaryTreeNode<Integer> n2 =  new BinaryTreeNode<Integer>(2, null, null, null);
			BinaryTreeNode<Integer> n3 =  new BinaryTreeNode<Integer>(3, null, null, null);
			BinaryTreeNode<Integer> n4 =  new BinaryTreeNode<Integer>(4, null, null, null);
			
			BinaryTreeNode<Integer> nn2 =  new BinaryTreeNode<Integer>(2, n1, n2, root);
			BinaryTreeNode<Integer> nn3 =  new BinaryTreeNode<Integer>(3, n3, n4, root);
			
			n1.parent = nn2;
			n2.parent = nn2;
			
			n3.parent = nn3;
			n4.parent = nn3;
			
			root.leftChild = nn2;
			root.rightChild = nn3;

			
			traverse(root, 3);
			//printTree(root);
		}
	}
	
	static void printTreeClockWise(BinaryTreeNode<Integer> node){
	
		while(node.leftChild != null){
				System.out.println(node.data);
				node = node.leftChild;
		}
		
		//TODO
	}
	/***********END: Printing Paths in a Tree***********/
	
	
	/*********START: Kth lowest Common Ancestor***********/
	static BinaryTreeNode<Integer> getLCA_generalCase(BinaryTreeNode<Integer> root, BinaryTreeNode<Integer> node1, BinaryTreeNode<Integer> node2){
		/**O(n) time, O(h) space (due to recursion).
		 * This requires nothing. No link to parents
		 */
		if(root == null) //empty tree
			return null;
		else if(root == node1 || root == node2) 
			return root;
		
		BinaryTreeNode<Integer> resultsFromLeftSubTree = getLCA_generalCase(root.leftChild, node1, node2);
		BinaryTreeNode<Integer> resultsFromRightSubTree = getLCA_generalCase(root.rightChild, node1, node2);
		
		return resultsFromLeftSubTree != null ? resultsFromLeftSubTree : resultsFromRightSubTree;
	}
	static BinaryTreeNode<Integer> getLCA_WithLinkToParent_higherTimeLowerSpace(BinaryTreeNode<Integer> node1, BinaryTreeNode<Integer> node2){
		/**O(heightOfTree) time, O(1) space.
		 * This requires link to parents. We find the depth of each node, traverse up the 
		 * deeper node to get both of them to same depth. Then traverse up one at a time 
		 * to reach a common.
		 */
		return null;
	}
	static BinaryTreeNode<Integer> getLCA_WithLinkToParent_LowerTimeHigherSpace(BinaryTreeNode<Integer> node1, BinaryTreeNode<Integer> node2){
		/**O(max(depthNode1, depthNode2)) time, O(h) space.
		 * This requires link to parents. Uses a HashMap
		 */
		HashMap<Integer, BinaryTreeNode<Integer>> map = new HashMap<>();
		while(node1 != null){
			map.put(node1.data, node1);
			node1 = node1.parent;
		}
		
		while(node2.parent != null){
			if(map.containsKey(node2.data))
				return node2;
			node2 = node2.parent;
		}
		
		return node2;
	}
	/*********END: Kth lowest Common Ancestor*************/
	
	
	/*****START: Store the number of nodes (# of childs) under each node *****/
	static int storeChildCounts(BinaryTreeNode n){
		if(n == null)
			return 0;
		
		int countLeft = storeChildCounts(n.leftChild);
		int countRight = storeChildCounts(n.rightChild);
		
		n.numberOfChilds = countLeft + countRight;
		
		return n.numberOfChilds + 1; //count this one too
	}
	/*******END: Store the number of nodes (# of childs) under each node *****/
	
	
	
	static void kos(BinaryTreeNode n){
		if(n == null)
			return;
		kos(n.leftChild);
		System.out.println(n.data + " :  " + n.numberOfChilds);
		kos(n.rightChild);
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
		
		storeChildCounts(root);
		kos(root);
		
	}

}