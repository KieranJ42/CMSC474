package LloydsAlgorithmP8;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class LloydsAlgorithm {

	public void lloyd(File input) throws IOException {
		Scanner scan = new Scanner(input);
		//FileWriter writer = new FileWriter("output"); //use for submission
		FileWriter writer = new FileWriter("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\LloydsAlgorithmP8\\output");
		String firstWord = "";
		boolean first = true;
		ArrayList<String> pointWords = new ArrayList<String>();
		while (scan.hasNextLine()) {
			if (first) {
				firstWord = scan.nextLine();
				first = false;
			} else {
				pointWords.add(scan.nextLine());
			}
		}
		
		ArrayList<Point> points = new ArrayList<Point>();
		String[] tempWords;
		double[] coords;
		
		//System.out.println(pointWords);
		
		for (int i = 0; i <pointWords.size(); i++) {
			tempWords = pointWords.get(i).split(" ");
			coords = new double[tempWords.length];
			for (int j = 0; j < tempWords.length; j++) {
				coords[j] = Double.parseDouble(tempWords[j]);
			}
			points.add(new Point(coords));
		}
		
		//System.out.println(points);
		int numCenters = Integer.parseInt(firstWord.split(" ")[0]);
		
		
		ArrayList<Point> centers = new ArrayList<Point>();
		for (int i = 0; i < numCenters; i++) {
			centers.add(points.get(i));
		}
		
		ArrayList<Point> oldCenters = null;
		
		
		//System.out.println(centers);
		boolean untilEqual = true;
		boolean okay = true;
		while (untilEqual) {
			oldCenters = centers;
			centers = findNewCenters(centers, points);
			for (int i = 0; i < centers.size(); i++) {
				if (!centers.get(i).equals(oldCenters.get(i))) {
					okay = false;
					break;
				}
			}
			if (okay) {
				untilEqual = false;
			}
			okay = true;
			//System.out.println(oldCenters);
			//System.out.println(centers);
		}
		
		String write = "";
		for (int i = 0; i < centers.size(); i++) {
			write += centers.get(i).write() + "\n";
		}
		write = write.substring(0, write.length()-1);
		writer.write(write);
		//System.out.println(write);
		writer.close();
	}
	
	public ArrayList<Point> findNewCenters(ArrayList<Point> centers, ArrayList<Point> points) {
		ArrayList<Point> newCenters = new ArrayList<Point>();
		double[][] sums = new double[centers.size()][points.get(0).coord.length];
		int[] numPoints = new int[centers.size()];
		int closest;
		for (int i = 0; i < points.size(); i++) {
			closest = closestCenter(centers, points.get(i));
			numPoints[closest]++;
			for (int j = 0; j < points.get(i).coord.length; j++) {
				sums[closest][j] += points.get(i).coord[j];
			}
		}
		
		for (int i = 0; i < sums.length; i++) {
			for (int j = 0; j < sums[i].length; j++) {
				sums[i][j] /= numPoints[i];
			}
			newCenters.add(new Point(sums[i]));
		}
		return newCenters;
	}
	
	public int closestCenter(ArrayList<Point> centers, Point p) {
		int minIndex = 0;
		for (int i = 1; i < centers.size(); i++) {
			if (p.sqDist(centers.get(i))<p.sqDist(centers.get(minIndex))) {
				minIndex = i;
			}
		}
		return minIndex;
	}
	
	private class Point implements Comparable<Point>{
		double[] coord;
		
		public Point(double[] coord) {
			this.coord = coord;
		}
		
		public double sqDist(Point other) {
			double retVal = 0;
			for (int i = 0; i < coord.length; i++) {
				retVal+=Math.abs(coord[i]*coord[i] - other.coord[i]*other.coord[i]);
			}
			return retVal;
		}
		
		public String toString() {
			String retVal = "(";
			for (int i = 0; i < coord.length; i++) {
				retVal += coord[i] + ", ";
			}
			retVal = retVal.substring(0, retVal.length()-2) + ")";
			return retVal;
		}
		
		public String write() {
			String retVal = "";
			for (int i = 0; i < coord.length; i++) {
				retVal += coord[i] + " ";
			}
			retVal = retVal.substring(0,retVal.length()-1);
			return retVal;
		}

		public int compareTo(Point other) {
			if (sqDist(other) == 0) {
				return 0;
			}
			for (int i = 0; i < coord.length; i++) {
				if (coord[i]<other.coord[i]) {
					return -1;
				} else if (coord[i]<other.coord[i]) {
					return 1;
				}
			}
			return 0;
		}
		
		public boolean equals(Point other) {
			return sqDist(other)==0;
		}
	}
	
	public static void main(String[] args) {
		//File input = new File("input"); //use for submission
		File input = new File("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\LloydsAlgorithmP8\\TestInput");
		//System.out.println("Hi");
		try {
			LloydsAlgorithm test = new LloydsAlgorithm();
			test.lloyd(input);
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
