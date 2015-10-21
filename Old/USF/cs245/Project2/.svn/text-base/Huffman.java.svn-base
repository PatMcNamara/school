import java.util.ArrayList;
import java.util.Arrays;
/**
 * Program for compressing and decompressing ascii text files useing Huffman
 * coding.
 * 
 * Classes:
 * This class contains main method (for processing command line args) which in
 *  turn calls either the compress or decompress methods.  It also has helper
 *  methods used by compress and decompress.
 * The HuffTree class contains methods for building and using a huffman tree.
 * Both TextFile and BinaryFile are used for reading and writing text and binary
 * 	files.  Both of these classes use the Assert class for some error handling.
 * 
 * TextFile, BinaryFile and Assert are the exact same as provided with a small
 *  exception.  The TextFile class was modified to support use in the enchanced
 *  for loop construct.  To acomplish this, it now implements the Iterable
 *  interface (along with the required iterator() method) and has an Iterator
 *  subclass.
 *  
 * @author pjmcnamara2
 *
 */
public class Huffman {
	// number of bits in a single char.  For ascii this value is 2**8 = 256
	public static final int SIZE_OF_CHAR = 256;
	
	private String inFileName, outFileName;
	private boolean verbose;
	/**
	 * Will compress the file.
	 */
	void compress() {
		// Set up io
		TextFile inFile = new TextFile(inFileName, 'r');
		BinaryFile outFile = new BinaryFile(outFileName, 'w');
		
		// Read input file
		int[] ascii = new int[SIZE_OF_CHAR];
		for(char i: inFile) 
			ascii[(int) i]++;
		
		// Build Huffman Tree
		ArrayList<HuffTree> huffList = new ArrayList<HuffTree>();
		for(int i = 0; i < ascii.length; i++)
			if(ascii[i] != 0)
				huffList.add(new HuffTree((char) i, ascii[i]));
		HuffTree[] huffArray = huffList.toArray(new HuffTree[0]);
		//OPTION 1
		HuffTree huffmanTree = buildTree(huffArray, 0);
		//OPTION 2
//		Arrays.sort(huffArray);
//		HuffTree huffmanTree = buildTree(huffArray, 0);

		// Build Lookup Table
		String[] huffmanTable = new String[SIZE_OF_CHAR];
		huffmanTree.buildTable(huffmanTable);
		
		// Make verbose output if enabled
		if(verbose)
			verbosePrint(ascii, huffmanTable, huffmanTree);
		
		// Print out the huffman tree
		huffmanTree.write(outFile);
		
		// Use lookup table to encode file
		for(char i: inFile) 
			for(char code: huffmanTable[(int) i].toCharArray())
				if(code == '0')
					outFile.writeBit(false);
				else
					outFile.writeBit(true);
		
		//close files
		inFile.close();
		outFile.close();
	}
	
	/**
	 * Recursive method that builds a Huffman tree from the bottom up out
	 * of an array filled with Huffman trees.
	 * @param huffArray
	 * 		an array where each Huffman tree contains a single character and the
	 * 		number of times that character appears in the file.
	 * @param start
	 * 		The first index of the array that is to be used.  This method
	 * 		assumes that, when the array is sorted, all elements less then that
	 * 		index are to be ignored.  This way we don't have to create a new
	 * 		array with each call, insted we can just increment start.
	 * @return
	 * 		A single huffman tree containing all the nodes of the input.
	 */
	private static HuffTree buildTree(HuffTree[] huffArray, int start) {
		if(huffArray.length == start + 1) // Base case, one element in array
			return huffArray[start];
		//OPTION 1
		Arrays.sort(huffArray);
		huffArray[start+1] = new HuffTree(huffArray[start], huffArray[start+1]);
		
		//OPTION 2
//		HuffTree tmp = new HuffTree(huffArray[start], huffArray[start+1]);
//		int i;
//		for(i = start + 1; i < huffArray.length-1 && tmp.compareTo(huffArray[i+1]) > 0; i++)
//			huffArray[i] = huffArray[i+1];
//		huffArray[i] = tmp;
		//OPTION
		return buildTree(huffArray, start + 1);
	}
	/**
	 * Used by the compress method to do verbose output.  Prints the frequency
	 * table, the Huffman tree and the Huffman table.
	 * @param ascii
	 * @param huffmanTable
	 * @param huffmanTree
	 */
	private static void verbosePrint(int[] ascii, String[] huffmanTable,
			HuffTree huffmanTree) {
		// print character frequency
		System.out.println("Frequency of ascii codes: ");
		int count = 0;
		for(int i: ascii) {
			if(i > 0) // if ascii code exists
				System.out.println("Ascii value " + count + " appears " + i +
						" times");
			count++;
		}
		
		//print huffman tree
		System.out.println("\nHuffman Tree: \n" + huffmanTree);
		
		// print huffman code table
		System.out.println("Ascii values to huffman codes: ");
		count = 0;
		for(String i: huffmanTable) {
			if(i != null)
				System.out.println("Ascii value " + count + " has huffman code "
						+ i);
			count++;
		}
		System.out.println();
	}
	
	/**
	 * Used to decompress the file.
	 */
	void decompress() {
		// Set up io
		BinaryFile inFile = new BinaryFile(inFileName, 'r');
		TextFile outFile = new TextFile(outFileName, 'w');
		
		// Read huffman tree
		HuffTree huffmanTree = new HuffTree(inFile);
		
		//Make verbose output if enabled
		if(verbose)
			System.out.println("Huffman Tree: \n" + huffmanTree);

		// Use huffman tree to decode file
		while(!inFile.EndOfFile()) // inFile is not pointing to start of file
			outFile.writeChar(huffmanTree.decode(inFile));
	}

	/**
	 * This will process command line arguements then call either compress or
	 * decompress.
	 * @param args
	 * 		1) "-c" or "-u" for compress or decompress
	 * 		2) "-v" for optional verbose output
	 * 		3) input file
	 * 		4) output file
	 */
	public static long main(String[] args) {//TODO change back to void
		long time = System.currentTimeMillis();
		Huffman huff = new Huffman();
		
		// Check for minimal number of args
		if(args.length < 3)
			useage();
		
		// Process verbose flag
		int counter = 1;
		if(args[1].equals("-v")) {
			huff.verbose = true;
			counter++;
			if(args.length != 4)
				useage();
		} else if(args.length != 3)
			useage();
		
		// Set the input and output file
		huff.inFileName = args[counter++];
		huff.outFileName = args[counter++];
		
		// either compress or decompress
		if(args[0].equals("-c")) {
			huff.compress();
		} else if(args[0].equals("-u")) {
			huff.decompress();
		} else // either -c or -u was not entered
			useage();
		return System.currentTimeMillis() - time;
	}
	/**
	 * Display how to use the Huffman command.
	 */
	public static void useage() {
		System.out.println(
				"Usage: java Huffman (-c|-u) [-v] infile outfile\n" +
				"\n" +
				"-c compresses, -u decompresses\n" +
				"infile: path to input file; outfile: path to outputfile" +
				"-v gives verbose output"
				);
		System.exit(0);
	}
}
