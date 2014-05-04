package client;

import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
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
	ArrayList<CardGUI> newCards = new ArrayList<CardGUI>();
	Random rand = new Random();
	public Board() throws IOException
	{
		
	}
	
	public void init(){
		this.setLayout(new GridLayout(3,7));
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
		int num = this.getComponentCount();
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
	public String getSelected(){
		if(card1 != null && card2 != null && card3 != null){
			return String.valueOf(cardsInPlay.indexOf(card1)) +"\n" + String.valueOf(cardsInPlay.indexOf(card2)) + "\n" + String.valueOf(cardsInPlay.indexOf(card3));
		}else{
			return null;
		}
	}
	public void updateBoard(String board) throws IOException{
		if(cardsInPlay.isEmpty()){//set up board for the first time
			for(int pos = 0; pos < board.length(); pos = pos+4){
				CardGUI card = new CardGUI(Character.getNumericValue(board.charAt(pos)), Character.getNumericValue(board.charAt(pos+1)), Character.getNumericValue(board.charAt(pos+2)), Character.getNumericValue(board.charAt(pos+3)));
				ClickListener clicked = new ClickListener(card);
				card.addMouseListener(clicked);
				cardsInPlay.add(card);
				add(card);
			}
		}else{//change only the new cards
			//first, if any cards still have the newCard border, we must get rid of it
			for(int i = 0; i<newCards.size(); ++i){
				newCards.get(i).resetBorder();
			}
			newCards.clear();
			for(int i = 0; i < cardsInPlay.size(); ++i){
				if(cardsInPlay.get(i).shape != Integer.parseInt(board.substring(i*4,i*4+1)) || cardsInPlay.get(i).number != Integer.parseInt(board.substring(i*4+1,i*4+2)) || cardsInPlay.get(i).color != Integer.parseInt(board.substring(i*4+2,i*4+3)) || cardsInPlay.get(i).filled != Integer.parseInt(board.substring(i*4+3,i*4+4))){//card is different
					//card is different - reset card
					cardsInPlay.get(i).shape = Integer.parseInt(board.substring(i*4,i*4+1));
					cardsInPlay.get(i).number = Integer.parseInt(board.substring(i*4+1,i*4+2));
					cardsInPlay.get(i).color = Integer.parseInt(board.substring(i*4+2,i*4+3));
					cardsInPlay.get(i).filled = Integer.parseInt(board.substring(i*4+3,i*4+4));
					cardsInPlay.get(i).resetPic();
					cardsInPlay.get(i).setNewCardBorder();
					newCards.add(cardsInPlay.get(i));
					card1 = null;
					card2 = null;
					card3 = null;
				}
			}
			while(board.length() > 4*cardsInPlay.size()){//we need to add cards to the board - either 3, 6, or 9 of them
				int pos = 4*cardsInPlay.size() - 1;
				CardGUI card = new CardGUI(Character.getNumericValue(board.charAt(pos)), Character.getNumericValue(board.charAt(pos+1)), Character.getNumericValue(board.charAt(pos+2)), Character.getNumericValue(board.charAt(pos+3)));
				ClickListener clicked = new ClickListener(card);
				card.addMouseListener(clicked);
				cardsInPlay.add(card);
				newCards.add(card);
				add(card);
				card.setNewCardBorder();
			}
			if(board.length() < 4*cardsInPlay.size()){//we need to remove the selected cards from the board
				card1.setNewCardBorder();
				card2.setNewCardBorder();
				card3.setNewCardBorder();
				card1.removePic();
				card2.removePic();
				card3.removePic();
				newCards.add(card1);
				newCards.add(card2);
				newCards.add(card3);
				card1 = null;
				card2 = null;
				card3 = null;
			}
		}
		validate();
		repaint();
	}
	public void updatePlayers(String players){
		//TODO make this do something useful
		System.out.println("players: "+players);
	}
	
}