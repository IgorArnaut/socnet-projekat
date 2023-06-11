package rs.ac.uns.pmf.io;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import com.opencsv.CSVWriter;

public abstract class CSVExporter {

	protected List<String[]> data;

	protected abstract void insertHeader();

	protected abstract void insertRows();

	protected void insertData() {
		insertHeader();
		insertRows();
	}

	public void exportToCSV(String graphName, String fileName) {
		new File("resources/" + graphName + "/").mkdirs();
		File file = new File("resources/" + graphName + "/" + fileName + ".csv");

		try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
			writer.writeAll(data);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
