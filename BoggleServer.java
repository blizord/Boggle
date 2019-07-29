import java.net.*;
import java.io.*;
import java.util.Scanner;

public class BoggleServer {	
	public static void main(String [] args) {
		System.out.println("How many players?");
		Scanner scan = new Scanner(System.in);
		ServerSocket serverSocket;
		int numPlayers = scan.nextInt();
		Socket[] server = new Socket[numPlayers];
		boolean[] ready = new boolean[numPlayers];
		DataInputStream in[] = new DataInputStream[numPlayers];
		DataOutputStream out[] = new DataOutputStream[numPlayers];
		try {
			int port = Integer.parseInt(args[0]);
			serverSocket = new ServerSocket(port);
			System.out.println("Waiting for client on port " + serverSocket.getLocalPort() + "...");
			for(int i = 0; i < numPlayers; i++) {
				server[i] = serverSocket.accept();
				System.out.println(server[i].getRemoteSocketAddress() + " has joined the server");
				in[i] = new DataInputStream(server[i].getInputStream());     
				out[i] = new DataOutputStream(server[i].getOutputStream());
			}
			
			while (!allTrue(ready)) {
				for(int i = 0; i < numPlayers; i++) {
					if(in[i].readUTF().equals("y")) {
						ready[i] = true;
					}
				}
			}
			
			GameServer gameServer = new GameServer(server, in, out);
			
			gameServer.start();
			
		} catch(Exception e) {
			System.out.println(e);
		}
	}
	
	public static boolean allTrue(boolean[] array) {
		for (int i = 0; i < array.length; i++) {
			if (!array[i]) {
				return false;
			}
		}
		return true;
	}
}
