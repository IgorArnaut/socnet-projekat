package rs.ac.uns.pmf.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

public class MacroscopicExporter implements CSVExporter {

	private Map<Integer, Double> results;

	public void setData(Map<Integer, Double> results) {
		this.results = results;
	}

	@Override
	public void exportToCSV(String folder, String file, String header) {
		new File(folder).mkdirs();

		try (BufferedWriter bw = new BufferedWriter(new PrintWriter(new FileWriter(folder + file)))) {
			bw.write(header);
			bw.newLine();

			for (Integer r : results.keySet()) {
				bw.append(String.format("%d;%.2f", r, results.get(r)));
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}