package Poker;

import java.util.Collections;

public class FiveCardHand extends Hand{
	
	
	
	public FiveCardHand() {
		this(defaultDeck);
	}
	
	public FiveCardHand(Deck deck) {
		super(deck);
		for (Card c: deck.getHand(5))
			hand.add(c); 
		Collections.sort(hand);
	}
	
}
