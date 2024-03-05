package Poker;

import java.util.Collections;

public class UnknownHandSizeHand extends Hand {
	
	private static int defaultSize = 7;
	
	public UnknownHandSizeHand() {
		this(defaultDeck, defaultSize);
	}
	
	public UnknownHandSizeHand(int handSize) {
		this(defaultDeck, handSize);
	}
	
	public UnknownHandSizeHand(Deck deck) {
		this(deck, defaultSize);
	}
	
	public UnknownHandSizeHand(Deck deck, int handSize) {
		super(deck);
		for (Card c: deck.getHand(handSize))
			hand.add(c); 
		Collections.sort(hand);
	}

}
