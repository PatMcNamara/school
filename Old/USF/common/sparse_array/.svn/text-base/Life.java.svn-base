package sparse_array;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Implements the game of life using the sparse array interface.
 * @author pjmcnamara2
 * NOTE finished commenting
 */
public class Life {
	private SparseArray<Boolean> currentGeneration; // Should only contain booleans
	
	/**
	 * Takes in a sparse array of booleans to be used as the first generation.
	 * @param initialGeneration
	 */
	public Life(SparseArray<Boolean> initialGeneration) {
		currentGeneration = initialGeneration;
	}
	
	/**
	 * Finds the next generation, sets current generation to next generation
	 * @return the next generation
	 */
	public SparseArray<Boolean> findNextGeneration() {
		SparseArray<Integer> numNeighbors = new MySparseArray<Integer>(0);
		AxisIterator<Boolean> cib = currentGeneration.iterateColumns();
		
		// fill number of neighbors sparse array
		while(cib.hasNext()){
			ElemIterator<Boolean> ei = cib.next();
			while(ei.hasNext()) {
				MatrixElem<Boolean> me = ei.next();
				try {
					numNeighbors = getNeighbors(me, numNeighbors);
				} catch (ArrayIndexOutOfBoundsException e) {} // Ignore elements off board
			}
		}
		
		// calculate next generation
		SparseArray<Boolean> nextGeneration = new MySparseArray<Boolean>(false);
		AxisIterator<Integer> cii = numNeighbors.iterateColumns();
		while(cii.hasNext()){
			ElemIterator<Integer> ei = cii.next();
			while(ei.hasNext()) {
				MatrixElem<Integer> me = ei.next();
				nextGeneration.setValue(me.rowIndex(), me.columnIndex(), isAlive(me));
			}
		}
		return (currentGeneration = nextGeneration);
	}
	
	//TODO I don't think this method needs to return the sparse array.
	/**
	 * Takes a MatrixElement and fills in the number of neighbors sparse array
	 * @param element this element should be alive in the current generation
	 * @param numOfNeighbors should be a sparse array of ints
	 * @return parameter numOfNeighbors with all of the nodes boardering element filled in
	 */
	private SparseArray<Integer> getNeighbors(MatrixElem<Boolean> element, SparseArray<Integer> numOfNeighbors) {
		// Iterate through all your neighbors (and yourself) and get the number of neighbors they have
		for(int i=-1; i<=1; i++)
			for(int j=-1; j<=1; j++)
				numNeighbors(element.rowIndex()+i, element.columnIndex()+j, numOfNeighbors);
		return numOfNeighbors;
	}
	
	/**
	 * Finds the number of neighbors for the cell (row, col)
	 * @param row
	 * @param col
	 * @param numOfNeighbors output arg, should be a sparse array of ints
	 */
	private void numNeighbors(int row, int col, SparseArray<Integer> numOfNeighbors) {
		// if number of neighbors has already been calculated, return
		if(numOfNeighbors.elementAt(row, col) != numOfNeighbors.defaultValue())
			return;
		
		int neighbors = 0;
		// if you are in the current gen, subtract one because you will add 1 for yourself
		if((Boolean) currentGeneration.elementAt(row, col))
			neighbors--;
		
		// loop through neighbors, if they are in the current generation, add 1 to counter
		for(int i=-1; i<=1; i++)
			for(int j=-1; j<=1; j++)
				if((Boolean) currentGeneration.elementAt(row + i, col + j))
					neighbors++;
		
		// set number of neighbors
		numOfNeighbors.setValue(row, col, neighbors);
	}
	
	/**
	 * Check to see if a node is alive.  Acording to the rules of the game of life, an element is alive if:
	 * 1) The element is alive in the current generation and has 2 or 3 neighbors
	 * 2) The element is dead in the current generation and has 3 neighbors
	 * If one of these 2 conditions are not met, the cell is dead.
	 * @param numberOfNeighbors Sparse array of ints
	 * @return
	 */
	private boolean isAlive(MatrixElem<Integer> numberOfNeighbors) {
		if((Integer) numberOfNeighbors.value() == 3 || ((Integer)numberOfNeighbors.value() == 2 && (Boolean) currentGeneration.elementAt(numberOfNeighbors.rowIndex(), numberOfNeighbors.columnIndex())))
			return true;
		return false;
	}
	
	/**
	 * Main method for the game of life, takes 3 inputs:
	 * 1) Name of the input file
	 * 2) Name of the output file
	 * 3) Number of iterations between the input and the out
	 * If input file should be in vspace.1in file format.  Output file will be in the same format
	 * @param args arguments must be in the order String String int
	 * @throws IOException 
	 * 				if either the input file is not found or there was a problem writing to the output file
	 */
	public static void main(String[] args) throws IOException {
		long time = System.currentTimeMillis();//XXX
		// check proper number of args
		if(args.length != 3) {
			throw new IllegalArgumentException();
		}
		
		// create file and reader/writer
		File inFile = new File(args[0]);
		File outFile = new File(args[1]);
		Scanner reader;
		FileWriter writer;
		
		// open scanner and filewriter
		try {
			reader = new Scanner(inFile).useDelimiter("[,\\n]");
			writer = new FileWriter(outFile);
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Input file " + args[0] + " not found");
		} catch (IOException e) {
			throw new IllegalArgumentException("Output file " + args[1] + " can not be written to");
		}	
		
		// fill in first generation
		SparseArray<Boolean> array = new MySparseArray<Boolean>(false);
		for(; reader.hasNextLine(); reader.nextLine())
			array.setValue(reader.nextInt(), reader.nextInt(), true);
		reader.close();
		
		// create new game of life, find generation args[2]
		Life game = new Life(array);
		for(int i=0; i<Integer.parseInt(args[2]); i++) {
			/*if(i%1000 == 0)
				System.out.println(i);*/
			array = game.findNextGeneration();
		}
		
		// write solution to outfile
		AxisIterator<Boolean> ci = array.iterateRows();
		while(ci.hasNext()) {
			ElemIterator<Boolean> ei = ci.next();
			while(ei.hasNext()) {
				MatrixElem<Boolean> me = ei.next();
				writer.write(me.rowIndex() + "," + me.columnIndex() + "\n");
			}
		}
		writer.close();
		System.out.println(System.currentTimeMillis() - time);///XXX
	}
}