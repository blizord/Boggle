import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Game {
	
	private DataInputStream in;
	private DataOutputStream out;
	private Scanner scan = new Scanner(System.in);
	
	private boolean running = true;
	private char[][] grid = new char[4][4];
	private String word = "";
	private String wordList = "";
	private int path = 1;
	private int[][] solution;
	ArrayList<String> words = new ArrayList<String>();
	
	public Game(DataInputStream in, DataOutputStream out) {
		this.in = in;
		this.out = out;
	}
	
	Thread task = new Thread() {
		public void run() {
			try {
				while(running) {
					if(isInterrupted()) {
						throw new InterruptedException();
					}
					System.out.println();
					printGrid();
					System.out.println();
					printWordsFound();
					System.out.print("Enter a word ");
					word = scan.nextLine();
					word = word.toUpperCase();
					checkWord(word);
				}		
			} catch(InterruptedException e) {
				//System.out.println(e);
			}
		}
	};
	
	public void start() {
		
		loadGrid();
		wordList = loadWordList();
		
		Thread t = new Thread(task);
		t.start();
		
		long start = System.currentTimeMillis();
		long end = start + 180 * 1000;
		while (System.currentTimeMillis() < end) {
			//wait
		}
		System.out.println("Times up. Press enter to see results");
		running = false;
		
	}
	
	public ArrayList<String> getWords() {
		return words;
	}
	
	private void checkWord(String word) {
		for(String i : words) {
			if(i.equals(word)) {
				System.out.println("\nAlready found");
				return;
			}
		}
		if(word.length() < 3) {
			System.out.println("\nToo short");
		}
		else if(!inGrid(grid, word)) {
			System.out.println("\nNot in puzzle");
		}
		else if(!inDict(word))  {
			System.out.println("\nNot in Dictionary");
		}
		else {
			words.add(word);
		}
	}
	
	private boolean inGrid(char[][] grid, String word) {
		int n = grid.length;
		path = 1;
		solution = new int[n][n];
		for (int x = 0; x < n; x++) {
			for (int y = 0; y < n; y++) {
				if (search(grid, word, x, y, 0, n)) {
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean search(char[][] matrix, String word, int row, int col, int index, int N) {

		// check if current cell not already used or character in it is not not

		if (solution[row][col] != 0 || word.charAt(index) != matrix[row][col]) {
			return false;
		}

		if (index == word.length() - 1) {
			// word is found, return true
			solution[row][col] = path++;
			return true;
		}

		// mark the current cell as 1
		solution[row][col] = path++;		
		// check if cell is already used

		if (row + 1 < N && search(matrix, word, row + 1, col, index + 1, N)) { // go
																				// down
			return true;
		}
		if (row - 1 >= 0 && search(matrix, word, row - 1, col, index + 1, N)) { // go
																				// up
			return true;
		}
		if (col + 1 < N && search(matrix, word, row, col + 1, index + 1, N)) { // go
																				// right
			return true;
		}
		if (col - 1 >= 0 && search(matrix, word, row, col - 1, index + 1, N)) { // go
																				// left
			return true;
		}
		if (row - 1 >= 0 && col + 1 < N
				&& search(matrix, word, row - 1, col + 1, index + 1, N)) {
			// go diagonally up right
			return true;
		}
		if (row - 1 >= 0 && col - 1 >= 0
				&& search(matrix, word, row - 1, col - 1, index + 1, N)) {
			// go diagonally up left
			return true;
		}
		if (row + 1 < N && col - 1 >= 0
				&& search(matrix, word, row + 1, col - 1, index + 1, N)) {
			// go diagonally down left
			return true;
		}
		if (row + 1 < N && col + 1 < N
				&& search(matrix, word, row + 1, col + 1, index + 1, N)) {
			// go diagonally down right
			return true;
		}

		// if none of the option works out, BACKTRACK and return false
		solution[row][col] = 0;
		path--;
		return false;
	}

	
	private boolean inDict(String word) {
		if(wordList.indexOf(" " + word + " ") != -1) {
			return true;
		}
		return false;
	}
	
	private void printGrid() {
		for(int x = 0; x < grid.length; x++) {
			for(int y = 0; y < grid[0].length; y++) {
				System.out.print(grid[x][y]);
			}
			System.out.println();
		}
	}
	
	private void printWordsFound() {
		System.out.print("Words Found: ");
		for(int i = 0; i < words.size(); i++) {
			System.out.print(words.get(i) + ", ");
		}
		System.out.println();
	}
	
	private void loadGrid() {
		try {
			for(int x = 0; x < grid.length; x++) {
				for(int y = 0; y < grid[0].length; y++) {
					grid[x][y] = in.readChar();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private String loadWordList() {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get("Words2.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content.toUpperCase();
	}
	
}
