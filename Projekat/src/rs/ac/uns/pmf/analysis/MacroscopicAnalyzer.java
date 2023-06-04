package rs.ac.uns.pmf.analysis;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.exporters.ClusteringExporter;
import rs.ac.uns.pmf.analysis.exporters.ComponentCountExporter;
import rs.ac.uns.pmf.analysis.exporters.DensityExporter;
import rs.ac.uns.pmf.analysis.exporters.DiameterExporter;
import rs.ac.uns.pmf.analysis.exporters.EdgeCountExporter;
import rs.ac.uns.pmf.analysis.exporters.PercentageExporter;
import rs.ac.uns.pmf.analysis.exporters.SmallWorldExporter;
import rs.ac.uns.pmf.analysis.exporters.VertexCountExporter;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class MacroscopicAnalyzer implements Analyzer {

	private VertexCountExporter vertexCountExporter;
	private EdgeCountExporter edgeCountExporter;
	private DensityExporter densityExporter;
	private ComponentCountExporter componentCountExporter;
	private PercentageExporter percentageExporter;
	private DiameterExporter diameterExporter;
	private SmallWorldExporter smallWorldExporter;
	private ClusteringExporter clusteringExporter;

	private void init(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		decomposer.decompose(graph);
		this.vertexCountExporter = new VertexCountExporter(graph, decomposer);
		this.edgeCountExporter = new EdgeCountExporter(graph, decomposer);
		this.densityExporter = new DensityExporter(graph, decomposer);
		this.componentCountExporter = new ComponentCountExporter(graph, decomposer);
		this.percentageExporter = new PercentageExporter(graph, decomposer);
		this.diameterExporter = new DiameterExporter(graph, decomposer);
		this.smallWorldExporter = new SmallWorldExporter(graph, decomposer);
		this.clusteringExporter = new ClusteringExporter(graph, decomposer);
	}

	@Override
	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer, String folder) {
		init(graph, decomposer);
		vertexCountExporter.saveToCSV(folder, "vertex-count.csv");
		edgeCountExporter.saveToCSV(folder, "edge-count.csv");
		densityExporter.saveToCSV(folder, "density.csv");
		componentCountExporter.saveToCSV(folder, "component-count.csv");
		percentageExporter.saveToCSV(folder, "percentage.csv");
		diameterExporter.saveToCSV(folder, "diameter.csv");
		smallWorldExporter.saveToCSV(folder, "small-world-coefficient.csv");
		clusteringExporter.saveToCSV(folder, "clustering-coefficient.csv");
	}

}
