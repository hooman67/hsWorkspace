package ElementsOfProgrammingIterviewsSolutions;

public class Strings {
	/******************START: RunLength Encoding*****************/
	static void runLengthEncode(StringBuffer s){
		/**O(n) time, O(1) space
		 */
		int count = 1;
		int outInd = 0;
		for(int i = 1; i < s.length(); i++){
			if(s.charAt(i-1) == s.charAt(i))
				count++;
			else{
				s.setCharAt(outInd++, s.charAt(i-1));
				while(count > 1){
					int temp = count % 10;
					s.setCharAt(outInd++, (char)(temp+'0'));
					count -= temp;
					count /= 10;
				}
				count = 1;
			}
		}
		s.setCharAt(outInd++, s.charAt(s.length()-1));
		while(count > 1){
			int temp = count % 10;
			s.setCharAt(outInd++, (char)(temp+'0'));
			count -= temp;
			count /= 10;
		}
		
		s.setLength(outInd);
	}
	
	static String runLengthEncode(String s){
		/**O(n) time, O(n) space. Can do in C++ in O(1) space but not java unless input is StringBuffer
		 */
		int count = 1;
		StringBuffer buf = new StringBuffer();
		for(int i =1; i < s.length(); i++){
			if(s.charAt(i) == s.charAt(i-1)){
				count++;
			}
			else{
				buf.append(s.charAt(i-1));
				if(count > 1)
					buf.append(Integer.toString(count));
				count = 1;
			}
		}
		
		buf.append(s.charAt(s.length()-1));
		if(count > 1)
			buf.append(Integer.toString(count));
		
		return buf.toString();
	}

	static String runLengthDecode(String s){
		StringBuffer out = new StringBuffer();
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) >= '0' && s.charAt(i) <= '9'){
				int count = ((int)(s.charAt(i)-'0')) - 1;
				while(count-- > 0 ){
					out.append(s.charAt(i-1));
				}
			}
			else{
				out.append(s.charAt(i));
			}
		}

		return out.toString();
	}
	/******************END: RunLength Encoding*******************/
	
	
	/******************START: Find SubString*********************/
	/******************END: Find SubString***********************/
	
	
	/*********START: Reverse the order of words******************/
	static void reverseWords(StringBuffer s){
		/**time O(n), space O(1)
		 */
		reverseString(s, 0, s.length());
		int start = 0;
		
		for(int i = 0; i < s.length(); i++){
			if(s.charAt(i) == ' '){
				reverseString(s, start, i);
				start = i+1;
			}
		}
		
		reverseString(s, start, s.length());
	}
	static void reverseString(StringBuffer s, int start, int end){
		/**time O(n), space O(1)
		 */
		for(int i = 0; i < (end-start)/2; i++){
			char temp = s.charAt(i+start);
			s.setCharAt(i+start, s.charAt(end-1 - i));
			s.setCharAt(end-1 - i, temp);
		}
	}
	/***********END: Reverse the order of words******************/
	
	
	/***START: in sorted string replace 'a' with "dd" and delete 'b'***/	
	static void subst(StringBuffer s){
		/**Time O(n), space O(1), uses reverseString(s, start, end) above */
		int acount = 0;

		while(s.charAt(acount) == 'a' && acount < s.length())
			acount++;

		int dcount = acount*2;

		int endInd = s.length();
		for(int i =0; i < dcount; i++){
			if(s.charAt(i) == 'a' || s.charAt(i) == 'b')
				s.setCharAt(i, 'd');
			else{
				s.setCharAt(--endInd, s.charAt(i));
				s.setCharAt(i, 'd');
			}
		}
		
		for(int i = dcount; i < s.length(); i++){
			if(s.charAt(i) == 'b')
				s.delete(i, i+1);
		}
		
		if(endInd != s.length())
			reverseString(s, dcount, s.length());
	}
	/*****END: in sorted string replace 'a' with "dd" and delete 'b'***/
	
	
	/*********START: String Matching******************/
	static int rabin_karp(String text, String s){
		/**time O(len.text + len.s), space O(1), 
		 * Return: Index of first match of s in text, -1 if not found
		 * matches the hashcode of s to hashcode of len.s size segments of text.
		 */
		if(s.length() > text.length())
			return -1;
		
		final int BASE = 26, MOD = 997;
		int s_hash = 0, text_hash = 0;
		
		//calc s_hash, and first text_hash segment
		for(int i =0; i < s.length(); i++){
			s_hash = (s_hash*BASE + s.charAt(i)) % MOD;
			text_hash = (text_hash*BASE + text.charAt(i)) % MOD;
		}
		
		//calc the rest of text_hash segments
		for(int i = s.length(); i < text.length(); i++){
			//if collision match the strings to make sure 
			if(s_hash == text_hash && s.compareTo( text.substring(i - s.length(), i) ) == 0){
				return i - s.length(); //found match
			}
			
			//take the first char out of hashcode for text
			text_hash -= ( text.charAt(i - s.length()) * (int)(Math.pow(BASE, s.length()-1)) )  % MOD;
			
			if(text_hash < 0)
				text_hash += MOD;
			
			//add the next char's hashcode 
			text_hash = (text_hash*BASE + text.charAt(i)) % MOD;
		}
		
		//match the last piece
		if(s_hash == text_hash && s.compareTo( text.substring(text.length() - s.length(), s.length()) ) == 0){
			return text.length() - s.length(); //found match
		}
		
		return -1; //s is not a substr in text
	}
	/***********END: String Matching******************/

	
	/***START: Print all posble phone nemonic representations of a nb***/
	static void phone_mnemonic(String num){
		String[] map = {"0", "1", "ABC", "DEF", "GHI", "JKL", "MNO", "PQRS", "TUV", "WXYZ"};
		StringBuffer ans = new StringBuffer(num);
		phone_mnemonic_helper(num, map, ans, 0);
	}
	static void phone_mnemonic_helper(String num, String[] map, StringBuffer ans, int d){
		if(d >= num.length())
			System.out.println(ans);
		else{
			for(char c : map[num.charAt(d) - '0'].toCharArray()){
				ans.setCharAt(d, c);
				phone_mnemonic_helper(num, map, ans, d+1);
			}
		}
	}
	/***end: Print all posble phone nemonic representations of a nb***/
	
	
	/***************START: String Multiplication of BigInts *************/
	class BigInt{
		int sign = 1; //1 or -1
		public char[] digits;
		
		BigInt(int capacity){
			digits = new char[capacity];
		}
		
		BigInt(String nb){
			sign = nb.charAt(0) == '-' ? -1 : 1;
			int tempSign = nb.charAt(0) == '-' ? 1 : 0;
			digits = new char[nb.length() - tempSign];
			
			int j = 0;
			for(int i = nb.length() - 1; i >= (tempSign); i--){
				if( nb.charAt(i) >= '0' && nb.charAt(i) <= '9')
					digits[j++] = (char)(nb.charAt(i) - '0');
			}
		}
		
		BigInt multiply(BigInt nb2){
			//If one nb is 0 out should be 0
			if(digits.length == 1 && digits[0] == (char)('0' - '0') || 
					nb2.digits.length == 1 && nb2.digits[0] == (char)('0' - '0')) {
				return new BigInt("0");
			}
			
			BigInt out= new BigInt(digits.length + nb2.digits.length);
			out.sign = sign*nb2.sign;
			
			int i,j;
			for(i = 0; i < nb2.digits.length; i++){
				int carry = 0;
				for(j=0; j < digits.length || (carry > 0); j++){
					int n_digit = out.digits[i+j] + 
							(j < digits.length ? nb2.digits[i] * digits[j] : 0) + carry;
					out.digits[i+j] =  (char)(n_digit%10);
					carry = n_digit / 10;
				}
			}
					
			return out;
		}
	}
	/*****************END: String Multiplication of BigInts *************/
}
