package rs.ac.uns.pmf.analysis;

import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.exporters.BetweennessExporter;
import rs.ac.uns.pmf.analysis.exporters.ClosenessExporter;
import rs.ac.uns.pmf.analysis.exporters.DegreeExporter;
import rs.ac.uns.pmf.analysis.exporters.EigenvectorExporter;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class CentralitiesAnalyzer implements Analyzer {

	private Map<Vertex, Integer> shellIndices;
	private DegreeExporter degreeExporter;
	private BetweennessExporter betweennessExporter;
	private ClosenessExporter closenessExporter;
	private EigenvectorExporter eigenvectorExporter;

	private void init(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		this.shellIndices = decomposer.decompose(graph);
		this.degreeExporter = new DegreeExporter(graph, shellIndices);
		this.betweennessExporter = new BetweennessExporter(graph, shellIndices);
		this.closenessExporter = new ClosenessExporter(graph, shellIndices);
		this.eigenvectorExporter = new EigenvectorExporter(graph, shellIndices);
	}

	@Override
	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer, String folder) {
		init(graph, decomposer);
		degreeExporter.saveToCSV(folder, "degree.csv");
		betweennessExporter.saveToCSV(folder, "betweenness-centrality.csv");
		closenessExporter.saveToCSV(folder, "closeness-centrality.csv");
		eigenvectorExporter.saveToCSV(folder, "eigenvector-centrality.csv");
	}

}
