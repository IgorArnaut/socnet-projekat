package rs.ac.uns.pmf.analysis.exporters;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class DensityExporter extends Exporter {

	private Decomposer decomposer;

	public DensityExporter(Graph<Vertex, Edge> graph, Decomposer decomposer) {
		super(graph);
		this.decomposer = decomposer;
	}

	private double getDensity(Graph<Vertex, Edge> graph) {
		int vertexCount = graph.getVertexCount();
		int edgeCount = graph.getEdgeCount();

		if (vertexCount == 0)
			return 0.0;

		return 2.0 * edgeCount / (vertexCount * (vertexCount - 1));
	}

	@Override
	public void saveToCSV(String folder, String file) {
		try (BufferedWriter bw = new BufferedWriter(new PrintWriter(new FileWriter("src/" + folder + "/" + file)))) {
			bw.append("Core;Density\n");
			Graph<Vertex, Edge> core = null;
			int x = 0;

			do {
				core = decomposer.getKCore(graph, x);
				double y = getDensity(core);
				String row = String.format("%d;%.2f\n", x, y);
				bw.append(row);
				x++;
			} while (core.getVertexCount() > 0);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
