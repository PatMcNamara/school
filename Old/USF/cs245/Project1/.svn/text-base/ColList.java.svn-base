import java.util.NoSuchElementException;

/**
 * This is a doubly linked list of columns (which are vertical ElementLists).
 * 
 * Notice that this class does not contain a method similer to
 * RowList.find(int,int), all finds should be done by the row list.
 * 
 * @author pjmcnamara2
 *
 * @param <E> Type of object contained in nodes of the Sparse Array (this list
 * 		doesn't actually store any values of type E).
 */
class ColList<E> {
	private ElementList<E> head, tail;

	/**
	 * Sets head and tail to a dummy column ElementList.
	 */
	public ColList() {
		head = tail = new ElementList<E>(MySparseArray.SENTINEL_VALUE, true);
	}
	
	/**
	 * Adds a node to the proper column.  If the column does not yet exists, it creates it. 
	 * @param e MatrixNode to be added
	 * @return true if the list was changed.
	 */
	public boolean add(MatrixNode<E> e) {
		ElementList<E> tmp = findClosestCol(e.col);
		
		// if the column exists, return the result of adding the node to that column
		if(tmp != null && tmp.value == e.col) {return tmp.add(e);}
		
		// else, create new column and insert into list
		ElementList<E> newElem = new ElementList<E>(e.col, true);
		if(tmp == null) { // if tmp is null; you will be inserting at tail
			newElem.next = null;
			newElem.prev = tail;
			tail.next =  newElem;
			tail = newElem;
		} else { // insert before tmp
			newElem.next = tmp;
			newElem.prev = tmp.prev;
			newElem.prev.next = newElem;
			tmp.prev = newElem;
		}
		// add element to newly created column
		return newElem.add(e);
	}

	/**
	 * Remove the element at the given row and column.  If this makes the list empty,
	 * it will also remove the column from this list.
	 * @param row
	 * @param col
	 * @return true if list was changed
	 */
	public boolean remove(int row, int col) {
		ElementList<E> tmp = findClosestCol(col);
		// if the column does not exist, there is no node to remove
		if(tmp == null || tmp.value != col) {return false;}
		
		if(tmp.remove(row)) { // will be true if an element was removed the list
			if(tmp.isEmpty()) {
				// if last element was removed, take this column out of the list
				tmp.prev.next = tmp.next;
				if(tmp == tail)
					tail = tmp.prev;
				else
					tmp.next.prev = tmp.prev;
			}
			return true;
		}
		return false;
	}
	
	/**
	 * Helper method that is used by the add and remove methods.  It returns
	 * either the column where the element is to be added or removed, or (if
	 * that does not exist), it will return the column that would be after the
	 * given column (if it existed).  If the given column is > the last column
	 * @param col
	 * @return
	 *      the member of this list with the smallest non-negitive value for the equation column.value() - col
	 *      if no element exists to qualify above condition, return null
	 */
	private ElementList<E> findClosestCol(int col) {
		ElementList<E> tmp;
		for(tmp = head; tmp != null && tmp.value < col; tmp = tmp.next);
		return tmp;
	}
	
	/**
	 * Get a column iterator.
	 * @return ColumnIterator for this list.
	 */
	public ColumnIterator getIterator() {
		return new CIterator();
	}
	
	public String toString() {
		String ret = "Printing Cols:\n";
		for(ElementList<E> tmp = head; tmp != null; tmp = tmp.next) {
			ret += tmp;
		}
		ret += "Finished Printing Cols";
		return ret;
	}
	
	/**
	 * Implementation of the ColumnIterator abstract class
	 * @author pjmcnamara2
	 */
	private class CIterator extends ColumnIterator {
		private ElementList<E> next;
		
		public CIterator() {
			next = head.next;
		}
		@Override
		public boolean hasNext() {
			if(next == null)
				return false;
			return true;
		}
		@Override
		public ElemIterator next() {
			if(!hasNext())
				throw new NoSuchElementException();
			ElemIterator retVal = next.getIterator();
			next = next.next;
			return retVal;
		}
	}
}