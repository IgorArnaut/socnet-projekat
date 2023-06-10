package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.GiantComponent;

public class VertexPercentageAnalyzer extends MacroscopicAnalyzer {

	private GiantComponent component = new GiantComponent();

	private double getPercentage(Graph<Vertex, Edge> graph, Graph<Vertex, Edge> component) {
		if (component == null)
			return 0.0;

		double n = graph.getVertexCount();
		double ng = component.getVertexCount();
		return (100.0 * ng) / n;
	}

	@Override
	public void analyze(List<Graph<Vertex, Edge>> cores) {
		for (int x = 0; x < cores.size(); x++) {
			Graph<Vertex, Edge> core = cores.get(x);
			Graph<Vertex, Edge> giantComponent = component.getGiantComponent(core);
			double y = getPercentage(core, giantComponent);
			results.put(x, y);
		}
	}

	@Override
	public void report(String folder) {
		String file = "giant-component-vertex-percentages.csv";
		String header = "Core;Giant component vertex percentage";
		exporter.setData(results);
		exporter.exportToCSV(folder, file, header);
	}

}
