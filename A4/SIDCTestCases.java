//Matthew Pan 40135588, November 2020

package A4;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class SIDCTestCases {

	public static void main(String[] args) {
		Random rand = new Random();
		Scanner read = new Scanner(System.in);
		System.out.println("1. Sequence manual entries");
		System.out.println("2. Sequence randomized entries");
		System.out.println("3. AVL Tree manual entries");
		System.out.println("4. AVL Tree randomized entries");
		System.out.println("5. Switching from sequence to AVL Tree threshold");
		System.out.println("6. Read from one of the 3 files");
		System.out.print("Choose test case: ");
		int choice = read.nextInt();
		System.out.println();

		/* ---- Test Cases ---- */
		switch(choice) {
		case 1:{ //Small entry number to showcase the individual methods
			System.out.println("[Sequence]");
			IntelligentSIDC test = new IntelligentSIDC();
			test.add(11111112, 1);
			test.add(11111118, 1);
			test.add(30000000, 10);
			test.add(30000001, 10);
			test.add(30000002, 10);
			test.add(30000003, 70);
			test.add(99999999, 1);  //TESTING REMOVE()
			test.remove(99999999);
			test.print();
			System.out.println("\nnextKey(30000001): " + test.nextKey(30000001));
			System.out.println("prevKey(30000001): " + test.prevKey(30000003));
			System.out.println("getValue(30000003): " + test.getValue(30000003));
			System.out.println("rangeKey(30000000, 30000003): " + test.rangeKey(30000000, 30000003));
			//displaying allKeys()
			System.out.println("\nDisplaying allKeys() sequence:");
			ArrayList<Integer> seq = test.allKeys();
			for(Integer i : seq)
				System.out.println(i);
			}
		break;
		case 2:{
			System.out.println("[Sequence]");
			System.out.print("Input sequence size (<=50 000): ");
			int size = read.nextInt();
			IntelligentSIDC test = new IntelligentSIDC(size);
			test.generate();
			test.print();
			}
		break;
		case 3:{
			System.out.println("[AVL Tree]");
			IntelligentSIDC test = new IntelligentSIDC(60000);
			test.add(11111112, 1);
			test.add(30000000, 10);
			test.add(30000001, 10);
			test.add(30000003, 70);
			test.add(11111118, 1);
			test.add(30000002, 10);
			test.add(99999999, 1);  //TESTING REMOVE()
			test.remove(99999999);
			test.print();
			System.out.println("\nnextKey(30000001): " + test.nextKey(30000001));
			System.out.println("prevKey(30000003): " + test.prevKey(30000003));
			System.out.println("getValue(30000003): " + test.getValue(30000003));
			System.out.println("rangeKey(30000000, 30000003): " + test.rangeKey(30000000, 30000003));
			//displaying allKeys()
			System.out.println("\nDisplaying allKeys() sequence:");
			ArrayList<Integer> seq = test.allKeys();
			for(Integer i : seq)
				System.out.println(i);
			}
		break;
		case 4:{
			System.out.println("[AVL Tree]");
			System.out.print("Input AVL Tree size (>50 000): ");
			int size = read.nextInt();
			IntelligentSIDC test = new IntelligentSIDC(size);
			test.generate();
			test.print();
			}
		break;
		case 5:{
			System.out.println("[Sequence + AVL Tree]");
			System.out.print("Original sequence size: 50000, adding one"
					+ "will convert the underlying\n data structure to an AVL Tree.");
			IntelligentSIDC test = new IntelligentSIDC(50000);
			test.generate();
			test.datastructure(); //display data structure being used
			test.add(99999999, 1); //adding to surpass threshold
			test.add(99999993, 1);
			test.add(99999995, 1);
			test.print();
			test.datastructure();
			}
		break;
		case 6:{//file read
			System.out.println("1. Text file 1\n2. Text file 2\n3. Text file 3");
			System.out.print("Choose a file: ");
			int file = read.nextInt();
			String txt;
			switch(file) {
			case 1: txt = "CSTA_test_file1.txt";
				break;
			case 2: txt = "CSTA_test_file2.txt";
					break;
			default: txt = "CSTA_test_file3.txt";
			}
			try(Scanner in = new Scanner(new FileReader(txt))){
				IntelligentSIDC test = new IntelligentSIDC();
				while(in.hasNextLine()) {
					test.add(in.nextInt(), rand.nextInt(10)+10);
				}
				test.print();
			}
			catch(Exception o) { o.printStackTrace(); }
			}
		break;
		default: System.out.println("Invalid input, please try again.");
		}
		read.close();
	}
}
