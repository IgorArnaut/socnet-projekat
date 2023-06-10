package rs.ac.uns.pmf.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CentralitiesExporter implements CSVExporter {

	private List<Triple<String, Double, Double>> results;
	private double correlation;

	public void setData(List<Triple<String, Double, Double>> results, double correlation) {
		this.results = results;
		this.correlation = correlation;
	}

	@Override
	public void exportToCSV(String folder, String file, String header) {
		new File(folder).mkdirs();

		try (BufferedWriter bw = new BufferedWriter(new PrintWriter(new FileWriter(folder + file)))) {
			bw.flush();
			bw.write(header);
			bw.newLine();

			for (Triple<String, Double, Double> r : results) {
				bw.append(String.format("%s;%.2f;%.2f", r.first(), r.second(), r.third()));
				bw.newLine();
			}

			bw.append(String.format("Spearmans correlation;%.2f", correlation));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}