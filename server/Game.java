package server;

import java.util.ArrayList;

public class Game {
	private ArrayList<PlayerServer> players;
	private Board board;
	public int id;
	private String name;
	private boolean gameOver;
	
	public Game(int id,String name){
		this.id = id;
		this.name = name;
		players = new ArrayList<PlayerServer>();
		board = new Board();
		gameOver = false;
		
		startGame();
	}
	
	public void addPlayer(PlayerServer pserver){
		players.add(pserver);
		alertAll(0);
		System.out.println("Game: player added");
	}
	
	public boolean removePlayer(PlayerServer pserver){
		players.remove(pserver);
		if(!gameOver) alertAll(0);
		return players.isEmpty();
	}
	
	public void startGame(){
		board.printBoard(); // Not really necessary...
		board.printSets(); //TEMP - Also unnecessary
	}
	
	// This must be synchronized. This way, only one PlayerServer can submit a move at a time. (I think.)
	public synchronized boolean checkMove(PlayerServer pserver, Move move){
		switch(board.checkMove(move)){
			case 0:
				pserver.badMove();
				break;
			case 1:
				pserver.player.addPoints(3);
				alertAll(1);
				break;
			case 2:
				pserver.player.addPoints(3);
				gameOver = true;
				alertAll(2);
				return true;
		}
		return false;
	}
	
	// Only one PlayerServer will get into checkmove at a time, and it will alert all the other players, using the playerserver objects.
	// Therefore, this method is also technically synchronized.
	// 0 = Just send player list. 1 = Send Board and Player List. 2 = Send over and Player List.
	private synchronized void alertAll(int sendBoard){
		String boardString = null;
		if(sendBoard==1) boardString = board.toString();
		String playerList = getPlayerList();
		String winner = getWinner();
		for(PlayerServer pserver : players){
			// This will be run on this main thread. The other thread will continue to be
			switch(sendBoard){
				case 0: pserver.sendList(playerList); break;
				case 1: pserver.sendInfo(boardString,playerList); break;
				case 2: pserver.sendWinner(winner, playerList); break;
			}
		}
	}
	
	public Board getBoard(){
		return board;
	}
	
	public String getWinner(){
		return players.get(0).player.name;
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