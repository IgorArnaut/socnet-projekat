package rs.ac.uns.pmf.main;

import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.GraphIOException;
// import rs.ac.uns.pmf.decomposers.BatageljZaversnikDecomposer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.decomposers.StraightforwardDecomposer;
import rs.ac.uns.pmf.generators.BarabasiAlbertGenerator;
import rs.ac.uns.pmf.generators.Generator;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class Main {

	private static final int nodeCount = 100;
	private static final double probability = 0.01;

	private static final Generator generator = new BarabasiAlbertGenerator();
	private static final Decomposer decomposerS = new StraightforwardDecomposer();
	// private static final Decomposer decomposerBZ = new
	// BatageljZaversnikDecomposer();

	public static void main(String[] args) throws GraphIOException {
		Graph<Node, Link> graph = generator.generate(nodeCount, probability);

		Map<Node, Integer> shellIndicesS = decomposerS.decompose(graph);
		// Map<Node, Integer> shellIndicesBZ = decomposerBZ.decompose(graph);

		System.out.println(shellIndicesS);
		// System.out.println(shellIndicesBZ);
	}
}