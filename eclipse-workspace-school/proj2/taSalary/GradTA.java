package taSalary;


/*
 * Has a yearly salary and can only grade a certain amount of projects
 */
public class GradTA extends TA {
	
	private double yearlySalary;
	
	//constructor
	public GradTA(String firstName, String lastName, double yearlySalary) {
		super(firstName, lastName);
		this.yearlySalary = yearlySalary;
	}
	
	
	/*
	 * returns paycheck amount
	 */
	@Override public double calculatePaycheckAmount() {
		return yearlySalary/21;
	}
	
	
	/*
	 * grades projects as long as projectsGraded won't exceed 150
	 */
	@Override public boolean gradeProjects(int numProjects) {
		if (getProjectsGraded() + numProjects > 150) 
			return false;
		else
			addProjectsGraded(numProjects);
		return true;
	}
	
}
