/*
s * Matthew Pan
 * 40135588
 * Assignment 1 Version 1: Recursive method for binary strings
 * Comp 352
 * Dr. Nora Houari
 * Sept. 18, 2020
 */
package A1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class RecursiveBStrings {
		
	/**
	 * This recursive method generates all combinations of a given binary string containing wild cards.
	 * This algorithm uses backtracking to permutate the wild cards in the given string.
	 * By using the StringBuilder class, we don't have to convert the given string into an array.
	 *  
	 * @param str the input binary string
	 * @param i indicates the index of the char we're looking at inside the given string
	 */
	public static void RevealStr(StringBuilder str, int i) {
		//base case: prints each possibility when reached
		try(PrintWriter out = new PrintWriter(new FileWriter("out.txt", true))){
			if(str.length() == i) {
				System.out.println(str);
				out.println(str);
				return;
			}
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
		
		//recursive step if wild card (*) found
		if(str.charAt(i) == '*') {
			//exploring permutations for 0 and 1 in the stack frame
			for(char ch = '0'; ch <= '1'; ch++) {
				//making the change to our string
				str.setCharAt(i, ch);
				//recur to reach permutations
				RevealStr(str, i+1);
				//undo changes to backtrack (important for the 2nd branch that starts with 1; the other wild cards need to be set back to *)
				str.setCharAt(i, '*');
			}
			return;
		}
		else	//if no wild card found, recur next char
			RevealStr(str, i+1);
	}

	public static void main(String[] args) {
		System.out.println("============= Recursive function to display permutations of a given string pattern =============\n");
		
		//generating a random binary number to append wild cards to
		Random rand = new Random();
		int binaryNb = rand.nextInt(64);
		StringBuilder test = new StringBuilder(Integer.toBinaryString(binaryNb));
		
		StringBuilder demo = new StringBuilder("0*0**0**0000");
		RevealStr(demo, 0);
		
		//displaying the permutations of the binary string with incrementing wild cards
		//also writing the output to a text file
		try (PrintWriter out = new PrintWriter(new FileWriter("out.txt", true))){
			System.out.println("[Recursive]\nThe randomized binary number is " + test + ". These are the combinations of it when incrementing with two wildcards repeatedly.\n");
			out.println("\n[Recursive]\nThe randomized binary number is " + test + ". These are the combinations of it when incrementing with two wildcards repeatedly.\n");
			for(int i = 0; i <= 5; i += 2) { // -------------> put 50 for up to a 100 stars.
					test.append("**");
					long timeStart = System.nanoTime();
					RevealStr(test, 0);
					long timeStop = System.nanoTime();
					System.out.println("Runtime Above -- " + (timeStop - timeStart) + "ns");
					out.println("Runtime Above -- " + (timeStop - timeStart) + "ns");
				}
			System.out.println("\n============= End of program =============");
			out.println("\n============= End of program =============\n");
		}
		catch(IOException e) {
			System.out.println(e.getMessage());
		}
	}
}
