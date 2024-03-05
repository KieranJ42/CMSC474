package tests;

import taSalary.*;
import org.junit.*;
import static org.junit.Assert.*;

public class StudentTests {
	
	
	/*
	 * Confirms that the adding method works properly with invalid names
	 * 
	 * This helped me fix the methods findTAIndex() and validName() which
	 * were previously broken
	 */
	@Test public void studentTest1() {
		Course cmsc132 = UMCP.createRegularCourse("CMSC", 132, 10);
		
		assertFalse(cmsc132.addGradTA("", "Johnson", 10000));
		assertFalse(cmsc132.addGradTA(null, "Johnson", 10000));
		assertFalse(cmsc132.addGradTA("Bill", "", 10000));
		assertFalse(cmsc132.addGradTA("Bill", null, 10000));
		assertFalse(cmsc132.addUGTA("", "Johnson", 10000));
		assertFalse(cmsc132.addUGTA(null, "Johnson", 10000));
		assertFalse(cmsc132.addUGTA("Bill", "", 10000));
		assertFalse(cmsc132.addUGTA("Bill", null, 10000));
	}
	
	
	/*
	 * Checks other methods to help further confirm findTAIndex() and 
	 * validName() work properly
	 */
	@Test public void studentTest2() {
		Course cmsc250 = UMCP.createRegularCourse("CMSC", 250, 10);

		assertEquals(-1,(int)cmsc250.getPaycheckAmount("Antonio", "Antelope"));
		assertEquals(-1,cmsc250.numProjectsGraded("Quinn", "Quokka"));
		assertEquals(-1,cmsc250.numOfficeHours("Quinn", "Quokka"));
	}
	
	
	/*
	 * Makes sure that office hours and grading projects work properly
	 * when max hours worked are near/fully filled
	 */
	@Test public void studentTest3() {
		Course cmsc250 = UMCP.createRegularCourse("CMSC", 250, 10);
		
		cmsc250.addUGTA("John", "Paul", 16.0);
		cmsc250.addUGTA("Quinn", "James", 20.0);
		
		assertTrue(cmsc250.holdOfficeHours("John", "Paul", 19));
		assertTrue(cmsc250.holdOfficeHours("Quinn", "James", 19));
		assertTrue(cmsc250.gradeProjects("John", "Paul", 1));
		assertTrue(cmsc250.gradeProjects("Quinn", "James", 1));
		assertFalse(cmsc250.holdOfficeHours("Quinn", "James", 1));
		assertTrue(cmsc250.gradeProjects("John", "Paul", 1));
		
		assertEquals(390,(int)cmsc250.getPaycheckAmount("Quinn", "James"));
		assertEquals(320,(int)cmsc250.getPaycheckAmount("John", "Paul"));
	}
	
	
	/*
	 * confirms that numOfficeHours() and numProjectsGraded() work if names
	 * are invalid
	 */
	@Test public void studentTest4() {
		Course cmsc250 = UMCP.createRegularCourse("CMSC", 250, 10);
		
		assertEquals(-1,cmsc250.numOfficeHours(null, null));
		assertEquals(-1,cmsc250.numProjectsGraded(null, null));
	}

}
