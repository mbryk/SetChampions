package server;

import java.util.ArrayList;

public class Lobby {
	private ArrayList<Game> games;
	private int totalGames = 0;
	
	public Lobby(){
		games = new ArrayList<Game>();
	}
	
	public Game getGame(int index,String name){
		Game game = null;
		if(index<totalGames){
			game = games.get(index);
		} else {
			game = new Game(totalGames++,name);
			games.add(game);
		}
		return game;
	}

	public void removeGame(Game game){
		if(games.contains(game)){
			games.remove(game);
			totalGames--;
			updateIds(game.id);
		}
	}	
	
	private void updateIds(int id){
		for (Game game : games.subList(id,totalGames)){
			game.id--;
		}
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
