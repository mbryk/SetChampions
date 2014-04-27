package server;

import java.util.ArrayList;

import javax.swing.JPanel;

public class Board extends JPanel
{

	private BoardUtilities bu;
	public Board(){
		bu = new BoardUtilities(new Card[21], 15);
//		Card[] board = bu.getBoard();
//		for(int i=0; i<3; i++){
//			for(int j=0; j<5; j++){
//				System.out.print(board[i*5+j] + " ");
//			}
//			System.out.println();
//		}
	}
	
	public boolean checkMove(Move move){
		if(bu.getSets().contains(move)){
			bu.makeMove(move);
			return true;
		}
		
		return false;
	}
	
	// Check the printBoard method to see how positions are designated
	public Card getCardAtPos(int p){
		if(bu.getBoard().length > p)
			return bu.getBoard()[p];
		else
			return null;
	}
	
	public void printBoard(){
		// For the viewing of the game, server side... (swing)
		Card[] board = bu.getBoard();
		for(int i=0; i<3; i++){
			for(int j=0; j<5; j++){
				System.out.print(board[i*5+j] + " ");
			}
			System.out.println();
		}
	}
	
	public void printSets()
	{
		for(Move cards : bu.getSets())
		{
			System.out.print(cards.cards[0] + " ");
			System.out.print(cards.cards[1] + " ");
			System.out.print(cards.cards[2] + " ");
			System.out.println();
		}
	}
	
	/**
	 *  for testing
	 */
	public ArrayList<Move> getSets(){
		return bu.getSets();
	}
	
	//DELETE LATER
	public BoardUtilities getBU(){
		return bu;
	}
	/** Convert this board to string
	 *  Four digits = one card (shape, color, fill, number)
	 *  ie) 15 cards -> 60 integers
	 */
	public String toString() {
		String b = "";
		
		for(Card c : bu.getBoard()){
			b += c.toString();
		}
		return b;
	}
}
