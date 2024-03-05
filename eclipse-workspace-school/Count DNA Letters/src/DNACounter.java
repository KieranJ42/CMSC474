import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class DNACounter {
	public static void readLetters(File input) throws IOException {
		Scanner scan = new Scanner(input);
		String line = "";
		int[] count = new int[4];
		String letter = "";
		while (scan.hasNextLine()) {
			line = scan.nextLine();
			for (int i = 0; i < line.length(); i++) {
				letter = line.substring(i,i+1);
				if (letter.equals("A")) {
					count[0]++;
				} else if (letter.equals("C")) {
					count[1]++;
				} else if (letter.equals("G")) {
					count[2]++;
				} else if (letter.equals("T")) {
					count[3]++;
				} else {
					System.out.println("invalid char");
				}
			}
		}
		FileWriter writer = new FileWriter("output");
		writer.write(count[0] + " " + count[1] + " "
				 + count[2] + " " + count[3]);
		writer.close();
		//System.out.println(count[0] + " " + count[1] + " "
		//		 + count[2] + " " + count[3]);
	}
	
	public DNACounter() {
		File input = new File("input");
		try {
			readLetters(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		File input = new File("input");
		try {
			readLetters(input);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
