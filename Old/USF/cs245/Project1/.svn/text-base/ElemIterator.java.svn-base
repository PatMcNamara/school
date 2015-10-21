/**
 * Iterator that can be used to iterate through a given row or column.
 * @author pjmcnamara2
 */
abstract class ElemIterator implements java.util.Iterator<MatrixElem>
{
	/**
	 * @return
	 * 		true if this iterator is iterating through a row (that is, if this
	 * 		iterator was obtained from a call to next from a ColumnIterator)
	 */
    public abstract boolean iteratingRow();
    /**
     * @return
     * 		Returns true if this iterator is iterating through a column (that
     * 		is, if this iterator was obtained from a call to next from a RowIterator)
     */
    public abstract boolean iteratingCol();
    /**
     * @return
     * 		If we are iterating through a row, return the index of the row we
     * 		are traversing. If we are iterating through a column, return the
     * 		index of the column we are traversing
     */
    public abstract int nonIteratingIndex();
    public abstract MatrixElem next();
    public abstract boolean hasNext();
    public void remove()
    {
        throw new UnsupportedOperationException();
    }
}
