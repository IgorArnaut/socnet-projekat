package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.algorithms.shortestpath.UnweightedShortestPath;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.GiantComponent;

public class SmallWorldAnalyzer extends MacroscopicAnalyzer {

	private GiantComponent component = new GiantComponent();

	private double getSmallWorldCoefficient(Graph<Vertex, Edge> graph) {
		if (graph == null)
			return 0.0;

		UnweightedShortestPath<Vertex, Edge> usp = new UnweightedShortestPath<>(graph);
		List<Vertex> vertices = new ArrayList<>();
		graph.getVertices().forEach(v -> vertices.add(v));
		double distanceSum = 0.0;

		for (int j = 0; j < vertices.size() - 1; j++) {
			for (int k = j + 1; k < vertices.size(); k++)
				distanceSum += usp.getDistance(vertices.get(j), vertices.get(k)).doubleValue();
		}

		int n = vertices.size();

		if (n - 1 == 0)
			return 0.0;

		return (1.0 * distanceSum) / (n * (n - 1));
	}

	@Override
	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		Graph<Vertex, Edge> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			Graph<Vertex, Edge> giantComponent = component.getGiantComponent(graph);
			double y = getSmallWorldCoefficient(giantComponent);
			results.put(x, y);
			x++;
		} while (core.getVertexCount() > 0);
	}
	
	@Override
	public void report(String folder) {
		String file = "giant-component-small-world-coefficients.csv";
		String header = "Core;Giant component small world coefficient";
		exportToCSV(folder, file, header);
	}

}
