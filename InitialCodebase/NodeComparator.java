import java.util.Comparator;

public class NodeComparator implements Comparator<Node> {
	// override the compare() method
	public int compare(Node n1, Node n2) {
		// the int returned is either 1, 0 or -1. Just basically compares the two double
		// values and decides which is bigger
		return Double.compare(Double.parseDouble(n1.getVal()), Double.parseDouble(n2.getVal()));
	}
}
