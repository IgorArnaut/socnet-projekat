package rs.ac.uns.pmf.main;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.io.GraphIOException;
import rs.ac.uns.pmf.analysis.MacroscopicAnalyzer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.decomposers.StraightforwardDecomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.io.GraphReader;

public class Main {

	public static void main(String[] args) {
		Graph<Vertex, Edge> graph;

		try {
			graph = GraphReader.readGraphml("src/graph1.graphml");
			Decomposer<Vertex, Edge> decomposer = new StraightforwardDecomposer<Vertex, Edge>();
			MacroscopicAnalyzer<Vertex, Edge> analyzer = new MacroscopicAnalyzer<Vertex, Edge>();
			analyzer.analyze(graph, decomposer);
		} catch (GraphIOException e) {
			e.printStackTrace();
		}
	}

}