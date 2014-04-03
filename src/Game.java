package src;

import java.util.ArrayList;

public class Game {
	private ArrayList<PlayerServer> players;
	private Board2 board;
	
	public Game(){
		players = new ArrayList<PlayerServer>();
		board = new Board2();
		startGame();
	}
	
	public void addPlayer(PlayerServer player){
		players.add(player);
	}
	
	public void startGame(){
		board.printBoard();
	}
	
	public void checkMove(PlayerServer player, Move move){
		if(board.checkMove(move)){
			player.points += 3;
			alertPlayers();
		}
	}
	
	private void alertPlayers(){
		for(PlayerServer player : players){
			player.sendBoard(board);
		}
	}
	
	public Board2 getBoard(){
		return board;
	}
}