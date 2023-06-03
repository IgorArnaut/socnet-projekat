package rs.ac.uns.pmf.analysis.exporters;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import edu.uci.ics.jung.algorithms.scoring.EigenvectorCentrality;
import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class EigenvectorExporter extends Exporter {

	private Map<Vertex, Integer> shellIndices;
	private EigenvectorCentrality<Vertex, Edge> centrality;
	private SpearmansCorrelation correlation = new SpearmansCorrelation();

	public EigenvectorExporter(Graph<Vertex, Edge> graph, Map<Vertex, Integer> shellIndices) {
		super(graph);
		this.shellIndices = shellIndices;
		this.centrality = new EigenvectorCentrality<Vertex, Edge>(graph);
	}

	private double calculateCorrelation(List<Double> x1, List<Double> x2) {
		double[] array1 = x1.stream().mapToDouble(d -> d).toArray();
		double[] array2 = x2.stream().mapToDouble(d -> d).toArray();
		return correlation.correlation(array1, array2);
	}

	public double getSpearmansCorrelation() {
		List<Double> xs = new ArrayList<>();
		List<Double> ys = new ArrayList<>();
		shellIndices.forEach((k, v) -> {
			xs.add(1.0 * v);
			ys.add(centrality.getVertexScore(k));
		});
		return calculateCorrelation(xs, ys);
	}

	@Override
	public void saveToCSV(String folder, String file) {
		try (BufferedWriter bw = new BufferedWriter(new PrintWriter(new FileWriter("src/" + folder + "/" + file)))) {
			bw.append("Id;Shell index;Eigenvector centrality\n");
	
			for (Vertex vertex : shellIndices.keySet()) {
				int id = Integer.parseInt(vertex.getId());
				int x = shellIndices.get(vertex);
				double y = centrality.getVertexScore(vertex);
				String row = String.format("%d;%d;%.2f\n", id, x, y);
				bw.append(row);
			}
			
			double correlation = getSpearmansCorrelation();
			bw.append("Spearman's correlation;" + correlation + "\n");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}