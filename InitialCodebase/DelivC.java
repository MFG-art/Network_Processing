import java.io.File;
import java.io.PrintWriter;

import java.util.Collections;
import java.util.Stack;
//import java.util.List;

// Class DelivC does the work for deliverable DelivC of the Prog340

public class DelivC {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;

	// DFS variables
	int time;
	Stack<Node> nodesStack = new Stack<Node>();
	Boolean inverted = false; // is the graph inverted?
	int subtree; // used to find subtrees in DFS for cross edges
	int SCCCount;
	String SCCString;

	public DelivC(File in, Graph gr) {
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

		/**
		 * TO DO: 1. The discovery and finish times of each node in the depth first
		 * search of the original graph. 2. The classification of each edge in the
		 * original graph. 3. The discovery and finish times of each node in the depth
		 * first search of the complementary graph. 4. Each strongly connected component
		 * of the original graph.
		 */

		// program DFS algorithm
		for (Node n : gr.getNodeList()) {
			Collections.sort(n.getOutgoingEdges(), new EdgeComparator());
		}
		dfs(gr);

		System.out.println("DFS of graph:\nNode\tDisc\tFinish");
		output.println("DFS of graph:\nNode\tDisc\tFinish");
		
		Collections.sort(gr.getNodeList(), new DiscoveryTimeComparator());
		for (Node node : gr.getNodeList()) {
			System.out.println(node.getAbbrev() + "\t" + node.getDiscoveryTime() + "\t" + node.getFinishTime());
			output.println(node.getAbbrev() + "\t" + node.getDiscoveryTime() + "\t" + node.getFinishTime());
		}

		labelEdges(gr);


		System.out.println("\nEdge Classification:");
		output.println("\nEdge Classification:");
		
		for (Edge edge : gr.getEdgeList()) {
			System.out.println(edge.toString() + "\t" + edge.getEdgeType());
			output.println(edge.toString() + "\t" + edge.getEdgeType());
		}

		invert_graph(gr);

		for (Node n : gr.getNodeList()) {
			Collections.sort(n.getOutgoingEdges(), new EdgeComparator());
		}

		dfs(gr);

		System.out.println("\nDFS of complementary graph:\nNode\tDisc\tFinish");
		output.println("\nDFS of complementary graph:\nNode\tDisc\tFinish");
		
		Collections.sort(gr.getNodeList(), new DiscoveryTimeComparator());
		for (Node node : gr.getNodeList()) {
			System.out.println(node.getAbbrev() + "\t" + node.getDiscoveryTime() + "\t" + node.getFinishTime());
			output.println(node.getAbbrev() + "\t" + node.getDiscoveryTime() + "\t" + node.getFinishTime());
		}
		
		System.out.println("\nStrongly Connected Components: " + SCCCount);
		System.out.println(SCCString);
		
		output.println("\nStrongly Connected Components: " + SCCCount);
		output.println(SCCString);
		output.flush();

	}

	public void dfs(Graph gr) {
		time = 0;
		subtree = 1;

		// reset all DFS variables
		for (Edge edge : gr.getEdgeList()) {
			edge.setEdgeType("");
		}
		for (Node node : gr.getNodeList()) {
			node.setColor("white");
			node.setPred(null);
			node.setDiscoveryTime(0);
			node.setFinishTime(0);
		}

		// find start node
		if (!inverted) {
			for (Node n : gr.getNodeList()) {
				if (n.getVal().equals("S")) {
					dfs_visit(gr, n);
				}
			}
		} else {
			SCCCount = 0;
			SCCString = "";
			dfs_visit(gr, nodesStack.pop());
		}
	}

	public void dfs_visit(Graph gr, Node n) {
		SCCString = SCCString + n.getAbbrev();
		time++;
		n.setDiscoveryTime(time);
		n.setColor("gray");

		// the node has unexplored edges
		for (Edge edge : n.getOutgoingEdges()) {
			if (edge.getHead().getColor().equals("white")) {
				edge.setEdgeType("Tree");
				edge.getHead().setPred(n);
				edge.getHead().setSubtree(subtree);
				dfs_visit(gr, edge.getHead());
			}

		}

		// the current node has no outgoing edges, so backtrack
		time++;
		n.setFinishTime(time);
		n.setColor("black");
		n.setSubtree(subtree);
		if (!inverted) {
			nodesStack.push(n);
		}

	

		// if there doesn't exists a predecessor
		if (n.getPred() == null) {

			n.setFinishTime(time);
			n.setColor("black");
			subtree++; // create a new subtree
			if (inverted) {
//				System.out.println("SCC string: " + SCCString);
				SCCString = SCCString + "\n";
				SCCCount++;
			}

			// find unvisited node earliest in alphabetical order
			if (!inverted) {
				for (Node node : gr.getNodeList()) {
					if (node.getColor().equals("white")) {
						dfs_visit(gr, node);
					}
				}
			} else {
				while(!nodesStack.isEmpty()) {
					if (nodesStack.peek().getDiscoveryTime() > 0) {
						nodesStack.pop();
					} else {
						dfs_visit(gr, nodesStack.pop());
					}
				}
			}
		}



	}

	public void invert_graph(Graph gr) {

		for (Node node : gr.getNodeList()) {
			node.getIncomingEdges().clear();
			node.getOutgoingEdges().clear();
		}

		for (Edge edge : gr.getEdgeList()) {
			Node temp = edge.getHead();
			edge.setHead(edge.getTail());
			edge.setTail(temp);
			edge.getHead().addIncomingEdge(edge);
			edge.getTail().addOutgoingEdge(edge);
		}
		
		inverted = true;

	}

	public void labelEdges(Graph gr) {
		for (Edge edge : gr.getEdgeList()) {
			if (edge.getEdgeType().equals("")) {
				// if the head and tail are from different subtrees it is a cross edge
				if (edge.getHead().getSubtree() != edge.getTail().getSubtree()) {
					edge.setEdgeType("Cross");
				} else {
					if (edge.getHead().getDiscoveryTime() < edge.getTail().getDiscoveryTime()) {
						edge.setEdgeType("Back");
					} else {
						edge.setEdgeType("Forward");
					}
				}
			}
		}

	}
}
