package taSalary;


/*
 * This is what is used to create different types of courses
 */
public class UMCP {

	//creates a course that doesn't allow infinite TA's without specific 
	//instruction
  public static Course createRegularCourse(String department, int courseNumber,
                                           int TAMaxNum) {
    if (department == null || courseNumber < 100 || TAMaxNum < 1) 
    	return null;
    return new Course(department, courseNumber, TAMaxNum, false);
  }

  //creates a course that allows infinite TA's to be added
  public static Course createDifferentialCourse(String department,
                                                int courseNumber,
                                                int TAMaxNum) {
	 if (department == null || courseNumber < 100 || TAMaxNum < 1) 
		 return null;
	 return new Course(department, courseNumber, TAMaxNum, true);
  }

}
