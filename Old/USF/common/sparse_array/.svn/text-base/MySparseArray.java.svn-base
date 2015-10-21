package sparse_array;

//TODO you could have taken out the row and column lists, just made them element lists with 0 row or column values and had them pointing to element list objects
/**
 * Implements SparseArray interface.  Even though it is genaricized, the limitations
 * of the interface (specifiying that the value must be an Object) mean that setValue
 * and element at will still work no matter what Object is put into the array (presuming
 * it is a SparseArray object). If the type of the object is MySparseArray, calling setValue
 * with an element that is not of type E will still work, but calling elementAt
 * on that object will cause a ClassCastException.
 * @author pjmcnamara2
 * NOTE comments finished
 */
class MySparseArray<E> implements SparseArray<E> {
	// Value of sentinel nodes in linked list
	public static final int SENTINEL_VALUE = -1;
	
	private E defaultValue;
	private AxisList<E> rl;
	private AxisList<E> cl;
	
	/**
	 * Creates a sparse array filled with the defaultValue
	 * @param defaultValue
	 */
	public MySparseArray(E defaultValue){
		this.defaultValue = defaultValue;
		rl = new AxisList<E>(false);
		cl = new AxisList<E>(true);
	}
	
	/**
	 * The type of value should be E.  The only reason this method takes an object
	 * is because that is what is specified in the interface (see not in class header).
	 */
	public void setValue(int row, int col, E value) {
		// array index must be greater then sentinel node value
		checkIndex(row);
		checkIndex(col);
		// if the value is equal to the default value, node should be removed
		if(value.equals(defaultValue)) {
			rl.remove(row, col);
			cl.remove(row, col);
		} else { //else, add a new node
			MatrixNode<E> tmp = new MatrixNode<E>(row, col, value);
			rl.add(tmp);
			cl.add(tmp);
		}
	}
	
	/**
	 * @throws ClassCastException if element was inserted at (row,col) is not of type E
	 */
	public E elementAt(int row, int col) {
		checkIndex(row);
		checkIndex(col);
		E result = rl.find(row, col); // note that ColList does not have a find method
		if(result == null) // find returns null if the element is not found
			return defaultValue;
		return result;
	}
	private void checkIndex(int index) {
		// array index must be greater then sentinel node value
		if(index <= SENTINEL_VALUE)
			throw new ArrayIndexOutOfBoundsException(index);
	}

	public E defaultValue() {
		return defaultValue;
	}
	
	public AxisIterator<E> iterateColumns() {
		return cl.getIterator();
	}

	public AxisIterator<E> iterateRows() {
		return rl.getIterator();
	}

	public String toString() {
		return "Printing Array:\n" + rl.toString() + "\n" + cl.toString() + "\nFinished printing array\n";
	}
	
/*XXX
 * 	public static void main(String[] args) {
		SparseArray sa = new MySparseArray<Integer>(0);
		for(int i=0; i<10000; i++) {
			for(int j=0; j<10000; j++) {
				sa.setValue(i, j, 1);
				sa.setValue(i, j, 0);
			}
			if(i%1000 == 0)
				System.out.println("finished " + i + "\n" + sa);
		}
	}*/
	
/*	// XXX
	public static void main(String[] args) {
		MySparseArray<Integer> sa = new MySparseArray<Integer>(0);
		System.out.println(sa + "\n");
		sa.setValue(1, 1, 1);
		System.out.println(sa);
		System.out.println("defval = " + sa.defaultValue + 
				"; (1,1)=" + sa.elementAt(1, 1) + "; (1,2)=" + sa.elementAt(1, 2)
				+ "; (1,3)=" + sa.elementAt(1, 3) + "; (1,4)=" + sa.elementAt(1, 4)
				+ "; (2,1)=" + sa.elementAt(2, 1) + "; (2,2)=" + sa.elementAt(2, 2) + "\n");
		sa.setValue(1, 3, 10);
		System.out.println(sa);
		System.out.println("defval = " + sa.defaultValue + 
				" (1,1)=" + sa.elementAt(1, 1) + "; (1,2)=" + sa.elementAt(1, 2)
				+ "; (1,3)=" + sa.elementAt(1, 3) + "; (1,4)=" + sa.elementAt(1, 4)
				+ "; (2,1)=" + sa.elementAt(2, 1) + "; (2,2)=" + sa.elementAt(2, 2) + "\n");
		sa.setValue(2, 1, 3);
		System.out.println(sa);
		System.out.println("defval = " + sa.defaultValue + 
				" (1,1)=" + sa.elementAt(1, 1) + "; (1,2)=" + sa.elementAt(1, 2)
				+ "; (1,3)=" + sa.elementAt(1, 3) + "; (1,4)=" + sa.elementAt(1, 4)
				+ "; (2,1)=" + sa.elementAt(2, 1) + "; (2,2)=" + sa.elementAt(2, 2) + "\n");
		sa.setValue(1, 2, 5);
		System.out.println(sa);
		System.out.println("defval = " + sa.defaultValue + 
				" (1,1)=" + sa.elementAt(1, 1) + "; (1,2)=" + sa.elementAt(1, 2)
				+ "; (1,3)=" + sa.elementAt(1, 3) + "; (1,4)=" + sa.elementAt(1, 4)
				+ "; (2,1)=" + sa.elementAt(2, 1) + "; (2,2)=" + sa.elementAt(2, 2) + "\n");
		sa.setValue(1, 2, 0);
		System.out.println(sa);
		System.out.println("defval = " + sa.defaultValue + 
				" (1,1)=" + sa.elementAt(1, 1) + "; (1,2)=" + sa.elementAt(1, 2)
				+ "; (1,3)=" + sa.elementAt(1, 3) + "; (1,4)=" + sa.elementAt(1, 4)
				+ "; (2,1)=" + sa.elementAt(2, 1) + "; (2,2)=" + sa.elementAt(2, 2) + "\n");
	}*/
}