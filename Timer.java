import java.io.*;
//import java.lang.*;

//public class Timer implements Runnable {
	
	private Thread t;
	
	public Timer() {
		
	}
	
	public void run() {
		long start = System.currentTimeMillis();
		long end = start + 60 * 1000;
		while (System.currentTimeMillis() < end) {
			//wait
		}
		t.interrupt();
	} 
	
//}
