package rs.ac.uns.pmf.graph;

public class Node {

	private String label;

	public Node(String label) {
		this.label = label;
	}

	public String getLabel() {
		return label;
	}

	@Override
	public String toString() {
		return this.label;
	}

}
