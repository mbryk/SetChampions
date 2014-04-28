package server;

import java.util.ArrayList;

public class Game {
	private ArrayList<PlayerServer> players;
	private Board board;
	
	public Game(){
		players = new ArrayList<PlayerServer>();
		board = new Board();
		startGame();
		// Game starts empty. It will now just sit here, waiting for players, waiting to be told about new moves.
	}
	
	/* private void retrievePlayers(){
		//populate players array from 
	} */
	
	public void addPlayer(PlayerServer pserver){
		players.add(pserver);
		System.out.println("Game: player added");
	}
	
	public void removePlayer(PlayerServer pserver){
		players.remove(pserver);
	}
	
	public void startGame(){
		board.printBoard(); // Not really necessary...
	}
	
	// This must be synchronized. This way, only one PlayerServer can submit a move at a time. (I think.)
	public synchronized void checkMove(PlayerServer pserver, Move move){
		if(board.checkMove(move)){
			pserver.player.addPoints(3);
			alertAll();
		}
		else{
			pserver.badMove(); // This can't be in a return, because we want this to be synchronized as well.
		}
	}
	
	// Only one PlayerServer will get into checkmove at a time, and it will alert all the other players, using the playerserver objects.
	// Therefore, this method is also technically synchronized.
	private void alertAll(){
		String boardString = board.toString();
		String playerList = getPlayerList();
		for(PlayerServer pserver : players){
			// This will be run on this main thread. The other thread will continue to be 
			pserver.sendInfo(boardString,playerList);
		}
	}
	
	public Board getBoard(){
		return board;
	}
	public String getPlayerList(){
		return "hi";
	}
	
}