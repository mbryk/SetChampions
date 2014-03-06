package src;

import java.util.ArrayList;

public class Game {
	private ArrayList<Player> players;
	private Board board;
	
	public Game(){
		players = new ArrayList<Player>();
		board = new Board();
		startGame();
	}
	
	public void addPlayer(Player player){
		players.add(player);
	}
	
	public void startGame(){
		board.printBoard();
	}
	
	public void checkMove(Player player, Move move){
		if(board.checkMove(move)){
			addPoint(player);
		}
	}
	
	private void addPoint(Player player){
		
	}
	
	public Board getBoard(){
		return board;
	}
}
