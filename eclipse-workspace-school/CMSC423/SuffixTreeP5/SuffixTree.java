package SuffixTreeP5; //remove for submission

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class SuffixTree {

		
	public static void buildSuffixTree(File input) throws IOException {
		Scanner scan = new Scanner(input);
		//FileWriter writer = new FileWriter("output"); //use for submission
		String line = "";
		FileWriter writer = new FileWriter("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\SuffixTreeP5\\output");
		while (scan.hasNextLine()) {
			line = line + scan.nextLine();	
		}
		
		class Node {
			public String str;
			public ArrayList<Node> nodes;
			
			public Node(String str) {
				this.str = str;
				nodes = new ArrayList<Node>();
			}
			
			public void addLeaf(String str) {
				int i = 0;
				while (i<nodes.size() && !matchIthLetter(str, nodes.get(i), 0)) 
					i++;
				
				if (i == nodes.size()) {
					nodes.add(new Node(str));
				} else {
					int j = 1;
					while (matchIthLetter(str, nodes.get(i), j)) {
						j++;
					}
					if (j == nodes.get(i).str.length())
						nodes.get(i).addLeaf(str.substring(j));
					else {
						Node a = nodes.get(i);
						Node b = new Node(a.str.substring(0,j));
						Node c = new Node(str.substring(j));
						nodes.remove(a);
						a.str = a.str.substring(j);
						nodes.add(b);
						b.nodes.add(a);
						b.nodes.add(c);
								
								
						//nodes.get(i).nodes.add(new Node(str.substring(j)));
						//nodes.get(i).nodes.add(new Node(nodes.get(i).str.substring(j)));
						//nodes.get(i).str = nodes.get(i).str.substring(0,j);
					}
				}
				
				
			}
			
			public boolean matchIthLetter(String str, Node node, int i) {
				if (str.length() > i && node.str.length() > i) {
					return str.substring(i,i+1).equals(node.str.substring(i,i+1));
				}
				return false;
			}
			
			public ArrayList<String> toList() {
				ArrayList<String> retVal = new ArrayList<String>();
				retVal.add(str);
				for (Node node: nodes) {
					retVal.addAll(node.toList());
				}
				return retVal;
			}
			
			
		}
		
		Node root = null;
		
		for (int i = 0; i < line.length(); i++) {
			if (root == null) {
				root = new Node("");
			}
			root.addLeaf(line.substring(i));
			System.out.println(line.substring(i) + ": " + root.toList());
		}
		
		
		boolean wrote = false;
		
		ArrayList<String> list = root.toList();
		Collections.sort(list);
		
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i).length() != 0) {
				if (wrote == true)
					writer.write("\n" + list.get(i));
				else {
					writer.write("" + list.get(i));
					wrote = true;
				}
			}
		}
		//System.out.println(root.toList());
		
		writer.close();
	}
	
	public static void main(String[] args) {
		//File input = new File("input"); //use for submission
		File input = new File("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\SuffixTreeP5\\TestInput");
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
