package PatternMatcherP2B; //remove package header for submission
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.io.File;

public class PatternMatcher {
	public static void matchPattern(File input) throws IOException {
		Scanner scan = new Scanner(input);
		String line = "";
		String pattern = null;
		boolean wrote = false;
		FileWriter writer = new FileWriter("output"); //use for submission
		//FileWriter writer = new FileWriter("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\PatternMatcherP2B\\output");
		while (scan.hasNextLine()) {
			if (pattern == null) 
				pattern = scan.nextLine();
			else 
				line += scan.nextLine();
			
		}
		
		int[] skew = new int[line.length()];
		String printVal = "";
		if (line.length()!=0) {
			skew[0] = 0;
			for (int i = 0; i < line.length()-1; i++) {
				if (line.substring(i,i+1).equals("C")) {
					skew[i+1] = skew[i] - 1;
				} else if (line.substring(i,i+1).equals("G"))
					skew[i+1] = skew[i] + 1;
			}
			
			int loopTimes = line.length()-pattern.length()+1;
			int minSkew = line.length();
			for (int i = 0; i < loopTimes; i++) {
				if (pattern.equals(line.substring(i,i+pattern.length()))
						&& skew[i] <= minSkew) {
					if (skew[i] < minSkew) {
						printVal = "";
						wrote = false;
						minSkew = skew[i];
					}
					if (wrote)
						printVal += " " + i;
					else {
						printVal += i;
						wrote = true;
					}
				}
			}
		}
		writer.write(printVal);
		writer.close();
		//System.out.println(count[0] + " " + count[1] + " "
		//		 + count[2] + " " + count[3]);
		
	}
	
	public static void main(String[] args) {
		File input = new File("input"); //use for submission
		//File input = new File("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\PatternMatcherP2B\\TestInput");
		try {
			matchPattern(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
