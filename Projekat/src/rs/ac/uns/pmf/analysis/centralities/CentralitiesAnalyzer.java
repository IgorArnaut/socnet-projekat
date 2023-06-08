package rs.ac.uns.pmf.analysis.centralities;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.math3.stat.correlation.SpearmansCorrelation;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.Analyzer;
import rs.ac.uns.pmf.decomposers.Decomposer;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.CSVExporter;
import rs.ac.uns.pmf.utils.Triple;

public class CentralitiesAnalyzer implements Analyzer, CSVExporter {

	protected Map<Vertex, Integer> shellIndices;
	protected List<Triple<String, Integer, Double>> results;

	public CentralitiesAnalyzer() {
		this.results = new ArrayList<>();
	}

	@Override
	public void analyze(Graph<Vertex, Edge> graph, Decomposer decomposer) {
	}

	private double calculateCorrelation(List<Double> xs, List<Double> ys) {
		SpearmansCorrelation correlation = new SpearmansCorrelation();
		double[] xArray = xs.stream().mapToDouble(d -> d).toArray();
		double[] yArray = ys.stream().mapToDouble(d -> d).toArray();
		return correlation.correlation(xArray, yArray);
	}

	private double getCorrelation() {
		List<Double> xs = new ArrayList<>();
		List<Double> ys = new ArrayList<>();
		results.forEach(t -> {
			xs.add(1.0 * t.second());
			ys.add(t.third());
		});
		return calculateCorrelation(xs, ys);
	}

	@Override
	public void exportToCSV(String folder, String file, String header) {
		new File(folder).mkdirs();

		try (BufferedWriter bw = new BufferedWriter(new PrintWriter(new FileWriter(folder + file)))) {
			bw.flush();
			bw.write(header);
			bw.newLine();

			for (Triple<String, Integer, Double> r : results) {
				bw.append(String.format("%s;%d;%.2f", r.first(), r.second(), r.third()));
				bw.newLine();
			}

			double correlation = getCorrelation();
			bw.append(String.format("Spearmans correlation;%.2f", correlation));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void report(String folder) {
	}

}
