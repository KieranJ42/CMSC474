package Poker;

import java.util.Collections;

public class SevenCardHand extends Hand{
	
	
	
	public SevenCardHand() {
		this(defaultDeck);
	}
	
	public SevenCardHand(Deck deck) {
		super(deck);
		for (Card c: deck.getHand(7))
			hand.add(c); 
		Collections.sort(hand);
	}
	
}
