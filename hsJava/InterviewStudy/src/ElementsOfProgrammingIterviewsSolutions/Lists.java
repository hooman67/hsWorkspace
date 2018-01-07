package ElementsOfProgrammingIterviewsSolutions;

class ListNode<T>{
	T data;
	ListNode<T> next;
	
	ListNode<T> jump;//used just for postings list
	int visited = -1;
	
	ListNode(T data, ListNode<T> next){
		this.data = data;
		this.next = next;
		this.jump = null;
	}
	ListNode(T data){
		this(data, null);
	}
}

public class Lists {
	/***START: Merge two sorted lists*********/
	static ListNode<Integer> MergeLists(ListNode<Integer> a, ListNode<Integer> b){
		/**time O(n), space O(1) because in java all objects are passed by reference
		 * so reuses the same nodes as a and b. (creates no knew nodes, so doesnt allocate memory) 
		 */
		ListNode<Integer> ap = a;
		ListNode<Integer> bp = b;
		
		ListNode<Integer> sortedHead = null, sortedTail = null;
		
		if(ap.data < bp.data){
			sortedHead = ap;
			ap = ap.next;
		}
		else{
			sortedHead = bp;
			bp = bp.next;
		}
		
		sortedTail = sortedHead;
		
		while( ap != null && bp != null){
			if(ap.data < bp.data){
				sortedTail.next = ap;
				ap = ap.next;
			}else{
				sortedTail.next = bp;
				bp = bp.next;
			}
			
			sortedTail = sortedTail.next;
		}
		
		while(ap != null){
			sortedTail.next = ap;
			ap = ap.next;
			sortedTail = sortedTail.next;
		}
		
		while(bp != null){
			sortedTail.next = bp;
			bp = bp.next;
			sortedTail = sortedTail.next;
		}
		
		return sortedHead;
	}
	
	static ListNode<Integer> MergeLists_Rec(ListNode<Integer> list1, ListNode<Integer> list2) {
		if (list1 == null){
			/**1. base case: We've reached the end of one of the lists, either A or B is null:
			   we've reached the end of list1, return the rest of list2 and stick it to the 
			   end of list 1 because list one must have called this, for it to reach the end
			*/
			 
			return list2;
		}
		if (list2 == null) return list1;

		/**2. we have 1 node in each list (ending in null), which list should we stick the result to?
		   which list should we look at the next value for?
		 */
		if (list1.data < list2.data) {
			list1.next = MergeLists_Rec(list1.next, list2);
			return list1;
		} else {
			list2.next = MergeLists_Rec(list2.next, list1);
			return list2;
		}
	}
	/*****END: Merge two sorted lists*********/

	
	/***START: Reverse Singly LinkedList*********/
	static ListNode<Integer> reverseList_Itr(ListNode<Integer> head){
		/**SinglyLinkedList: O(n) time and O(n) space due to recursion*/
		ListNode<Integer> cur = head, prev = null;
		
		while(cur != null){
			ListNode<Integer> rest = cur.next;
			
			cur.next = prev;
			
			prev = cur; //prev is now the head of the reverse list
			cur = rest; //cur is now the head of the unreverse portion of the list
		}
		
		return prev;
	}
	
	static ListNode<Integer> reverseList_Rec(ListNode<Integer> head){
		/**SinglyLinkedList: O(n) time and O(n) space due to recursion*/
		if(head == null || head.next == null){
			//base case, return the end of this list as new head
			return head;
		}
		
		//This newHead never changes just propagates through and gets returned
		ListNode<Integer> newHead = reverseList_Rec(head.next);
		
		//Nodes change on the way back from the end
		head.next.next = head;
		head.next = null;
		
		return newHead;
	}		
	
	static ListNode<Integer> reverseList_Itr_Wrong(ListNode<Integer> head){
		/**This is wrong because if you update the head.next to null, 
		 * your list gets discontinued, same if you dont. This is fixed
		 * in the correct version by using prev and cur instead of next and cur*/
		ListNode<Integer> curNode = head;
		ListNode<Integer> nextNode = head.next;
		
		while(nextNode != null){
			ListNode<Integer> rest = nextNode.next;
			
			nextNode.next = curNode;
			curNode.next = null;
			
			curNode = nextNode;
			nextNode = rest;
		}
		
		return curNode;
	}
	/*****END: Reverse Singly LinkedList*********/
	
	
	/***START: Presence/length/location of cycles in singlyLinked***/
	static ListNode<Integer> findCycle(ListNode<Integer> head){
		/**Returns the begining of the cycle or null if none.
		 * Finds the length of the cylce too.
		 * O(n), space O(1)
		 *  Uses a fast and a slow pointer
		 */
	
		ListNode<Integer> fast = head, slow = head;
		
		while(fast != null && fast.next != null && fast.next.next != null
				&& slow != null && slow.next != null){
			
			slow = slow.next;
			fast = fast.next.next;
			
			/*found cycle (Both slow and fast reference the same node):
			 * Now we must find the start of the cycle using its length*/
			if(slow == fast){
				/*To find length of the cycle:
				 * Fix fast, move slow till you reach fast again, count the nodes*/
				slow = slow.next;
				int cycleLen = 1;
				
				while(slow != fast){
					slow = slow.next;
					cycleLen++;
				}
				
				
				/*To find start of cycle:
				 *1. start both fast and slow to head
				 *2. move fast ahead by cycleLen
				 *3. move slow and fast ahead 1 at a time
				 *4. when slow = fast you are at head of cycle*/
				slow = fast = head;
				while(cycleLen > 0){
					fast = fast.next;
				}
				
				while(slow != fast){
					slow = slow.next;
					fast = fast.next;
				}
				
				//Now that slow == fast, we are at head of cycle
				return slow;
			}
			
		}
		
		//while loop ending means something is null == no cycles
		return null;
	}
	
	static double findMedianOfSortedCircularList(ListNode<Integer> head){
		/**time O(n), space O(1) 
		 * Prob: given an arbitrary node, of a Sorted, Circular List return the median.  
		 * 
		 * Sol: find the length of the cycle, and the minimum element in the list. 
		 * take the minElement as the start, and return the middle if len is odd or avg
		 * of two middle if len is even.
		 */
		ListNode<Integer> startOfLoop = head;
		
		ListNode<Integer> itr = head.next;
		int loopLen = 1;
		
		while(itr != head){
			if(itr.data < head.data){
				startOfLoop = itr;
			}
			
			itr = itr.next;
			loopLen++;
		}
		
		if(loopLen % 2 == 0){
			int loc = (loopLen)/2 - 1;
			
			for(int i =0; i < loc; i++){
				startOfLoop = startOfLoop.next;
			}
			
			return (startOfLoop.data + startOfLoop.next.data) / 2;
		}
		else{
			int loc = (loopLen-1)/2;
			
			for(int i =0; i < loc; i++){
				startOfLoop = startOfLoop.next;
			}
			
			return startOfLoop.data;
		}
	}
	
	static ListNode<Integer> findMiddleNodeOfSinglyLinkedList(ListNode<Integer> head){
		/**O(n) time, O(1) space
		 * Prob: Return the middle element of a singlyLL if odd number of elements,
		 *or the element to the right of middle of the list if even total # of elements.
		 * 
		 * Sol:
		 * Uses slow and fast pointers. By the time fast pointer is at the end, 
		 * slow pointer is at the middle, because it moves at half speed.
		 */
		
		ListNode<Integer> fast = head, slow = head;
		
		while(fast != null){
			fast = fast.next;
			
			if(fast != null){
				fast = fast.next;
				slow = slow.next;
			}
		}
		
		return slow;
	}
	/*****END: Presence/length/location of cycles in singlyLinked***/
	
	
	/***START: Detect/find the start of overlap btw two singlyLinkedLists***/
	static ListNode<Integer> findFirstOverlap(ListNode<Integer> a, ListNode<Integer> b){
		/**time O(n), space O(1)
		 * Prob: Detect whether the two given lists converge, if so, return the first point
		 * they converge on.
		 * 
		 * Sol: If there is any overlap, the tail of both lists are the same.
		 * Then, If we move the longer list by as many nodes as the difference btw their lengths
		 * then the two pointers are the same distance from the first point of convergence.
		 */
		if(a == null || b == null)
			return null;
		
		//Check to see if there is overlap, find the length at the same time
		int len_a = 1, len_b = 1;
		ListNode<Integer> tail_a = a, tail_b = b;
		
		while(tail_a.next != null){
			tail_a = tail_a.next;
			len_a++;
		}
		
		while(tail_b.next != null){
			tail_b = tail_b.next;
			len_b++;
		}
		
		if(tail_a != tail_b)//There is no overlap
			return null;
		
		//Now that there is an overlap, put both pointers at the same distance from 1st common node
		ListNode<Integer> ap = a, bp = b;
		
		if(len_a >= len_b){
			int length_diff = len_a - len_b;
			for(;length_diff > 0; length_diff--)
				ap = ap.next;
		}
		else{
			int length_diff = len_b - len_a;
			for(;length_diff > 0; length_diff--)
				bp = bp.next;
		}
		
		//Now just find the first common node
		while(ap != bp){
			ap = ap.next;
			bp = bp.next;
		}
		
		return ap;
	}
	/*****END: Detect/find the start of overlap btw two singlyLinkedLists***/
	
	
	/*********START: Detect if singlyLinkedLists is Palindrome*********/
	static boolean isPalindrom(ListNode<Integer> head){
		/**
		 * 
		 * Sol:
		 * Find the middle of the list (using slow-fast pnt to find the middle (not the same as
		 * finding cycles) reverse the second half of the list (using prev-next) then compare it
		 * to the first half (original) using head.
		 * 
		 * Note: Can't assume that When you are at the middle element, prev == next.
		 * Because it might be (1 2 2 1) not (1 2 3 2 1). But if you have a slow pnt
		 * and a fst one, by the time fast reaches the end, slow is at the middle 
		 * (cause it has moved at half speed).
		 */
		
		//Find the middle element in the list
		ListNode<Integer> slow = head, fast = head;
		while(fast != null){
			fast = fast.next;
			
			if(fast != null){
				fast = fast.next;
				slow = slow.next;
			}
		}
		
		//reverse the second half of the list
		ListNode<Integer> prev = null, cur = slow;
		while(cur != null){
			ListNode<Integer> rest = cur.next;
			cur.next = prev;
			
			prev = cur; //prev is now the head of the reverse list
			cur = rest; //cur is now the head of the unreverse portion of the list
		}
		
		//Now compare the first half to the reversed half.
		while(prev != null && head != null){
			if(head.data != prev.data)
				return false;
			else{
				head = head.next;
				prev = prev.next;
			}
		}
			
		return true;
	}
	/***********END: Detect if singlyLinkedLists is Palindrome*********/
	
	
	/****START: Reorder a singlyLinkedLists so that even indexed elements come before odd****/
	static ListNode<Integer> evenOddMerge(ListNode<Integer> head){
		/**time O(n), space O(1)
		 * problem: reorder the given list such that all even indexed elements 
		 * come before odd ones, but the order is kept (i.e. 0,2,4,...,1,2,3,...)
		 * 
		 * Sol: Keep odd and even pointers, update their next values, and merge the two lists.
		 * 
		 * Note: you must chane the next pointer of even and odd at the same time
		 */
		if(head == null)
			return head;
		
		ListNode<Integer> even = head, odd = head.next, oddHead = head.next;
		
		while(even != null && even.next != null && odd != null && odd.next != null){
			even.next = even.next.next;
			odd.next = odd.next.next;
			
			even = even.next;
			odd = odd.next;
		}
		
		even.next = oddHead; //set the end of even list to the head of odd list

		return head;
	}
	/******END: Reorder a singlyLinkedLists so that even indexed elements come before odd****/
	
	
	/****START: Given a node in a singlyLinkedList, delete it in O(1) time****/
	static void deleteNode(ListNode<Integer> node){
		/**O(1) time, O(1) space.
		 * Sol: if the given node is not the tail, copy the data of the next node
		 * into the current node, and skip (i.e. delete) the next node.
		 */
		if(node == null || node.next == null){
			//Given node is the tail of the list
			node = null;
		}
		else{
			node.data = node.next.data;
			node.next = node.next.next;
		}
		
		return;
	}
	/******END: Given a node in a singlyLinkedList, delete it in O(1) time****/
	
	
	/**************START: remove Kth Last Element Of a SinglyLinkedList**************/
	static void removeKthLastElementOfSinglyLinkedList(ListNode<Integer> head, int k){
		/**O(n) time, O(1) space 
		 * Problem: k = 1, means remove last element. So k is always >= 1
		 * 
		 * Sol: Use two pointers k+1 elements apart So that When forward is at the end of 
		 * list, behind is the parent of the node you wanna remove.
		 */
		if( k < 1){
			System.out.println("Invalid k value");
			return;
		}
		
		ListNode<Integer> forward = head, behind = head;
		
		//put fwd k + 1 spaces forward
		while(forward != null && k >= 0){
			forward = forward.next;
			k--;
		}
		
		if(k == 0){ // ended because forward == null
			//The first element is the removal target (but we can't change that because java
			//does passByRefrenceValue), so copy the next element over and skip it instead.
			head.data = head.next.data;
			head.next = head.next.next;
			return;
		}
		else if(k > 0){ // ended because forward == null
			System.out.println("not enough nodes in list");
			return;
		}
		
		while(forward != null){
			forward = forward.next;
			behind = behind.next;
		}
		
		//remove behind.next element
		behind.next = behind.next.next;
		return;
	}
	/****************END: remove Kth Last Element Of a SinglyLinkedList**************/
	
	
	/***START: re-order/ZIP a singlyLinkedList such that (n-b) element comes before element b***/
	static void zip(ListNode<Integer> head){
		//find the middle element (1st half bigger if list.size is even).
		ListNode<Integer> slow = head, fast = head;
		while(fast != null){
			fast = fast.next;
			
			if(fast != null){
				fast = fast.next;
				slow = slow.next;
			}
		}
		
		//Revese the 2nd half (starting from slow i.e. middle elments)
		ListNode<Integer> prev = null, cur = slow;
		while(cur != null){
			ListNode<Integer> rest = cur.next;
			cur.next = prev;
			
			prev = cur;
			cur = rest;
		}
		
		//Interleave the two lists, with prev being the head of reversed list
		while(head != null && prev != null){
			ListNode<Integer> restOfHead = head.next;
			ListNode<Integer> restOfPrev = prev.next;
			
			if(prev == restOfHead)//even total # of elements
				break;
			
			head.next = prev;
			prev.next = restOfHead;
			
			head = restOfHead;
			prev = restOfPrev;
		}
		
		return;
	}
	/*****END: re-order/ZIP a singlyLinkedList such that (n-b) element comes before element b***/
	
	
	/****START: copy a list that has a jump field as well as next (i.e. a postings list)****/
	static ListNode<Integer> copyPostingsList(ListNode<Integer> head){
		/**O(n) time, O(1) space (in addition to what's needed for copy)
		 * 
		 * Prob: Copy a postings list (i.e. a list that has a jump pointer to any node in
		 * the list).
		 * 
		 * Sol Do 3 passes over data:
		 * 1. For each node in origList, create a new node and copy the data.
		 * Then, set the next field of the new node to the next node in origList. And the next node
		 * of origList to the new node (i.e to go from one node in origList to the next, we gotta 
		 * go through its counterpart in copyList)   
		 * 2. Do the jump nodes now that for each node in the origList, we have access to its copyList 
		 * counterpart
		 * 3. Restore the next fields in the origList, but setting them to the field pointed to in
		 * their copyList counterpart's next field
		 */
		if(head == null)
			return null;

		//pass1. Do nodes of the copy and modify nexts of original
		ListNode<Integer> oldListPtr = head;
		while(oldListPtr != null){
			ListNode<Integer> newNode = new ListNode<Integer>(oldListPtr.data);
			newNode.next = oldListPtr.next;
			
			ListNode<Integer> temp = oldListPtr.next;
			oldListPtr.next = newNode;
			
			oldListPtr = temp;
		}
		
		//pass2 Do jumps of the copy using the nexts of original
		oldListPtr = head;
		while(oldListPtr != null){
			ListNode<Integer> restOldListPtr1 = oldListPtr.next;
			
			oldListPtr.next.jump = oldListPtr.jump.next;
			oldListPtr = oldListPtr.next.next;
			
			oldListPtr = restOldListPtr1;
		}
		
		//store a reference to new head to return later
		ListNode<Integer> newHead = head.next;
		
		//pass3 Restore the nexts of both copy and original
		oldListPtr = head;
		while(oldListPtr != null){
			ListNode<Integer> restOldListPtr2 = oldListPtr.next;
			
			ListNode<Integer> newListVersion = oldListPtr.next;
			oldListPtr.next = newListVersion.next;
			newListVersion.next = oldListPtr.next.next;
			
			oldListPtr = restOldListPtr2;
		}
		
		return newHead;
	}
	
	static ListNode<Integer> copyPostingsList_WrongApproach(ListNode<Integer> head){
		/**
		 * Prob: Given a postings list create a copy from it, issue is that for the next
		 * field, you need to create new objects, cannot use reference to the same objects.
		 * But for the jump field, you cannot create new objects. Have to reference the 
		 * existing objects in the newList.
		 * 
		 * In below, the next field copies properly, but the jump field does not, 
		 * because instead of pointing to an existing node in the new list, we 
		 * create a new node.
		 */
		if(head == null)
			return null;
		
		ListNode<Integer> newHead = new ListNode<Integer>(head.data);
		
		//Do the next fields
		ListNode<Integer> newListPtr = newHead, oldListPtr = head;
		while(oldListPtr.next != null){
			newListPtr.next = new ListNode<Integer>(oldListPtr.next.data);
			oldListPtr = oldListPtr.next;
			newListPtr = newListPtr.next;
		}
		
		//Do the jump fields
		newListPtr = newHead; oldListPtr = head;
		while(oldListPtr != null){
			if(oldListPtr.jump != null)
				newListPtr.jump = new ListNode<Integer>(oldListPtr.jump.data);
			oldListPtr = oldListPtr.next;
			newListPtr = newListPtr.next;
		}

		return newHead;
	}
	/******END: copy a list that has a jump field as well as next (i.e. a postings list)****/


	
	
	public static void main(String[] args) {
		ListNode<Integer> A = new ListNode<Integer>(0, new ListNode<Integer>(1, new ListNode<Integer>(2, new ListNode<Integer>(3,new ListNode<Integer>(4)))));
		
		ListNode<Integer> a =  new ListNode<Integer>(1);
		ListNode<Integer> b =  new ListNode<Integer>(2);
		ListNode<Integer> c =  new ListNode<Integer>(3);
		ListNode<Integer> d =  new ListNode<Integer>(4);
		ListNode<Integer> e =  new ListNode<Integer>(5);
		ListNode<Integer> f =  new ListNode<Integer>(6);
		
		a.next = b;
		b.next = c;
		c.next = d;
		d.next = e;
		e.next = f;
		
		a.jump = c;
		b.jump = d;
		c.jump = e;
		d.jump = f;
		e.jump = b;
		f.jump = f; 
		
		ListNode<Integer> C = a;
		while(C!=null){
			System.out.println(C.data + "  jump: " + C.jump.data);
			C = C.next;
		}
		System.out.println("\n\n");
		
		ListNode<Integer> D = copyPostingsList(a);
		while(D!=null){
			System.out.println(D.data + "  jump: " + D.jump.data);
			D = D.next;
		}
	}

}
