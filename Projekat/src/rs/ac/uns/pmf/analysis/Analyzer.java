package rs.ac.uns.pmf.analysis;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public abstract class Analyzer {

	protected Graph<Vertex, Edge> graph;
	protected List<String> rows;

	public Analyzer(Graph<Vertex, Edge> graph) {
		this.graph = graph;
		this.rows = new ArrayList<>();
	}

	public void saveToCSV(String file) {
		try (BufferedWriter bw = new BufferedWriter(new PrintWriter(new FileWriter(file)))) {
			for (String row : rows)
				bw.append(row + "\n");			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public abstract void analyze();

}
