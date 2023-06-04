package rs.ac.uns.pmf.io;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedSparseGraph;
import edu.uci.ics.jung.io.GraphIOException;
import edu.uci.ics.jung.io.GraphMLReader;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class GraphReader {

	public static Graph<Vertex, Edge> readGraphml(String file) throws GraphIOException {
		try {
			GraphMLReader<UndirectedSparseGraph<Vertex, Edge>, Vertex, Edge> reader = new GraphMLReader<>();
			UndirectedSparseGraph<Vertex, Edge> graph = new UndirectedSparseGraph<>();
			reader.load(file, graph);
			return graph;
		} catch (IOException | ParserConfigurationException | SAXException e) {
			e.printStackTrace();
			return null;
		}
	}

}