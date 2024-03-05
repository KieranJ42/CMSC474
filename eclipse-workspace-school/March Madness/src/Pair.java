
public class Pair {

	private int rank;
	private int wins;
	
	public Pair(int rank, int wins) {
		this.rank = rank;
		this.wins = wins;
	}
	
	public Pair(int rank) {
		this(rank, 0);
	}
	
	public int getRank() {
		return rank;
	}
	
	public int getWins() {
		return wins;
	}
	
	public void addWin() {
		wins++;
	}
	
}
