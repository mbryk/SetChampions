package client;

import java.util.ArrayList;
import java.util.HashMap;
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
	//ArrayList<CardGUI> cardsInPlay = new ArrayList<CardGUI>();
	HashMap<CardGUI, Integer> cardsToPos = new HashMap<CardGUI, Integer>();
	HashMap<Integer, CardGUI> posToCards = new HashMap<Integer, CardGUI>();
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
			return String.valueOf(cardsToPos.get(card1)) +"\n" + String.valueOf(cardsToPos.get(card2)) + "\n" + String.valueOf(cardsToPos.get(card3));
		}else{
			return null;
		}
	}
	public void updateBoard(String board) throws IOException{
		//debugging code
		if(cardsToPos.size() != posToCards.size()){
			System.out.println("Error - cardsToPos.size() != posToCards.size()");
		}
		if(cardsToPos.isEmpty()){//set up board for the first time
			for(int pos = 0; pos < board.length(); pos = pos+4){
				CardGUI card = new CardGUI(Character.getNumericValue(board.charAt(pos)), Character.getNumericValue(board.charAt(pos+1)), Character.getNumericValue(board.charAt(pos+2)), Character.getNumericValue(board.charAt(pos+3)));
				ClickListener clicked = new ClickListener(card);
				card.addMouseListener(clicked);
				cardsToPos.put(card, pos/4);
				posToCards.put(pos/4, card);
				add(card);
			}
		}else{
			//first, if any cards still have the newCard border, we must get rid of it
			for(int i = 0; i<newCards.size(); ++i){
				newCards.get(i).resetBorder();
			}
			newCards.clear();
			while(board.length() > 4*cardsToPos.size()){//we need to add cards to the board - either 3, 6, or 9 of them
				int pos = 4*cardsToPos.size();//TODO is this the bug? it shouldn't be -1?
				CardGUI card = new CardGUI(Character.getNumericValue(board.charAt(pos)), Character.getNumericValue(board.charAt(pos+1)), Character.getNumericValue(board.charAt(pos+2)), Character.getNumericValue(board.charAt(pos+3)));
				ClickListener clicked = new ClickListener(card);
				card.addMouseListener(clicked);
				cardsToPos.put(card, pos/4);
				posToCards.put(pos/4, card);
				newCards.add(card);
				add(card);
				card.setNewCardBorder();
			}
			if(board.length() < 4*cardsToPos.size()){//we need to remove the selected cards from the board
				for(int i = 0; i < cardsToPos.size(); ++i){
					CardGUI test = posToCards.get(i);
					if(i != cardsToPos.get(test)){//position is not valid - these are the last cards
						CardGUI temp = posToCards.get(posToCards.size()-1); //the last card
						posToCards.remove(i);
						posToCards.put(i, posToCards.get(cardsToPos.size()));//replace the card at the first discrepancy with the last card
						posToCards.remove(posToCards.size()-1);//remove the last card
						cardsToPos.remove(test);//remove the missing card
						cardsToPos.remove(temp);//remove last card
						cardsToPos.put(temp, i);//put the last card in its place
					}
				}
			}
			for(int i = 0; i < cardsToPos.size(); ++i){//change only the new cards
				CardGUI test = new CardGUI(Character.getNumericValue(board.charAt(4*i)), Character.getNumericValue(board.charAt(4*i+1)), Character.getNumericValue(board.charAt(4*i+2)), Character.getNumericValue(board.charAt(4*i+3)));
				if(cardsToPos.containsKey(test) && cardsToPos.get(test) != i){//the card was moved from the end
					//update its position
					posToCards.remove(cardsToPos.size()-1);//remove last card
					cardsToPos.remove(test);//remove last card
					//TODO add mouselistener?
					posToCards.put(i, test);
					cardsToPos.put(test, i);
				}
				if(!cardsToPos.containsKey(test)){//this is a new card
					CardGUI oldCard = posToCards.get(i);
					//check if this is card1, card2, card3
					//check if card1,2,or 3 was removed
					if(oldCard == card3){
						card3 = null;
					}
					if(oldCard == card2){
						if(card3 != null){
							card2 = card3;
							card3 = null;
						}else{
							card2 = null;
						}
					}
					if(oldCard == card1){
						if(card3 != null){
							card1 = card2;
							card2 = card3;
							card3 = null;
						}else if(card2 != null){
								card1 = card2;
								card2 = null;
						}else{
							card1 = null;
						}
					}
					//update the card
					oldCard.color = test.color;
					oldCard.filled = test.filled;
					oldCard.shape = test.shape;
					oldCard.number = test.number;
					oldCard.resetPic();
					oldCard.setNewCardBorder();
					newCards.add(oldCard);
					//now, update the lists
					posToCards.remove(i);//remove the old card
					posToCards.put(i, test);//put in the new card
					cardsToPos.remove(i);//remove the old card
					cardsToPos.put(test, i);//put in the new card
				}
			}
		}
		validate();
		repaint();
	}
}