package rs.ac.uns.pmf.analysis.exporters;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class VertexCountExporter extends Exporter {

	private Decomposer decomposer;

	public VertexCountExporter(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		super(graph);
		this.decomposer = decomposer;
	}

	@Override
	public void saveToCSV(String folder, String file) {
		try (BufferedWriter bw = new BufferedWriter(new PrintWriter(new FileWriter("src/" + folder + "/" + file)))) {
			bw.append("Core;Vertex count\n");
			Graph<Vertex, Edge> core = null;
			int x = 0;

			do {
				core = decomposer.getKCore(graph, x);
				int y = core.getVertexCount();
				String row = String.format("%d;%d\n", x, y);
				bw.append(row);
				x++;
			} while (core.getVertexCount() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
