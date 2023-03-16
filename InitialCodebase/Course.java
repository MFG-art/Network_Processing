import java.util.List;

public class Course extends Node {

	String[] classDays;
	int semester;
	List<Edge> relationships;
	int conflicts;

	public Course(String theAbbrev, String theName) {
		super(theAbbrev, theName);
		// TODO Auto-generated constructor stub
	}

	public List<Edge> getRelationships() {
		return relationships;
	}

	public void setRelationships(List<Edge> relationships) {
		this.relationships = relationships;
	}

	public String[] getClassDays() {
		return classDays;
	}

	public void setClassDays(String[] classDays) {
		this.classDays = classDays;
	}

	public int getSemester() {
		return semester;
	}

	public void setSemester(int semester) {
		this.semester = semester;
	}

	public int getConflicts() {
		return conflicts;
	}

	public void setConflicts(int conflicts) {
		this.conflicts = conflicts;
	}
	public void incrementConflicts() {
		this.conflicts++;
	}

}
