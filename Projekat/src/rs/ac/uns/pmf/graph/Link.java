package rs.ac.uns.pmf.graph;

public class Link {

	private String label;

	public Link(String label) {
		this.label = label;
	}

	public String getLabel() {
		return this.label;
	}

	@Override
	public String toString() {
		return this.label;
	}

}
