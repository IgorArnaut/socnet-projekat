package rs.ac.uns.pmf.generators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class CorePeripheryGenerator extends Generator {

	private final int COMMUNITY_COUNT = 2;
	private Map<Integer, List<Vertex>> communities;

	private int n;
	private double p;
	private double q;

	public CorePeripheryGenerator(int n, double p, double q) {
		this.n = n;
		this.p = p;
		this.q = q;
	}

	private void populateCommunities() {
		this.communities = new HashMap<>();

		for (int i = 0; i < COMMUNITY_COUNT; i++)
			communities.put(i, new ArrayList<>());

		for (int i = 0; i < n; i++) {
			if ((int) (Math.random() * 2) == 1)
				communities.get(0).add(new Vertex(String.format("%03d", i)));
			else
				communities.get(1).add(new Vertex(String.format("%03d", i)));
		}
	}

	private void insertCommunities() {
		for (int i = 0; i < COMMUNITY_COUNT; i++) {
			List<Vertex> vertices = communities.get(i);

			for (int j = 0; j < vertices.size() - 1; j++) {
				for (int k = j + 1; k < vertices.size(); k++) {
					Vertex source = vertices.get(j);
					Vertex target = vertices.get(k);
					Edge edge = new Edge(Integer.toString(id));
					id++;
					insertEdge(edge, source, target, p);
				}
			}
		}
	}

	private void linkCommunities() {
		List<Vertex> sources = communities.get(0);
		List<Vertex> targets = communities.get(1);

		sources.forEach(source -> {
			targets.forEach(target -> {
				Edge edge = new Edge(Integer.toString(id));
				id++;
				insertEdge(edge, source, target, q);
			});
		});
	}

	@Override
	public Graph<Vertex, Edge> generate() {
		this.graph = new UndirectedSparseGraph<>();
		populateCommunities();
		insertCommunities();
		linkCommunities();
		return graph;
	}

}
