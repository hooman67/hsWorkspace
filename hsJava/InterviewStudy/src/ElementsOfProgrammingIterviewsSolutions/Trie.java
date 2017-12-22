package ElementsOfProgrammingIterviewsSolutions;

import java.util.HashMap;

class TrieNode{
	boolean isString; //Whether the path from root to this node is an element in the collection.
	HashMap<Character, TrieNode> childs;
	TrieNode(){
		isString = false;
		childs = new HashMap<Character, TrieNode>();
	}
}
public class Trie {
	TrieNode root;
	Trie(){
		root = new TrieNode();
	}
	boolean insert(String s){
		TrieNode curNode = root;
		
		for(char c : s.toCharArray()){
			
			if(!curNode.childs.containsKey(c))
				curNode.childs.put(c, new TrieNode());
			
			curNode = curNode.childs.get(c);
		}
		
		//s already existed in trie return false
		if(curNode.isString){
			return false;
		}
		else{
			curNode.isString = true;
			return true;
		}	
	}
	
	String getShortestPrefixNotInTry(String s){
		/**O(s.length)
		 * Returns the shortest prefix of s that is not in this try
		 */
		String curPrefix = new String();
		TrieNode curNode = root;
		
		for(char c : s.toCharArray()){
			curPrefix += c;
			
			if(!curNode.childs.containsKey(curPrefix))
				return curPrefix;
			
			curNode = curNode.childs.get(c);
		}
		
		return "";
	}
	
	boolean isInTrie(String s){
		//TODO
		return false;
	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
