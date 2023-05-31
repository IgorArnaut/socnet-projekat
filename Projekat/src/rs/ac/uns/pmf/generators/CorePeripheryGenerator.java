package rs.ac.uns.pmf.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class CorePeripheryGenerator {

	private final Random RANDOM = new Random();

	private final int COMMUNITY_COUNT = 2;
	private Map<Integer, List<Vertex>> communities;

	private Graph<Vertex, Edge> graph;

	// 1.
	private void insertToCommunity(int i, double z) {
		if (RANDOM.nextDouble() <= z)
			communities.get(0).add(new Vertex(String.format("%03d", i)));
		else
			communities.get(1).add(new Vertex(String.format("%03d", i)));
	}

	// 2.
	private void populateCommunities(int vertexCount, double r) {
		this.communities = new HashMap<>();

		for (int i = 0; i < COMMUNITY_COUNT; i++)
			communities.put(i, new ArrayList<>());

		for (int i = 0; i < vertexCount; i++)
			insertToCommunity(i, r);
	}

	// 3.
	private void insertEdge(double probability, Edge edge, Vertex first, Vertex second) {
		if (RANDOM.nextDouble() <= probability)
			graph.addEdge(edge, first, second);
	}

	// 4.
	private void insertCommunities(double p) {
		for (int i = 0; i < COMMUNITY_COUNT; i++) {
			List<Vertex> vertices = communities.get(i);

			for (int j = 0; j < vertices.size() - 1; j++) {
				for (int k = j + 1; k < vertices.size(); k++) {
					Edge edge = new Edge();
					Vertex first = vertices.get(j);
					Vertex second = vertices.get(k);
					// 3.
					insertEdge(p, edge, first, second);
				}
			}
		}
	}

	// 5.
	private void linkCommunities(double q) {
		List<Vertex> vertices0 = communities.get(0);
		List<Vertex> vertices1 = communities.get(1);

		for (int i = 0; i < vertices0.size(); i++) {
			for (int j = 0; j < vertices1.size(); j++) {
				Edge edge = new Edge();
				Vertex first = vertices0.get(i);
				Vertex second = vertices1.get(j);
				// 3.
				insertEdge(q, edge, first, second);
			}
		}
	}

	public Graph<Vertex, Edge> generate(int vertexCount, double p, double q, double r) {
		this.graph = new UndirectedSparseGraph<>();
		// 2.
		populateCommunities(vertexCount, r);
		// 4.
		insertCommunities(p);
		// 5.
		linkCommunities(q);
		return graph;
	}

}
