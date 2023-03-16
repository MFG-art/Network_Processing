import java.io.*;
// Class DelivA does the work for deliverable DelivA of the Prog340

public class DelivA {

	File inputFile;
	File outputFile;
	PrintWriter output;
	Graph g;

	public DelivA(File in, Graph gr) {
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

		// number of nodes
		System.out.println("There are " + gr.getNodeList().size() + " nodes in the graph.\n");

		// number of edges
		System.out.println("There are " + gr.getEdgeList().size() + " edges in the graph.\n");

		// strings used for output
		String outgoingEdgesOutput;
		String incomingEdgesOutput;

		// finding all outgoing edges and printing result to console
		for (Node node : gr.getNodeList()) {
			outgoingEdgesOutput = "There are outgoing edges from Node " + node.getAbbrev() + " to nodes ";

			for (Edge edge : node.getOutgoingEdges()) {
				outgoingEdgesOutput = outgoingEdgesOutput + edge.getHead().getAbbrev() + ", ";
			}

			System.out.println(outgoingEdgesOutput.substring(0, outgoingEdgesOutput.length() - 2) + ".");

		}

		System.out.println("");

		// finding all imcoming edges and printing result to console
		for (Node node : gr.getNodeList()) {
			incomingEdgesOutput = "There are incoming edges from Node " + node.getAbbrev() + " from nodes ";

			for (Edge edge : node.getIncomingEdges()) {
				incomingEdgesOutput = incomingEdgesOutput + edge.getTail().getAbbrev() + ", ";
			}

			System.out.println(incomingEdgesOutput.substring(0, incomingEdgesOutput.length() - 2) + ".");
		}

		int count = 0;
		int sum = 0;

		// find mean edge length
		for (Edge edge : gr.getEdgeList()) {
			sum += edge.getDist();
			count++;
		}

		System.out.println("\nAll edges are integers. The mean edge length (to the nearest integer) is "
				+ ((int)(sum / count)) + ".");

	}

}
