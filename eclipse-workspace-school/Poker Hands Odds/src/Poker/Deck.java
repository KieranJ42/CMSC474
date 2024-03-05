package Poker;

import java.util.*;

public class Deck {
	
	private static List<Card> fullDeck;
	
	private List<Card> deck;
	
	public Deck() {
		deck = new ArrayList<Card>(fullDeck);
	}
	
	public List<Card> getHand(int handSize) {
		List<Card> deck = new ArrayList<Card>(this.deck);
		List<Card> hand = new ArrayList<Card>();
		for (int i = 0; i < handSize; i++) {
			hand.add(deck.remove((int)(deck.size()*Math.random())));
		}
		return hand;
	}
	
	static {
		fullDeck = new ArrayList<Card>();
		for (int i = 0; i < 52; i++) {
			fullDeck.add(new Card(i%13, i/13));
		}
	}
	
}
