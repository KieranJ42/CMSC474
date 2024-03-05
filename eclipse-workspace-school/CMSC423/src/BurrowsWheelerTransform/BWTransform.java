package BurrowsWheelerTransform;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class BWTransform {

	public static void buildSuffixTree(File input) throws IOException {
		Scanner scan = new Scanner(input);
		FileWriter writer = new FileWriter("output"); //use for submission
		String line = "";
		//FileWriter writer = new FileWriter("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\SuffixTreeP5\\output");
		while (scan.hasNextLine()) {
			line = line + scan.nextLine();	
		}
		
		ArrayList<String> rotations = new ArrayList<String>();
		for (int i = 0; i < line.length(); i++) {
			//rotations.add()
		}
		
		writer.close();
	}
	
	public static void main(String[] args) {
		File input = new File("input"); //use for submission
		//File input = new File("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\SuffixTreeP5\\TestInput");
		try {
			buildSuffixTree(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
}
