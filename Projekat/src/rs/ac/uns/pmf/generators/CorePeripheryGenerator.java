package rs.ac.uns.pmf.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class CorePeripheryGenerator {

	private final String LINE = "--";
	private final Random RANDOM = new Random();

	private final int CORE_COUNT = 2;
	private double[] probabilities;
	private Map<Integer, List<Node>> cores;

	private Graph<Node, Link> graph;

	public Graph<Node, Link> generate(int nodeCount, double[] probabilities) {
		this.probabilities = probabilities;
		populateCores(nodeCount);

		this.graph = new UndirectedSparseGraph<Node, Link>();

		insertLinks(0, 0);
		linkCores(0, 1);
		insertLinks(1, 2);
		return graph;
	}

	private void linkCores(int c, int p) {
		List<Node> nodes0 = cores.get(c);
		List<Node> nodes1 = cores.get(c + 1);
		
		for (int i = 0; i < nodes0.size(); i++) {
			for (int j = 0; j < nodes1.size(); j++) {
				Link link = new Link(String.format("%03d%s%03d", i, LINE, j));
				Node first = nodes0.get(i);
				Node second = nodes1.get(j);
				
				if (RANDOM.nextDouble() <= probabilities[p])
					graph.addEdge(link, first, second);
			}
		}
	}

	private void insertLinks(int c, int p) {
		List<Node> nodes = cores.get(c);

		for (int i = 0; i < nodes.size() - 1; i++) {
			for (int j = i + 1; j < nodes.size(); j++) {
				Link link = new Link(String.format("%03d%s%03d", i, LINE, j));
				Node first = nodes.get(i);
				Node second = nodes.get(j);

				if (RANDOM.nextDouble() <= probabilities[p])
					graph.addEdge(link, first, second);
			}
		}
	}

	private void populateCores(int nodeCount) {
		this.cores = new HashMap<Integer, List<Node>>();

		for (int i = 0; i < CORE_COUNT; i++)
			cores.put(i, new ArrayList<Node>());

		for (int i = 0; i < nodeCount; i++) {
			Node node = new Node(String.format("%03d", i));
			int core = (int) (Math.random() * (CORE_COUNT - 1));
			cores.get(core).add(node);
		}
	}

}
