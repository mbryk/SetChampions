package client;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class Board extends JPanel
{
	ArrayList<CardGUI> cardsInPlay = new ArrayList<CardGUI>();
	CardGUI card1 = null;
	CardGUI card2 = null;
	CardGUI card3 = null;
	private BoardUtilities bu;
	Random rand = new Random();
	public Board() throws IOException
	{
		setLayout(new GridLayout(3,7));
		bu = new BoardUtilities(new Card[21]);
		Card[] board = bu.getBoard();
		for(int j=0; j<5; j++)
		{
			for(int i=0; i<3; i++)
			{
				System.out.println(board[i*5+j]);
				CardGUI card = new CardGUI(board[i*5+j].getShape(), board[i*5+j].getNumber(), board[i*5+j].getColor(), board[i*5+j].getFill());
				ClickListener clicked = new ClickListener(card);
				card.addMouseListener(clicked);
				cardsInPlay.add(card);
			}
			System.out.println();
		}
		for(int i=0; i< cardsInPlay.size(); ++i){
			this.add(cardsInPlay.get(i));
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
	public class ClickListener implements MouseListener {
		CardGUI thisCard;

		public ClickListener(CardGUI card) {
			thisCard = card;
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		}

		@Override
		public void mouseEntered(MouseEvent e) {	
		}

		@Override
		public void mouseExited(MouseEvent e) {
		}

		@Override
		public void mousePressed(MouseEvent e) {
			if(thisCard == card1){
				card1 = card2;
				card2 = card3;
				card3 = null;
			}else if(thisCard == card2){
				card2 = card3;
				card3 = null;
			}else if (thisCard == card3){
				card3 = null;
			}else if(card1 == null){
				card1 = thisCard;
			}else if(card2 == null){
				card2 = thisCard;
			}else if(card3 == null){
				card3 = thisCard;
			}else{
				card1.toggleBorder();
				card1 = card2;
				card2 = card3;
				card3 = thisCard;
			}
			thisCard.toggleBorder();			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
		}
		
	}
	public void shuffle(){
		int num = getComponentCount();
		int ind;
		Component swap;
		for(int i = 0; i < num*2; ++i){
			ind = rand.nextInt(num);
			swap = getComponent(ind);
			remove(swap);
			add(swap);
		}
		validate();
		repaint();
	}
	public ArrayList<Card> getSelected(){
		ArrayList<Card> possibleSet = new ArrayList<Card>();
		if(card1 != null && card2 != null && card3 != null){
			possibleSet.add(card1.getCard());
			possibleSet.add(card2.getCard());
			possibleSet.add(card3.getCard());
			return possibleSet;
		}else{
			return null;
		}
	}
	
}