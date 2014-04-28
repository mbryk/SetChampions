package client;

import java.util.ArrayList;
import java.util.Random;

public class BoardUtilities 
{
	private Card[] gameBoard;
	private int numCards;
	private Card[] deck;
	
	public BoardUtilities(Card[] gb)
	{
		gameBoard = gb;
		deck = new Card[81];
		generateDeck();
		for(int i=0; i<15; i++)
				gameBoard[i] = deck[i];
		numCards = 15;
	}
	
	/**
	 * Returns all possible sets in the current gameBoard. If there are none, adds three more cards to the board
	 */
	public ArrayList<Card[]> getSets()
	{
		ArrayList<Card[]> sets = new ArrayList<Card[]>();
		for(int i=0; i<numCards; i++)
			for(int j=1; j<numCards; j++)
				for(int k=2; k<numCards; k++)
					if(!(i==j || i==k || j==k))
						if(checkSet(i, j, k))
						{
							Card[] cards = new Card[3];
							cards[0] = gameBoard[i];
							cards[1] = gameBoard[j];
							cards[2] = gameBoard[k];
							sets.add(cards);
						}
				
		return sets;
	}
	
	public Card[] getBoard()
	{
		return gameBoard;
	}
	
	private boolean checkSet(int i, int j, int k)
	{		
		return newXOR(gameBoard[i].getColor(), gameBoard[j].getColor(), gameBoard[k].getColor()) &&
				newXOR(gameBoard[i].getShape(), gameBoard[j].getShape(), gameBoard[k].getShape()) &&
				newXOR(gameBoard[i].getNumber(), gameBoard[j].getNumber(), gameBoard[k].getNumber()) &&
				newXOR(gameBoard[i].getFill(), gameBoard[j].getFill(), gameBoard[k].getFill());
	}
	
	private boolean newXOR(int i, int j, int k)
	{
		return ((i==j) && (i==k)) || ((i!=j)&&(i!=k)&&(j!=k)); 
	}
	
	/**
	 * Generates the deck and shuffles it
	 */
	private void generateDeck()
	{
		for(int i=0; i<3; i++)
			for(int j=0; j<3; j++)
				for(int k=0; k<3; k++)
					for(int m=0; m<3; m++)
						deck[i*27+j*9+k*3+m] = new Card(i,j,k,m);
		
		Random rnd = new Random();
		for(int i=0; i<200; i++)
		{
			swap(rnd.nextInt(80), rnd.nextInt(80));
		}
	}
	
	private void swap(int i, int j)
	{
		Card temp = deck[i];
		deck[i] = deck[j];
		deck[j] = temp;
	}
}
