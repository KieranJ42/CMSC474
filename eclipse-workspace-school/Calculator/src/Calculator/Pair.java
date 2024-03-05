package Calculator;

public class Pair {
	
	private String operator;
	private boolean available;
	
	public Pair(String operator, boolean available) {
		this.operator = operator;
		this.available = available;
	}
	
	public String getOperator() {
		return operator;
	}
	
	public boolean getAvailable() {
		return available;
	}
	
	public Pair(Pair pair) {
		this(pair.getOperator(),pair.getAvailable());
		addPhoto(strings[0],Integer.parseInt(strings[1]),Integer.parseInt(strings[2]),strings[3]
	}

}
