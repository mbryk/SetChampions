package src;

import java.net.*;
import java.io.*;

public class Player{
	private static BufferedReader stdin; // Temp. I know all this will be done with Swing
	private static BufferedReader inFromServer;
	private static PrintWriter outToServer;
	private static Board2 board;
	
	public static void main(String[] args){
		board = new Board2();
		//int port = Integer.parseInt(args[1]);
		
		try{
			//Socket socket = new Socket(args[0],port);
			Socket socket = new Socket("localhost",3000);
			System.out.println("Player: Connected to Server");
			inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outToServer = new PrintWriter(socket.getOutputStream(), true);
			updateBoard();
			new MoveListener(outToServer).start(); // This is probably reverse from what is actually smart. But we'll be changing all this anyway because of swing. i.e. listening for moves and updating the board
			while(true){ 
				inFromServer.readLine(); // Wait for incoming Board
				System.out.println("Player: Receiving New Board");
				updateBoard();
			}			
		} catch(IOException e){
			System.err.println("Player Connection Error: "+e);
			System.exit(-1);
		}
	}
	
	private static void updateBoard(){
		try{
			parseBoard(inFromServer.readLine());
			board.printBoard();
			System.out.println("Player: Received Board");
		} catch(IOException e){
			System.out.println("Board Reading Error: "+e);
		}
	}
	
	private static void parseBoard(String boardString){
		// Split up by commas, fill in board's member variables...
		// Or we can just do some object sending, using ObjectReaders... That's not too hard.
	}
}
