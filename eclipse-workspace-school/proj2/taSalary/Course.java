package taSalary;


/*
 * A Course Object stores a list of TA's for the course and processes or
 * directs processes to the proper spot
 */
public class Course {

	private TA[] TAList;
	private String department;
	private int courseNumber;
	private boolean differential;

	
	//Constructor
	public Course(String department, int courseNumber, int TAMaxNum, 
			boolean differential) {
		this.TAList = TAList;
		this.department = department;
		this.courseNumber = courseNumber;
		this.differential = differential;
		TAList = new TA[TAMaxNum];
	}


	/*
	 * returns the course name of a course
	 */
	public String getCourseName() {
		return department + " " + courseNumber;
	}

	
	/*
	 * Tries to add a UGTA to TAList
	 */
	public boolean addUGTA(String firstName, String lastName,
			double hourlySalary) {
		//if name is invalid: return false
		if (!validName(firstName, lastName)) 
			return false;
		/*
		 * this must be separate from the top because findTAIndex requires
		 * validName() to be called and will affect the proper adding 
		 * requirements if they are put together
		 */
		if (!(hourlySalary > 0) || findTAIndex(firstName, lastName) != -1) { 
			return false;
		}
		
		//If TAList is full return false unless it's a differential course
		if (TAList.length == numTAs()) {
			if (differential) {
				addTACapacity(1);
			}
			else
				return false;
		}
		TAList[numTAs()] = new UGTA(firstName, lastName, hourlySalary);
		return true;
	}

	
	/*
	 * tries to add a GradTA to TAList
	 */
	public boolean addGradTA(String firstName, String lastName,
			double yearlySalary) {
		//if name is invalid: return false
		if (!validName(firstName, lastName)) 
			return false;
		/*
		 * this must be separate from the top because findTAIndex requires
		 * validName() to be called and will affect the proper adding 
		 * requirements if they are put together
		 */
		if (!(yearlySalary > 0) || findTAIndex(firstName, lastName) != -1) { 
			return false;
		}
		
		//If TAList is full return false unless it's a differential course
		if (TAList.length == numTAs()) {
			if (differential)
				addTACapacity(1);
			else
				return false;
		}
		TAList[numTAs()] = new GradTA(firstName, lastName, yearlySalary);
		return true;	
	}

	
	/*
	 * returns the number of TA's that are initialized in TAList, not length
	 */
	public int numTAs() {
		int i = 0;  
		while (i < TAList.length && TAList[i] != null) {
			i++;
		}
		return i;
	}

	
	/*
	 * returns the length or capacity of TA's for a class
	 */
	public int courseTACapacity() {
		return TAList.length;  
	}
	
	
	/*
	 * adds a number of TA's to a course
	 */
	public boolean addTACapacity(int numTAsToAdd) {
		if (numTAsToAdd < 1)
			return false;
		int x;
		TA[] temp;
		
		while (numTAsToAdd!=0) {
			/*
			 * only adds 10 add a time in accordance with Part 4 of the
			 * assignment: Array sizes 
			 */
			if(numTAsToAdd>10) {
				x = 10;
				numTAsToAdd -= 10;
			} else {
				x = numTAsToAdd;
				numTAsToAdd = 0;
			}
			temp = new TA[TAList.length + x];
			
			//puts data from original array into new array
			for (int i = 0; i < numTAs(); i++) {
				temp[i] = TAList[i];
			}
			TAList = temp;
		}
		return true;
	}


	/*
	 * returns a sorted list of the names of the TA's in a course
	 */
	public String[] getTANames() {
		if (numTAs() == 0)
			return null;
		String[] names = new String[numTAs()];
		String[] firstNames = new String[numTAs()];
		String[] lastNames = new String[numTAs()];
		
		//populates the above variables
		for (int i = 0; i < names.length; i++) {
			names[i] = TAList[i].getFirstName() + " " + TAList[i].getLastName();
			firstNames[i] = TAList[i].getFirstName();
			lastNames[i] = TAList[i].getLastName();
		}
		String temp;
		
		/*
		 * checks to see if the last names are the same
		 * 
		 * if they aren't: sorts using the last names and then properly
		 * switches the indices in each array
		 * 
		 * if they are: it goes to first names where the same thing happens:
		 * switches two indices if necessary
		 */
		for (int i = 0; i < names.length-1; i++) {
			for (int j = 0; j < names.length-1-i; j++) {
				if (lastNames[j].compareTo(lastNames[j+1]) > 0) {
					names = switchArrayIndex(names, j, j+1);
					firstNames = switchArrayIndex(firstNames, j, j+1);
					lastNames = switchArrayIndex(lastNames, j, j+1);
				} else {
					if (lastNames[j].compareTo(lastNames[j+1]) == 0) 
						if (firstNames[j].compareTo(firstNames[j+1]) > 0) {
							names = switchArrayIndex(names, j, j+1);
							firstNames = switchArrayIndex(firstNames, j, j+1);
							lastNames = switchArrayIndex(lastNames, j, j+1);
						}
				}
			}
		}
		return names;
	}

	
	/*
	 * Switches the positions of two objects in an array (only Strings)
	 * helps with above method
	 */
	public String[] switchArrayIndex(String[] arr, int i, int j) {
		String temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
		return arr;
	}

	
	/*
	 * If findTAIndex() isn't -1 and it's a valid name:
	 * calls the holdOfficeHours method on a TA 
	 */
	public boolean holdOfficeHours(String firstName, String lastName,
			int numHours) {
		int i = findTAIndex(firstName, lastName);
		boolean j = validName(firstName, lastName);
		if (i == -1 || !j)
			return false;
		return TAList[i].holdOfficeHours(numHours);
	}

	
	/*
	 * If findTAIndex() isn't -1 and it's a valid name:
	 * returns the number of office hours held by a TA 
	 */
	public int numOfficeHours(String firstName, String lastName) {
		int i = findTAIndex(firstName, lastName);
		boolean j = validName(firstName, lastName);
		if (i == -1 || !j)
			return -1;
		return TAList[i].getNumOfficeHours();
	}

	
	/*
	 * If findTAIndex() isn't -1 and it's a valid name:
	 * calls the gradeProjects method on a TA 
	 */
	public boolean gradeProjects(String firstName, String lastName,
			int numProjects) {
		int i = findTAIndex(firstName, lastName);
		boolean j = validName(firstName, lastName);
		if (i == -1 || !j)
			return false;  
		return TAList[i].gradeProjects(numProjects);
	}


	/*
	 * If findTAIndex() isn't -1 and it's a valid name:
	 * returns the number of projects graded by a TA 
	 */
	public int numProjectsGraded(String firstName, String lastName) {
		int i = findTAIndex(firstName, lastName);
		boolean j = validName(firstName, lastName);
		if (i == -1 || !j)
			return -1;
		return TAList[i].getProjectsGraded();
	}

	
	/*
	 * If findTAIndex() isn't -1 and it's a valid name:
	 * returns the result from the TA's paycheckAmount calculation 
	 */
	public double getPaycheckAmount(String firstName, String lastName) {
		int i = findTAIndex(firstName, lastName);
		boolean j = validName(firstName, lastName);
		if (i == -1 || !j)
			return -1;
		return TAList[i].calculatePaycheckAmount();
	}

	
	/*
	 * returns the index of a string
	 * returns -1 if its invalid or it doesn't exist
	 */
	public int findTAIndex(String firstName, String lastName) {
		if (!validName(firstName, lastName)) 
			return -1;
		//loops through TAList to try to find a TA
		for (int i = 0; i < numTAs(); i++) {
			if (firstName.equals(TAList[i].getFirstName()) && 
					lastName.equals(TAList[i].getLastName()))
				return i;
		}
		return -1; 
	}
	
	/*
	 * the above and below methods need separation (while validName() is in
	 * findTAIndex) to make sure adding TA's to a course work properly
	 */
	
	
	/*
	 * Confirms that a name is valid: not a blank string or null
	 */
	public boolean validName(String firstName, String lastName) {
		if (firstName == null || lastName == null || firstName.equals("") ||
				lastName.equals(""))
			return false;
		return true;
	}

}
