import java.util.*;

// A node of a graph for the Spring 2018 ICS 340 program

public class Node {

	String name;
	String val; // The value of the Node
	String abbrev; // The abbreviation for the Node

	// These variables are used in DFS search
	String color;
	int discoveryTime;
	int finishTime;
	Node pred;
	ArrayList<Edge> outgoingEdges;
	ArrayList<Edge> incomingEdges;
	int subtree; // used in DFS to detect cross edges
	
//	since nodes are used in edges, this lets us reference courses from edges with a pointer variable
	Course course;


	public Node(String theAbbrev) {
		setAbbrev(theAbbrev);
		val = null;
		name = null;
		pred = null;
		color = "white";
		outgoingEdges = new ArrayList<Edge>();
		incomingEdges = new ArrayList<Edge>();
	}
	public Node(String theAbbrev, String theName) {
		setAbbrev(theAbbrev);
		val = null;
		setName(theName);
		pred = null;
		color = "white";
		outgoingEdges = new ArrayList<Edge>();
		incomingEdges = new ArrayList<Edge>();
	}

	public int getDiscoveryTime() {
		return discoveryTime;
	}

	public void setDiscoveryTime(int discoveryTime) {
		this.discoveryTime = discoveryTime;
	}

	public int getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(int finishTime) {
		this.finishTime = finishTime;
	}

	public Node getPred() {
		return pred;
	}

	public void setPred(Node pred) {
		this.pred = pred;
	}

	public String getAbbrev() {
		return abbrev;
	}

	public String getName() {
		return name;
	}

	public String getVal() {
		return val;
	}

	public String getColor() {
		return color;
	}

	public int getSubtree() {
		return subtree;
	}

	public ArrayList<Edge> getOutgoingEdges() {
		return outgoingEdges;
	}

	public ArrayList<Edge> getIncomingEdges() {
		return incomingEdges;
	}

	public void setAbbrev(String theAbbrev) {
		abbrev = theAbbrev;
	}

	public void setName(String theName) {
		name = theName;
	}

	public void setVal(String theVal) {
		val = theVal;
	}

	public void setColor(String theColor) {
		color = theColor;
	}

	public void addOutgoingEdge(Edge e) {
		outgoingEdges.add(e);
	}

	public void addIncomingEdge(Edge e) {
		incomingEdges.add(e);
	}

	public void setSubtree(int st) {
		subtree = st;
	}
	
	public Course getCourse() {
		return course;
	}
	public void setCourse(Course course) {
		this.course = course;
	}
	

}
