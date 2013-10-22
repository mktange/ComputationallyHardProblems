package solver;

import java.util.HashSet;
import java.util.Set;

import parsing.UWG;

public class Solution {
	int B;
	Set<Integer> edges;
	
	public Solution(int B, Set<Integer> edges) {
		this(B);
		this.edges.addAll(edges);
	}
	
	public Solution(int B) {
		this.B = B;
		this.edges = new HashSet<Integer>();
	}
	
	public Solution(int B, Set<Integer> edges, int lastEdge) {
		this.B = B;
		this.edges = new HashSet<Integer>(edges);
		this.edges.add(lastEdge);
	}
	
	public Set<Integer> getEdges() {
		return edges;
	}
	
	public int getB() {
		return B;
	}
	
	public static Solution getBest(Solution t1, Solution t2) {
		if (t1 == null) return t2;
		if (t2 == null) return t1;
		if (t1.B < t2.B) {
			return t1;
		} else {
			return t2;
		}
	}
	
	public boolean isBetterThan(int other) {
		if (other >= this.B) return true;
		return false;
	}
	
	
	@Override
	public String toString() {
		return String.format(
				"B: %d\nEdges: %s",
				B,
				UWG.getCorrectedEdges(edges)
				);
	}
}