package tests;

import org.junit.*;

import socialNetwork.TikFace;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

public class StudentTests {

	@Test public void testPublic1() {
		TikFace tikFace = new TikFace();
		String[] tikFacers = {"Connor", "Catherine", "Christian", "", 
		"Camille"};
		for (String str : tikFacers) {
			tikFace.addUser(str);
		}

		Set<String> expectedNames = new HashSet<String>();
		expectedNames.add("Connor");
		expectedNames.add("Catherine");
		expectedNames.add("Christian");
		expectedNames.add("Camille");

		assertTrue(tikFace.getAllUsers().containsAll(expectedNames));
		assertTrue(expectedNames.containsAll(tikFace.getAllUsers()));
	}

	
	
	@Test public void testPublic2() {
		TikFace tikFace = new TikFace();
		String[] tikFacers = {"Connor", "Catherine", "Christian", "Calvin", 
		"Camille"};
		for (String str : tikFacers) {
			tikFace.addUser(str);
		}
		
		for (String str : tikFacers) {
			tikFace.addFriends("Cindy", str);
		}
		
		Set<String> expectedNames = new HashSet<String>();
		expectedNames.add("Connor");
		expectedNames.add("Catherine");
		expectedNames.add("Christian");
		expectedNames.add("Camille");
		expectedNames.add("Calvin");
		
		assertTrue(tikFace.getFriends("Cindy").containsAll(expectedNames));
		assertTrue(expectedNames.containsAll(tikFace.getFriends("Cindy")));
		
		expectedNames.add("Cindy");
		
		assertTrue(tikFace.getAllUsers().containsAll(expectedNames));
		assertTrue(expectedNames.containsAll(tikFace.getAllUsers()));
		
		tikFace.unfriend("Cindy", "Connor");
		tikFace.unfriend("Cindy", "Cindy");
		expectedNames.remove("Connor");
		expectedNames.remove("Cindy");
		
		assertTrue(tikFace.getFriends("Cindy").containsAll(expectedNames));
		assertTrue(expectedNames.containsAll(tikFace.getFriends("Cindy")));
	}
	
	
	
	@Test public void testPublic3() {
		TikFace tikFace = new TikFace();
		String[] tikFacers = {"Connor", "Catherine", "Christian", "Calvin", 
		"Camille"};
		for (String str : tikFacers) {
			tikFace.addUser(str);
		}
		
		for (String str : tikFacers) {
			tikFace.addFriends("Cindy", str);
		}
		
		String[] tikFacers2 = {"Chad", "Thad", "Brad", "Grad", "Jad"};
		
		boolean switcher = true;
		for (String str : tikFacers2) {
			if (switcher)
				tikFace.addFriends("Connor", str);
			else
				tikFace.addFriends("Catherine", str);
		}
		
		Set<String> expectedNames = new HashSet<String>();
		for (String str : tikFacers2) 
			expectedNames.add(str);
		
		assertTrue(tikFace.peopleWhoMayWantToKnowYou("Cindy").
				containsAll(expectedNames));
		assertTrue(expectedNames.containsAll(tikFace.
				peopleWhoMayWantToKnowYou("Cindy")));
	}

}
