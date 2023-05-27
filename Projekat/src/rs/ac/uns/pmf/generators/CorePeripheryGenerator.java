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

	private final double z = 0.6;
	private final int COMMUNITY_COUNT = 2;
	private Map<Integer, List<Node>> communities;

	private Graph<Node, Link> graph;

	public Graph<Node, Link> generate(int nodeCount, double p, double q) {
		this.graph = new UndirectedSparseGraph<Node, Link>();
		populateCommunities(nodeCount);
		insertCommunities(p);
		linkCommunities(q);
		return graph;
	}

	private void linkCommunities(double q) {
		List<Node> nodes0 = communities.get(0);
		List<Node> nodes1 = communities.get(1);
		
		for (int i = 0; i < nodes0.size(); i++) {
			for (int j = 0; j < nodes1.size(); j++) {
				Link link = new Link(String.format("%03d%s%03d", i, LINE, j));
				
				if (RANDOM.nextDouble() <= q) {
					Node first = nodes0.get(i);
					Node second = nodes1.get(j);
					graph.addEdge(link, first, second);
				}
			}
		}
	}

	private void insertCommunities(double p) {
		for (int i = 0; i < COMMUNITY_COUNT; i++) {
			List<Node> nodes = communities.get(i);
			
			for (int j = 0; j < nodes.size() - 1; j++) {
				for (int k = j + 1; k < nodes.size(); k++) {
					Link link = new Link(String.format("%03d%s%03d", j, LINE, k));
					
					if (RANDOM.nextDouble() <= p) {
						Node first = nodes.get(j);
						Node second = nodes.get(k);
						graph.addEdge(link, first, second);
					}
				}
			}
		}
	}

	private void populateCommunities(int nodeCount) {
		this.communities = new HashMap<Integer, List<Node>>();
		
		for (int i = 0; i < COMMUNITY_COUNT; i++)
			communities.put(i, new ArrayList<Node>());
			
		for (int i = 0; i < nodeCount; i++) {
			if (RANDOM.nextDouble() <= z)
				communities.get(0).add(new Node(String.format("%03d", i)));
			else
				communities.get(1).add(new Node(String.format("%03d", i)));
		}
	}

}
