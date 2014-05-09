package server;

import java.net.*;
import java.io.*;

public class PlayerServer extends Thread {
	// This guy creates the Player Object and enters him into the game.
	// He also controls all communication with the Client (although not necessarily in THIS thread. He can get called by another PlayerServer thread, when it gets a move right.) 

	private Lobby lobby;
	private Game game;
	private PrintWriter outToPlayer;
	private BufferedReader inFromPlayer;
	public Player player;	
	
	public PlayerServer(Socket socket, Lobby lobby) throws IOException{
		this.lobby = lobby;
		outToPlayer = new PrintWriter(socket.getOutputStream(), true);
        inFromPlayer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.player = new Player();
	}
	
	public void run() {
		System.out.println("Server: Started PlayerServer");
		
		try{player.name = inFromPlayer.readLine();}
		catch(IOException e){};
		System.out.println("Got Player Name: "+player.name);
		boolean play = true;
		String gameIDstr = null;
		String gameName = null;
		
		while(play){
			outToPlayer.println("lobby: "+lobby);
			
			try{
				while((gameIDstr=inFromPlayer.readLine()).equals("refresh"))
					outToPlayer.println("lobby: "+lobby);
				gameName = inFromPlayer.readLine();
			} catch(IOException e){};
			
			int gameID = Integer.parseInt(gameIDstr);
			game = lobby.getGame(gameID,gameName);

			// Ready to play
		
			//game.getBoard().setBoard("012222012202021012112002020000211112011022002001");
			outToPlayer.println("board: "+game.getBoard().toString());
			System.out.println("Sent Game Info");
			game.addPlayer(this);

			String typeStr = null; int type;
			while(true){
				try{ typeStr = inFromPlayer.readLine();}
				catch(IOException e){};
				type = Integer.parseInt(typeStr);
				if(type==1){ // move
					Move move = listenToPlayer(); // Wait for move
					System.out.println("Server: Received Move");
					if(game.checkMove(this,move)){
						lobby.removeGame(game);
					}
				} else{
					player.resetPoints();
					if(game.removePlayer(this)) // Game empty
						lobby.removeGame(game);

					//type 2 = quit game
					if(type==3){ // exit
						outToPlayer.println("Goodbye");
						play=false;
					}
					break;
				}
			}
			System.out.println("PlayerServer: Player Left :(");
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
			int pos; Card card; String c;
			for(int i=0;i<3;i++){
				c = inFromPlayer.readLine();
				System.out.println(c);
				pos = Integer.parseInt(c);
				card = game.getBoard().getCardAtPos(pos);
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
		outToPlayer.println("board: "+board);
		outToPlayer.println("player: "+playerList);
	}

	public void sendList(String playerList){ 
		outToPlayer.println("player: "+playerList);
	}
	
	public void sendWinner(String winner, String playerList){ 
		outToPlayer.println("over: "+winner);
		outToPlayer.println("player: "+playerList);
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
