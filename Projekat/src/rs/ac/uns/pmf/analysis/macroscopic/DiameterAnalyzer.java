package rs.ac.uns.pmf.analysis.macroscopic;

import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.GiantComponent;

public class DiameterAnalyzer extends MacroscopicAnalyzer {

	private GiantComponent component = new GiantComponent();

	private double getDiameter(Graph<Vertex, Edge> graph) {
		if (graph == null)
			return 0.0;

		return DistanceStatistics.diameter(graph);
	}

	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		Graph<Vertex, Edge> core = null;
		int x = 0;

		do {
			core = decomposer.getKCore(graph, x);
			Graph<Vertex, Edge> giantComponent = component.getGiantComponent(graph);
			double y = getDiameter(giantComponent);
			results.put(x, y);
			x++;
		} while (core.getVertexCount() > 0);
	}
	
	@Override
	public void report(String folder) {
		String file = "giant-component-diameters.csv";
		String header = "Core;Giant component diameter";
		exportToCSV(folder, file, header);
	}

}
