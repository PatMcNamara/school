package sparse_array;

public interface SparseArray<E> 
{	
	/**
	 * Note that it is an object, so we could really have any default value that we wanted.
	 * @return the default value for the sparse array.
	 */
    public E defaultValue();
    /**
     * @return an iterator that can be used to iterate through the rows of the array.
     */
    public AxisIterator<E> iterateRows();
    /**
     * @return an iterator that can be used to iterate through the columns of the array.
     */
    public AxisIterator<E> iterateColumns();
    /**
     * @param row
     * @param col
     * @return the object stored at (row,col), if such an element exists, or the default value, if not such element exists.
     */
    public E elementAt(int row, int col);
    /**
     * Sets the value of the matrix at position (row,col), adding new linked list element(s) as necessary.
     * @param row
     * @param col
     * @param value
     */
    public void setValue(int row, int col, E value);
}
