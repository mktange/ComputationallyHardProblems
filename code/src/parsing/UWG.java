package parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class UWG {
	private int n;
	private int[][] matrix;
	private int[] columnWidth;
	private ArrayList<Edge> edges;
	
	public UWG(String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists()) throw new FileNotFoundException();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		// Read in n and m
		n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());
		
		// Initialize adjacency matrix and edge list
		matrix = new int[n][n];
		edges = new ArrayList<Edge>();
		
		// Pretty print, column width
		columnWidth = new int[n];
		for (int i = 0; i < columnWidth.length; i++) columnWidth[i] = 1;
		
		
		// Read in edges
		String[] parts;
		int x1, x2, w;
		for (int i = 0; i < m; i++) {
			parts = br.readLine().split(" ");
			
			x1 = Integer.parseInt(parts[0])-1;
			x2 = Integer.parseInt(parts[1])-1;
			w = Integer.parseInt(parts[2]);
			
			matrix[x1][x2] = w;
			matrix[x2][x1] = w;
			edges.add(new Edge(x1, x2, w));
			
			// Update pretty print variables
			if (parts[2].length() > columnWidth[x1]) columnWidth[x1] = parts[2].length();
			if (parts[2].length() > columnWidth[x2]) columnWidth[x2] = parts[2].length();
		}
		
		br.close();
	}
	
	public int[][] getMatrix() {
		return matrix;
	}
	
	public int getN() {
		return n;
	}
	
	public int getEdge(int x1, int x2) {
		return matrix[x1][x2];
	}
	
	public Edge getEdge(int k) {
		return edges.get(k);
	}

	public void printMatrix() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.printf("%"+columnWidth[j]+"d ", matrix[i][j]);
			}
			System.out.println();
		}
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
}
