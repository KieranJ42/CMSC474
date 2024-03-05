package Poker;

public class Tester {

	public static void main(String[] args) {
		/*
		Hand hand;
		int[] handAmounts = new int[10];
		for (int i = 0; i < 10000000; i++) {
			hand = new UnknownHandSizeHand(8);
			handAmounts[hand.getBestCombo()]++;
		}
		
		for (int i = 0; i < handAmounts.length; i++)
			System.out.println((double)handAmounts[i]/100000 + "%");
		*/
		
		
		AllHandSizeBeforePrinter printer = new AllHandSizeBeforePrinter();
		
		System.out.println(printer);
	}
	
}
