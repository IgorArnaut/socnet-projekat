package rs.ac.uns.pmf.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import edu.uci.ics.jung.algorithms.filters.FilterUtils;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.centralities.BetweennessAnalyzer;
import rs.ac.uns.pmf.analysis.centralities.CentralitiesAnalyzer;
import rs.ac.uns.pmf.analysis.centralities.ClosenessAnalyzer;
import rs.ac.uns.pmf.analysis.centralities.DegreeAnalyzer;
import rs.ac.uns.pmf.analysis.centralities.EigenvectorAnalyzer;
import rs.ac.uns.pmf.analysis.macroscopic.ClusteringAnalyzer;
import rs.ac.uns.pmf.analysis.macroscopic.ComponentCountAnalyzer;
import rs.ac.uns.pmf.analysis.macroscopic.DensityAnalyzer;
import rs.ac.uns.pmf.analysis.macroscopic.DiameterAnalyzer;
import rs.ac.uns.pmf.analysis.macroscopic.EdgeCountAnalyzer;
import rs.ac.uns.pmf.analysis.macroscopic.MacroscopicAnalyzer;
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

	public Reporting() {
		this.graphs = new LinkedHashMap<>();
		graphs.put("adjnoun", GraphReader.readGraphml("resources/adjnoun.graphml"));
		graphs.put("celegansneural", GraphReader.readGraphml("resources/celegansneural.graphml"));
		graphs.put("football", GraphReader.readGraphml("resources/football.graphml"));
		graphs.put("polbooks", GraphReader.readGraphml("resources/polbooks.graphml"));
	}

	private void analyzeCentralities(Graph<Vertex, Edge> graph, Map<Vertex, Integer> shellIndices,
			CentralitiesAnalyzer[] analyzers, String folder) {

		for (CentralitiesAnalyzer analyzer : analyzers) {
			analyzer.analyze(graph, shellIndices);
			analyzer.report("resources/" + folder + "/");
		}
	}

	private List<Graph<Vertex, Edge>> getCores(Graph<Vertex, Edge> graph, Map<Vertex, Integer> shellIndices) {
		List<Graph<Vertex, Edge>> cores = new ArrayList<>();
		Graph<Vertex, Edge> core = null;
		int k = 1;

		do {
			final int k2 = k;
			Predicate<Vertex> predicate = v -> shellIndices.get(v) >= k2;
			List<Vertex> vertices = shellIndices.keySet().stream().filter(predicate).toList();
			core = FilterUtils.createInducedSubgraph(vertices, graph);
			cores.add(core);
			k++;
		} while (core.getVertexCount() > 0);

		return cores;
	}

	private void analyzeMacroscopic(List<Graph<Vertex, Edge>> cores, MacroscopicAnalyzer[] analyzers, String folder) {
		for (MacroscopicAnalyzer analyzer : analyzers) {
			analyzer.analyze(cores);
			analyzer.report("resources/" + folder + "/");
		}
	}

	public void saveReport() {
		Decomposer decomposer = new StraightforwardDecomposer();
		CentralitiesAnalyzer[] centralityAnalyzers = new CentralitiesAnalyzer[] { new BetweennessAnalyzer(),
				new ClosenessAnalyzer(), new DegreeAnalyzer(), new EigenvectorAnalyzer(), };
		MacroscopicAnalyzer[] macroscopicAnalyzers = new MacroscopicAnalyzer[] { new ClusteringAnalyzer(),
				new ComponentCountAnalyzer(), new DensityAnalyzer(), new DiameterAnalyzer(), new EdgeCountAnalyzer(),
				new PercentageAnalyzer(), new SmallWorldAnalyzer(), new VertexCountAnalyzer() };

		for (String folder : graphs.keySet()) {
			Graph<Vertex, Edge> graph = graphs.get(folder);
			Map<Vertex, Integer> shellIndices = decomposer.decompose(graph);
			List<Graph<Vertex, Edge>> cores = getCores(graph, shellIndices);

			analyzeCentralities(graph, shellIndices, centralityAnalyzers, folder);
			analyzeMacroscopic(cores, macroscopicAnalyzers, folder);
			System.out.println(folder + " analysis complete!");
		}
	}

}
