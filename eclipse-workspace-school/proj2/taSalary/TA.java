package taSalary;


/*
 * superclass for the two different types of TA's
 */
public class TA {
	
	private String firstName;
	private String lastName;
	private int projectsGraded;
	private int numOfficeHours;
	
	
	//constructor
	public TA(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	
	/*
	 * returns first name
	 */
	public String getFirstName() {
		return firstName;
	}
	
	
	/*
	 * returns last name
	 */
	public String getLastName() {
		return lastName;
	}
	
	
	/*
	 * returns projects graded
	 */
	public int getProjectsGraded() {
		return projectsGraded;
	}
	
	
	/*
	 * adds projects graded to the local field
	 */
	public void addProjectsGraded(int toAdd) {
		projectsGraded += toAdd;
	}
	
	
	/*
	 * returns office hours held
	 */
	public int getNumOfficeHours() {
		return numOfficeHours;
	}
	
	
	/*
	 * adds office hours to the local field
	 */
	public void addOfficeHours(int toAdd) {
		numOfficeHours += toAdd;
	}
	
	
	/*
	 * Should never actually run
	 * Therefore if -1 shows up I know there is an immediate issue
	 */
	public double calculatePaycheckAmount() {
		return -1.0;
	}
	
	/*
	 * Should never actually run
	 * Therefore returns false by default 
	 */
	public boolean gradeProjects(int numProjects) {
		//do nothing
		return false;
	}
	
	/*
	 * this is the method I want for a GradTA and because they can hold
	 * infinte office hours we don't need to change it for that subclass
	 */
	public boolean holdOfficeHours(int officeHours) {
		addOfficeHours(officeHours);
		return true;
	}

}
