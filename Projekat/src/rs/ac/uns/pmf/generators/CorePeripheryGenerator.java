package rs.ac.uns.pmf.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.graph.util.Pair;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class CorePeripheryGenerator {

	private final Random RANDOM = new Random();

	private final int COMMUNITY_COUNT = 2;
	private Map<Integer, List<Vertex>> communities;

	private Graph<Vertex, Edge> graph;

	private void populateCommunities(int vertexCount) {
		this.communities = new HashMap<>();

		for (int i = 0; i < COMMUNITY_COUNT; i++)
			communities.put(i, new ArrayList<>());

		for (int i = 0; i < vertexCount; i++) {
			if ((int) (Math.random() * 2) == 1)
				communities.get(0).add(new Vertex(String.format("%03d", i)));
			else
				communities.get(1).add(new Vertex(String.format("%03d", i)));
		}
	}

	private void insertEdge(double p, Edge edge, Pair<Vertex> pair) {
		if (RANDOM.nextDouble() <= p)
			graph.addEdge(edge, pair);
	}

	private void insertCommunities(double probability) {
		for (int i = 0; i < COMMUNITY_COUNT; i++) {
			List<Vertex> vertices = communities.get(i);

			for (int j = 0; j < vertices.size() - 1; j++) {
				for (int k = j + 1; k < vertices.size(); k++) {
					Edge edge = new Edge();
					Vertex first = vertices.get(j);
					Vertex second = vertices.get(k);
					Pair<Vertex> pair = new Pair<>(first, second);
					insertEdge(probability, edge, pair);
				}
			}
		}
	}

	private void linkCommunities(double probability) {
		List<Vertex> xs = communities.get(0);
		List<Vertex> ys = communities.get(1);

		xs.forEach(x -> {
			ys.forEach(y -> {
				Edge edge = new Edge();
				Pair<Vertex> pair = new Pair<>(x, y);
				insertEdge(probability, edge, pair);
			});
		});
	}

	public Graph<Vertex, Edge> generate(int vertexCount, double p, double q) {
		this.graph = new UndirectedSparseGraph<>();
		populateCommunities(vertexCount);
		insertCommunities(p);
		linkCommunities(q);
		return graph;
	}

}
