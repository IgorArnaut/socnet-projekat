package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.List;

import edu.uci.ics.jung.algorithms.shortestpath.DistanceStatistics;
import edu.uci.ics.jung.graph.Graph;
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

	public void analyze(List<Graph<Vertex, Edge>> cores) {
		for (int x = 0; x < cores.size(); x++) {
			Graph<Vertex, Edge> giantComponent = component.getGiantComponent(cores.get(x));
			double y = getDiameter(giantComponent);
			results.put(x, y);
		}
	}

	@Override
	public void report(String folder) {
		String file = "giant-component-diameters.csv";
		String header = "Core;Giant component diameter";
		exporter.setData(results);
		exporter.exportToCSV(folder, file, header);
	}

}
