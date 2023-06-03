package rs.ac.uns.pmf.main;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.Analyzer;
import rs.ac.uns.pmf.analysis.CentralitiesAnalyzer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.decomposers.StraightforwardDecomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.io.GraphReader;

public class Main {

	public static void main(String[] args) {
		Graph<Vertex, Edge> graph;
		graph = GraphReader.readGraphml("src/graph3.graphml");
		Decomposer<Vertex, Edge> decomposer = new StraightforwardDecomposer<Vertex, Edge>();
		Analyzer<Vertex, Edge> analyzer = new CentralitiesAnalyzer<Vertex, Edge>();
		analyzer.analyze(graph, decomposer);
	}

}