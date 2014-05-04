package server;

import java.util.ArrayList;

public class Lobby {
	private ArrayList<Game> games;
	private int totalGames = 0;
	
	public Lobby(){
		games = new ArrayList<Game>();
	}
	
	public Game getGame(int index){
		Game game = null;
		if(index<totalGames){
			game = games.get(index);
		} else {
			game = new Game(totalGames++);
			games.add(game);
		}
		return game;
	}
	
	public String toString(){
		String lobby = "";
		for(Game game : games){
			lobby += game + ";;";
		}
		// New Game = any other index
		return lobby;
	}
}
