package parsing;

public class Edge {
	private int n1, n2, w;

	public Edge(int n1, int n2, int w) {
		this.n1 = n1;
		this.n2 = n2;
		this.w = w;
	}
	
	public boolean connectsTo(int n) {
		return n==n1 || n==n2;
	}
	
	public int getOther(int n) {
		if (n == n1) return n2;
		if (n == n2) return n1;
		return -1;
	}
	
	public int getN1() {
		return n1;
	}
	
	public int getN2() {
		return n2;
	}
	
	public int getWeight() {
		return w;
	}
}
