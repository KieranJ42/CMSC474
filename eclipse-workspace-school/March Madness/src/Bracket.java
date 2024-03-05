
public class Bracket {
	
	private static Pair[][] bracket;
	
	public static void main(String[] args) {
		
		bracket = new Pair[9][];
		
		Pair[] round1 = new Pair[64];
		for (int i = 0; i < 4; i++) {
			round1[0+i*16] = new Pair(1);
			round1[1+i*16] = new Pair(16);
			round1[2+i*16] = new Pair(8);
			round1[3+i*16] = new Pair(9);
			round1[4+i*16] = new Pair(5);
			round1[5+i*16] = new Pair(12);
			round1[6+i*16] = new Pair(4);
			round1[7+i*16] = new Pair(13);
			round1[8+i*16] = new Pair(6);
			round1[9+i*16] = new Pair(11);
			round1[10+i*16] = new Pair(3);
			round1[11+i*16] = new Pair(14);
			round1[12+i*16] = new Pair(7);
			round1[13+i*16] = new Pair(10);
			round1[14+i*16] = new Pair(2);
			round1[15+i*16] = new Pair(15);
		}
		
		bracket[0] = round1;
		
		int matches = 32;
		int tempRank;
		for (int i = 0; i < 6; i++) {
			bracket[i+1] = new Pair[matches];
			for (int j = 0; j < matches; j++) {
				tempRank = bracket[i][2*j].getRank();
				if ((int)(Math.random()*(tempRank+bracket[i][2*j+1].getRank()) + 1) 
						<= tempRank) {
					bracket[i][2*j+1].addWin();
					bracket[i+1][j] = bracket[i][2*j+1];
				} else {
					bracket[i][2*j].addWin();
					bracket[i+1][j] = bracket[i][2*j];
				}
			}
			matches /= 2;
		}
		
		printBracket();
	}
	
	
	
	public static void printBracket() {
		for (int i = 0; i < 16; i++) {
			System.out.print(bracket[0][i].getRank() + ": " + 
					bracket[0][i].getWins() + "\t");
			System.out.print(bracket[0][i+16].getRank() + ": " + 
					bracket[0][i+16].getWins() + "\t");
			System.out.print(bracket[0][i+32].getRank() + ": " + 
					bracket[0][i+32].getWins() + "\t");
			System.out.println(bracket[0][i+48].getRank() + ": " + 
					bracket[0][i+48].getWins());
		}
	}

}
