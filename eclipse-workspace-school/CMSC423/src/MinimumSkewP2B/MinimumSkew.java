package MinimumSkewP2B; //remove package header for submission
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class MinimumSkew {
	
	public static void findSkew(File input) throws IOException {
		Scanner scan = new Scanner(input);
		String line = "";
		//FileWriter writer = new FileWriter("output"); //use for submission
		FileWriter writer = new FileWriter("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\MinimumSkewP2B\\output");
		while (scan.hasNextLine()) {
			line += scan.nextLine();
			
		}
		
		int[] skew = new int[line.length()+1];
		int minSkew = 1;
		boolean wrote = false;
		if (line.length()!=0) {
			skew[0] = 0;
			for (int i = 1; i < skew.length; i++) {
				if (line.substring(i-1,i).equals("C")) {
					skew[i] = skew[i-1] - 1;
					if (skew[i]<minSkew)
						minSkew = skew[i];
				} else if (line.substring(i-1,i).equals("G"))
					skew[i] = skew[i-1] + 1;
				else 
					skew[i] = skew[i-1];
			}
			
			for (int i = 1; i < skew.length; i++) {
				if (skew[i] == minSkew) {
					if (wrote)
						writer.write(" "+i);
					else {
						wrote = true;
						writer.write(""+i);
					}
				}
			}
		}
		writer.close();
		System.out.println(0 + ":\t"  + "\t" + skew[0]);
		for (int i = 1; i<skew.length; i++) {
			System.out.println(i + ":\t" + line.substring(i-1,i) + "\t" + skew[i]);
		}
		//System.out.println(count[0] + " " + count[1] + " "
		//		 + count[2] + " " + count[3]);
		
	}
	
	public static void main(String[] args) {
		//File input = new File("input"); //use for submission
		File input = new File("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\MinimumSkewP2B\\TestInput");
		try {
			findSkew(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
