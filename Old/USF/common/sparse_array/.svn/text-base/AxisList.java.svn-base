package sparse_array;

import java.util.NoSuchElementException;

class AxisList<E> {
	private ElementList<E> head, tail;
	private boolean isColumn;//Is it a list of columns?
	
	/**
	 * Sets head and tail to a dummy row ElementList.
	 */
	public AxisList(boolean isColumn) {
		this.isColumn = isColumn;
		head = tail = new ElementList<E>(MySparseArray.SENTINEL_VALUE, isColumn);
	}
	//TODO both add and remove could use the find method
	//TODO RowList and colList could become joined into AxisList
	/**
	 * Adds a node to the proper row.  If the row does not yet exists, it creates it. 
	 * @param e MatrixNode to be added
	 * @return true if the list was changed.
	 */
	public boolean add(MatrixNode<E> e) {
		int index;
		if(isColumn)
			index = e.col;
		else
			index = e.row;
		
		ElementList<E> tmp = findClosestNode(index);
		
		// if the row exists, return the result of adding the node to that row
		if(tmp != null && tmp.value == index) {return tmp.add(e);}
		
		// else, create new row and insert into list
		ElementList<E> newElem = new ElementList<E>(index, isColumn);
		
		if(tmp == null) { // if tmp is null; you will be inserting at tail
			newElem.next = null;
			newElem.prev = tail;
			tail.next = newElem;
			tail = newElem;
		} else { // insert before tmp
			newElem.next = tmp;
			newElem.prev = tmp.prev;
			newElem.prev.next = newElem;
			tmp.prev = newElem;
		}
		// add element to newly created row
		return newElem.add(e);
	}

	/**
	 * Remove the element at the given row and column.  If this makes the list empty,
	 * it will also remove the row from this list.
	 * @param row
	 * @param col
	 * @return true if list was changed
	 */
	public boolean remove(int row, int col) {
		int index;
		if(isColumn)
			index = col;
		else
			index = row;
		
		ElementList<E> tmp = findClosestNode(index);
		// if the row does not exist, there is no node to remove
		if(tmp == null || tmp.value != index) {return false;}
		
		if(tmp.remove(col)) { // will be true if an element was removed the list FIXME
			if(tmp.isEmpty()) {
				// if last element was removed, take this row out of the list
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
	 * Find the value of the element at the given position.  If node doesn't exsist, returns null
	 * Note that ColList does not contain a similer method.
	 * @param row
	 * @param col
	 * @return
	 */
	public E find(int row, int col) {
		if(isColumn) {
			ElementList<E> tmp = findClosestNode(col);
			if(tmp == null || tmp.value != col)
				return null;
			return tmp.find(row);
		} else {
			ElementList<E> tmp = findClosestNode(row);
			if(tmp == null || tmp.value != row)
				return null;
			return tmp.find(col);
		}
	}
	
	/**
	 * Helper method that is used by the add and remove methods.  It returns either the row where the element is to be added or removed, or (if that does not exist), it will return the row that would be after the given row (if it existed).  If the given row is > the last row in the list, return null.
	 * @param row
	 * @return
	 *      the member of this list with the smallest non-negitive value for the equation row.value() - row
	 * 		if no element exists to qualify above condition, return null
	 */
/*	private ElementList<E> findClosestRow(int row){
		ElementList<E> tmp;
		for(tmp = head; tmp != null && tmp.value < row; tmp = tmp.next);
		return tmp;
	}*/
	
	/**
	 * Helper method that is used by the add and remove methods.  It returns either the row where the element is to be added or removed, or (if that does not exist), it will return the row that would be after the given row (if it existed).  If the given row is > the last row in the list, return null.
	 * @param row
	 * @return
	 *      the member of this list with the smallest non-negitive value for the equation row.value() - row
	 * 		if no element exists to qualify above condition, return null
	 */
/*	private ElementList<E> findClosestCol(int col){
		ElementList<E> tmp;
		for(tmp = head; tmp != null && tmp.value < col; tmp = tmp.next);
		return tmp;
	}*/
	
	/**
	 * Helper method that is used by the add and remove methods.  It returns either the row where the element is to be added or removed, or (if that does not exist), it will return the row that would be after the given row (if it existed).  If the given row is > the last row in the list, return null.
	 * @param row
	 * @return
	 *      the member of this list with the smallest non-negitive value for the equation row.value() - row
	 * 		if no element exists to qualify above condition, return null
	 */
	private ElementList<E> findClosestNode(int index){
		ElementList<E> tmp;
		for(tmp = head; tmp != null && tmp.value < index; tmp = tmp.next);
		return tmp;
	}
	
	
	/**
	 * Get a row iterator.
	 * @return RowIterator for this list.
	 */
	public AxisIterator<E> getIterator() {
		return new RCIterator();
	}
	
	public String toString() {
		String ret = "";
		if(isColumn)
			ret += "Printing Cols:\n";
		else
			ret += "Printing Rows:\n";
		
		for(ElementList<E> tmp = head; tmp != null; tmp = tmp.next)
			ret += tmp;
		
		if(isColumn)
			ret += "Finished Printing Cols";
		else
			ret += "Finished Printing Rows";
		
		return ret;
	}
	
	private class RCIterator extends AxisIterator<E> {
		private ElementList<E> next;
		
		public RCIterator() {
			next = head.next;
		}
		@Override
		public boolean hasNext() {
			if(next == null)
				return false;
			return true;
		}
		@Override
		public ElemIterator<E> next() {
			if(!hasNext())
				throw new NoSuchElementException();
			ElemIterator<E> retVal = next.getIterator();
			next = next.next;
			return retVal;
		}
	}
}
