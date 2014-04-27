package client;

import java.net.*;
import java.io.*;

import javax.xml.bind.Marshaller.Listener;

public class Player{
	// "MACROS"
	public static final int PrintBoard = 1;
	public static final int PrintName = 2;
	
	private static BufferedReader stdin; // Temp. I know all this will be done with Swing
	private static BufferedReader inFromServer;
	private static PrintWriter outToServer;
	private static Board2 board; // This will actually be a different board object, than on server side. I assume it will be handled in SWING.
	
	public static void main(String[] args){
		board = new Board2();
		//int port = Integer.parseInt(args[1]);
		int msgType; String msgType_str;
		try{
			//Socket socket = new Socket(args[0],port);
			Socket socket = new Socket("localhost",3000);
			System.out.println("Player: Connected to Server");
			inFromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			outToServer = new PrintWriter(socket.getOutputStream(), true);
			updateBoard();
			// if move comes (event listener) spawn thread to sendMove() on that other socket....
			new MoveListener(outToServer).start(); // This is probably reverse from what is actually smart. But we'll be changing all this anyway because of swing. i.e. listening for moves and updating the board
			while(true){ 
				msgType_str = inFromServer.readLine(); // Wait for incoming Board
				msgType = Integer.parseInt(msgType_str);
				switch(msgType) {
					case PrintBoard:
						System.out.println("Player: Receiving New Board");
						updateBoard();
						break;
					case PrintName:
						System.out.println("Player: Receiving New Player Name");
						updatePlayerList();
						break;
					default:
						System.err.println("Weird Msg Type");
						throw new IOException();
				}
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
	
	private static void updatePlayerList(){
		
	}
	
	private static void parseBoard(String boardString){
		// Split up by commas, fill in board's member variables...
		// Or we can just do some object sending, using ObjectReaders... That's not too hard.
	}
	
	private static void logout(){
		//called by ActionListener
		outToServer.println(2);// Not a move - I am logging out
	}
}
