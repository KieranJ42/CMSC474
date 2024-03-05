package HighestScoringAlignmentP6A;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class HighScoreAlign {

	public static void HighScore(File input1, File input2) throws IOException {
		Scanner scan1 = new Scanner(input1);
		//FileWriter writer = new FileWriter("output"); //use for submission
		FileWriter writer = new FileWriter("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\HighestScoringAlignmentP6A\\output");
		String[] words = new String[2];
		words[0] = null;
		words[1] = null;
		int numLines = 0;
		while (scan1.hasNextLine()) {
			if (words[0] == null) {
				words[0] = scan1.nextLine();
			} else if (words[1] == null){
				words[1] = scan1.nextLine();
			}
		}
		
		HashMap<String, Integer> indices = new HashMap<String,Integer>();
		Scanner scan2 = new Scanner(input2);
		ArrayList<String> lines = new ArrayList<String>();;
		String firstLineInfo = "";
		boolean firstLine = true;
		
		while (scan2.hasNextLine()) {
			if (firstLine) {
				firstLineInfo = scan2.nextLine();
				firstLine = false;
			} else {
				lines.add(scan2.nextLine());
			}
		}
		
		String[] lettersTemp = firstLineInfo.trim().split(" ");
		int count = 0;
		ArrayList<String> lt = new ArrayList<String>();
		for (int i = 0; i < lettersTemp.length; i++) {
			if (!lettersTemp[i].equals("")) {
				indices.put(lettersTemp[i], count);
				lt.add(lettersTemp[i]);
				count++;
			}
		} 
		
		String[] letters = new String[lt.size()];
		for (int i = 0; i < letters.length; i++) {
			letters[i] = lt.get(i);
			//System.out.println(i+": "+letters[i]+"\t\t");
		}
		
		
		
		int[][] blosum62 = new int[letters.length][letters.length];
		for (int i = 0; i < lines.size(); i++) {
			
			
			lettersTemp = lines.get(i).trim().split(" ");
			count = 0;
			lt = new ArrayList<String>();
			for (int j = 0; j < lettersTemp.length; j++) {
				if (!lettersTemp[j].equals("")) {
					//indices.put(lettersTemp[j], count);
					lt.add(lettersTemp[j]);
					count++;
				}
			} 
			letters = new String[lt.size()];
			for (int j = 0; j < letters.length; j++) {
				letters[j] = lt.get(j);
				//System.out.println(j+": "+letters[j]+"\t\t");
			}
			
			
			for (int j = 1; j < letters.length; j++) {
				blosum62[i][j-1] = Integer.parseInt(letters[j]);
			}
		}
		
		int[][] matrix = new int[words[1].length()+1][words[0].length()+1];
		matrix[0][0] = 0;
		for (int i = 1; i < matrix.length; i++) {
			matrix[i][0] = matrix[i-1][0] - 5; 
		}
		for (int i = 1; i < matrix[0].length; i++) {
			matrix[0][i] = matrix[0][i-1] - 5;
		}
		
		for (int i = 1; i < matrix.length; i++) {
			for (int j = 1; j < matrix[i].length; j++) {
				matrix[i][j] = max(matrix[i][j-1]-5,matrix[i-1][j]-5,matrix[i-1][j-1]
						+blosum62[indices.get(words[1].substring(i-1,i))]
								[indices.get(words[0].substring(j-1,j))]);
			}
		}
		
		int i = matrix[0].length-1;
		int j = matrix.length-1;
		String word1 = "";
		String word2 = "";
		
		while(i>0 || j>0) {
			if (i==0) {
				word1 = "-" + word1;
				word2 = words[1].substring(j-1,j) + word2;
				j--;
			} else if (j==0) {
				word1 = words[0].substring(i-1,i) + word1;
				word2 = "-" + word2;
				i--;
			} else {
				if (matrix[j][i-1] == matrix[j][i]+5) {
					word1 = words[0].substring(i-1,i) + word1;
					word2 = "-" + word2;
					i--;
				} else if (matrix[j-1][i] == matrix[j][i]+5) {
					word1 = "-" + word1;
					word2 = words[1].substring(j-1,j) + word2;
					j--;
				} else {
					word1 = words[0].substring(i-1,i) + word1;
					word2 = words[1].substring(j-1,j) + word2;
					i--;
					j--;
				}
			}
		}
		
		boolean wrote = false;
		/*for (i = 0; i < matrix.length; i++) {
			for (j = 0; j < matrix[0].length; j++) {
				writer.write("\t" + matrix[i][j]);
			}
			writer.write("\n");
		}*/
		writer.write(matrix[matrix.length-1][matrix[0].length-1] + 
				"\n" + word1 + "\n" + word2);
		
		
		
		writer.close();
	}
	
	public static int max(int a, int b, int c) {
		return a>b?(a>c?a:c):(b>c?b:c);
	}
	
	public static void main(String[] args) {
		//File input1 = new File("input"); //use for submission
		//File input2 = new File("BLOSUM62.txt"); //use for submission
		File input1 = new File("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\HighestScoringAlignmentP6A\\TestInput");
		File input2 = new File("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\HighestScoringAlignmentP6A\\BLOSUM62.txt");
		//System.out.println("Hi");
		try {
			HighScore(input1,input2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//System.out.println("Hi");
	}
	
}
