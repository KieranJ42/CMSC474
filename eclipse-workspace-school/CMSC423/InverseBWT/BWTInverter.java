package InverseBWT; //remove for submission

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class BWTInverter {

	public static void invert(File input) throws IOException {
		Scanner scan = new Scanner(input);
		//FileWriter writer = new FileWriter("output"); //use for submission
		String line = "";
		FileWriter writer = new FileWriter("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\InverseBWT\\output");
		while (scan.hasNextLine()) {
			line = line + scan.nextLine();	
		}
		
		ArrayList<String> firstLetters = new ArrayList<String>();
		ArrayList<String> lastLetters = new ArrayList<String>();
		for (int i = 0; i < line.length(); i++) {
			firstLetters.add(line.substring(i,i+1));
			lastLetters.add(line.substring(i,i+1));
		}
		
		Collections.sort(firstLetters);
		
		int[] startLocs = new int[4];
		int[][] jumpTo = new int[line.length()][4];
		for(int i = 0; i < lastLetters.size(); i++) {
			switch(lastLetters.get(i)) {
			case "A":
				startLocs[0]++;
				if(i != lastLetters.size()-1) {
					jumpTo[i+1][0] = jumpTo[i][0] + 1;
					jumpTo[i+1][1] = jumpTo[i][1];
					jumpTo[i+1][2] = jumpTo[i][2];
					jumpTo[i+1][3] = jumpTo[i][3];
				}
				break;
			case "C":
				startLocs[1]++;
				if(i != lastLetters.size()-1) {
					jumpTo[i+1][0] = jumpTo[i][0];
					jumpTo[i+1][1] = jumpTo[i][1] + 1;
					jumpTo[i+1][2] = jumpTo[i][2];
					jumpTo[i+1][3] = jumpTo[i][3];
				}
				break;
			case "G":
				startLocs[2]++;
				if(i != lastLetters.size()-1) {
					jumpTo[i+1][0] = jumpTo[i][0];
					jumpTo[i+1][1] = jumpTo[i][1];
					jumpTo[i+1][2] = jumpTo[i][2] + 1;
					jumpTo[i+1][3] = jumpTo[i][3];
				}
				break;
			case "T":
				startLocs[3]++;
				if(i != lastLetters.size()-1) {
					jumpTo[i+1][0] = jumpTo[i][0];
					jumpTo[i+1][1] = jumpTo[i][1];
					jumpTo[i+1][2] = jumpTo[i][2];
					jumpTo[i+1][3] = jumpTo[i][3] + 1;
				}
				break;
			default:
				if(i != lastLetters.size()-1) {
					jumpTo[i+1][0] = jumpTo[i][0];
					jumpTo[i+1][1] = jumpTo[i][1];
					jumpTo[i+1][2] = jumpTo[i][2];
					jumpTo[i+1][3] = jumpTo[i][3];
				}
			}
			
		}
		startLocs[3] =  startLocs[0] + startLocs[1] + startLocs[2] + 1;
		startLocs[2] =  startLocs[0] + startLocs[1] + 1;
		startLocs[1] =  startLocs[0] + 1;
		startLocs[0] =  1;
		
		String original = "";
		int currLoc = 0;
		while (original.length()<line.length()) {
			original = firstLetters.get(currLoc) + original;
			switch (lastLetters.get(currLoc)) {
			case "A":
				currLoc = startLocs[0] + jumpTo[currLoc][0];
				break;
			case "C":
				currLoc = startLocs[1] + jumpTo[currLoc][1];
				break;
			case "G":
				currLoc = startLocs[2] + jumpTo[currLoc][2];
				break;
			case "T":
				currLoc = startLocs[3] + jumpTo[currLoc][3];
				break;
			
			}
		}
		/*for(int i = 0; i < lastLetters.size()-1; i++) {
			switch(lastLetters.get(i)) {
			case "A":
				
				break;
			}
		}*/
		
		/*writer.write("StartLocs: " + startLocs[0] + " " + startLocs[1] + " " 
		+ startLocs[2] + " " + startLocs[3] + "\n");
		
		boolean wrote = false;
		for (int i = 0; i < line.length(); i++) {
			if (!wrote) {
				writer.write(firstLetters.get(i) + " " + lastLetters.get(i) + "\t" +
			jumpTo[i][0] + " " + jumpTo[i][1] + " " + jumpTo[i][2] + " " + 
			jumpTo[i][3]);
				wrote = true;
			} else {
				writer.write("\n" + firstLetters.get(i) + " " + lastLetters.get(i) + "\t" +
						jumpTo[i][0] + " " + jumpTo[i][1] + " " + jumpTo[i][2] + " " + 
						jumpTo[i][3]);
			}
		}*/
		writer.write(original);
		writer.close();
	}
	
	public static void main(String[] args) {
		//File input = new File("input"); //use for submission
		File input = new File("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\InverseBWT\\TestInput");
		try {
			invert(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
