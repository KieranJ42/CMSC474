package taSalary;


/*
 * Has an hourly salary and can only work a certain number of hours
 */
public class UGTA extends TA {

	private double hourlySalary;
	
	
	//constructor
	public UGTA(String firstName, String lastName, double hourlySalary) {
		super(firstName, lastName);
		this.hourlySalary = hourlySalary;
	}
	
	
	/*
	 * calculates paycheck amount with given formula
	 */
	@Override public double calculatePaycheckAmount() {
		return hourlySalary * (getNumOfficeHours() + 
				(double)getProjectsGraded()/2);
	}
	
	
	/*
	 * grades projects if it won't make hours worked exceed 20
	 */
	@Override public boolean gradeProjects(int numProjects) {
		if (getProjectsGraded() + numProjects > 40-2*getNumOfficeHours()) 
			return false;
		else
			addProjectsGraded(numProjects);
		return true;
	}
	
	
	/*
	 * holds office hours if it won't make hours worked exceed 20
	 */
	@Override public boolean holdOfficeHours(int officeHours) {
		/*
		 * if statement adds one to confirm that rounding happens properly
		 * i.e. can't add an office hour if TA is at 19.5 hours, but due to
		 * truncation it will be 19 and be as if it can add another hour
		 */
		if (getNumOfficeHours() + officeHours > 20-(getProjectsGraded()+1)/2)
			return false;
		else
			addOfficeHours(officeHours);
		return true;
	}
	
}
