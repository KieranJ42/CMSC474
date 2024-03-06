package HierarchicalClusteringP9;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class HierarchicalClustering {

	public void cluster(File input) throws IOException {
		Scanner scan = new Scanner(input);
		//FileWriter writer = new FileWriter("output"); //use for submission
		FileWriter writer = new FileWriter("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\HierarchicalClusteringP9\\output");
		String firstWord = "";
		boolean first = true;
		ArrayList<String> matrixWords = new ArrayList<String>();
		while (scan.hasNextLine()) {
			if (first) {
				firstWord = scan.nextLine();
				first = false;
			} else {
				matrixWords.add(scan.nextLine());
			}
		}
		int nSize = Integer.parseInt(firstWord);
		double[][] matrix = new double[nSize][nSize];
		String[] matrixWordLine;
		for (int i = 0; i < matrixWords.size(); i++) {
			matrixWordLine = matrixWords.get(i).split(" ");
			for (int j = 0; j < matrixWordLine.length; j ++) {
				matrix[i][j] = Double.parseDouble(matrixWordLine[j]);
			}
		}
		
		/*for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}*/
		
		int temp = findMinIndex(matrix);
		String[] combos = new String[nSize];
		int[] weights = new int[nSize];
		for (int i = 0; i < combos.length; i++) {
			combos[i] = ""+(i+1);
			weights[i] = 1;
		}
		
		int comb1 = temp/matrix.length;
		int comb2 = temp%matrix.length;
		//System.out.println(comb1 + " " + comb2);
		int count = 1;
		int skip = 0;
		
		/*matrix = fixMatrix(matrix,weights,comb1,comb2);
		
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}*/
		while (matrix.length>1) {
			//printAll(matrix,weights,combos);
			if (count != 1) {
				writer.write("\n");
			}
			temp = findMinIndex(matrix);
			comb1 = (temp/matrix.length<temp%matrix.length)?(temp/matrix.length):(temp%matrix.length);
			comb2 = (temp/matrix.length<temp%matrix.length)?(temp%matrix.length):(temp/matrix.length);
			//String tempCombo = combos[comb1] + " " + combos[comb2];
			
			matrix = fixMatrix(matrix, weights, comb1, comb2);
			
			for (int i = 0; i < combos.length-count; i++) {
				if (i == comb1) {
					weights[i] += weights[comb2];
					combos[i] = combos[comb1] + " " + combos[comb2];
					writer.write(combos[i]);
				} else {
					if (i == comb2) {
						skip++;
					}
					weights[i] = weights[i+skip];
					combos[i] = combos[i+skip];
				}
			}
			
			skip = 0;
			count++;
		}
		
		/*
		 * start
		 * 	findMinIndex row x col = temp/matrix.length x temp%matrix.length
		 * 	write combined letters from combo
		 * 	combine letters in combos
		 * 	combine and rebuild matrix
		 * loop
		 */
		
		
		writer.close();
	}	
	
	public static void printAll(double[][] matrix, int[] weights, String[] combos) {
		for (int i = 0; i < matrix.length; i++) {
			System.out.print("{ ");
			for (int j = 0; j < matrix[i].length; j++) {
				System.out.print(((int)(matrix[i][j]*100))/100.0 + " \t");
			}
			System.out.print("} " + combos[i] + "; \t" + weights[i]+"  ");
			System.out.println();
		}
		System.out.println();
	}
	
	public static double[][] fixMatrix(double[][] oldMatrix, int[] weights, int row, int col) {
		double[][] newMatrix = new double[oldMatrix.length-1][oldMatrix.length-1];
		int rowSkip = 0;
		int colSkip = 0;
		for (int i = 0; i < newMatrix.length; i++) {
			if (i == col) {
				rowSkip++;
			}
			for (int j = 0; j < newMatrix[i].length; j++) {
				if (j == col) {
					colSkip++;
				}
				if (i==j) {
					newMatrix[i][j] = 0;
				} else if (i == row) {
					newMatrix[i][j] = (oldMatrix[i][j+colSkip]*weights[row] + 
							oldMatrix[col][j+colSkip]*weights[col])/(weights[row]+weights[col]);
				} else if (j == row) {
					newMatrix[i][j] = (oldMatrix[i+rowSkip][j]*weights[row] + 
							oldMatrix[i+rowSkip][col]*weights[col])/(weights[row]+weights[col]);
				} else {
					newMatrix[i][j] = oldMatrix[i+rowSkip][j+colSkip];
				}
			}
			colSkip = 0;
		}
		
		return newMatrix;
	}
	
	public int findMinIndex(double[][] matrix) {
		if (matrix.length <= 1) {
			return 0;
		}
		int minIndex = 1;
		double minVal = matrix[0][1];
		int count = 2;
		while (count < matrix.length*matrix.length) {
			if (count%(matrix.length+1)!=0) {
				if (matrix[count/matrix.length][count%matrix.length]<minVal) {
					minIndex = count;
					minVal = matrix[count/matrix.length][count%matrix.length];
				}
			} else {
				//do nothing we are on the diagonal
			}
			count++;
		}
		return minIndex;
	}
	
	public static void main(String[] args) {
		//File input = new File("input"); //use for submission
		File input = new File("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\HierarchicalClusteringP9\\TestInput");
		//System.out.println("Hi");
		try {
			HierarchicalClustering test = new HierarchicalClustering();
			test.cluster(input);
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
