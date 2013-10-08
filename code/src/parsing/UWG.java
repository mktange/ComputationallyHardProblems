package parsing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class UWG {
	private int n;
	private int[][] matrix;
	private int[] maxLength;
	
	public UWG(String filename) throws IOException {
		File file = new File(filename);
		if (!file.exists()) throw new FileNotFoundException();
		
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		
		n = Integer.parseInt(br.readLine());
		int m = Integer.parseInt(br.readLine());
		
		matrix = new int[n][n];
		maxLength = new int[n];
		for (int i = 0; i < maxLength.length; i++) maxLength[i] = 1;
		
		String[] parts;
		int x1, x2, w;
		for (int i = 0; i < m; i++) {
			parts = br.readLine().split(" ");
			
			x1 = Integer.parseInt(parts[0])-1;
			x2 = Integer.parseInt(parts[1])-1;
			w = Integer.parseInt(parts[2]);
			
			matrix[x1][x2] = w;
			matrix[x2][x1] = w;
			if (parts[2].length() > maxLength[x1]) maxLength[x1] = parts[2].length();
			if (parts[2].length() > maxLength[x2]) maxLength[x2] = parts[2].length();
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

	public void printMatrix() {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[0].length; j++) {
				System.out.printf("%"+maxLength[j]+"d ", matrix[i][j]);
			}
			System.out.println();
		}
	}
}
