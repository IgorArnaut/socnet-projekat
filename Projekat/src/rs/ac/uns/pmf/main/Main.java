package rs.ac.uns.pmf.main;

import java.util.Map;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.BatageljZaversnikDecomposer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.decomposers.StraightforwardDecomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.io.GraphReader;

public class Main {
	
	private static Decomposer sfDecomposer = new StraightforwardDecomposer();
	private static Decomposer bzDecomposer = new BatageljZaversnikDecomposer();
	
	public static void main(String[] args) {
		Graph<Vertex, Edge> graph = GraphReader.readGraphml("resources/graph3.graphml");
		Map<Vertex, Integer> sfIndices = sfDecomposer.decompose(graph);
		Map<Vertex, Integer> bzIndices = bzDecomposer.decompose(graph);
		
		System.out.println(sfIndices);
		System.out.println(bzIndices);
	}
}