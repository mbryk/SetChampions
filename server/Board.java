package server;

import java.util.ArrayList;

import javax.swing.JPanel;

public class Board extends JPanel
{
	private BoardUtilities bu;
	public Board(){
		bu = new BoardUtilities(new Card[21], 12);
//		Card[] board = bu.getBoard();
//		for(int i=0; i<3; i++){
//			for(int j=0; j<5; j++){
//				System.out.print(board[i*5+j] + " ");
//			}
//			System.out.println();
//		}
	}
	
	public void setBoard(String setBoard){
		bu.setBoard(setBoard);
		System.out.println(this);
	}
	
	public int checkMove(Move move){ // 0 = Bad Move, 1 = Good Move, 2 = Game Over!
		if(bu.getSets().contains(move)){
			boolean gameOver = bu.makeMove(move);
			return gameOver?2:1;
		}
		return 0;
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
		int i=0;
		for(Card c : bu.getBoard()){
			System.out.print(c + " ");
			if(i++==2) {System.out.println();i=0;}
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
	
	public int getCardsLeft(){
		return bu.numCardsDeck;
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
