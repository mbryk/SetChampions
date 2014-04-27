package server;

import java.net.*;
import java.io.*;

public class PlayerServer extends Thread {
	// This guy creates/finds the Player Object and enters him into the game.
	// He also controls all communication with the Client (although not necessarily in THIS thread. He can get called by another PlayerServer thread, when it gets a move right.) 

	private Game game;
	private PrintWriter outToPlayer;
	private BufferedReader inFromPlayer;
	public Player player;	
	
	public PlayerServer(Socket socket, Game game) throws IOException{
		this.game = game;
		outToPlayer = new PrintWriter(socket.getOutputStream(), true);
        inFromPlayer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
	}
	
	public void run() {
		System.out.println("Server: Started PlayerServer");
		findPlayer(); // Sets "player" member variable
		
		// Ready to play
		Board board = game.getBoard();
	    sendInfo(board.toString(),game.getPlayerList());
		game.addPlayer(this);
	    
		String typeStr = null; int type;
	    while(true){
	    	try{ typeStr = inFromPlayer.readLine();}
	    	catch(IOException e){};
	    	type = Integer.parseInt(typeStr);
	    	if(type==1){ // move
	    		Move move = listenToPlayer(); // Wait for move
	    		System.out.println("Server: Received Move");
	    		game.checkMove(this,move);
	    	} else if(type==2){ // logout
	    		game.removePlayer(this);
	    		storePoints();
	    		break;
	    	}
	    }        
    }
	
	private void findPlayer() {
		try{
			String username = inFromPlayer.readLine();
			String password = inFromPlayer.readLine();
		} catch(IOException e){
		}

//		check database
//		if exists {
//			player = game.findId(id)
//		}
//		else {		
			player = new Player();
			// Database.addPlayer(player);
//		}

	}
	
	private void storePoints(){
		// access database and update player.total points += game points 
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
		
	// This doesn't need to be synchronized. It will be called by this thread ONCE.
	// After game.addPlayer(), it will only be called by the alertAll function which itself is synchronized.
	public void sendInfo(String board, String playerList){ 
		outToPlayer.println(board);
		outToPlayer.println(playerList);
	}
	
	public void badMove(){
		outToPlayer.println("No");
	}
	
	public boolean equals(PlayerServer p){
		if(player.getId()==p.player.getId()) 
			return true;
		else return false;
	}
}
