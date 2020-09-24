package A1;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Stack;

public class IterativeBStrings {
	
	/**
	 * This iterative method generates all combinations of a given binary string containing wild cards.
	 * This algorithm uses a stack data structure to store the binary strings. The strings in the stack are
	 * checked to see if they contain stars, if so, replace with 0 and 1, then pop & display until empty.
	 * 
	 * @param s the input binary string
	 */
	static void RevealStr(StringBuilder s) {
		//stack to store the binary strings
		Stack<StringBuilder> strStack = new Stack<StringBuilder>();
		strStack.push(s);
		
		//will keep permutating and displaying the strings till stack is empty
		while(!strStack.empty()) {
			
			//popping a string to replace stars or directly print if nothing to replace
			StringBuilder str = strStack.pop();
			//stores the index of the * character in string
			int star = str.indexOf("*");
			
			//if wild card exists, then replace it with 0 and 1 and push both to the stack
			if(star != -1) {
				for(char c = '0'; c <= '1'; c++) {
					StringBuilder newStr = new StringBuilder(str);
					newStr.setCharAt(star, c);
					strStack.push(newStr);
				}
			}
			else {	//print string if no wild card
				try(PrintWriter out = new PrintWriter(new FileWriter("out2.txt", true))){
				System.out.println(str);
				out.println(str);
				}
				catch(IOException e) {
					System.out.println(e.getMessage());
				}
			}
		}
	}

	public static void main(String[] args) {
		System.out.println("============= Iterative function to display permutations of a given string pattern =============\n");
		
		//generating a random binary number to append wild cards to
		Random rand = new Random();
		int binaryNb = rand.nextInt(64);
		StringBuilder test = new StringBuilder(Integer.toBinaryString(binaryNb));
		
		//displaying the permutations of the binary string with incrementing wild cards
		//also writing the output to a text file
		try (PrintWriter out = new PrintWriter(new FileWriter("out2.txt", true))){
			System.out.println("[Iterative]\nThe randomized binary number is " + test + ". These are the combinations of it when incrementing with two wildcards repeatedly.\n");
			out.println("\n[Iterative]\nThe randomized binary number is " + test + ". These are the combinations of it when incrementing with two wildcards repeatedly.\n");
			for(int i = 0; i <= 5; i += 2) { // -------------> put 50 for up to a 100 stars.
					test.append("**");
					long timeStart = System.nanoTime();
					RevealStr(test);
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
