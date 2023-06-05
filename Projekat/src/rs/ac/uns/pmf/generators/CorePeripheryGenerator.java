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

	private void insertEdge(Edge edge, Vertex source, Vertex target, double probability) {
		if (RANDOM.nextDouble() <= probability)
			graph.addEdge(edge, source, target);
	}

	private void insertCommunities(double probability) {
		for (int i = 0; i < COMMUNITY_COUNT; i++) {
			List<Vertex> vertices = communities.get(i);

			for (int j = 0; j < vertices.size() - 1; j++) {
				for (int k = j + 1; k < vertices.size(); k++) {
					Vertex source = vertices.get(j);
					Vertex target = vertices.get(k);
					
					String sourceId = "" + source;
					String targetId = "" + target;
					
					Edge edge = new Edge(sourceId, targetId);
					insertEdge(edge, source, target, probability);
				}
			}
		}
	}

	private void linkCommunities(double probability) {
		List<Vertex> sources = communities.get(0);
		List<Vertex> targets = communities.get(1);

		sources.forEach(source -> {
			targets.forEach(target -> {				
				String sourceId = "" + source;
				String targetId = "" + target;
				
				Edge edge = new Edge(sourceId, targetId);
				insertEdge(edge, source, target, probability);
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
