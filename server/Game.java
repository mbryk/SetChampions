package server;

import java.util.ArrayList;

public class Game {
	private ArrayList<PlayerServer> players;
	private Board board;
	private int id;
	private String name;
	
	public Game(int id,String name){
		this.id = id;
		this.name = name;
		players = new ArrayList<PlayerServer>();
		board = new Board();
		
		startGame();
	}
	
	public void addPlayer(PlayerServer pserver){
		players.add(pserver);
		System.out.println("Game: player added");
	}
	
	public void removePlayer(PlayerServer pserver){
		players.remove(pserver);
	}
	
	public void startGame(){
		board.printBoard(); // Not really necessary...
		board.printSets(); //TEMP - Also unnecessary
	}
	
	// This must be synchronized. This way, only one PlayerServer can submit a move at a time. (I think.)
	public synchronized void checkMove(PlayerServer pserver, Move move){
		if(board.checkMove(move)){
			board.printSets(); //TEMP
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
		String playerList = "";
		for(PlayerServer pserver : players){
			playerList += pserver.player.name+";"+pserver.player.getPoints()+";";
		}
		return playerList;
	}
	
	public String toString(){
		String game = id+";"+name+";";
		game += board.getCardsLeft()+";";
		game += getPlayerList();
		return game;
	}
	
}