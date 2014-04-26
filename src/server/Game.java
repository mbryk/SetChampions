package src;

import java.util.ArrayList;

public class Game {
	private ArrayList<PlayerServer> players;
	private Board board;
	
	public Game(){
		players = new ArrayList<PlayerServer>();
		board = new Board();
		retrievePlayers();
		startGame();
	}
	
	private void retrievePlayers(){
		//populate players array
	}
	
	public void addPlayer(PlayerServer player){
		players.add(player);
		alertAllNewPlayer(player.getName());
	}
	
	public void startGame(){
		board.printBoard();
	}
	
	public void checkMove(PlayerServer player, Move move){
		if(board.checkMove(move)){
			player.points += 3;
			alertAllBoard();
		}
		else{
			//alert player that it was a bad move
		}
	}
	
	private void alertAllBoard(){
		for(PlayerServer player : players){
			player.sendBoard(board);
		}
	}
	
	private void alertAllNewPlayer(String name){
		for(PlayerServer player : players){
			player.sendPlayerName(name);
		}
	}
	
	public Board getBoard(){
		return board;
	}
}