package sparseArray;

public interface SparseArray {
	/**
	 * @return default value for the sparse array
	 */
    public Object defaultValue();
    /**
     * @return
     * 		iterator that can be used to iterate through the
     * 		rows of the array
     */
    public RowIterator iterateRows();
    /**
     * @return
	 * 		iterator that can be used to iterate through the
	 * 		columns of the array
     */
    public ColumnIterator iterateColumns();

    /**
     * @param row
     * @param col
     * @return
     * 		object stored at (row,col), if such an element exists,
     * 		or the default value, if not such element exists.
     */
    public Object elementAt(int row, int col);
    /**
     * Sets the value of the matrix at position (row,col).
     * @param row
     * @param col
     * @param value
     */
    public void setValue(int row, int col, Object value);
}