package BranchAndBoundP7;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Scanner;

public class BranchAndBound {

	public static void sequence(File input) throws IOException {
		Scanner scan = new Scanner(input);
		//FileWriter writer = new FileWriter("output"); //use for submission
		FileWriter writer = new FileWriter("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\BranchAndBoundP7\\output");
		String word = "";
		while (scan.hasNextLine()) {
			word+=scan.nextLine();
		}
		
		String[] words = word.split(" ");
		ArrayList<Integer> partPeps = new ArrayList<Integer>();
		for (int i = 0; i < words.length; i++) {
			partPeps.add(Integer.parseInt(words[i]));
		}
		int pSum = partPeps.get(partPeps.size()-1);
		
		ArrayList<Integer> amiAcids = new ArrayList<Integer>();
		int[] tempArray = {57, 71, 87, 97, 99, 101, 103, 113, 114, 115, 128, 129,
				131, 137, 147, 156, 163, 186};
	
		for (int i = 0; i < tempArray.length; i++) {
			amiAcids.add(tempArray[i]);
		}
		
		for (int i = amiAcids.size()-1; i >= 0; i--) {
			if (!partPeps.contains(amiAcids.get(i))) {
				amiAcids.remove(i);
			}
		}
		
		
		ArrayList<ArrayList<Integer>> branches = new ArrayList<ArrayList<Integer>>();
		ArrayList<Integer> tempList;
		for (int i = 0; i < amiAcids.size(); i++) {
			tempList = new ArrayList<Integer>();
			tempList.add(amiAcids.get(i));
			branches.add(tempList);
		}
		
		//do branch and bound
		int count = 0;
		ArrayList<Integer> toAdd;
		while (branches.size()>count) {
			System.out.print(count + ": ");
			System.out.println(branches);
			ArrayList<Integer> temp = branches.get(count);
			if (sum(temp)>pSum) {
				branches.remove(count);
			} else if (sum(temp)==pSum) {
				if (circSpec(temp).equals(partPeps)) {
					count++;
				} else {
					branches.remove(count);
				}
			} else {
				if (partPeps.containsAll(linSpec(temp))) {
					for (int i = 0; i < amiAcids.size(); i++) {
						toAdd = new ArrayList<Integer>(temp);
						toAdd.add(amiAcids.get(i));
						branches.add(toAdd);
					}
					branches.remove(count);
				} else {
					branches.remove(count);
				}
			}
		}
		System.out.println(branches);
		
		String total = "";
		//String word = "";
		for(int i = 0; i < branches.size(); i++) {
			for (int j = 0; j < branches.get(i).size(); j++) {
				total+=branches.get(i).get(j)+"-";
			}
			total = total.substring(0,total.length()-1);
			total += " ";
		}
		if (!total.equals(""))
			total = total.substring(0, total.length()-1);
		
	
		
		boolean wrote = false;
		
		writer.write(total);
		
		
		
		writer.close();
	}
	
	public static int sum(ArrayList<Integer> masses) {
		int retVal = 0;
		for (int i = 0; i < masses.size(); i++) {
			retVal += masses.get(i);
		}
		return retVal;
	}
	
	public static ArrayList<Integer> linSpec(ArrayList<Integer> masses) {
		ArrayList<Integer> spectrum = new ArrayList<Integer>();
		int sum = 0;
		spectrum.add(0);
		for (int i = 1; i < masses.size(); i++) {//lengths needed to check
			for (int j = 0; j <= masses.size()-i; j++) {//how many to check FE length
				for (int k = 0; k < i; k++) {//sum over to make masses
					sum+=masses.get(j+k);
				}
				spectrum.add(sum);
				sum = 0;
			}
		}
		for (int i = 0; i < masses.size(); i++) {
			sum+=masses.get(i);
		}
		spectrum.add(sum);
		Collections.sort(spectrum);
		return spectrum;
	}
	
	public static ArrayList<Integer> circSpec(ArrayList<Integer> masses) {
		ArrayList<Integer> spectrum = new ArrayList<Integer>();
		int sum = 0;
		spectrum.add(0);
		for (int i = 1; i < masses.size(); i++) {//lengths needed to check
			for (int j = 0; j < masses.size(); j++) {//how many to check FE length
				for (int k = 0; k < i; k++) {//sum over to make masses
					sum+=masses.get((j+k)%masses.size());
				}
				spectrum.add(sum);
				sum = 0;
			}
		}
		for (int i = 0; i < masses.size(); i++) {
			sum+=masses.get(i);
		}
		spectrum.add(sum);
		Collections.sort(spectrum);
		return spectrum;
	}
	
	public static void main(String[] args) {
		//File input = new File("input"); //use for submission
		File input = new File("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\BranchAndBoundP7\\TestInput");
		//System.out.println("Hi");
		try {
			sequence(input);
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
