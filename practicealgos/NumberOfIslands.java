package practicealgos;

public class NumberOfIslands {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
    public int numIslands(char[][] grid) {
        //when is it island?
        //case 1: finding a 1 that is surrounded by 0 in 4 directions
        //case 2: finding a 1 that has at least another 1 neighbouring
        //          --> look at neighbours recursively until all zeros beside or already visited
        //          --> visited spots are marked
        
        //--> Loop over the 2d grid, every time you find a 1, recurse on the cases neigbouring it
        //    to see if it's connected to other land. If there are, set them all to 0 to mark this
        //    land as being already counted and increment counter by one.
        //--> I think this is O(n^2) = n^2 + n^2
        if(grid == null)
            return 0;
        
        int count = 0;
        
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++)
                if(grid[i][j] == '1'){ //get rid of one island
                    check(i, j, grid);
                    count++; 
                }
        }
        
        return count;
    }
    
    //recursive function: check if there are other 1s around the one found, if so get rid of them
    //to prevent repetitive counts
    public void check(int i, int j, char[][] grid){
        if(i >= 0 && j >= 0 && i < grid.length && j < grid[i].length && grid[i][j] == '1'){    
            grid[i][j] = '0';
            check(i + 1, j, grid);
            check(i - 1, j, grid);
            check(i, j + 1, grid);
            check(i, j - 1, grid);
        }
        return; 
    }
}
