package rs.ac.uns.pmf.main;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.GraphIOException;
import rs.ac.uns.pmf.generators.CorePeripheryGenerator;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;
import rs.ac.uns.pmf.io.GraphWriter;

public class Main {

	private static final int nodeCount = 100;
	private static final double[] probabilities = { 1.0, 0.01, 0.0001 };
	// private static final double probability = 0.01;

	private static final CorePeripheryGenerator generator = new CorePeripheryGenerator();
	// private static final Decomposer decomposerS = new StraightforwardDecomposer();
	// private static final Decomposer decomposerBZ = new
	// BatageljZaversnikDecomposer();

	public static void main(String[] args) throws GraphIOException {
		Graph<Node, Link> graph = generator.generate(nodeCount, probabilities);
		GraphWriter.writeGraphml(graph, "src/graph.graphml");

		// Map<Node, Integer> shellIndicesS = decomposerS.decompose(graph);
		// Map<Node, Integer> shellIndicesBZ = decomposerBZ.decompose(graph);

		// System.out.println(shellIndicesS);
		// System.out.println(shellIndicesBZ);
	}
}