package src;

public class Move {
	public Card[] cards;
	//Move class has to have array of Card not ints.
	//You would need knowledge about board to make it ints (position)
	
	public Move(){
		cards = new Card[3];
	}
	
	public boolean equals(Object mo){
		Move m = (Move) mo;
		return cards[0].equals(m.cards[0]) && cards[1].equals(m.cards[1]) && cards[2].equals(m.cards[2]); 
	}
	
	@Override
	public String toString(){
		return cards[0].toString() + " " + cards[1].toString() + " " + cards[2].toString();
	}
	
	@Override
	public int hashCode(){
		return Integer.parseInt(cards[0].toString() + cards[1].toString() + cards[2].toString());
	}
}
