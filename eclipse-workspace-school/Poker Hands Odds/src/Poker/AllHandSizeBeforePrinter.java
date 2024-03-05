package Poker;

public class AllHandSizeBeforePrinter {

	private int handSizeBefore;

	private static int defaultSize = 13;

	public AllHandSizeBeforePrinter() {
		this(defaultSize);
	}

	public AllHandSizeBeforePrinter(int handSizeBefore) {
		this.handSizeBefore = handSizeBefore;
	}

	public String toString() {
		String retVal = "";

		Hand hand;
		int[] handAmounts = new int[10];
		
		retVal += "\t\tHigh card\tOne Pair\tTwo Pair\tTriples\t\tStraight"
				+ "\tFlush\t\tFull House\tFour of a Kind\tStraight Flush\tRoyal"
				+ "Flush";
		
		for (int j = handSizeBefore; j > 0; j--) {
			
			retVal += "\n" + j + " card" + (j == 1 ? "" : "s") + "\t";
			retVal += j / 10 == 0 ? "\t" : "";
			
			for (int i = 0; i < 1000000; i++) {
				hand = new UnknownHandSizeHand(j);
				handAmounts[hand.getBestCombo()]++;
			}

			for (int i = 0; i < handAmounts.length; i++) {
				retVal += String.format("%.5f", (double)handAmounts[i]/10000)
						+ "%\t";
			}
			
			handAmounts = new int[10];
		}

		return retVal;
	}

}
