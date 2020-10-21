/*
 * Matthew Pan
 * 40135588
 * Assignment 2 Version 2: Queue Breadth-First Search Algorithm board game
 * Comp 352
 * Dr. Nora Houari
 * Oct. 17, 2020
 */
package A2;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.Scanner;

/**
 * This class represents a board game in which you must reach the goal case (0 value). The number of 
 * possible movements for each step are restricted to the integers contained in each case.
 * 
 * This program utilizes a Breadth-First Search algorithm to make sure each randomly generated board
 * has at least one possible path to reach the goal case before the game starts.
 * 
 * @author Matthew Pan
 */
public class IterMagicBoard {
		//board of the game
		private static int[][] board;
		//same size as board; keeps track of the already visited cases
		private static boolean[][] visited;
		//start position index (row, col)
		private static int[] startPos; 
		
		/**
		 * Evaluates if the randomly generated board has a possible path to reach the goal (0 value).
		 * This iterative method uses Breadth-First Search (BFS) with a Queue to evaluate all cases
		 * and their adjacent cases first to find the goal case, or quits if it cannot be reached.
		 * 
		 * @param row	starting row index (startPos[0])
		 * @param col	starting col index (startPos[1])
		 * @return true if goal (0 value) is reachable, false otherwise
		 */
		private boolean isValid(int row, int col) {
			Queue<String> queue = new LinkedList<String>();
			
			//add starting position index into queue
			queue.add(row + "," + col);
			
			while(!queue.isEmpty()) {
				//retrieves the front coordinate from the queue and saves them as row and column indices
				String coord = queue.remove();
				int x = Integer.parseInt(coord.split(",")[0]);
				int y = Integer.parseInt(coord.split(",")[1]);
				
				//sets the current index to true to indicate this position has been visited, thus preventing
				//repeated cases to be added into the queue
				visited[x][y] = true;
				//sets current case value
				int current = board[x][y];
				
				if(!(current == 0)) {
					//next positions' indices
					int north = x - current;
					int south = x + current;
					int east = y + current;
					int west = y - current;
					
					/* Add each new direction's indices to the queue if they are valid */
					
					//evaluate north if valid
					if(north >= 0 && visited[north][y] != true)
						queue.add(north + "," + y);
					
					//evaluate south if valid
					if(south < board.length && visited[south][y] != true)
						queue.add(south + "," + y);

					//evaluate east if valid
					if(east < board.length && visited[x][east] != true)
						queue.add(x + "," + east);

					//evaluate west if valid
					if(west >= 0 && visited[x][west] != true)
						queue.add(x + "," + west);
				}
				else
					return true;
			}	
			return false;
		}
		
		/**
		 * Allows user input to choose a direction and move there if it is valid.
		 * This is repeated until the goal case is found. 
		 * This method uses a while loop in which each time a user chooses a direction,
		 * a new iteration is made.
		 * 
		 * @param row	index of starting row (startPos[0])
		 * @param col	index of starting column (startPos[1])
		 * @param read	scanner obj to allow user input for direction choice
		 */
		private void play(int row, int col, Scanner read) {
			//current; indicates the value contained inside current position
			int current = board[row][col];
			//x indicates the current row index. y indicates the current column index
			int x = row, y = col;
			//stops the loop when goal is reached or game quits
			boolean endLoop = false;
			
			//takes user input and moves current position there if valid
			while(!endLoop) {
				//next positions' indices
				int north = x - current;
				int south = x + current;
				int east = y + current;
				int west = y - current;
				
				//indicates if this direction is currently valid
				boolean goNorth = north >= 0;
				boolean goSouth = south < board.length;
				boolean goEast = east < board.length; 
				boolean goWest = west >= 0; 
				
				/* asks user to choose in which direction he/she wants to move */
				System.out.println("\n===============================================================================");
				System.out.println("\n --> Your current position is (" + x + ", " + y + "), "
						+ "you can now move " + current + " case(s) <--" );
				System.out.println("\n- In which direction would you like to go?\n  "
						+ "*Note: going in directions with a (false) tag means you'll fall off the board!\n"
						+ "\n\t1-North (" + goNorth + ") 2-South (" + goSouth + ") 3-East (" + goEast + ") "
								+ "4-West (" + goWest + ") 5-Quit");
				System.out.print("\n Enter your number choice: ");
				int choice = read.nextInt();
				
				//moves current position to the new position determined by user if valid, otherwise quits
				switch(choice) {
					case 1: if(goNorth) {
								current = board[north][y];
								x = north;	//sets the row coordinate to the new index to continue loop for next move
							}
							else System.out.println("\nYou'll fall off the board! Try a different direction...");
						break;
					case 2: if(goSouth) { 
								current = board[south][y];
								x = south;
							}
							else System.out.println("\nYou'll fall off the board! Try a different direction...");
						break;
					case 3: if(goEast) { 
								current = board[x][east];
								y = east;
							}
							else System.out.println("\nYou'll fall off the board! Try a different direction...");
						break;
					case 4: if(goWest) {
								current = board[x][west];
								y = west;
							}
							else System.out.println("\nYou'll fall off the board! Try a different direction...");
						break;
					case 5: System.out.println("\n Better luck next time!"); endLoop = true;
						break;
					default: System.out.println("\nInvalid choice. Try again!");
				}
				//quits loop if new position contains goal value
				if(current == 0) {
					System.out.println("\n ********* Nice job!! You reached the goal!!!!!! *********");
					endLoop = true;
				}
			}
		}
		
		/**
		 * Method to populate the board with random integers less than d. Also sets the goal's position.
		 * 
		 * @param d	dimension of the board d x d 
		 * @param start	start position chosen by user input
		 */
		public void createBoard(int d, int start) {
			if(d > 25 || d < 5) {
				d = 5;
				System.out.println("\n* Invalid board dimension. Defaulted size to 5 x 5. *");
			}
			
			//creating new board and its boolean mirror
			board = new int[d][d];
			visited = new boolean[d][d];
			
			//initializing board with random values
			Random rand = new Random();		
			for(int i = 0; i < board.length; i++) {
				for(int j = 0; j < board[i].length; j++) {
					board[i][j] = rand.nextInt(d -1) + 1;
					visited[i][j] = false;
				}
			}
			//sets the zero value randomly somewhere in the board
			setGoalPosition(start, d-1, rand);
		}
		
		/**
		 * Sets a random value in the board as the goal (0 value). 
		 * This goal position will not be the same as the starting position.
		 * This method also stores the index of the chosen starting position by the user.
		 * 
		 * @param start	start position chosen by user input
		 * @param end	last index of any given row or col
		 * @param rand	random number generator between 0 and d-1
		 */
		private void setGoalPosition(int start, int end, Random rand) {
			int rowGoal, colGoal;
			
			//set start position index given by user (one of the four corners)
			switch(start) {
				case 1: startPos = new int[] {0, 0};
					break;
				case 2: startPos = new int[] {0, end};
					break;
				case 3: startPos = new int[] {end, 0};
					break;
				case 4: startPos = new int[] {end, end};
					break;
				default: startPos = new int[] {0, 0}; System.out.println("\n* Invalid starting position."
						+ " Defaulted start to northwest. *");
			}
			
			//sets the goal value in a random position that isn't the start position
			do {
				rowGoal = rand.nextInt(end+1);
				colGoal = rand.nextInt(end+1);
			} while (startPos[0] == rowGoal && startPos[1] == colGoal);
			board[rowGoal][colGoal] = 0;
		}
		
		/**
		 * displays the formatted board
		 */
		public void displayBoard() {
			//printing index numbers
			System.out.print("Indices:     ");
			for(int i = 0; i < board.length; i++)
				System.out.printf("  %-2d  ", i);
			
			formatBoard();
			for(int i = 0; i < board.length; i++) {
				System.out.printf("\t %-2d |", i);
				for(int j = 0; j < board[i].length; j++) {
					System.out.printf("  %-2d |", board[i][j]);
				}
				formatBoard();
			}
		}
		
		/**
		 * formats the board with "-----" separators between lines
		 */
		private void formatBoard() {
			System.out.print("\n\t    -");
			for(int k = 0; k < board.length; k++)
				System.out.print("------");
			System.out.println();
		}
		
		/**
		 * menu of game; accepts user input for starting position and board size, creates the board and starts the game.
		 */
		public void menu() {
			Scanner read = new Scanner(System.in);
			
			//menu start; set starting position
			System.out.println("============================== Pan's Magic Board ===============================\n");
			System.out.println("\t\t\t[How to] \n First, choose a starting position and the board dimensions \n for your game. "
					+ "The goal is to reach the unique case containing\n zero. You can only move a number of "
					+ "steps as indicated in \n the case you are currently in. GL HF!\n");
			System.out.println("- Choose your starting position");
			System.out.print("\t1. Northwest (0, 0)"
					+ "\n\t2. Northeast (0, end)"
					+ "\n\t3. Southwest (end, 0)"
					+ "\n\t4. Southeast (end, end)"
					+ "\n\n Enter your number choice: ");
			int start = read.nextInt();
			
			//user input for board dimension d x d
			System.out.print("\n- Choose your board dimension (between 5 & 25): ");
			int size = read.nextInt();
			
			//creating board
			createBoard(size, start);
			System.out.println("\n Creating your board... Board created!\n");
			
			//display board
			displayBoard();
			
			//test board to see if configuration is valid then start game if it is
			System.out.print("\n Performing BFS to check if current board configuration is valid...");
			if(isValid(startPos[0], startPos[1])) {
				System.out.println(" Good to go!");
				play(startPos[0], startPos[1], read);
			}
			else
				System.out.println("\n [IMPOSSIBLE GAME] Oops, seems we have an impossible game, try creating a new board!");
			
			System.out.println("\n============================= Thanks for playing! ==============================\n");
			read.close();
		}
		
		/* main method to run program */
		public static void main(String[] args) {
			IterMagicBoard board = new IterMagicBoard();
			
			//start game
			board.menu();
		}
	}
