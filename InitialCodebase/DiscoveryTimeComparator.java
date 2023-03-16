import java.util.Comparator;

public class DiscoveryTimeComparator implements Comparator<Node> {
        // override the compare() method
        public int compare(Node n1, Node n2)
        {
        	// the integer returned is either 1, 0 or -1. Just basically compares the two double values and decides which is bigger
        	int compareValue = Integer.compare(n1.getDiscoveryTime(),n2.getDiscoveryTime());
        	
            return compareValue;
        }
    }