import java.util.Comparator;

public class CourseComparator implements Comparator<Course>{
	// override the compare() method
	public int compare(Course c1, Course c2) {
		// the integer returned is either 1, 0 or -1. Just basically compares the two double
		// values and decides which is bigger
		return Integer.compare(c1.getSemester(), c2.getSemester());
	}

}
