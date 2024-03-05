package socialNetwork;

import java.util.Collection;
import java.util.HashSet;
import java.util.Scanner;
import java.io.*;

public class TikFace{

	private GraphDir<String> tikFace;
	private String lock1 = "lock1";
	private String lock2 = "lock2";

	public TikFace() {
		tikFace = new GraphDir<String>();
	}

	public boolean addUser(String userName) {
		if (userName == null || userName.equals("") ||
				tikFace.vertexCheck(userName))
			return false;
		synchronized(lock1) {
			tikFace.vertexAdd(userName);
		}
		return true;
	}

	public Collection<String> getAllUsers() {
		return tikFace.getVertices();
	}

	public boolean addFriends(String userName1, String userName2) {
		if (userName1 == null || userName2 == null ||
				userName1.equals("") || userName2.equals("") || 
				userName1.length() == 0 || userName2.length() == 0 || 
				userName1.equals(userName2) || 
				tikFace.edgeGet(userName1, userName2) != -1)
			return false;
		synchronized(lock2) {
			tikFace.edgeAdd(userName1, userName2, 0);
			tikFace.edgeAdd(userName2, userName1, 0);
		}
		return true;
	}

	public Collection<String> getFriends(String userName) {
		if (userName == null || userName.equals(""))
			return null;
		
		Collection<String> retVal;

		if (userName.equals("") || userName == null)
			retVal = null;
		else if (!tikFace.vertexCheck(userName))
			retVal = new HashSet<String>();
		else 
			retVal = tikFace.vertexNeighbors(userName);
		return retVal;
	}

	public boolean unfriend(String userName1, String userName2) {
		if (userName1 == null || userName2 == null || 
				!tikFace.vertexCheck(userName1) || 
				!tikFace.vertexCheck(userName2) || 
				tikFace.edgeGet(userName1, userName2) == -1)
			return false;
		tikFace.edgeRemove(userName1, userName2);
		tikFace.edgeRemove(userName2, userName1);
		return true;
	}

	public Collection<String> peopleWhoMayWantToKnowYou(String userName) {
		if (userName == null ||	userName.equals(""))
			return null;
		Collection<String> retVal = new HashSet<String>();
		for (String str1 : getFriends(userName))
			retVal.addAll(getFriends(str1));

		retVal.removeAll(getFriends(userName));
		retVal.remove(userName);
		return retVal;
	}

	public boolean readUserData(Collection<String> filenames) {
		if (filenames == null)
			return false;
		Thread[] threads = new Thread[filenames.size()];
		int counter = 0;
		Scanner scan;
		for (String str : filenames) {
			try {
				scan = new Scanner(new FileReader(new File(str)));
				threads[counter] = new Thread(new MyThread(scan));
				counter++;
			} catch (FileNotFoundException fnfe) {

			}
		}

		for (Thread thread : threads)
			thread.start();

		for (Thread thread : threads)
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return true;
	}

	class MyThread implements Runnable {

		private Scanner scan;

		public MyThread(Scanner scanner) {
			scan = scanner;
		}

		public void run() {
			String[] s;
			while (scan.hasNext()) {
				s = scan.nextLine().split("\\s+");
				if (s.length == 2) 
					addUser(s[1]);
				else 
					addFriends(s[1], s[2]);
			}
		}

	}


}
