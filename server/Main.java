package server;


import java.util.ArrayList;

public class Main {

	
	public static void main(String[] args) 
	{
		Board board = new Board();
		
		board.printBoard();
		
		int points = 0;
		
		for(int i=0; i<27; i++) {
			Move m = board.getSets().get(0);
			switch(board.checkMove(m)){
			case 0:
				System.out.println("Bad Move");
				System.out.println("*******************************");
				break;
			case 1:
				System.out.println("i = " + i);
				System.out.println("Add 3 points");
				points += 3;
				System.out.println("Total points = " + points);
				System.out.println("*******************************");
				break;
			case 2:
				points += 3;
				System.out.println("i = " + i);
				System.out.println("Game over!");
				System.out.println("Total points = " + points);
			}
		}
	}

}
