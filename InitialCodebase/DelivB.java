import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;


// Class DelivB does the work for deliverable DelivB of the Prog340

public class DelivB {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;
	int[][] b;
	int[][]p;
	Node[][] pred;
	ArrayList<Node> sortedNodeList;

	public DelivB( File in, Graph gr ) {
		inputFile = in;
		g = gr;
		
		// Get output file name.
		String inputFileName = inputFile.toString();
		String baseFileName = inputFileName.substring( 0, inputFileName.length()-4 ); // Strip off ".txt"
		String outputFileName = baseFileName.concat( "_out.txt" );
		outputFile = new File( outputFileName );
		if ( outputFile.exists() ) {    // For retests
			outputFile.delete();
		}
		
		try {
			output = new PrintWriter(outputFile);			
		}
		catch (Exception x ) { 
			System.err.format("Exception: %s%n", x);
			System.exit(0);
		}
		
		// Sort nodes by latitude:
		Collections.sort(gr.getNodeList(),new NodeComparator());
		sortedNodeList = gr.getNodeList();
		
		// print sorted node list to check if sorting worked
		
		int n = sortedNodeList.size();
		b = new int[n][n];
		p = new int[n][n];
		pred = new Node[n][n];
		 
		// calculate P matrix
		for (int i = 0; i<n;i++) {
			for (int j = 1;j<n;j++) {
				Node node = sortedNodeList.get(j);
				int edges= sortedNodeList.get(i).getOutgoingEdges().size();
				
				for (int k = 0; k < edges; k++) {
					Edge edgeOne = sortedNodeList.get(i).getOutgoingEdges().get(k);
					if (node.equals(edgeOne.getHead())) {
						p[i][j] = edgeOne.getDist();
					}
					
				}
			}
		}
		
//		System.out.println(Arrays.deepToString(p));
		
		for(int j = 0; j<n; j++) {
			for(int i = 0; i<j; i++) {
				CalculateBMatrix(i,j);
				System.out.println("i: "+ i + ",j: " + j);
				System.out.println(Arrays.deepToString(b));
			}

		}
		b[n-1][n-1] = b[n-2][n-1] + p[n-2][n-1];
		System.out.println("i: n-1, j: n-1");
		System.out.println(Arrays.deepToString(b));
		
		System.out.println("The shortest bitonic tour has distance " + b[n-1][n-1]);
		output.println("The shortest bitonic tour has distance " + b[n-1][n-1]);
		output.flush();
		

		
		// calculate pred matrix
		
		// work backwards to find shortest bitonic path
		
	}
	
	// calculate B matrix
	public void CalculateBMatrix(int i, int j) {
		// base case
		if ((i==0) && (j==1)) {
			b[0][1] = p[0][1];
		// i does not directly precede
		} else if(i < j-1){
			b[i][j] = b[i][j-1] + p[j-1][j];
		// i directly precedes
		} else {
			b[i][j] = Integer.MAX_VALUE;
			for (int k = 0; k<j-1; k++) {
				if (b[k][j-1] + p[k][j] < b[i][j]) {
					b[i][j] = b[k][j-1] + p[k][j];
				}		
			}	
	}
	
	}
}
