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
	
	/****************Start: Insert/Remove a string into a trie ****************/
	static boolean insert(String s, TrieNode root){
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
	
	static boolean removeBySettingIsStringFlagToFalse(String s, TrieNode root){
		TrieNode curNode = root;
		
		for(char c : s.toCharArray()){
			
			if(!curNode.childs.containsKey(c))
				return false; // not in the trie
			
			curNode = curNode.childs.get(c);
		}
		
		if(curNode.isString){
			curNode.isString = false;
			return true;
		}
		else
			//given string wasn't a word in the trie. It was just the prefix of another word.
			return false;
	}
	/******************END: Insert/Remove a string into a trie ****************/
	
	
	/*****************Start: Is string in the trie? *****************/
	static boolean isAPrefixInTrie(String s, TrieNode root){
		TrieNode cur = root;
		
		for(char c : s.toCharArray()){
			if(!cur.childs.containsKey(c))
				return false;
			else
				cur = cur.childs.get(c);
		}
		
		return true;
	}
	
	static boolean isAStringInTrie(String s, TrieNode root){
		TrieNode cur = root;
		
		for(char c : s.toCharArray()){
			if(!cur.childs.containsKey(c))
				return false;
			else
				cur = cur.childs.get(c);
		}
		
		if(cur.isString)
			return true;
		else
			return false;
	}
	/*******************END: Is string in the trie? *****************/
	

	/********Start: find shortest prefix not in a given trie ********/
	static String getShortestPrefixNotInTrie(String s, TrieNode root){
		/**O(s.length)
		 * Returns the shortest prefix of s that is not a prefix in this trie. Basically, the last
		 * character of the returned string is the first character of s (input) that is not in the trie.
		 */
		String curPrefix = new String();
		TrieNode curNode = root;
		
		for(char c : s.toCharArray()){
			curPrefix += c;
			
			if(!curNode.childs.containsKey(c))
				return curPrefix;
			
			curNode = curNode.childs.get(c);
		}
		
		return "";
	}
	/**********END: find shortest prefix not in a given trie ********/
	
	
	/***Start: is a string made up of other words or prefixes in the tre? ***/
	static boolean isMadeUpOtherPrefixesInTheTrie(String s, TrieNode root){
		
		StringBuffer prefix = new StringBuffer();
		
		for(char c : s.toCharArray()){
			prefix.append(c);
			
			if(!isAPrefixInTrie(prefix.toString(), root)){
				/* All the chars before the last one in the prefix, are in the trie,
				 * See if the last char of prefix is the start of another string in the trie.
				 * If there is only one char in prefix, then we have already checked if char is 
				 * start of another string and the answer was no, so return false. 
				 */
				if(prefix.length() < 2)
					return false;
				
				String ss = s.substring(prefix.length() - 1, s.length());
				return isMadeUpOtherPrefixesInTheTrie(ss, root);
			}
		}
		
		return true;
	}
	
	static boolean isMadeUpOtherWordsInTheTrie(String s, TrieNode root){
		
		StringBuffer prefix = new StringBuffer();
		
		for(char c : s.toCharArray()){
			prefix.append(c);
			
			if(!isAPrefixInTrie(prefix.toString(), root)){
				/* All the chars before the last one in the prefix, are prefixes in the trie,
				 * Check to see if they are a word in the trie (not just prefix), if true, then
				 * See if the last char of prefix is the start of another string in the trie.
				 * If false, return false.
				 * If there is only one char in prefix, then we have already checked if char is 
				 * start of another string and the answer was no, so return false. 
				 */
				if(!isAStringInTrie(prefix.substring(0,prefix.length() - 1), root))
					return false;
				
				if(prefix.length() < 2)
					return false;
				
				String ss = s.substring(prefix.length() - 1, s.length());
				return isMadeUpOtherWordsInTheTrie(ss, root);
			}
		}
		
		//We know that every char in s is a prefix in the trie, check to make sure the last 
		//substring is an actual word.
		return isAStringInTrie(s, root);
	}
	/*****END: is a string made up of other words or prefixes in the tre? ***/
	
	
	
	public static void main(String[] args) {
		TrieNode root = new TrieNode();
		
		insert("dog", root);
		insert("cat", root);
		insert("catsdog",root);
		
		removeBySettingIsStringFlagToFalse("catsdog", root);

		System.out.println(isMadeUpOtherWordsInTheTrie("catsdog", root));
	}

}
