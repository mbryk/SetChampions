package src;

import java.util.ArrayList;

import javax.swing.JPanel;

public class Board extends JPanel
{

	private BoardUtilities bu;
	public Board()
	{
		bu = new BoardUtilities(new Card[21]);
		Card[] board = bu.getBoard();
		for(int j=0; j<5; j++)
		{
			for(int i=0; i<3; i++)
			{
				System.out.println(board[i*5+j]);
			}
			System.out.println();
		}
	}
	
	public void printSets()
	{
		for(Card[] cards : bu.getSets())
		{
			System.out.println(cards[0]);
			System.out.println(cards[1]);
			System.out.println(cards[2]);
			System.out.println();
		}
	}
	
}
