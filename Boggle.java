import java.net.*;
import java.io.*;
import java.util.Scanner;

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
			while(!ready.equals("y")) {
				System.out.println("Type 'y' to ready up");
				ready = scan.nextLine();
				out.writeUTF(ready);
			}
			
        } catch (Exception e) {
			//handle exception
        }
	}
}
