/**
 * Implements SparseArray interface.  Even though it is genaricized, the limitations
 * of the interface (specifiying that the value must be an Object) mean that setValue
 * and element at will still work no matter what Object is put into the array (presuming
 * it is a SparseArray object). If the type of the object is MySparseArray, calling setValue
 * with an element that is not of type E will still work, but calling elementAt
 * on that object will cause a ClassCastException.
 * @author pjmcnamara2
 */
class MySparseArray<E> implements SparseArray {
	// Value of sentinel nodes in linked list
	public static final int SENTINEL_VALUE = -1;
	
	private E defaultValue;
	private RowList<E> rl;
	private ColList<E> cl;
	
	/**
	 * Creates a sparse array filled with the defaultValue
	 * @param defaultValue
	 */
	public MySparseArray(E defaultValue){
		this.defaultValue = defaultValue;
		rl = new RowList<E>();
		cl = new ColList<E>();
	}
	
	/**
	 * The type of value should be E.  The only reason this method takes an object
	 * is because that is what is specified in the interface (see not in class header).
	 */
	public void setValue(int row, int col, Object value) {
		// array index must be greater then sentinel node value
		if(row <= SENTINEL_VALUE)
			throw new ArrayIndexOutOfBoundsException(row);
		if(col <= SENTINEL_VALUE)
			throw new ArrayIndexOutOfBoundsException(col);
		// if the value is equal to the default value, node should be removed
		if(value.equals(defaultValue)) {
			rl.remove(row, col);
			cl.remove(row, col);
		}else { //else, add a new node
			MatrixNode<E> tmp = new MatrixNode<E>(row, col, (E) value);
			rl.add(tmp);
			cl.add(tmp);
		}
	}
	
	/**
	 * @throws ClassCastException if element was inserted at (row,col) is not of type E
	 */
	public E elementAt(int row, int col) {
		E result = rl.find(row, col); // note that ColList does not have a find method
		if(result == null) // find returns null if the element is not found
			return defaultValue;
		return result;
	}
	public E defaultValue() {
		return defaultValue;
	}
	public ColumnIterator iterateColumns() {
		return cl.getIterator();
	}
	public RowIterator iterateRows() {
		return rl.getIterator();
	}
	public String toString() {
		return rl.toString() + "\n";
	}
}