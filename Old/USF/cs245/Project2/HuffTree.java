/**
 * This class is used to build and access a huffman tree.
 * @author pjmcnamara2
 */
public class HuffTree implements Comparable<HuffTree> {
	private HuffNode root;
	/**
	 * This constructor takes in a huffman coded file and creates a huffman tree
	 * out of it.
	 * @param inFile
	 * 		When method finishes, the next bit returned by inFile will be the
	 * 		first bit after the end of the huffman tree (presumibly to be passed
	 * 		into decode method).
	 */
	public HuffTree(BinaryFile inFile) {
		root = buildTree(inFile);
	}
	/**
	 * This constructor builds a new Huffman tree with a single node that
	 * contains the character and number specified by the inputs.
	 * @param character
	 * @param num 
	 * 		Number of times that character ocoures in the file (when reading,
	 * 		this is ignored and can be set to an arbitrary value, 0 in this
	 * 		method).
	 */
	public HuffTree(Character character, int num) {
		root = new HuffNode(character, num);
	}
	/**
	 * This constructor is used when building the Huffman tree, it takes 2
	 * Huffman trees and creates a new node who's left and right subchildren
	 * are specified in the inputs and who's number is equal to value obtained
	 * by adding the number of the right and left subchildren.
	 * @param leftChild
	 * @param rightChild
	 */
	public HuffTree(HuffTree leftChild, HuffTree rightChild) {
		this(null, leftChild.root.num + rightChild.root.num);
		root.rightChild = rightChild.root;
		root.leftChild = leftChild.root;
	}
	
	/**
	 * This helper method takes in a Huffman coded binary file and creates a
	 * huffman tree.   
	 * @param inFile
	 * 		When this method finishes, the next bit in the input will be the
	 * 		node after the end of the huffman tree.
	 * @return	root of the newly created Huffman Tree.
	 */
	private HuffNode buildTree(BinaryFile inFile) {
		if(!inFile.readBit()) // base case, at a leaf
			return new HuffNode(inFile.readChar(), 0);
		HuffNode tmpNode = new HuffNode(null, 0);
		tmpNode.leftChild = buildTree(inFile);
		tmpNode.rightChild = buildTree(inFile);
		return tmpNode;
	}
	/**
	 * This method builds a Huffman table out of this Huffman tree.  This method
	 * just calls the buildTable method of root.
	 * @param huffTable
	 * 		Should be an array that holds 256 Strings.
	 */
	public void buildTable(String[] huffTable) {
		if(root.character != null)
			root.buildTable(huffTable, "0");
		else
			root.buildTable(huffTable, "");
	}
	/**
	 * Writes this Huffman tree out to a binary file.
	 * @param file
	 */
	public void write(BinaryFile file) {
		root.write(file);
	}
	/**
	 * This method will decode the next character of given compressed file
	 * using this Huffman tree.  After this method finishes, inFile will point
	 * to the start of the encoded character after the one returned.
	 * @param inFile
	 * @return
	 */
	public char decode(BinaryFile inFile) {
		return root.decode(inFile);
	}
	
	/**
	 * Compares the number of the root node of the specified Huffman tree
	 * to the number of the root node of this Huffman tree.
	 */
	public int compareTo(HuffTree o) {
		return root.num - o.root.num;
	}
	/**
	 * Only true if obj is a huffman tree and obj.root.num == this.root.num
	 * Note that since this method relies on the compareTo method, if root.num
	 * is 0 for either Huffman tree, this method will not work correctly.
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof HuffTree)
			if(this.compareTo((HuffTree) obj) == 0)
				return true;
		return false;
	}
	/**
	 * Method prints this Huffman tree by calling the print helper method.
	 */
	@Override
	public String toString() {
		return print(root, 0);
	}
	/**
	 * This recursive helper method will print all the elements in the tree.
	 * @param tree
	 * @param offset
	 * 		Level this node is on (used for indentation)
	 * @return
	 */
	private String print(HuffNode tree, int offset) {
		String retVal = "";
		if(tree == null)
			return retVal;
		for(int i = 0; i < offset; i++)
			retVal += "    ";
		retVal += tree.toString() + "\n";
		retVal += print(tree.leftChild, offset+1);
		retVal += print(tree.rightChild, offset+1);
		return retVal;
	}

	/**
	 * This class represents a single node in the huffman tree.
	 * @author pjmcnamara2
	 * 
	 */
	private class HuffNode {
		private HuffNode rightChild = null, leftChild = null;
		private Character character; // not used by interior nodes
		private int num; // used in compression
		
		/**
		 * Creates a new node.
		 * @param character
		 * 		If this is an interior node, should be set to null
		 * @param num
		 * 		If you are decompressing, this number will be ignored
		 */
		public HuffNode(Character character, int num) {
			this.character = character;
			this.num = num;
		}
		
		/**
		 * This method will recursively fill Huffman table with this Huffman
		 * tree.  The left child has the prefix 0, right child has the prefix 1.
		 * @param huffTable
		 * @param prefix
		 */
		public void buildTable(String[] huffTable, String prefix) {
			if(character != null)
				huffTable[(int) character] = prefix;
			else {
				leftChild.buildTable(huffTable, prefix + "0");
				rightChild.buildTable(huffTable, prefix + "1");
			}
		}
		
		/**
		 * Recursive method to write this Huffman tree to specified binary file.
		 * @param file
		 */
		public void write(BinaryFile file) {
			if(character != null) { // External node
				file.writeBit(false);
				file.writeChar(character);
			} else { // Internal node
				file.writeBit(true);
				leftChild.write(file);
				rightChild.write(file);
			}
		}
		
		/**
		 * Recursive function that gives the next character in the given file.
		 * @param inFile
		 * @return
		 */
		private char decode(BinaryFile inFile) {
			if(character != null) // base case, at a leaf
				return character;
			if(inFile.readBit()) // go left
				return rightChild.decode(inFile);
			return leftChild.decode(inFile);
		}
		
		public String toString() {
			String retVal = "Ascii Value: ";
			if(character != null)
				retVal += (int) character.charValue();
			retVal += ", Number of apperances: " + num;
			return retVal;
		}
	}
}