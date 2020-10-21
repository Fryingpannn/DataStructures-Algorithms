/*
 * Matthew Pan
 * 40135588
 * Assignment 2 Version 1: Recursive Depth-First Search Algorithm board game
 * Comp 352
 * Dr. Nora Houari
 * Oct. 17, 2020
 */
package A2;

import java.util.Random;
import java.util.Scanner;

/**
 * This class represents a board game in which you must reach the goal case (0 value). The number of 
 * possible movements for each step are restricted to the integers contained in each case.
 * 
 * This program utilizes a Depth-First Search algorithm to make sure each randomly generated board
 * has at least one possible path to reach the goal case before the game starts.
 * 
 * @author Matthew Pan
 */
public class RecurMagicBoard {
	//board of the game
	private static int[][] board;
	//same size as board; keeps track of the already visited cases
	private static boolean[][] visited;
	//start position index (row, col)
	private static int[] startPos; 
	
	/**
	 * Evaluates if the randomly generated board has a possible path to reach the goal (0 value).
	 * This recursive method utilizes DFS to evaluate all possibilities of each given direction
	 * one by one until the goal (0 value) is reached, or quits when all possibilities are exhausted.
	 * 
	 * @param row	index of the row we are currently at (starts at startPos[0])
	 * @param col	index of the column we are currently at (starts at startPos[1])
	 * @return true if goal (0 value) is reachable, false otherwise
	 */
	private boolean isValid(int row, int col) {
		int current = board[row][col];
		//sets the current index to true to indicate this position has been visited, thus preventing
		//other recursive calls to evaluate the same case more than once.
		visited[row][col] = true;
		
		//base case, return if goal found
		if(current == 0) {
			return true;
		}
		
		/* Recursive cases below 
		 * - each recursive call will only be made if a move in a given direction is valid and if
		 *   the case has not been visited before.
		 */
		
		//next positions' indices
		int north = row - current;
		int south = row + current;
		int east = col + current;
		int west = col - current;
		
		//go north if valid
		if(north >= 0 && visited[north][col] != true)
			if(isValid(north, col))
				return true;
		
		//go south if valid
		if(south < board.length && visited[south][col] != true)	
			if(isValid(south, col))
				return true;

		//go east if valid
		if(east < board.length && visited[row][east] != true)
			if(isValid(row, east))
				return true;
		
		//go west if valid
		if(west >= 0 && visited[row][west] != true)
			if(isValid(row, west))
				return true;
		
		return false;
	}
	
	/**
	 * Recursive method which allows the user to play the game. 
	 * Lets the user choose their direction to find the zero value.
	 * A recursive call is made each time the user chooses a direction (unless the game quits).
	 * 
	 * @param row	index of the row we are currently at (starts at startPos[0])
	 * @param col	index of the column we are currently at (starts at startPos[1])
	 * @param read	scanner obj to allow user input for direction choice
	 */
	private void play(int row, int col, Scanner read) {
		int current = board[row][col];
		
		//base case
		if(current == 0) {
			System.out.println("\n ********* Nice job!! You reached the goal!!!!!! *********");
			return;
		}
		
		//next positions' indices
		int north = row - current;
		int south = row + current;
		int east = col + current;
		int west = col - current;
		
		//indicates if this direction is currently valid
		boolean goNorth = north >= 0;
		boolean goSouth = south < board.length;
		boolean goEast = east < board.length; 
		boolean goWest = west >= 0; 
		
		/* asks user to choose in which direction he/she wants to move */
		System.out.println("\n===============================================================================");
		System.out.println("\n --> Your current position is (" + row + ", " + col + "), "
				+ "you can now move " + current + " case(s) <--" );
		System.out.println("\n- In which direction would you like to go?\n  "
				+ "*Note: going in directions with a (false) tag means you'll fall off the board!\n"
				+ "\n\t1. North (" + goNorth + ") 2. South (" + goSouth + ") 3. East (" + goEast + ") "
						+ "4. West (" + goWest + ") 5. Quit");
		System.out.print("\n Enter your number choice: ");
		int choice = read.nextInt();
		
		//recursive calls in the user input direction if valid, otherwise quits
		switch(choice) {
			case 1: if(goNorth)	play(north, col, read);
					else System.out.println("\nYou fell off the board! Defaulted to Quit... Try again next time!");
				break;
			case 2: if(goSouth) play(south, col, read);
					else System.out.println("\nYou fell off the board! Defaulted to Quit... Try again next time!");
				break;
			case 3: if(goEast) play(row, east, read);
					else System.out.println("\nYou fell off the board! Defaulted to Quit... Try again next time!");
				break;
			case 4: if(goWest) play(row, west, read);
					else System.out.println("\nYou fell off the board! Defaulted to Quit... Try again next time!");
				break;
			case 5: System.out.println("\n Better luck next time!"); return;
			default: System.out.println("\nInvalid choice. Defaulted to Quit... Try again next time!"); return; 
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
		
		//test board to see if configuration is valid
		System.out.print("\n Performing DFS to check if current board configuration is valid...");
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
		RecurMagicBoard board = new RecurMagicBoard();
		
		//start game
		board.menu();
	}
}
