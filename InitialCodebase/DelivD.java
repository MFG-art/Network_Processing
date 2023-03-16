import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Class DelivD does the work for deliverable DelivD of the Prog340

public class DelivD {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;

	static int conflicts;
	boolean hasConflicts = true;

//	tabu list to keep track of old courses
//	pointer keeps track of where to write in tabu list
	static String[] tabu = new String[10000];
	static int tabuPointer = 0;

	String[][] courseDaySem = { { "A", "A", "A", "A", "A", "A", "A", "A", "A" },
			{ "WS", "MT", "~", "HF", "MS", "M", "MH", "TF", "M" }, { "H", "H", "~", "H", "H", "~", "H", "N", "~" },
			{ "M", "TH", "~", "W", "H", "~", "H", "MT", "~" }, { "MHF", "MHFS", "W", "MF", "HFS", "W", "HF", "M", "W" },
			{ "H", "~", "~", "H", "~", "~", "H", "H", "~" },
			{ "MTWH", "MTW", "TH", "MTWH", "MT", "W", "TWH", "MT", "TW" },
			{ "MTWH", "MHF", "TW", "MTWH", "MH", "T", "TWF", "MWH", "WH" },
			{ "TH", "MW", "H", "T", "M", "H", "W", "M", "H" },
			{ "MF", "HS", "TW", "MH", "TF", "TW", "MWH", "MT", "TW" },
			{ "MWH", "MWH", "MW", "MTH", "MTW", "TH", "MTH", "MWH", "TH" },
			{ "MT", "MWH", "H", "MH", "HS", "H", "WH", "WH", "H" }, { "H", "~", "~", "H", "M", "~", "T", "T", "~" },
			{ "MW", "TW", "M", "TW", "TH", "W", "MT", "MT", "T" }, { "H", "TH", "T", "MT", "WH", "T", "WH", "MH", "W" },
			{ "~", "M", "~", "~", "H", "~", "~", "W", "~" }, { "H", "MH", "M", "M", "WH", "W", "M", "MW", "H" },
			{ "TW", "T", "H", "TH", "TH", "H", "TH", "TH", "T" }, { "M", "MW", "~", "H", "H", "~", "M", "H", "~" },
			{ "MTWH", "WHS", "MW", "TWH", "TWH", "TH", "MTW", "TWH", "TW" },
			{ "W", "~", "~", "W", "~", "~", "W", "~", "~" }, { "TF", "WH", "W", "TF", "TF", "W", "MT", "TW", "M" },
			{ "MT", "TH", "T", "TW", "WH", "T", "MW", "WH", "T" }, { "H", "M", "T", "M", "M", "H", "M", "M", "H" },
			{ "MTWH", "MTW", "MH", "MTWH", "MWH", "WH", "MTW", "MWH", "MT" },
			{ "MTWH", "MTWH", "~", "MTWH", "MTWH", "~", "MTWH", "MTWH", "~" },
			{ "F", "M", "~", "F", "M", "~", "F", "M", "~" }, { "H", "W", "~", "~", "H", "~", "~", "M", "~" },
			{ "T", "H", "~", "T", "WT", "~", "MH", "W", "~" }, { "A", "A", "A", "A", "A", "A", "A", "A", "A" },
			{ "A", "A", "A", "A", "A", "A", "A", "A", "A" }, { "A", "A", "A", "A", "A", "A", "A", "A", "A" } };

	List<Course> cList = new ArrayList<Course>();

	public DelivD(File in, Graph gr) {
		inputFile = in;
		g = gr;

		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring(0, inputFileName.length() - 4); // Strip off ".txt"
		String outputFileName = baseFileName.concat("_out.txt");
		outputFile = new File(outputFileName);
		if (outputFile.exists()) { // For retests
			outputFile.delete();
		}

		try {
			output = new PrintWriter(outputFile);
		} catch (Exception x) {
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		System.out.println("DelivD:  To be implemented");


		/*
		 * THINGS TO DO: [DONE] Add a dummy course [DONE] Generate a random schedule
		 * [DONE] RELATIONSHIPS ARE EDGE VALUES. How to count the number of conflicts?
		 * [DONE] Hard-code in date conflicts Generate new schedule from old schedule
		 */

//		Creating a dummy course
		Course dummy = new Course("D", "Dummy");
		dummy.setRelationships(new ArrayList<Edge>());
		dummy.setClassDays(courseDaySem[31]);
		cList.add(dummy);

//		This populates cList and adds conflict info to courses
//		This code only needs to run once at the start. Once cList is populated it can be reused.
		for (int i = 0; i < gr.getNodeList().size(); i++) {
			Course newCourse = new Course(gr.getNodeList().get(i).getAbbrev(), gr.getNodeList().get(i).getName());
			newCourse.setClassDays(courseDaySem[i]);
			newCourse.setRelationships(gr.getNodeList().get(i).getOutgoingEdges());
			cList.add(newCourse);
			gr.getNodeList().get(i).setCourse(newCourse);
		}

		// MAIN LOOP STARTS HERE
		randomSchedule(cList);
		clearConflicts(cList);
		countConflicts(cList);
		countClassDayConflicts(cList);

		int i = 0;
		while(hasConflicts) {
			// if we have seen the same course before
			// don't want to add same course to tabu list twice
			if (checkIfInTabu(cList)) {
				// random walk
				randomWalk(cList);

			} else { // this is a course not in tabu list
				addToTabu(cList);
				ibi(cList);

//			TEST FOR CONFLICTS
				clearConflicts(cList);
				countConflicts(cList);
				countClassDayConflicts(cList);

				System.out.println("The schedule has " + conflicts + " conflicts" + "  i = " + i);

				if (conflicts == 0) {
					hasConflicts = false;
				}
				i++;
				if (i > 100000) {
					randomSchedule(cList);
					i = 0;
				}
			}
		}
		printSchedule(cList, output);

	} // DelivD function

	// Takes a course list and randomizes it
	private void randomSchedule(List<Course> cList) {
		// uncomment the setSeed(…) line to get a non-random starting assignment.
		// different seeds will give different assignments.
		Random r = new Random();
//		r.setSeed(10);

		// Swaps two random courses 32 times.
		for (int course = 0; course < 32; course++) {
			int pos = r.nextInt(32 - course);
			Course courseClass = cList.get(course);
			cList.set(course, cList.get(pos));
			cList.set(pos, courseClass);
		}

		// Semester is determined by random order
//		for (Course course : cList) {
//			int semester = Math.ceilDiv(cList.indexOf(course) + 1, 4);
//			course.setSemester(semester);
//		}
		
		for (int i = 0; i<cList.size(); i = i+4) {
			cList.get(i).setSemester(i/4 + 1);
			cList.get(i+1).setSemester(i/4 + 1);
			cList.get(i+2).setSemester(i/4 + 1);
			cList.get(i+3).setSemester(i/4 + 1);
		}

	}

	// Takes a course list and prints its contents
//	private void printSchedule(List<Course> cList) {
//		for (Course course : cList) {
//			System.out.println(course.getName() + "\t" + course.getAbbrev() + "\t" + course.getSemester());
//		}
//		System.out.println("\n");
//	}

	// calculates the total number of prerequisite conflicts
	private void clearConflicts(List<Course> cList) {
		for (Course course : cList) {
			course.setConflicts(0);
		}
		conflicts = 0;
	}

	private void countConflicts(List<Course> cList) {

		for (Course course : cList) {

			for (Edge edge : course.getRelationships()) {

				if (edge.getRelation().equals(">")) {

					// if the tail course is not an explicit prerequisite for the head course
					if (!(edge.getTail().getCourse().getSemester() > edge.getHead().getCourse().getSemester())) {

						// adds one to the conflict of each course
						edge.getHead().getCourse().incrementConflicts();
						edge.getTail().getCourse().incrementConflicts();
						conflicts = conflicts + 2; // total course conflicts + 2
					}
				} else if (edge.getRelation().equals(">=")) {

					// if the tail course is not a prerequisite for the head course
					if (!(edge.getTail().getCourse().getSemester() >= edge.getHead().getCourse().getSemester())) {

						// adds one to the conflict of each course
						edge.getHead().getCourse().incrementConflicts();
						edge.getTail().getCourse().incrementConflicts();
						conflicts = conflicts + 2; // total course conflicts + 2
					}
				}
			}
		}

	}

	private void countClassDayConflicts(List<Course> cList) {
		// for each semester
		Course course1, course2, course3, course4;
		String course1days, course2days, course3days, course4days;

		for (int i = 0; i < cList.size(); i = i + 4) {
			// select courses four at a time
			course1 = cList.get(i);
			course2 = cList.get(i + 1);
			course3 = cList.get(i + 2);
			course4 = cList.get(i + 3);
			Course[] semesterCourses = { course1, course2, course3, course4 };

			// if the course is not offered that semester that counts as an error
			for (Course course : semesterCourses) {
				if (course.getClassDays()[course1.getSemester() - 1].equals("~")) {
					course.incrementConflicts();
				}
			}
			
			
			// get course days for selected semester
			course1days = course1.getClassDays()[course1.getSemester() - 1];
			course2days = course2.getClassDays()[course2.getSemester() - 1];
			course3days = course3.getClassDays()[course3.getSemester() - 1];
			course4days = course4.getClassDays()[course4.getSemester() - 1];
			String[] semesterCourseDays = { course1days, course2days, course3days, course4days };

			String[] days = { "M", "T", "W", "H", "F", "S" };
			Course[] hasDay = { null, null, null, null, null, null };

//			j is used to iterate over class days, k iterates over the 4 courses
			for (int j = 0; j < 6; j++) {
				for (int k = 0; k < 4; k++) {
					// does the course contain that day?
					if (semesterCourseDays[k].contentEquals(days[j])) {
						if (hasDay[j] == null) {
							hasDay[j] = semesterCourses[k];
						} else {
							hasDay[j].incrementConflicts();
							semesterCourses[k].incrementConflicts();
							conflicts = conflicts + 2;
						}

					}

				}

			}
		}


	}

	private void addToTabu(List<Course> cList) {
		String courseString = null;
		for (int i = 0; i < 32; i++) {
			courseString = courseString + cList.get(i).getAbbrev() + ".";
		}
		tabu[tabuPointer] = courseString;
		tabuPointer++;
		tabuPointer = tabuPointer % 9999;

	}

	private boolean checkIfInTabu(List<Course> cList) {
		boolean isInTabu = false;
		String courseString = null;
		for (int i = 0; i < 32; i++) {
			courseString = courseString + cList.get(i).getAbbrev() + ".";
		}
		for (int i = 0; i < 10000; i++) {
			if ((tabu[i] != null) && (tabu[i].equals(courseString))) {
				isInTabu = true;
			}
		}
		return isInTabu;
	}

	private void randomWalk(List<Course> cList) {
		Random r = new Random();
		int val1 = 0, val2 = 0;

		// this ensures we swap two separate courses
		while (val1 == val2) {
			val1 = r.nextInt(32);
			val2 = r.nextInt(32);
		}

//		this swaps the two courses
		Course temp = cList.get(val1);
		cList.set(val1, cList.get(val2));
		cList.set(val2, temp);
		
		assignSemesters(cList);
	}

	private void ibi(List<Course> cList) {
		// find course with most conflicts
		Course mostConflicts = cList.get(0);
		for (Course course : cList) {
			if (course.getConflicts() > mostConflicts.getConflicts()) {
				mostConflicts = course;
			}
		}

		int lowestConflicts = conflicts; 
		int bestSemester = mostConflicts.getSemester();
//		for all other semesters, which one gets rid of the most conflicts?
		for (int i = 1; i < 9; i++) {
			
			int courseIndex = cList.indexOf(mostConflicts);
			Course temp = cList.get(i*4-1);
			cList.set(i*4-1, mostConflicts);
			cList.set(courseIndex,temp);
			assignSemesters(cList);
			
			clearConflicts(cList);
			countConflicts(cList);
			countClassDayConflicts(cList);
			
			
			// if new schedule reduces conflicts
			if (conflicts < lowestConflicts) {
				lowestConflicts = conflicts;
				bestSemester = i;
			}
			
		}
		int courseIndex = cList.indexOf(mostConflicts);
		Course temp = cList.get(bestSemester*4-1);
		cList.set(bestSemester*4-1, mostConflicts);
		cList.set(courseIndex,temp);
		assignSemesters(cList);
	}

	// given a course, how many conflicts does it have?
	
	private void printSchedule(List<Course> cList, PrintWriter output) {
		System.out.println("\n\n\n\n");
		String[] semesters = {"Fa-20", "Sp-21", "Su-21", "Fa-21", "Sp-22", "Su-22", "Fa-22", "Sp-23"};
		for (int i = 0; i < cList.size(); i = i+4) {
			String outputString = semesters[i/4] + ": ";
			for (int j = 0; j<4;j++) {
				if (! cList.get(i+j).getName().equals("Dummy")) {
					outputString = outputString + cList.get(i+j).getName() + "  ";
				}
			}
		
			System.out.println(outputString);
			output.println(outputString);
			output.flush();

		}
		
	}
	
	private void assignSemesters(List<Course> cList) {
		for (int i = 0; i<cList.size(); i = i+4) {
			cList.get(i).setSemester(i/4 + 1);
			cList.get(i+1).setSemester(i/4 + 1);
			cList.get(i+2).setSemester(i/4 + 1);
			cList.get(i+3).setSemester(i/4 + 1);
		}
	}

} // DelivD Class
