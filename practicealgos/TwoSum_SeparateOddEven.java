package practicealgos;
import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map; 
//this file contains 1. separate odd and even & 2. two sum algos
  
//    REARRANGE ODD NUMBERS TO THE END OF AN ARRAY

class TwoSum_SeparateOddEven
{ 

    static void separateOddEven(int arr[], int n) 
    { 
        int odd = -1;
        int temp; 
      
        for (int i = 0; i < n; i++) { 
      
            // swap if we see a even number, will swap it with odd
            if (arr[i] % 2 == 0) { 
      
                // odd will start pointing at odd nb if there's one
                odd++; 
      
                // swap the element 
                temp = arr[i]; 
                arr[i] = arr[odd]; 
                arr[odd] = temp; 
            } 
        } 
    } 
    
    //O(n^2) two sum solution
    static int[] twoSum1(int[] arr, int target) {
    	
    	for(int i = 0; i < arr.length; i++) {
    		for(int j = i + 1; j < arr.length; j++) {
    			if(arr[j] == target - arr[i])
    				return new int[] {i, j};
    		}
    	}
    	return null;
    }
    
    //O(n) two sum solution
    static int[] twoSum(int[] arr, int target) {
    	Map<Integer, Integer> sum = new HashMap<Integer, Integer>();
    	
    	for(int i = 0; i < arr.length; i++) {
    		int complement = target - arr[i];
    		if(sum.containsKey(complement))
    			return new int[] {sum.get(complement), i};
    		sum.put(arr[i], i);
    	}
    	return null;
    }
    
    
    // Driver code 
    public static void main(String args[]) 
    { 
        int arr[] = { 51, 88, 3, 70, 96, 38, 47}; 
        int n = arr.length; 
      
        separateOddEven(arr, n); 
      
        for (int i = 0; i < n; i++) 
            System.out.print(arr[i] + " "); 
        
        System.out.println();
        for(int a : twoSum(arr, 54))
        	System.out.printf("[%d]", a);
        
        int arr2[] = { 1, 10, 9, 45, 2, 10, 10, 45 }; 
        Arrays.sort(arr2);
        for(int a : arr2)
        	System.out.print(a + " ");
       
    } 
} 
 