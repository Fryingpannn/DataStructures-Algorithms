package practicealgos;
import java.util.HashMap;
import java.util.Map;

public class FacebookStr {

	//question 2: string concatenation
	static String concatSwaps(String s, int[] sizes) {

	    Map<Integer, String> strMap = new HashMap<Integer, String>();
	    
	    //put separated string in hashmap
	    int sum = 0;
	    for(int i = 0; i < sizes.length; i++){
	
	    	if(i == 0)
	    		 strMap.put(i, s.substring(0, sizes[i]));
	    	else if(i+1 == sizes.length)
	    		strMap.put(i, s.substring(sum));
	    	else
	    		strMap.put(i, s.substring(sum, sum+sizes[i]));
	    	
	     	sum += sizes[i];
	    }
	    
	    //string builder to build the new string
	    StringBuilder str = new StringBuilder();
	    
	    for(int i = 0; i < strMap.size(); i += 2) {
	    	
	    	if(i + 1 == strMap.size())
	    		str.append(strMap.get(i));
	    	else {
		    	str.append(strMap.get(i+1));
		    	str.append(strMap.get(i));
	    	}
	    }
	    

	    strMap.entrySet().forEach(entry->{
	        System.out.println(entry.getKey() + " " + entry.getValue());  
	     });
	    
	    return str.toString();
	}
	
	//question 1: testing operations
	static boolean[] testOps(int[] a, char[] signs, int[] b, int[] c) {
	    boolean[] output = new boolean[a.length];
	    
	    for(int i = 0; i < a.length; i++){
	        /*I do not have enough time to finish. However, I think I will have to use
	        ASCII code 43 for the plus operator and 45 for the minus operator. I could check for their
	        equality then use the appropriate sign to add the indices and equate with the value in array c.
	        */
	        
	        if(signs[i] == 43){
	            output[i] = a[i] + b[i] == c[i];
	        }
	        if(signs[i] == 45)
	        output[i] = a[i] - b[i] == c[i] ;
	    }
	    
	    return output;
	}
	
	
	public static void main(String[] args) {
		int[] sizes = {5, 5, 2, 4,2};
		String test = concatSwaps("worldhellookdudehi", sizes);
		System.out.println(test);
		
		int[] a = {3, 2, -1, 4};
		int[] b = {2, 7, -5, 2};
		char[] signs = {'+', '-', '-', '+'};
		int[] c = {5, 5, 4, 2};
		
		boolean[] results = testOps(a, signs, b, c);
		
		for(boolean ok : results)
			System.out.println(ok);

	}

}
