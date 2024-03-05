package Poker;

public class Card implements Comparable<Card>{

	private int faceValue;
	private int suit;
	
	public Card(int faceValue, int suit) {
		this.faceValue = faceValue;
		this.suit = suit;
	}
	
	public String toString() {
		String val = "", suit = "";
		switch (faceValue) {
		case 0:
			val += "Ace";
			break;
		case 10:
			val += "Jack";
			break;
		case 11:
			val += "Queen";
			break;
		case 12:
			val += "King";
			break;
		default :
			val += faceValue;
		}
		
		switch (this.suit) {
		case 0:
			suit += "Spades";
			break;
		case 1:
			suit += "Hearts";
			break;
		case 2:
			suit += "Clubs";
			break;
		case 3:
			suit += "Diamonds";
			break;
		}
		
		return val + " of " + suit;
	}
	
	public int getValue() {
		return suit*13 + faceValue;
	}

	@Override
	public int compareTo(Card o) {
		return getValue()-o.getValue();
	}
	
}
