package server;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

public class Main {

	
	public static void main(String[] args) 
	{
		Board board = new Board();
		
		board.printBoard();
		
		int points = 0;
		int mmm;
		for(int i=0; i<10; i++) {
			Move m = board.getSets().get(0);
			mmm = board.checkMove(m);
			System.out.println(mmm+","+board.getBU().numCardsDeck);
			System.out.println("m = " + m);
			switch(mmm){
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
				break;
			}
		}
		Date date = new Date();
		Timestamp ts = new Timestamp(date.getTime());
		System.out.println(ts.getTime());
	}

}
