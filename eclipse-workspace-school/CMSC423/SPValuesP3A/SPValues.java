package SPValuesP3A; //remove for submission
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class SPValues {
	
	public static void findSPValues(File input) throws IOException {
		Scanner scan = new Scanner(input);
		String line = "";
		String firstLineInfo;
		FileWriter writer = new FileWriter("output"); //use for submission
		//FileWriter writer = new FileWriter("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\SPValuesP3A\\output");
		boolean firstLine = true;
		while (scan.hasNextLine()) {
			if (firstLine) {
				firstLineInfo = scan.nextLine();
				firstLine = false;
			} else {
				line += scan.nextLine();
			}
		}
		int[] zValues = findZValues(line);
		int[] spValues = new int[zValues.length];
		for (int i : zValues)
			System.out.println(i);
		for (int i = 1; i < zValues.length; i++) {
			if (zValues[i]>=1) {
				for (int j = zValues[i]-1; j>=0; j--) {
					if (spValues[i+j] != 0) 
						break;
					else
						spValues[i+j] = j+1;
				}
			}
		}
		boolean wrote = false;
		for (int i = 0; i < spValues.length; i++) {
			if (!wrote) {
				writer.write("" + spValues[i]);
				wrote = true;
			} else {
				writer.write(" " + spValues[i]);
			}
		}
		/*System.out.println("index\tletter\tzVal\tspVal");
		for (int i = 0; i < line.length(); i++) {
			System.out.println(i + ":\t" + line.charAt(i) + "\t" + zValues[i]+"\t"+spValues[i]);
			writer.write(i + ":\t" + line.charAt(i) + "\t" + zValues[i] + "\n");
		}*/
		writer.close();
		
	}
	
	public static int[] findZValues(String line) {
		int[] zValues = new int[line.length()];
		int zFrameStart = 0;
		int zFrameEnd = 0;
		if (line.length()>1) {
			zValues[0] = 0; 
			for (int i = 1; i < line.length(); i++) {
				if (i<zFrameEnd) {
					zValues[i] = zValues[i-zFrameStart];
					if (zValues[i]+i >= zFrameEnd) {
						while (zFrameEnd<line.length() && line.charAt(zFrameEnd) == line.charAt(zValues[i])) {
							zFrameEnd++;
							zValues[i]++;
						}
						zFrameStart = i;
					}
				} else {
					zFrameEnd = i;
					while (zFrameEnd<line.length() && line.charAt(zFrameEnd) == line.charAt(zValues[i])) {
						zFrameEnd++;
						zValues[i]++;
					}
					zFrameStart = i;
					
				}
				if (zValues[i]>zValues.length-i)
					zValues[i]=zValues.length-i;
			}
		}
		return zValues;
	}
	
	public static void main(String[] args) {
		File input = new File("input"); //use for submission
		//File input = new File("C:\\Users\\kgj\\eclipse-workspace-school\\CMSC423\\src\\SPValuesP3A\\TestInput");
		try {
			findSPValues(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
