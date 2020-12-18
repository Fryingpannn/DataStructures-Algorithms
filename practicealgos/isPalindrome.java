package practicealgos;

import java.util.Queue;
import java.util.Stack;

/**
 * THINGS TO CHECK:
 * - alphanumeric only? (spaces, case sensitivity, special characters)
 * - when string is empty or equal to 1
 * 
 * @author mattx
 *
 */
public class isPalindrome {

	//iterative: 29 ms
    public static boolean pal(String s){
    	//replace all non alphanumeric characters, and remove case sensitivity
    	String str = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    	if(s.isEmpty()) return true;
    	
        int len = str.length();
    	
        for(int i = 0; i < len/2; i++)
        		if(str.charAt(i) != str.charAt(len-i-1))
        			return false;
        return true;
    }
    
    //recursive: TAIL RECURSIVE time O(n), space O(1)
    public static boolean palR(String s, int start, int end, boolean format){
    	String str;
    	if(format)
    		str = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
    	else
    		str = s;
        
    	if(str.isEmpty() || start >= end) //need to check for 1, otherwise substring will fail
            return true;
        if(str.charAt(0) != str.charAt(str.length()-1))
            return false;
        
       return palR(str, start+1, end-1, false);
    }
    
    //iterative: stack
    public boolean palS(String s){
        String str = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
      
        Stack<Character> stack = new Stack<Character>();
        
        for(int i = 0; i < str.length(); i++){
            stack.push(str.charAt(i));
        }
        
        for(int i = 0; i < stack.size(); i++){
            if(stack.pop() != str.charAt(i))
                return false;
        }
        return true;
    }
    
    //stringbuilder method
    public static boolean palBuilder(String s){
        String str = s.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        StringBuilder str1 = new StringBuilder(str);
        String re = str1.reverse().toString();
        return re.equals(str);
    }
    
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		pal(" ");
		String test = "0z;z   ; 0"; 
		if(palR("ab   cdedcb:   a", 0, test.length(), true))
			System.out.println("true");
		else
			System.out.println("false");
		
		String s = "ok";
		System.out.println(s.substring(1,1));
		
		Stack<Character> stack = new Stack<Character>();
	}

}
