package parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class UWG {
	private int n;
	private int[] columnWidth;
	private ArrayList<Edge> edges;
	private ArrayList<Edge>[] neighbours; 
	
	@SuppressWarnings("unchecked")
	public UWG(String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists()) throw new FileNotFoundException();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		// Read in n and m
		n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());
		
		// Initialize edge list
		edges = new ArrayList<Edge>();
		
		// Pretty print, column width
		columnWidth = new int[n];
		neighbours = new ArrayList[n];
		for (int i = 0; i < n; i++) {
			columnWidth[i] = 1;
			neighbours[i] = new ArrayList<Edge>();
		}
		
		
		// Read in edges
		String[] parts;
		int x1, x2, w;
		Edge e;
		for (int i = 0; i < m; i++) {
			parts = br.readLine().split(" ");
			
			x1 = Integer.parseInt(parts[0])-1;
			x2 = Integer.parseInt(parts[1])-1;
			w = Integer.parseInt(parts[2]);
			
			e = new Edge(i, x1, x2, w);
			edges.add(e);
			neighbours[x1].add(e);
			neighbours[x2].add(e);
			
			// Update pretty print variables
			if (parts[2].length() > columnWidth[x1]) columnWidth[x1] = parts[2].length();
			if (parts[2].length() > columnWidth[x2]) columnWidth[x2] = parts[2].length();
		}
		
		br.close();
	}
	
	public ArrayList<Edge> getNeighbours(int k) {
		return neighbours[k];
	}
	
	public int getN() {
		return n;
	}
	
	public ArrayList<Edge> getEdges() {
		return edges;
	}

	public int countEdges() {
		return edges.size();
	}
	
	public Edge getEdge(int k) {
		return edges.get(k);
	}
	
	public Edge getMirrorEdge(int k) {
		return this.getEdge(this.countEdges()-k-1);
	}
	
	public int getW(int k) {
		return getEdge(k).getWeight();
	}
	
	public int getMirrorW(int k) {
		return getMirrorEdge(k).getWeight();
	}
	
	public void printEdges() {
		Edge e;
		for (int i = 0; i < edges.size(); i++) {
			e = edges.get(i);
			System.out.printf(
					"e%d = {%d,%d}, w(e%d) = %d\n",
					i+1, e.getN1()+1, e.getN2()+1, i+1, e.getWeight()
					);
		}
	}
	
	public static String getCorrectedEdges(int i, Collection<Edge> neighbours) {
		ArrayList<Integer> ints = new ArrayList<Integer>();
		for (Edge e : neighbours) {
			ints.add(e.getOther(i));
		}
		return getCorrectedEdges(ints);
	}
	
	public static String getCorrectedEdges(Collection<Integer> neighbours) {
		if (neighbours.isEmpty()) return "[]";
		
		ArrayList<Integer> sorted = new ArrayList<Integer>(neighbours);
		Collections.sort(sorted);
				
		String output = "";
		for (Integer i : sorted) {
			output += (i+1)+", ";
		}
		return "["+output.substring(0,output.length()-2)+"]";
	}

	public void printNeighbours() {
		for (int i = 0; i < neighbours.length; i++) {
			System.out.println((i+1)+": "+UWG.getCorrectedEdges(i, neighbours[i]));
		}
	}
}
