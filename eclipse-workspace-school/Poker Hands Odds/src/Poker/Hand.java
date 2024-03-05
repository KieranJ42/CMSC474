package Poker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public abstract class Hand {

	protected List<Card> hand;
	protected Deck deck;
	
	public static Deck defaultDeck = new Deck();
	
	public Hand(Deck deck) {
		hand = new ArrayList<Card>();
		this.deck = deck;
	}
	
	public String toString() {
		String retVal = "";
		for (Card c: hand)
			retVal += c.toString() + "\n";
		return retVal;
	}
	
	public int getBestCombo() {
		int best = 0;
		/*
		 * HC = 0; P = 1; 2P = 2; Thr = 3; Str = 4; FLu = 5; Ful = 6; Fou = 7;
		 * SF = 8; RF = 9;
		 */
		int[] multiplicity = new int[13], flushAmount = new int[4];
		int /*bestSFLength = 0, */ currentSFLength = 1;
		//boolean RFChance = false;
		int /*bestStraightLength = 0, */ currentStraightLength = 0;
		
		int currSuit = 0, lastValue = -2;
		boolean newSuit = false, acePresent = false;
		
		int tempValue;
		for (Card c: hand) {
			tempValue = c.getValue();
			
			multiplicity[tempValue%13]++;
			flushAmount[tempValue/13]++;
			
			if (currSuit != tempValue/13) {
				currSuit = tempValue/13;
				newSuit = true;
				acePresent = false;
			} else {
				newSuit = false;
			}
			if (!newSuit && tempValue%13 == lastValue+1)
				currentSFLength++;
			else
				currentSFLength = 1;
			
			lastValue = tempValue % 13;
			if (lastValue == 0)
				acePresent = true;
			
			
			if (currentSFLength > 4)
				best = 8;
			
			if (acePresent == true && lastValue == 12 && currentSFLength >= 4) {
				best = 9;
				break;
			}
		}
		
		int highestMultIndex = 0, highestMult;
		
		if (best > 7) {
			
		} else {
			for (int i = 1; i < multiplicity.length; i++) {
				if (multiplicity[i] > multiplicity[highestMultIndex]) {
					highestMultIndex = i;
				}
			}
			
			highestMult = multiplicity[highestMultIndex];
			
			int first2 = -1, last2 = -1;
			for (int i = 0; i < multiplicity.length; i++) {
				if (multiplicity[i] == 2 && first2 == -1)
					first2 = i;
				if (multiplicity[i] == 2)
					last2 = i;
			}
			
			switch (highestMult) {
			case 2:
				if (first2 == last2)
					best = 1;
				else
					best = 2;
				break;
			case 3:
				if (first2 != -1)
					best = 6;
				else
					best = 3;
				break;
			case 4:
				best = 7;
				break;
			}
			
			
			if (best > 5) {
				
			} else {
				for (int i = 0; i < flushAmount.length; i++) {
					if (flushAmount[i] >= 5) {
						best = 5;
						break;
					}
				}
				
				if (best > 4) {
					
				} else {
					acePresent = multiplicity[0] == 0 ? false : true;
					for (int i = 0; i < multiplicity.length; i++) {
						if (multiplicity[i] != 0)
							currentStraightLength++;
						else
							currentStraightLength = 0;
						
						if (currentStraightLength >= 5)
							best = 4;
					}
					
					if (currentStraightLength == 4 && acePresent)
						best = 4;
				}
			}
		}
		
		return best;
	}
	
}
