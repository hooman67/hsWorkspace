package ElementsOfProgrammingIterviewsSolutions;
import java.util.*;

public class PastInterviewQuestions{
	/************START: Balance Paranthesis ************/
	static class BalancePParentheses{
		/**Given a string with different types of parentheses viz. {},() and [] determine 
		 * if the string has balanced parentheses. Balanced parentheses means that the brackets 
		 * should be ordered and matched correctly
		 * 
		 * Sol: Easy if you remember to use a stack. And that you can only pop from the stack if
		 * the opening statment is whats at the top, because the order matters: "{(})" is not balanced
		 */
		static boolean areParenthesesBalanced(String input){

			Stack<Character> st = new Stack<Character>();

			for(char c : input.toCharArray()){
				if(c == '(' || c == '[' || c == '{')
					st.push(c);
				else if((c == ']' || c == '}' || c == ')') && st.size() != 0){
					if (((char) st.peek() == '(' && c == ')')
							|| ((char) st.peek() == '{' && c == '}')
							|| ((char) st.peek() == '[' && c == ']')) {
						st.pop();
					} else {
						return false;
					}
				}
				else{
					if(c == ']' || c == '}' || c == ')')
						return false;
				}
			}

			if(st.size() == 0)
				return true;
			else
				return false;
		}
		static public void main(String[] args){
			String input1 = "{(})";
			String input2 = ")2";

			if (areParenthesesBalanced(input2))
				System.out.println("Balanced");
			else
				System.out.println("NOt Balanced");
		}
	}
	/**************END: Balance Paranthesis ************/


	/************START: Longest Permutation Chain Not in array************/
	static class LongestPermChain {
		/**
		 * You have an array of words, you wanna pick a word, and move 1 letter of it, 
		 * if the result is in the array, youll remove another letter of it, till you are
		 * left with empty string which is NOT part of the array. You wanna return the longest
		 * such a chain. In the example below the answer is 4 (two ways you can do it with bdca to get 4).
		 */
		
		static int longestChain(String[] words) {
			int longest = Integer.MIN_VALUE;
			for(String word : words){
				int temp = getHighestCount(word, words);
				if(temp > longest)
					longest = temp;
			}
			
			return longest;
		}
		
		static int getHighestCount(String word, String[] words){
		
			HashSet<String> wordsSet = new HashSet<String>();
			for(String wrd : words)
				wordsSet.add(wrd);
			
			HashMap<String, HashSet<String>> allSubs = new HashMap<String, HashSet<String>>();
			
			getAllSubstrings(word, allSubs, wordsSet);
			
			System.out.println(allSubs);	
			ArrayList<Integer> counts = new ArrayList<Integer>();
			
			int i = 0;
			
			for(String key : allSubs.keySet()){
				if(key.length() == word.length()-1){
					counts.add(0);
					countChilds(key, allSubs, counts, i);
					i++;
				}
					
			}
			
			int highestCount = Integer.MIN_VALUE;
			for(Integer in : counts){
				if(in > highestCount)
					highestCount = in;
			}
			
			return highestCount;	
		}

		static void countChilds(String key, HashMap<String, HashSet<String>> allSubs, ArrayList<Integer> count, int index){
			if(!allSubs.containsKey(key)){
				return;
			}
				
			for(String subKey : allSubs.get(key))
				countChilds(subKey, allSubs, count, index);
			
			count.set(index, count.get(0)+allSubs.get(key).size());
			return;
		}
		

		static void getAllSubstrings(String word, HashMap<String, HashSet<String>> set, HashSet<String> words){
			if(word.length() <= 0)
				return;
			for(int i =0; i < word.length(); i++){
				StringBuilder sb = new StringBuilder(word);
				sb.deleteCharAt(i);
				String newWord = sb.toString();
				if(words.contains(newWord)){
					if(!set.containsKey(word))
						set.put(word, new HashSet<String>());
					
					set.get(word).add(newWord);
					getAllSubstrings(newWord, set, words);
				}
			}
		}
		
		public static void main(String[] args) {
			String[] words = {"6","a","b","ba", "bca", "bda", "bdca"};	
			System.out.println(longestChain(words));
		}	
	}
	/**************END: Longest Permutation Chain Not in array************/


	/************START: Smallest SubArray With Sum >= target************/
	//Goldman's 1st question
	/**
	 * Given an array of integers find the length of the Smallest 
	 * subarray with sum greater than or equal a given value.
	 * Return -1 if all the elements sumed up < the given value)
	 */
	//Solution in ArraysAndStrings.java  lenSmallestContinuousSubArrayBiggerEqualSum
	/**************END: Smallest SubArray With Sum >= target************/


	/************START: Sort Pairs to make contiguous path************/
	//goldman's 2nd question
	static class SortPairs {
		/**
		 * Given a jumbled collection of segments, each of which is represented as
		 * a Pair(startPoint, endPoint), this function sorts the segments to
		 * make a continuous path.
		 *
		 * A few assumptions you can make:
		 * 1. Each particular segment goes in one direction only, i.e.: if you
		 * see (1, 2), you will not see (2, 1).
		 * 2. Each starting point only have one way to the end point, i.e.: if
		 * you see (6, 5), you will not see (6, 10), (6, 3), etc.
		 *
		 * For example, if you're passed a list containing the following int arrays:
		 *      [(4, 5), (9, 4), (5, 1), (11, 9)]
		 * Then your implementation should sort it such:
		 *      [(11, 9), (9, 4), (4, 5), (5, 1)]
		 *
		 * @param segments collection of segments, each represented by a Pair(startPoint, endPoint).
		 * @return The sorted segments such that they form a continuous path.
		 * @throws Exception if there is no way to create one continuous path from
		 *          all the segments passed into this function. Feel free to change the
		 *          Exception type as you think appropriate.
		 */
		public static List<Pair<Integer, Integer>> sortSegments(final List<Pair<Integer, Integer>> segments)
				throws Exception {
			// Pair is a simple data structure from Commons Lang.
			// Use getLeft() and getRight() to access the first and second value respectively.

			HashMap<Pair<Integer, Integer>, LinkedList<Pair<Integer,    Integer>>> map = new HashMap<Pair<Integer, Integer>, LinkedList<Pair<Integer,    Integer>>>();

			for(Pair<Integer, Integer> p : segments){
				for(Pair<Integer, Integer> pp : segments){
					if(p.getRight() == pp.getLeft()){
						if(!map.containsKey(p))
							map.put(p, new LinkedList<Pair<Integer,Integer>>());      map.get(p).add(pp);
					}
				}
			}

			for(Pair<Integer, Integer> key : map.keySet()){
				Pair<Integer,Integer> last = map.get(key).getLast();

				for(Pair<Integer, Integer> key2 : map.keySet()){
					if(last.getRight() == key2.getLeft()){
						map.get(key).add(key2);
						for(Pair<Integer, Integer> ppp : map.get(key2))
							map.get(key).add(ppp);
					}
				}



				for(Pair<Integer, Integer> key3 : map.keySet()){
					System.out.println(map.get(key3));
				}   

			}



			throw new UnsupportedOperationException("Not implemented yet.");
		}

		public static boolean testBasicSort() throws Exception {
			List<Pair<Integer, Integer>> jumbledSegments = new ArrayList<>();
			jumbledSegments.add(new Pair<Integer, Integer>(4, 5));
			jumbledSegments.add(new Pair<Integer, Integer>(9, 4));
			jumbledSegments.add(new Pair<Integer, Integer>(5, 1));
			jumbledSegments.add(new Pair<Integer, Integer>(11, 9));

			List<Pair<Integer, Integer>> actualContinuousPath = sortSegments(jumbledSegments);

			List<Pair<Integer, Integer>> expectedContinuousPath = new ArrayList<>();
			expectedContinuousPath.add(new Pair<Integer, Integer>(11, 9));
			expectedContinuousPath.add(new Pair<Integer, Integer>(9, 4));
			expectedContinuousPath.add(new Pair<Integer, Integer>(4, 5));
			expectedContinuousPath.add(new Pair<Integer, Integer>(5, 1));

			return expectedContinuousPath.equals(actualContinuousPath);
		}

		public static boolean doTestsPass() throws Exception {
			boolean allPass = true;
			allPass = allPass && testBasicSort();

			return allPass;
		}

		public static void main(String[] args) throws Exception {
			if(doTestsPass()) {
				System.out.println("All tests pass");
			} else {
				System.out.println("Some tests fail");
			}
		}
	}

	//helper class
	static class Pair<T, N>{
		T left;
		N right;
		Pair(T l, N r){
			left = l;
			right = r;
		}
		
		public N getRight(){return right;};
		public T getLeft(){return left;};
	}
	/**************END: Sort Pairs to make contiguous path************/


	/************START: Chess Board Depth First Score Search Game************/
	static class boardTraversalProblem {
		/**Problem: You have chess board with integers in cells, you start from the first row at column p, at
		 * each turn you must go one row down, but you can either go to column p, p+1, or p-1. 
		 * Calculate the maximum possible score if you start from column p.
		 * */
		public static int getMaxScore(int[][] board, int p){
			ArrayList<Integer> maxSum = new ArrayList<Integer>();
			maxSum.add(Integer.MIN_VALUE);
			locatePath(new Cell(0,p,board[0][p], null), board, maxSum);
			return maxSum.get(0);
		}
		
		static void locatePath(Cell cell, int[][] board, ArrayList<Integer> maxSum){
			if (cell.row >= board.length || cell.col < 0 || cell.col >= board[0].length){
				return; //base case
			}
			
			//if you are not a leaf go down to childs
			if (cell.row < board.length - 1) {
				if(cell.col > 0)
					locatePath(new Cell(cell.row+1, cell.col-1, board[cell.row+1][cell.col-1], cell), board, maxSum);
				
				locatePath(new Cell(cell.row+1, cell.col, board[cell.row+1][cell.col], cell), board, maxSum);
				
				if(cell.col < board[0].length - 1)
					locatePath(new Cell(cell.row+1, cell.col+1, board[cell.row+1][cell.col+1], cell), board, maxSum);
			}else{
				//this is a leaf node so traverse up and find the sum
				ArrayList<Integer> sumOfThisPath = new ArrayList<Integer>(); //sum must be an object
				sumOfThisPath.add(0);
				traversePath(board, cell, sumOfThisPath);
				if(sumOfThisPath.get(0) > maxSum.get(0))
					maxSum.set(0, sumOfThisPath.get(0));
			}
		}
		
		static void traversePath(int[][] board, Cell cell, ArrayList<Integer> sum){
			if (cell.parent == null){
				return; //base case
			}
			
			sum.set(0, sum.get(0)+cell.val);
			traversePath(board, cell.parent, sum);
		}
		
		
		public static void main(String[] args) {
			int[][] board = new int[3][];
			board[0] = new int[]{4, 1, 2, 3};
			board[1] = new int[]{5, 10, -10, 2};
			board[2] = new int[]{6, 2, 9, 100};
			
			System.out.println(getMaxScore(board, 1));
		}

	}

	//Helper class
	static class Cell{
		int row,col,val;
		Cell parent;
		Cell(int r, int c, int v, Cell par){
			row = r;
			col = c;
			val = v;
			parent = par;
		}
	}
	/**************END: Chess Board Depth First Score Search Game************/


	/************START: Detecting Friend Cycles************/
	static class DetectFriendCircle {
		static class Helper {
			/** List of cycles */
			private List cycles = null;

			/** Adjacency-list of graph */
			private int[][] adjList = null;

			/** Graphnodes */
			private Object[] graphNodes = null;

			/** Blocked nodes, used by the algorithm of Johnson */
			private boolean[] blocked = null;

			/** B-Lists, used by the algorithm of Johnson */
			private Vector[] B = null;

			/** Stack for nodes, used by the algorithm of Johnson */
			private Vector stack = null;


			public Helper(boolean[][] matrix, Object[] graphNodes) {
				this.graphNodes = graphNodes;
				this.adjList = AdjacencyList.getAdjacencyList(matrix);
			}

			/**
			 * Returns List::List::Object with the Lists of nodes of all elementary
			 * cycles in the graph.
			 */
			static StrongConnectedComponents getStrongConnectedComponents(int[][] adjList){
				return new StrongConnectedComponents(adjList);
			}
			public List getElementaryCycles() {
				this.cycles = new Vector();
				this.blocked = new boolean[this.adjList.length];
				this.B = new Vector[this.adjList.length];
				this.stack = new Vector();
				//StrongConnectedComponents sccs = new StrongConnectedComponents(this.adjList);
				StrongConnectedComponents sccs = getStrongConnectedComponents(this.adjList);
				int s = 0;

				while (true) {
					SCCResult sccResult = sccs.getAdjacencyList(s);
					if (sccResult != null && sccResult.adjList != null) {
						Vector[] scc = sccResult.adjList;
						s = sccResult.lowestNodeId;
						for (int j = 0; j < scc.length; j++) {
							if ((scc[j] != null) && (scc[j].size() > 0)) {
								this.blocked[j] = false;
								this.B[j] = new Vector();
							}
						}

						this.findCycles(s, s, scc);
						s++;
					} else {
						break;
					}
				}

				return this.cycles;
			}


			private boolean findCycles(int v, int s, Vector[] adjList) {
				boolean f = false;
				this.stack.add(new Integer(v));
				this.blocked[v] = true;

				for (int i = 0; i < adjList[v].size(); i++) {
					int w = ((Integer) adjList[v].get(i)).intValue();
					// found cycle
					if (w == s) {
						Vector cycle = new Vector();
						for (int j = 0; j < this.stack.size(); j++) {
							int index = ((Integer) this.stack.get(j)).intValue();
							cycle.add(this.graphNodes[index]);
						}
						this.cycles.add(cycle);
						f = true;
					} else if (!this.blocked[w]) {
						if (this.findCycles(w, s, adjList)) {
							f = true;
						}
					}
				}

				if (f) {
					this.unblock(v);
				} else {
					for (int i = 0; i < adjList[v].size(); i++) {
						int w = ((Integer) adjList[v].get(i)).intValue();
						if (!this.B[w].contains(new Integer(v))) {
							this.B[w].add(new Integer(v));
						}
					}
				}

				this.stack.remove(new Integer(v));
				return f;
			}

			/**
			 * Unblocks recursivly all blocked nodes, starting with a given node.
			 */
			private void unblock(int node) {
				this.blocked[node] = false;
				Vector Bnode = this.B[node];
				while (Bnode.size() > 0) {
					Integer w = (Integer) Bnode.get(0);
					Bnode.remove(0);
					if (this.blocked[w.intValue()]) {
						this.unblock(w.intValue());
					}
				}
			}
		}
		
		static Helper getSol(boolean[][] matrix, Object[] graphNodes){
			return new Helper(matrix,graphNodes);
		}
		
		static int friendCircles(String[] friends) {
			Object[] nodes = friends;
			boolean[][] adjacency = new boolean[friends.length][];
			
			for(int i=0; i< friends.length; i++){
				adjacency[i] = new boolean[friends[i].length()];
			
				for(int j = 0; j < friends[i].length(); j++){
					if(friends[i].charAt(j) == 'Y')
						adjacency[i][j] = true;
					else
						adjacency[i][j] = false;
				}
			}
			
			Helper s = getSol(adjacency, nodes);
			List listOfCycles = s.getElementaryCycles();
			int counter = 0;
			for(Object ob : listOfCycles)
				if(((Vector)ob).size() > 1)
					counter++;
			return counter;
	    }
		
		public static void main(String[] args) {
			String[] mat = {"YYNN","YYYN","NYYN","NNNY"};		
			System.out.println(friendCircles(mat));
		}
	}

	//Helper class 1/3
	static class SCCResult {
		Set nodeIDsOfSCC = null;
		Vector[] adjList = null;
		int lowestNodeId = -1;
		
		public SCCResult(Vector[] adjList, int lowestNodeId) {
			this.adjList = adjList;
			this.lowestNodeId = lowestNodeId;
			this.nodeIDsOfSCC = new HashSet();
			if (this.adjList != null) {
				for (int i = this.lowestNodeId; i < this.adjList.length; i++) {
					if (this.adjList[i].size() > 0) {
						this.nodeIDsOfSCC.add(new Integer(i));
					}
				}
			}
		}
	}

	//helper class 2/3
	static class AdjacencyList {
		/**
		 * Calculates a adjacency-list for a given array of an adjacency-matrix.
		 */
		public static int[][] getAdjacencyList(boolean[][] adjacencyMatrix) {
			int[][] list = new int[adjacencyMatrix.length][];

			for (int i = 0; i < adjacencyMatrix.length; i++) {
				Vector v = new Vector();
				for (int j = 0; j < adjacencyMatrix[i].length; j++) {
					if (adjacencyMatrix[i][j]) {
						v.add(new Integer(j));
					}
				}

				list[i] = new int[v.size()];
				for (int j = 0; j < v.size(); j++) {
					Integer in = (Integer) v.get(j);
					list[i][j] = in.intValue();
				}
			}
			
			return list;
		}
	}

	//helper class 3/3
	static class StrongConnectedComponents {
		/** Adjacency-list of original graph */
		private int[][] adjListOriginal = null;

		/** Adjacency-list of currently viewed subgraph */
		private int[][] adjList = null;
		
		/** Helpattribute for finding scc's */
		private boolean[] visited = null;

		/** Helpattribute for finding scc's */
		private Vector stack = null;

		/** Helpattribute for finding scc's */
		private int[] lowlink = null;

		/** Helpattribute for finding scc's */
		private int[] number = null;

		/** Helpattribute for finding scc's */
		private int sccCounter = 0;

		/** Helpattribute for finding scc's */
		private Vector currentSCCs = null;

		public StrongConnectedComponents(int[][] adjList) {
			this.adjListOriginal = adjList;
		}

		public SCCResult getAdjacencyList(int node) {
			this.visited = new boolean[this.adjListOriginal.length];
			this.lowlink = new int[this.adjListOriginal.length];
			this.number = new int[this.adjListOriginal.length];
			this.visited = new boolean[this.adjListOriginal.length];
			this.stack = new Vector();
			this.currentSCCs = new Vector();

			this.makeAdjListSubgraph(node);

			for (int i = node; i < this.adjListOriginal.length; i++) {
				if (!this.visited[i]) {
					this.getStrongConnectedComponents(i);
					Vector nodes = this.getLowestIdComponent();
					if (nodes != null && !nodes.contains(new Integer(node)) && !nodes.contains(new Integer(node + 1))) {
						return this.getAdjacencyList(node + 1);
					} else {
						Vector[] adjacencyList = this.getAdjList(nodes);
						if (adjacencyList != null) {
							for (int j = 0; j < this.adjListOriginal.length; j++) {
								if (adjacencyList[j].size() > 0) {
									return new SCCResult(adjacencyList, j);
								}
							}
						}
					}
				}
			}

			return null;
		}

		/**
		 * Builds the adjacency-list for a subgraph containing just nodes
		 * >= a given index.
		 */
		private void makeAdjListSubgraph(int node) {
			this.adjList = new int[this.adjListOriginal.length][0];

			for (int i = node; i < this.adjList.length; i++) {
				Vector successors = new Vector();
				for (int j = 0; j < this.adjListOriginal[i].length; j++) {
					if (this.adjListOriginal[i][j] >= node) {
						successors.add(new Integer(this.adjListOriginal[i][j]));
					}
				}
				if (successors.size() > 0) {
					this.adjList[i] = new int[successors.size()];
					for (int j = 0; j < successors.size(); j++) {
						Integer succ = (Integer) successors.get(j);
						this.adjList[i][j] = succ.intValue();
					}
				}
			}
		}

		/**
		 * Calculates the strong connected component out of a set of scc's, that
		 * contains the node with the lowest index.
		 */
		private Vector getLowestIdComponent() {
			int min = this.adjList.length;
			Vector currScc = null;

			for (int i = 0; i < this.currentSCCs.size(); i++) {
				Vector scc = (Vector) this.currentSCCs.get(i);
				for (int j = 0; j < scc.size(); j++) {
					Integer node = (Integer) scc.get(j);
					if (node.intValue() < min) {
						currScc = scc;
						min = node.intValue();
					}
				}
			}

			return currScc;
		}

		private Vector[] getAdjList(Vector nodes) {
			Vector[] lowestIdAdjacencyList = null;

			if (nodes != null) {
				lowestIdAdjacencyList = new Vector[this.adjList.length];
				for (int i = 0; i < lowestIdAdjacencyList.length; i++) {
					lowestIdAdjacencyList[i] = new Vector();
				}
				for (int i = 0; i < nodes.size(); i++) {
					int node = ((Integer) nodes.get(i)).intValue();
					for (int j = 0; j < this.adjList[node].length; j++) {
						int succ = this.adjList[node][j];
						if (nodes.contains(new Integer(succ))) {
							lowestIdAdjacencyList[node].add(new Integer(succ));
						}
					}
				}
			}

			return lowestIdAdjacencyList;
		}

		 void getStrongConnectedComponents(int root) {
			this.sccCounter++;
			this.lowlink[root] = this.sccCounter;
			this.number[root] = this.sccCounter;
			this.visited[root] = true;
			this.stack.add(new Integer(root));

			for (int i = 0; i < this.adjList[root].length; i++) {
				int w = this.adjList[root][i];
				if (!this.visited[w]) {
					this.getStrongConnectedComponents(w);
					this.lowlink[root] = Math.min(lowlink[root], lowlink[w]);
				} else if (this.number[w] < this.number[root]) {
					if (this.stack.contains(new Integer(w))) {
						lowlink[root] = Math.min(this.lowlink[root], this.number[w]);
					}
				}
			}

			// found scc
			if ((lowlink[root] == number[root]) && (stack.size() > 0)) {
				int next = -1;
				Vector scc = new Vector();

				do {
					next = ((Integer) this.stack.get(stack.size() - 1)).intValue();
					this.stack.remove(stack.size() - 1);
					scc.add(new Integer(next));
				} while (this.number[next] > this.number[root]);

				// simple scc's with just one node will not be added
				if (scc.size() > 1) {
					this.currentSCCs.add(scc);
				}
			}
		}
	}
	/**************END: Detecting Friend Cycles************/
}