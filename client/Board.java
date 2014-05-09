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
			validate();
			repaint();
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
			removeAll();
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
				System.out.println("entering while loop");
				int pos = 4*cardsToPos.size();
				CardGUI card = new CardGUI(Character.getNumericValue(board.charAt(pos)), Character.getNumericValue(board.charAt(pos+1)), Character.getNumericValue(board.charAt(pos+2)), Character.getNumericValue(board.charAt(pos+3)));
				ClickListener clicked = new ClickListener(card);
				card.addMouseListener(clicked);
				cardsToPos.put(card, pos/4);
				posToCards.put(pos/4, card);
				newCards.add(card);
				add(card);
				card.setNewCardBorder();
			}
			CardGUI tmp;
			if(board.length() < 4*cardsToPos.size()){//we need to remove the selected cards from the board
				int removed = 0;
				for(int pos = 0; pos < board.length(); pos = pos+4){
					
					CardGUI test = new CardGUI(Character.getNumericValue(board.charAt(pos)), Character.getNumericValue(board.charAt(pos+1)), Character.getNumericValue(board.charAt(pos+2)), Character.getNumericValue(board.charAt(pos+3)));
					if(cardsToPos.get(test) != pos/4){//this card should be removed
						tmp = posToCards.get(pos/4);
						remove(tmp);
						posToCards.remove(pos/4);
						posToCards.remove(cardsToPos.get(test));
						
						cardsToPos.remove(tmp);
						cardsToPos.remove(test);
						
						cardsToPos.put(test, pos/4);
						posToCards.put(pos/4, test);
						removed++;
					}
					//reset card1, card2, card3
					if(test == card3){
						card3.resetBorder();
						card3 = null;
					}
					if(test == card2){
						if(card3 != null){
							card2 = card3;
							card3 = null;
						}else{
							card2.resetBorder();
							card2 = null;
						}
					}
					if(test == card1){
						if(card3 != null){
							card1 = card2;
							card2 = card3;
							card3 = null;
						}else if(card2 != null){
								card1 = card2;
								card2 = null;
						}else{
							card1.resetBorder();
							card1 = null;
						}
					}
				}
				if(removed<3){
					for(int j = removed; j<3; j++){
						tmp = posToCards.get(posToCards.size()-1);
						posToCards.remove(cardsToPos.size()-1);
						cardsToPos.remove(tmp);
						remove(tmp);
					}
				}
			}else{
				for(int i = 0; i < cardsToPos.size(); ++i){//change only the new cards
					System.out.println("debug: there are " + cardsToPos.size() + " cards on the board");
					
					//debugging code
					if(cardsToPos.size() != posToCards.size()){
						System.out.println("Error - cardsToPos.size() = " + cardsToPos.size() + "and posToCards.size() = " + posToCards.size());
					}
					
					CardGUI test = new CardGUI(Character.getNumericValue(board.charAt(4*i)), Character.getNumericValue(board.charAt(4*i+1)), Character.getNumericValue(board.charAt(4*i+2)), Character.getNumericValue(board.charAt(4*i+3)));
					if(cardsToPos.containsKey(test) && cardsToPos.get(test) != i){//the card was moved from the end
						//update its position
						posToCards.remove(cardsToPos.size()-1);//remove last card
						System.out.println("attempt1before "+cardsToPos.size());
						cardsToPos.remove(test);//remove last card
						System.out.println("attempt1after "+cardsToPos.size());
						//TODO add mouselistener?
						posToCards.put(i, test);
						cardsToPos.put(test, i);
					}
					else if(!cardsToPos.containsKey(test)){//this is a new card
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
						cardsToPos.remove(oldCard);//remove the old card
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
						posToCards.put(i, oldCard);//put in the new card
						cardsToPos.put(oldCard, i);//put in the new card
						
					}
				}
			}
		}
		//validate();
		//repaint();
		System.out.println("exiting updateBoard()");
	}
}