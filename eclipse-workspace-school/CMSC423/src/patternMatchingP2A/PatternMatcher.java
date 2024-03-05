package patternMatchingP2A; //remove package header for submission
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import java.io.File;

public class PatternMatcher {
	public static void matchPattern(File input) throws IOException {
		final long startTime = System.currentTimeMillis();
		
		Scanner scan = new Scanner(input);
		String line = "";
		String pattern = null;
		boolean wrote = false;
		//FileWriter writer = new FileWriter("output"); //use for submission
		FileWriter writer = new FileWriter("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\patternMatchingP2A\\output");
		while (scan.hasNextLine()) {
			if (pattern == null) 
				pattern = scan.nextLine();
			else 
				line += scan.nextLine();
			
		}
		int loopTimes = line.length()-pattern.length()+1;
		for (int i = 0; i < loopTimes; i++) {
			if (pattern.equals(line.substring(i,i+pattern.length())))
				if (wrote)
					writer.write(" " + i);
				else {
					writer.write("" + i);
					wrote = true;
				}
		}
		
		final long endTime = System.currentTimeMillis();
		writer.write("\nTotal execution time: " + (endTime - startTime));
		
		writer.close();
		//System.out.println(count[0] + " " + count[1] + " "
		//		 + count[2] + " " + count[3]);
		
	}
	
	public static void main(String[] args) {
		//File input = new File("input"); //use for submission
		File input = new File("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\patternMatchingP2A\\TestInput");
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
