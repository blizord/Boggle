import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.ArrayList;

public class Boggle {
	public static void main(String [] args) {
		Scanner scan = new Scanner(System.in);
		String serverName = args[0];
		int port = Integer.parseInt(args[1]);
		try {
			System.out.println("Connecting to " + serverName + " on port " + port);
			Socket s = new Socket(serverName, port);
			System.out.println("Just connected to " + s.getRemoteSocketAddress());
			
			DataOutputStream out = new DataOutputStream(s.getOutputStream());
			DataInputStream in = new DataInputStream(s.getInputStream());
			
			String ready = "";
			System.out.println("Type name to ready up");
			ready = scan.nextLine();
			out.writeUTF(ready);
			
			//wait for GameServer to send signal to start
			in.readUTF();
			
			Game game = new Game(in, out);
			
			game.start();
			ArrayList<String> words = game.getWords();
			out.writeInt(words.size());
			for(int i = 0; i < words.size(); i++) {
				out.writeUTF(words.get(i));
			}
			
			try {
				String results = in.readUTF();
				System.out.println(results);
			} catch(IOException e) {
				System.out.println(e);
			}
		
			
			
        } catch (Exception e) {
			//handle exception
        }
	}
}
