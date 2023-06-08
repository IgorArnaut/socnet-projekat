package rs.ac.uns.pmf.main;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.Analyzer;
import rs.ac.uns.pmf.analysis.centralities.BetweennessAnalyzer;
import rs.ac.uns.pmf.analysis.centralities.ClosenessAnalyzer;
import rs.ac.uns.pmf.analysis.centralities.DegreeAnalyzer;
import rs.ac.uns.pmf.analysis.centralities.EigenvectorAnalyzer;
import rs.ac.uns.pmf.analysis.macroscopic.ClusteringAnalyzer;
import rs.ac.uns.pmf.analysis.macroscopic.ComponentCountAnalyzer;
import rs.ac.uns.pmf.analysis.macroscopic.DensityAnalyzer;
import rs.ac.uns.pmf.analysis.macroscopic.DiameterAnalyzer;
import rs.ac.uns.pmf.analysis.macroscopic.EdgeCountAnalyzer;
import rs.ac.uns.pmf.analysis.macroscopic.PercentageAnalyzer;
import rs.ac.uns.pmf.analysis.macroscopic.SmallWorldAnalyzer;
import rs.ac.uns.pmf.analysis.macroscopic.VertexCountAnalyzer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.decomposers.StraightforwardDecomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.io.GraphReader;

public class Reporting {

	private Map<String, Graph<Vertex, Edge>> graphs;

	private Analyzer[] analyzers;

	public Reporting() {
		this.graphs = new LinkedHashMap<>();
		graphs.put("adjnoun", GraphReader.readGraphml("resources/adjnoun.graphml"));
		graphs.put("celegansneural", GraphReader.readGraphml("resources/celegansneural.graphml"));
		graphs.put("football", GraphReader.readGraphml("resources/football.graphml"));
		graphs.put("polbooks", GraphReader.readGraphml("resources/polbooks.graphml"));

		this.analyzers = new Analyzer[] {
				new BetweennessAnalyzer(), 
				new ClosenessAnalyzer(), 
				new DegreeAnalyzer(),
				new EigenvectorAnalyzer(),

				new ClusteringAnalyzer(), new ComponentCountAnalyzer(), new DensityAnalyzer(), new DiameterAnalyzer(),
				new EdgeCountAnalyzer(), new PercentageAnalyzer(), new SmallWorldAnalyzer(),
				new VertexCountAnalyzer() };
	}

	public void saveReport() {
		Decomposer decomposer = new StraightforwardDecomposer();

		for (String folder : graphs.keySet()) {
			Graph<Vertex, Edge> graph = graphs.get(folder);
			System.out.println(folder);

			for (Analyzer analyzer : analyzers) {
				analyzer.analyze(graph, decomposer);
				analyzer.report("resources/" + folder + "/");

			}
		}
	}

}
