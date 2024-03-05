package GreedyMotifLaplace; //leave out for submission

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class GreedyMotif {
	
	public static void findMotif(File input) throws IOException {
		Scanner scan = new Scanner(input);
		ArrayList<String> lines = new ArrayList<String>();;
		String firstLineInfo;
		//FileWriter writer = new FileWriter("output"); //use for submission
		FileWriter writer = new FileWriter("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\GreedyMotifLaplace\\output");
		boolean firstLine = true;
		int motifSize = 0;
		int numLines = 0;
		while (scan.hasNextLine()) {
			if (firstLine) {
				firstLineInfo = scan.nextLine();
				firstLine = false;
				String[] nums = firstLineInfo.split(" ");
				motifSize = Integer.parseInt(nums[0]);
				numLines = Integer.parseInt(nums[1]);
			} else {
				lines.add(scan.nextLine());
			}
		}
		int[][] profMat;
		//int[][] currBest;
		//int[][] totalBest;
		int totalBestScore = 0;
		int bestScore = 0;
		int score;
		int posBest;
		int[] posCurrBestMotif;
		int[] posBestMotif = new int[numLines];
		
		for (int i = 0; i < lines.get(0).length()-motifSize+1; i++) {
			profMat = new int[4][motifSize];
			posCurrBestMotif = new int[numLines];
			for (int j = 0; j < motifSize; j++) {
				switch (lines.get(0).substring(i+j,i+j+1)) {
				case "A":
					profMat[0][j]++;
					break;
				case "C":
					profMat[1][j]++;
					break;
				case "G":
					profMat[2][j]++;
					break;
				case "T":
					profMat[3][j]++;
					break;
				}
			}
			
			posCurrBestMotif[0] = i;
			for (int j = 1; j < numLines; j++) {
				bestScore = 0;
				
				posBest = 0;
				
				for (int k = 0; k < lines.get(j).length()-motifSize+1; k++) {
					score = 1;
					for (int l = 0; l < motifSize; l++) {
						switch (lines.get(j).substring(k+l,k+l+1)) {
						case "A":
							score *= (1+profMat[0][l]);
							break;
						case "C":
							score *= (1+profMat[1][l]);
							break;
						case "G":
							score *= (1+profMat[2][l]);
							break;
						case "T":
							score *= (1+profMat[3][l]);
							break;
						}
					}
					if (score > bestScore) {
						bestScore = score;
						posBest = k;
					}
				}
				posCurrBestMotif[j] = posBest;

				for (int k = 0; k < motifSize; k++) {
					switch (lines.get(j).substring(posBest+k,posBest+k+1)) {
					case "A":
						profMat[0][k]++;
						break;
					case "C":
						profMat[1][k]++;
						break;
					case "G":
						profMat[2][k]++;
						break;
					case "T":
						profMat[3][k]++;
						break;
					}
				}
			}
			
			/*boolean wrote = false;
			for (int j = 0; j < posBestMotif.length; j++) {
				if (!wrote) {
					writer.write(lines.get(j).substring(posCurrBestMotif[j],posCurrBestMotif[j]+motifSize));
					wrote = true;
				} else {
					writer.write("\n" + lines.get(j).substring(posCurrBestMotif[j],posCurrBestMotif[j]+motifSize));
				}
			}
			writer.write("\nScore = " + bestScore + "\n");
			*/
			if (totalBestScore<bestScore) {
				totalBestScore = bestScore;
				posBestMotif = posCurrBestMotif;
			}
		}
		boolean wrote = false;
		for (int i = 0; i < posBestMotif.length; i++) {
			if (!wrote) {
				writer.write(lines.get(i).substring(posBestMotif[i],posBestMotif[i]+motifSize));
				wrote = true;
			} else {
				writer.write("\n" + lines.get(i).substring(posBestMotif[i],posBestMotif[i]+motifSize));
			}
		}
		
		writer.close();
	}
	
	public static void main(String[] args) {
		//File input = new File("input"); //use for submission
		File input = new File("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\GreedyMotifLaplace\\TestInput");
		System.out.println("Hi");
		try {
			findMotif(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("Hi");
	}
	
}
