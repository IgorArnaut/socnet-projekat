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

	private void insertCore() {
		List<Vertex> core = communities.get(0);

		for (int i = 0; i < core.size() - 1; i++) {
			for (int j = i + 1; j < core.size(); j++) {
				Vertex source = core.get(i);
				Vertex target = core.get(j);
				Edge edge = new Edge(Integer.toString(id));
				id++;
				insertEdge(edge, source, target, p);
			}
		}
	}

	private void insertPeriphery() {
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
		insertCore();
		insertPeriphery();
		return graph;
	}

}
