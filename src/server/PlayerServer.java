package src;

import java.net.*;
import java.io.*;

public class PlayerServer extends Thread {
	// "MACROS"
	public static int PrintBoard = 1;
	public static int PrintName = 2;
	
	private Game game;
	private PrintWriter outToPlayer;
	private BufferedReader inFromPlayer;
	public int points;
	
	public PlayerServer(Socket socket, Game game) throws IOException{
		this.game = game;
		points = 0; // For this game. Also keep track of total points in DB...
		outToPlayer = new PrintWriter(socket.getOutputStream(), true);
        inFromPlayer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void run() {
		System.out.println("Server: Started PlayerServer");
		Board board = game.getBoard();
	    sendBoard(board);
	    while(true){
	    	Move move = listenToPlayer(); // Wait for move
	    	System.out.println("Server: Received Move");
		    game.checkMove(this,move);
		    System.out.println("Server: Received Move");
	    }        
    }
	
	/**
	 * The String from the player should be the POSITION of the chosen card on the board.
	 * Check printBoard() method of Board class
	 * @return
	 */
	private Move listenToPlayer(){
		Move move = new Move();
		try{
			for(int i=0;i<3;i++){
				String c = inFromPlayer.readLine();
				//This is trickier than I expected.. Since we are only sending card position on Board,
				//	we can't just do this:
				//move.cards[i] = Integer.parseInt(c);
				// Assuming c is the position of card on board,
				int pos = Integer.parseInt(c);
				Card card = game.getBoard().getCardAtPos(pos);
				if(card == null){
					System.out.println("Error at PlayerServer.listenToPlayer. Move received is out of bounds");
					throw new IOException();
				}
				else
					move.cards[i] = card;
			}	
		} catch(IOException e) {
			System.err.println("Error Receiving Move: " + e);
			System.exit(-1);
		}
        return move;
	}
		
	public synchronized void sendBoard(Board board){
		outToPlayer.println(PrintBoard);
		outToPlayer.println(board.toString());
	}
	
	public synchronized void sendPlayerName(String name){
		outToPlayer.println(PrintName);
		outToPlayer.println(name);
	}
	
	public boolean equals(){
		return false;
	}
}