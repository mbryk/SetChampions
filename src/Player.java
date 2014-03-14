package src;

import java.net.*;
import java.io.*;

public class Player extends Thread {
	
	public static void main(String[] args){
		
		int port = Integer.parseInt(args[1]);
		
		try{
			Socket socket = new Socket(args[0],port);
			
		} catch(IOException e){
			System.out.println("Player Connection Error: "+e);
		}
		
	}
}
