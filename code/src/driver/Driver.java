package driver;

import java.io.IOException;
import java.util.Scanner;

import parsing.UWG;
import solver.Solution;
import solver.Solver;

public class Driver {

	public static void main(String[] args) {
		UWG uwg = null;
		try {
			uwg = new UWG(getFilename(args));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		System.out.println("n: "+uwg.getN());
		uwg.printEdges();
		uwg.printNeighbours();
		
		System.out.println("================================");
		long start = System.currentTimeMillis();
		Solution solution = Solver.solve(uwg);
		long end = System.currentTimeMillis();
		System.out.println("Optimal solution: ");
		System.out.println(solution);
		System.out.println();
		System.out.println("Found in "+(end-start)/1000.0+" seconds");
	}
	
	
	/**
	 * Get a filename from either arguments or System.in
	 * @param args
	 * @return string
	 */
	private static String getFilename(String[] args) {
		if (args.length > 0) return args[0];
		
		System.out.print("Enter name of testfile: ");
		Scanner in = new Scanner(System.in);
		String filename = in.nextLine();
		in.close();
		
		if (!filename.endsWith(".uwg")) {
			filename += ".uwg";
		}
		
		return "testfiles/"+filename;
	}
}
