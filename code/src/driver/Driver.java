package driver;

import java.io.IOException;
import java.util.Scanner;

import parsing.UWG;

public class Driver {

	public static void main(String[] args) {
		UWG uwg = null;
		try {
			uwg = new UWG(getFilename(args));
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
		
		uwg.printMatrix();
	}
	
	private static String getFilename(String[] args) {
		if (args.length > 0) return args[0];
		
		Scanner in = new Scanner(System.in);
		String filename = in.nextLine();
		in.close();
		
		return filename;
	}
}
