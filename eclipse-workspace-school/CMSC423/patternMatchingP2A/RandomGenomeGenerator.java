package patternMatchingP2A;

public class RandomGenomeGenerator {
	public static String generate(int size) {
		String retVal = "";
		for (int i = 0; i < size; i++) {
			if (i!=0 && i%60==0)
				retVal += "\n";
			switch ((int)(Math.random()*4)) {
			case 0:
				retVal += "A";
				break;
			case 1:
				retVal += "C";
				break;
			case 2:
				retVal += "G";
				break;
			case 3:
				retVal += "T";
				break;
			}
		}
		return retVal;
	}
	
	public static void main(String[] args) {
		System.out.println(generate(100));
	}
}
