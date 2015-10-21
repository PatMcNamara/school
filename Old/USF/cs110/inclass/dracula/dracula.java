package dracula;

import java.util.Scanner;
import java.io.*;

public class dracula {

	public static void main(String[] args) {
		
		String line;
		String word;
		Scanner in = new Scanner(System.in);
		Scanner dracula = null;
		PrintWriter out = null;
		try {
			out = new PrintWriter(new File("/home/pjmcnamara2/out.txt"));
			dracula = new Scanner(new
				File("/home/pjmcnamara2/cs110/excersises/Python/dracula.txt"));
		} catch (IOException nofile) {
			System.out.println("File not found");
		}
			
		System.out.println("What word do you want to find?");
		word = in.nextLine().toLowerCase();
		
		for (int i = 0; dracula.hasNext(); i++) {
			line = dracula.nextLine();
			if (line.toLowerCase().contains(word)) {
				out.println(i + ": " + line);
			}
		}
		out.close();
	}
}