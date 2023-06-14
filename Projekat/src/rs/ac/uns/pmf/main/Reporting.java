package rs.ac.uns.pmf.main;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import edu.uci.ics.jung.algorithms.filters.FilterUtils;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.BatageljZaversnikDecomposer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.io.CSVExporter;
import rs.ac.uns.pmf.io.CentralitiesExporter;
import rs.ac.uns.pmf.io.GraphReader;
import rs.ac.uns.pmf.io.MacroscopicExporter;

public class Reporting {

	private Map<String, Graph<Vertex, Edge>> graphs;

	public Reporting() {
		this.graphs = new LinkedHashMap<>();
		graphs.put("adjnoun", GraphReader.readGraphml("resources/adjnoun.graphml"));
		graphs.put("celegansneural", GraphReader.readGraphml("resources/celegansneural.graphml"));
		graphs.put("football", GraphReader.readGraphml("resources/football.graphml"));
		graphs.put("polbooks", GraphReader.readGraphml("resources/polbooks.graphml"));
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

	public void saveReport() {
		Decomposer decomposer = new BatageljZaversnikDecomposer();

		for (String name : graphs.keySet()) {
			Graph<Vertex, Edge> graph = graphs.get(name);
			Map<Vertex, Integer> shellIndices = decomposer.decompose(graph);
			List<Graph<Vertex, Edge>> cores = getCores(graph, shellIndices);

			CSVExporter me = new MacroscopicExporter(cores);
			me.exportToCSV(name, "macroscopic");
			CSVExporter ce = new CentralitiesExporter(graph, shellIndices);
			ce.exportToCSV(name, "centralities");
			System.out.println(name + " analysis complete!");
		}
	}

}
