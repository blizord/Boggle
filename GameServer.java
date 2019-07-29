import java.net.*;
import java.io.*;
import java.nio.file.*;
import java.util.Scanner;
import java.util.Timer;

public class GameServer {
	
	private Socket[] server;
	private DataInputStream[] in;
	private DataOutputStream[] out;
	
	private char[][] grid = new char[4][4];
	private char[] letters = {'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'A', 'A', 'A', 'A', 'A', 'A', 'I', 'I', 'I', 'I', 'I', 'I', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'L', 'L', 'L', 'L', 'N', 'N', 'N', 'N', 'N', 'N', 'S', 'S', 'S', 'S', 'S', 'S', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'D', 'D', 'D', 'R', 'R', 'R', 'R', 'R', 'U', 'U', 'U', 'B', 'B', 'C', 'C', 'G', 'G', 'H', 'H', 'H', 'H', 'H', 'M', 'M', 'P', 'P', 'Y', 'Y', 'Y', 'F', 'F', 'K', 'V', 'V', 'W', 'W', 'W', 'J', 'Q', 'X', 'X', 'Z'};
	
	private String wordList = "";
	
	public GameServer(Socket[] server, DataInputStream[] in, DataOutputStream[] out) {
		this.server = server;
		this.in = in;
		this.out = out;
	}
	
	public void start() {
		createGrid();
		wordList = loadWordList();
		Timer timer = new Timer();
		
	}
	
	private void createGrid() {
		for(int x = 0; x < grid.length; x++) {
			for(int y = 0; y < grid[0].length; y++) {
				grid[x][y] = letters[(int)(Math.random() * letters.length)];
			}
		}
	}
	
	private String loadWordList() {
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get("Words.txt")));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return content;
	}
	
}
