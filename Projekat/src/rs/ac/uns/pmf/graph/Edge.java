package rs.ac.uns.pmf.graph;

public class Edge {

	private String label;
	
	public Edge(String sourceId, String targetId) {
		this.label = sourceId + "--" + targetId;
	}
	
	@Override
	public String toString() {
		return this.label;
	}
	
}
