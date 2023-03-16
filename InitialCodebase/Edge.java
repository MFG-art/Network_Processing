//import java.util.*;

// Edge between two nodes
public class Edge {

	int dist;
	String relation;
	Node tail;
	Node head;
	

	// Used in DFS search
	String edgeType;

	public Edge(Node tailNode, Node headNode, int dist) {
		setDist(dist);
		setTail(tailNode);
		setHead(headNode);
	}
	
	public Edge(Node tailNode, Node headNode, String r) {
		setRelation(r);
		setTail(tailNode);
		setHead(headNode);
	}

	public Node getTail() {
		return tail;
	}

	public Node getHead() {
		return head;
	}

	public int getDist() {
		return dist;
	}
	
	public String getRelation() {
		return relation;
	}

	public String getEdgeType() {
		return edgeType;
	}

	public void setTail(Node n) {
		tail = n;
	}

	public void setHead(Node n) {
		head = n;
	}

	public void setDist(int i) {
		dist = i;
	}
	
	public void setRelation(String r) {
		relation = r;
	}

	public void setEdgeType(String t) {
		edgeType = t;
	}
	
	public String toString() {
		return this.tail.getAbbrev()+ "->" + this.head.getAbbrev();
	}

}
