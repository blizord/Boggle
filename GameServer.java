import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.Timer;
import java.util.ArrayList;

public class GameServer {
	
	private int numPlayers;
	private int[] score;
	private String[] name;
	private Socket[] server;
	private DataInputStream[] in;
	private DataOutputStream[] out;
	
	private char[][] grid = new char[4][4];
	private char[] letters = {'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'E', 'A', 'A', 'A', 'A', 'A', 'A', 'I', 'I', 'I', 'I', 'I', 'I', 'O', 'O', 'O', 'O', 'O', 'O', 'O', 'L', 'L', 'L', 'L', 'N', 'N', 'N', 'N', 'N', 'N', 'S', 'S', 'S', 'S', 'S', 'S', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'T', 'D', 'D', 'D', 'R', 'R', 'R', 'R', 'R', 'U', 'U', 'U', 'B', 'B', 'C', 'C', 'G', 'G', 'H', 'H', 'H', 'H', 'H', 'M', 'M', 'P', 'P', 'Y', 'Y', 'Y', 'F', 'F', 'K', 'V', 'V', 'W', 'W', 'W', 'J', 'Q', 'X', 'X', 'Z'};
	
	public GameServer(int numPlayers, Socket[] server, DataInputStream[] in, DataOutputStream[] out, String[] name) {
		this.numPlayers = numPlayers;
		this.name = name;
		this.server = server;
		this.in = in;
		this.out = out;
	}
	
	public void start() {
		createGrid();
		try {
			for(int i = 0; i < numPlayers; i++) {
				out[i].writeUTF("");
			}
			for(int i = 0; i < numPlayers; i++) {
				for(int x = 0; x < grid.length; x++) {
					for(int y = 0; y < grid[0].length; y++) {
						out[i].writeChar(grid[x][y]);
					}
				}
			}
			score = new int[numPlayers];
			String results = "";
			ArrayList<String>[] words = new ArrayList[numPlayers]; 
			for(int i = 0; i < numPlayers; i++) {
				words[i] = new ArrayList<String>();
				int numWords = in[i].readInt();
				for(int j = 0; j < numWords; j++) {
					words[i].add(in[i].readUTF());
				}
			}
			for(int i = 0; i < numPlayers; i++) {
				for(int a = 0; a < words[i].size(); a++) {
					for(int j = 0; j < numPlayers; j++) {
						if(j == i) { continue; }
						for(int t = 0; t < words[j].size(); t++) {
							if(words[i].get(a).equals(words[j].get(t))) {
								words[i].remove(a);
								words[j].remove(t);
							}
						}
					}
				}
			}
			for(int i = 0; i < numPlayers; i++) {
				for(int j = 0; j < words[i].size(); j++) {
					int l = words[i].get(j).length();
					if(l == 3 || l == 4) {
						score[i] += 1;
					}
					else if(l == 5) {
						score[i] += 2;
					}
					else if(l == 6) {
						score[i] += 3;
					}
					else if(l == 7) {
						score[i] += 5;
					}
					else {
						score[i] += 11;
					}
				}
			}
			for(int i = 0; i < numPlayers; i++) {
				results += "\n\n";
				results += name[i] + "'s words: ";
				for(int j = 0; j < words[i].size(); j++) {
					results += words[i].get(j) + ", ";
				}
				results += "\nTotal Score: " + score[i];
			}
			for(int i = 0; i < numPlayers; i++) {
				out[i].writeUTF(results);
			}
			System.out.println(results);
			
		} catch(Exception e) {
			e.printStackTrace();
			//System.out.println(e);
		}
		
	}
	
	private void createGrid() {
		for(int x = 0; x < grid.length; x++) {
			for(int y = 0; y < grid[0].length; y++) {
				grid[x][y] = letters[(int)(Math.random() * letters.length)];
			}
		}
	}
	
	
}
