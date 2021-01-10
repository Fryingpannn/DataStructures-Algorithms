package practicealgos;
import java.util.Arrays;

public class TwoDimensionalGrid {

	
	//square sorted binary matrix: find the row with most 1s
	public static void findMaxRow(int[][] m) {
		int n = m.length;
		int row = 0;
		int j = 0, i;
		for( i = 0, j=n-1; i < n; i++) {
			
			while(m[i][j] == 1) {
				row = i;
				j--;
			}	
		}
		
		System.out.println("Row with max number of 1s: " + (row + 1));
		System.out.println("Number of 1s in this row: " + (n - j - 1));
		
	}
	
	
	
	
	
	
	public static void main(String[] args) {
		int[][] m = new int[4][4];
		
		for(int i = 0; i < m.length; i++) {
			Arrays.sort(m[i]);
		}
		
		int arr[][] = {{0, 0, 0, 1},  
                {0, 0, 0, 1},  
                {0, 1, 1, 1},  
                {0, 1, 1, 1}}; 
		
		findMaxRow(arr);
		
		System.out.println();
		
		for(int row = 0; row < arr.length; row++) {
			for(int col = 0; col < arr[row].length; col++)
				System.out.print(arr[row][col] + " ");
			System.out.println();
		}
		
		
	}

}
