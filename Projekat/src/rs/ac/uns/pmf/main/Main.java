package rs.ac.uns.pmf.main;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.Analyzer;
import rs.ac.uns.pmf.analysis.CentralitiesAnalyzer;
import rs.ac.uns.pmf.analysis.MacroscopicAnalyzer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.decomposers.StraightforwardDecomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.io.GraphReader;

public class Main {

	public static void main(String[] args) {
		Graph<Vertex, Edge> graph;
		graph = GraphReader.readGraphml("src/graph1.graphml");
		Decomposer decomposer = new StraightforwardDecomposer();
		Analyzer macroscopic = new MacroscopicAnalyzer();
		Analyzer centralities = new CentralitiesAnalyzer();
		macroscopic.analyze(graph, decomposer, "graph1");
		centralities.analyze(graph, decomposer, "graph1");
	}

}