package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.GiantComponent;

public class PercentageAnalyzer extends MacroscopicAnalyzer {

	private GiantComponent component = new GiantComponent();

	private double getPercentage(Graph<Vertex, Edge> graph) {
		if (graph == null)
			return 0.0;

		int n = graph.getVertexCount();
		int l = graph.getEdgeCount();

		if (l == 0)
			return 0;

		return (100.0 * n) / l;
	}

	@Override
	public void analyze(List<Graph<Vertex, Edge>> cores) {
		for (int x = 0; x < cores.size(); x++) {
			Graph<Vertex, Edge> giantComponent = component.getGiantComponent(cores.get(x));
			double y = getPercentage(giantComponent);
			results.put(x, y);
		}
	}

	@Override
	public void report(String folder) {
		String file = "giant-component-percentages.csv";
		String header = "Core;Giant component percentage";
		exporter.setData(results);
		exporter.exportToCSV(folder, file, header);
	}

}
