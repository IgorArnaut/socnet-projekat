package rs.ac.uns.pmf.decomposers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Link;
import rs.ac.uns.pmf.graph.Node;

public class BatageljZaversnikDecomposer extends Decomposer {

	private Graph<Node, Link> graph;
	private List<Node> nodes;

	private int[] degrees;
	private int maxDegree;
	private Map<Integer, List<Node>> nodesPerDegree;

	private void populateNodes() {
		Predicate<Node> predicate = node -> graph.degree(node) > 0;
		this.nodes = graph.getVertices().stream().filter(predicate).toList();
	}

	private void populateDegrees() {
		ToIntFunction<Node> function = node -> graph.degree(node);
		this.degrees = nodes.stream().mapToInt(function).toArray();
	}

	private void getMaxDegree() {
		this.maxDegree = Arrays.stream(degrees).max().getAsInt();
	}

	private void populateNodesPerDegree() {
		this.nodesPerDegree = new HashMap<Integer, List<Node>>();

		for (int i = 0; i < maxDegree; i++)
			nodesPerDegree.put(i, new ArrayList<Node>());
		
		for (int i = 0; i < nodes.size(); i++) {
			Node node = nodes.get(i);
			int degree = degrees[i];
			nodesPerDegree.get(degree).add(node);
		}
	}

	@Override
	public Map<Node, Integer> decompose(Graph<Node, Link> graph) {
		this.graph = graph;
		populateNodes();
		populateDegrees();
		getMaxDegree();
		populateNodesPerDegree();
	
		shellIndices = new HashMap<Node, Integer>();
	
		for (int i = 1; i <= maxDegree; i++) {
			if (nodesPerDegree.containsKey(i)) {
				List<Node> nodesOfDegree = nodesPerDegree.get(i);
	
				while (!nodesOfDegree.isEmpty()) {
					int randomIndex = (int) (Math.random() * nodesOfDegree.size());
					Node node = nodesOfDegree.remove(randomIndex);

					shellIndices.put(node, i);

					Iterator<Node> neighbors = graph.getNeighbors(node).iterator();
					
					while (neighbors.hasNext()) {
						Node neighbor = neighbors.next();
						int neighborIndex = nodes.indexOf(neighbor);
						int neighborDegree = degrees[neighborIndex];
				
						if (neighborDegree > i) {
							nodesPerDegree.get(neighborDegree).remove(neighbor);
				
							if (nodesPerDegree.containsKey(neighborDegree - 1))
								nodesPerDegree.put(neighborDegree - 1, new ArrayList<Node>());
				
							System.out.println(neighborDegree - 1);
							nodesPerDegree.get(neighborDegree - 1).add(neighbor);
							degrees[neighborIndex] -= 1;
						}
					}
				}
			}
		}

		sortShellIndices();
		return shellIndices;
	}

}
