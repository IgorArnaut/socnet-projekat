package rs.ac.uns.pmf.utils;

public class Triple<X, Y, Z> {

	private X first;
	private Y second;
	private Z third;

	public Triple(X x, Y y, Z z) {
		this.first = x;
		this.second = y;
		this.third = z;
	}

	public X first() {
		return this.first;
	}

	public Y second() {
		return this.second;
	}

	public Z third() {
		return this.third;
	}

}
