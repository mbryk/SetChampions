package server;

import java.io.*;

public class MoveListener extends Thread{
	private BufferedReader stdin;
	private PrintWriter out;
	
	public MoveListener(PrintWriter out) {
		this.out = out;
	}
	
	public void run(){
		try{
			stdin = new BufferedReader(new InputStreamReader(System.in));
			String nextMove = stdin.readLine();
			out.println(nextMove);
		} catch(IOException e){
			System.err.println("Error Reading Player's Move: "+e);
			System.exit(-1);
		}
		
	}
}
