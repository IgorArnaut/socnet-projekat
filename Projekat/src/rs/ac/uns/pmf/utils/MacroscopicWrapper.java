package rs.ac.uns.pmf.utils;

import java.util.List;

import edu.uci.ics.jung.graph.Graph;
import rs.ac.uns.pmf.analysis.macroscopic.AverageClusteringCoefficient;
import rs.ac.uns.pmf.analysis.macroscopic.ComponentCount;
import rs.ac.uns.pmf.analysis.macroscopic.Density;
import rs.ac.uns.pmf.analysis.macroscopic.Diameter;
import rs.ac.uns.pmf.analysis.macroscopic.EdgeCount;
import rs.ac.uns.pmf.analysis.macroscopic.EdgePercentage;
import rs.ac.uns.pmf.analysis.macroscopic.Macroscopic;
import rs.ac.uns.pmf.analysis.macroscopic.SmallWorldCoefficient;
import rs.ac.uns.pmf.analysis.macroscopic.VertexCount;
import rs.ac.uns.pmf.analysis.macroscopic.VertexPercentage;
import rs.ac.uns.pmf.graph.Edge;
import rs.ac.uns.pmf.graph.Vertex;

public class MacroscopicWrapper {

	private Macroscopic vertexCount = new VertexCount();
	private Macroscopic edgeCount = new EdgeCount();
	private Macroscopic density = new Density();
	private Macroscopic componentCount = new ComponentCount();
	private Macroscopic vertexPercentage = new VertexPercentage();
	private Macroscopic edgePercentage = new EdgePercentage();
	private Macroscopic swc = new SmallWorldCoefficient();
	private Macroscopic diameter = new Diameter();
	private Macroscopic acc = new AverageClusteringCoefficient();

	private List<Graph<Vertex, Edge>> cores;
	private double[] vertexCounts;
	private double[] edgeCounts;
	private double[] densities;
	private double[] componentCounts;
	private double[] vertexPercentages;
	private double[] edgePercentages;
	private double[] swcs;
	private double[] diameters;
	private double[] accs;

	private void init(List<Graph<Vertex, Edge>> cores) {
		this.cores = cores;
		this.vertexCounts = vertexCount.getValues(cores);
		this.edgeCounts = edgeCount.getValues(cores);
		this.densities = density.getValues(cores);
		this.componentCounts = componentCount.getValues(cores);
		this.vertexPercentages = vertexPercentage.getValues(cores);
		this.edgePercentages = edgePercentage.getValues(cores);
		this.swcs = swc.getValues(cores);
		this.diameters = diameter.getValues(cores);
		this.accs = acc.getValues(cores);
	}

	public MacroscopicWrapper(List<Graph<Vertex, Edge>> cores) {
		init(cores);
	}

	public List<Graph<Vertex, Edge>> getCores() {
		return cores;
	}

	public double[] getVertexCounts() {
		return vertexCounts;
	}

	public double[] getEdgeCounts() {
		return edgeCounts;
	}

	public double[] getDensities() {
		return densities;
	}

	public double[] getComponentCounts() {
		return componentCounts;
	}

	public double[] getVertexPercentages() {
		return vertexPercentages;
	}

	public double[] getEdgePercentages() {
		return edgePercentages;
	}

	public double[] getSwcs() {
		return swcs;
	}

	public double[] getDiameters() {
		return diameters;
	}

	public double[] getAccs() {
		return accs;
	}

}
