package ptiCalc;

public class Tester {

	public static void main(String[] args) {
		//System.out.println(Math.cbrt(8));
		
		Player p1 = new Player(6, "Don", .8, 6);
		Player p2 = new Player(3, "John", .5, 30);
		Player p3 = new Player(12, "Paul", .7, 30);
		Player p4 = new Player(2, "Mike", 1, 30);
		
		Player[] players = new Player[4];
		players[0] = p1;
		players[1] = p2;
		players[2] = p3;
		players[3] = p4;
		
		int[] score = {5,7,6,7};
		
		//try {
			Match match = new Match(players, score);
		//} catch(Exception ex) {
		//	System.out.println("Error!");
		//}
		
		//System.out.println(match);
	}
	
}
