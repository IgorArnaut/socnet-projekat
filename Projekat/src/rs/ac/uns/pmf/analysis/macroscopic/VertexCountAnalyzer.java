package rs.ac.uns.pmf.analysis.macroscopic;

import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class VertexCountAnalyzer extends MacroscopicAnalyzer {

	@Override
	public void analyze(List<Graph<Vertex, Edge>> cores) {
		for (int x = 0; x < cores.size(); x++) {
			int y = cores.get(x).getVertexCount();
			results.put(x, 1.0 * y);
		}
	}

	@Override
	public void report(String folder) {
		String file = "vertex-counts.csv";
		String header = "Core;Vertex count";
		exporter.setData(results);
		exporter.exportToCSV(folder, file, header);
	}

}
