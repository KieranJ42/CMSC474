package randomGenomeGeneration;

import java.io.FileWriter;
import java.io.IOException;

public class GenomeGenerator {
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
	
	public static String generateSpecific(int size, int patternSize) {
		String retVal = "";
		for (int i = 0; i < patternSize; i++) {
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
		
		retVal += "\n";
		
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
	
	public static void main(String[] args) throws IOException {
		FileWriter writer = new FileWriter("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\randomGenomeGeneration\\output");
		writer.write(generate(50));
		writer.close();
		//System.out.println(generate(50000));
	}
}
