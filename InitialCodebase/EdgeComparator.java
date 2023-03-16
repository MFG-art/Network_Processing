import java.util.Comparator;

public class EdgeComparator implements Comparator<Edge> {
        // override the compare() method
        public int compare(Edge e1, Edge e2)
        {
        	// the integer returned is either 1, 0 or -1. Just basically compares the two double values and decides which is bigger
        	int compareValue = Integer.compare(e1.getDist(), e2.getDist());
        	
        	// if the two paths have the same distance, compare the nodes alphabetically by name
        	if (compareValue == 0) {
        		int stringCompare = e1.getHead().getAbbrev().compareTo(e2.getHead().getAbbrev());
        		
        		// if first node's abbreviation is alphabetically first, it compares as 'lesser's
        		if (stringCompare < 0) {
        			compareValue = -1;
        		} else {
        			// if first nodes's abbreviation is alphabetically last, it compares as 'greater'
        			compareValue = 1;
        		}
        		
        	}
        	
            return compareValue;
        }
    }
