package src;

public class Move {
	public Card[] cards;
	//Move class has to have array of Card not ints.
	//You would need knowledge about board to make it ints (position)
	
	public Move(){
		cards = new Card[3];
	}
	
	public boolean equals(Move m){
//		return (cards[0].equals(m.cards[0]) && cards[1].equals(m.cards[1]) && cards[2].equals(m.cards[2])) ||
//				(cards[0].equals(m.cards[0]) && cards[0].equals(m.cards[0]) && cards[0].equals(m.cards[0]))||
//				(cards[0].equals(m.cards[0]) && cards[0].equals(m.cards[0]) && cards[0].equals(m.cards[0]))||
//				(cards[0].equals(m.cards[0]) && cards[0].equals(m.cards[0]) && cards[0].equals(m.cards[0]))||
//				(cards[0].equals(m.cards[0]) && cards[0].equals(m.cards[0]) && cards[0].equals(m.cards[0]))||
//				(cards[0].equals(m.cards[0]) && cards[0].equals(m.cards[0]) && cards[0].equals(m.cards[0])))
		// I was going to do this ugly permutation, but I think my getSets method in BU actually contains all possible
		//	permutations of all sets. If not, we can fill in the above abomination..
		return cards[0].equals(m.cards[0]) && cards[1].equals(m.cards[1]) && cards[2].equals(m.cards[2]); 
	}
	
	public String toString(){
		return cards[0].toString() + " " + cards[1].toString() + " " + cards[2].toString();
	}
}
