package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.List;
import java.util.Set;

import edu.uci.ics.jung.algorithms.cluster.WeakComponentClusterer;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class ComponentCountAnalyzer extends MacroscopicAnalyzer {

	private int getComponentCount(Graph<Vertex, Edge> graph) {
		Set<Set<Vertex>> clusters = new WeakComponentClusterer<Vertex, Edge>().apply(graph);
		return clusters.size();
	}

	@Override
	public void analyze(List<Graph<Vertex, Edge>> cores) {
		for (int x = 0; x < cores.size(); x++) {
			int y = getComponentCount(cores.get(x));
			results.put(x, 1.0 * y);
		}
	}

	@Override
	public void report(String folder) {
		String file = "component-counts.csv";
		String header = "Core;Component count";
		exporter.setData(results);
		exporter.exportToCSV(folder, file, header);
	}

}
