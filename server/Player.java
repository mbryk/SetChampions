package server;

public class Player {
	private int points;
	private int id;
	public String name;

	public Player() {
		points = 0; // For this game. Also keep track of total points in DB...
	}
	
	public int getPoints(){
		return points;
	}
	public void addPoints(int newPoints){
		points += newPoints;
	}
	public String getName(){
		return name;
	}
	public int getId(){
		return id;
	}
}
