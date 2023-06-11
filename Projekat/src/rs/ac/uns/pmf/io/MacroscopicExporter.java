package rs.ac.uns.pmf.io;

import java.util.ArrayList;
import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;
import rs.ac.uns.pmf.utils.MacroscopicWrapper;

public class MacroscopicExporter extends CSVExporter {

	private MacroscopicWrapper wrapper;

	@Override
	protected void insertHeader() {
		String[] header = new String[10];
		header[0] = "Core";
		header[1] = "Vertex count";
		header[2] = "Edge count";
		header[3] = "Density";
		header[4] = "Component count";
		header[5] = "Giant component vertex percentage";
		header[6] = "Giant component edge percentage";
		header[7] = "Giant component small world coefficient";
		header[8] = "Giant component diameter";
		header[9] = "Average clustering coefficient";
		data.add(header);
	}

	@Override
	protected void insertRows() {
		for (int i = 0; i < wrapper.getCores().size(); i++) {
			String[] row = new String[10];
			row[0] = "" + i;
			row[1] = "" + wrapper.getVertexCounts()[i];
			row[2] = "" + wrapper.getEdgeCounts()[i];
			row[3] = "" + wrapper.getDensities()[i];
			row[4] = "" + wrapper.getComponentCounts()[i];
			row[5] = "" + wrapper.getVertexPercentages()[i];
			row[6] = "" + wrapper.getEdgePercentages()[i];
			row[7] = "" + wrapper.getSwcs()[i];
			row[8] = "" + wrapper.getDiameters()[i];
			row[9] = "" + wrapper.getAccs()[i];
			data.add(row);
		}
	}

	public MacroscopicExporter(List<Graph<Vertex, Edge>> cores) {
		this.wrapper = new MacroscopicWrapper(cores);
		this.data = new ArrayList<>();
		insertData();
	}

}
