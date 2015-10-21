package sparse_array;

import java.util.NoSuchElementException;
/**
 * This class can represent either a specific column or a specific row within
 * the sparse array.  It is used as both elements of either row or column list
 * and is it's self a linked list containing MatrixNodes.
 * 
 * @author pjmcnamara2
 *
 * @param <E> type of object contained by elements of this list (this list doesn't actually store any values of type E)
 * NOTE finished commenting
 */
class ElementList<E> {
	// These variables are used by either ColList or RowList
	ElementList<E> prev, next;
	int value;
	// These variables keep track of this classes linked list
	private MatrixNode<E> head, tail;
	private boolean isColumn;
	
	/**
	 * @param value
	 * 		this is either the column or row value (int) that will be the same
	 * 		for all elements of this list
	 * @param isColumn is this list a column or a row
	 */
	public ElementList(int value, boolean isColumn) {
		this.isColumn = isColumn;
		this.value = value;
		if(isColumn)
			head = tail = new MatrixNode<E>(MySparseArray.SENTINEL_VALUE, value, null);
		else
			head = tail = new MatrixNode<E>(value, MySparseArray.SENTINEL_VALUE, null);
	}
	
	
	/**
	 * Add a given MatrixNode to the element list.
	 * Assumes that said MatrixNode actually belongs in this list (ie. if isColumn then
	 * the method won't actually check that the MatrixNode belongs in this column, and
	 * likewise with rows).
	 * @param e
	 * @return true if list was modified.
	 */
	public boolean add(MatrixNode<E> e) {
		// Find element or (if it doesn't exist) the next greater one
		MatrixNode<E> tmp = head;
		//TODO use findElementAt submethod
		while(tmp != null && tmp.compareTo(e, isColumn) < 0)
			tmp = tmp.next(isColumn);
		
		if(tmp == null) {// insert at tail of list
			e.setNext(null, isColumn);
			e.setPrev(tail, isColumn);
			tail.setNext(e, isColumn);
			tail = e;
		} else if(tmp.compareTo(e, isColumn) == 0) { // node already exists
			if(tmp.value == e.value) {return false;}
			tmp.value = e.value;
		} else { // insert before tmp
			e.setNext(tmp, isColumn);
			e.setPrev(tmp.prev(isColumn), isColumn);
			tmp.prev(isColumn).setNext(e, isColumn);
			tmp.setPrev(e, isColumn);
		}
		return true;
	}
	
	/**
	 * Removes element at a given index.
	 * Assumes that said MatrixNode actually belongs in this list (ie. if isColumn then
	 * the method won't actually check that the MatrixNode belongs in this column, and
	 * likewise with rows).
	 * @param index
	 * @return true if list was modified
	 */
	public boolean remove(int index) {
		// Find element or (if it doesn't exist) the next greater one
		MatrixNode<E> tmp = head;
		//TODO use findElementAt method
		while(tmp != null && tmp.compareTo(index, isColumn) < 0)
			tmp = tmp.next(isColumn);
		
		// If index is not in list, return false
		if(tmp == null || tmp.compareTo(index, isColumn) != 0)
			return false;
		
		// Remove tmp
		if(tmp == tail)
			tail = tmp.prev(isColumn);
		else
			tmp.next(isColumn).setPrev(tmp.prev(isColumn), isColumn);
		tmp.prev(isColumn).setNext(tmp.next(isColumn), isColumn);
		return true;
	}
	
	//TODO this could be done much better by using MatrixNode's built in methods (including tmp.compairTo), also it is should be structured more like Col or RowList
	/**
	 * Helper method.
	 * @param index Index of desired element
	 * @return either the node at the given index or (if no node exists at index) null
	 */
	private MatrixNode<E> findElementAt(int index){
		MatrixNode<E> tmp;
		if(isColumn) {
			for(tmp = head; tmp != null && tmp.row < index; tmp = tmp.next(isColumn));
			if(tmp == null || tmp.row != index)
				return null;
		} else {
			for(tmp = head; tmp != null && tmp.col < index; tmp = tmp.next(isColumn));
			if(tmp == null || tmp.col != index)
				return null;
		}
		return tmp;
	}
	
	/**
	 * Find the value of a given index within the list
	 * @param index index of desired value
	 * @return value of Matrix elem at given index, will return null if index does not exist
	 */
	public E find(int index) {
		MatrixNode<E> tmp = findElementAt(index);
		if(tmp == null)
			return null;
		return tmp.value();
	}

	/**
	 * Tell if the list is empty
	 * @return true if list is empty
	 */
	public boolean isEmpty() {
		if(head == tail)
			return true;
		return false;
	}
	
	/**
	 * Get iterator that can be used to traverse the list from outside the sparse array
	 * @return
	 */
	public ElemIterator<E> getIterator() {
		return new EIterator();
	}
	
	public String toString() {
		String ret;
		if(isColumn)
			ret = "Column ";
		else
			ret = "Row ";
		ret += "Value = " + value + "\n";
		if(isColumn)
			for(MatrixNode<E> tmp = head; tmp != null; tmp = tmp.nextRow)//Col
				ret += tmp.toString();
		else
			for(MatrixNode<E> tmp = head; tmp != null; tmp = tmp.nextCol)//Row
				ret += tmp.toString();
		return ret;
	}
	
	/**
	 * Iterator that extend the ElemIterator abstract class
	 * @author pjmcnamara2
	 */
	class EIterator extends ElemIterator<E> {
		private MatrixNode<E> next;
		
		public EIterator() {
			next = head.next(isColumn);
		}
		@Override
		public boolean hasNext() {
			if(next == null)
				return false;
			return true;
		}

		@Override
		public boolean iteratingCol() {
			if(isColumn)
				return true;
			return false;
		}

		@Override
		public boolean iteratingRow() {
			return !iteratingCol();
		}

		@Override
		public MatrixElem<E> next() {
			if(!hasNext())
				throw new NoSuchElementException();
			MatrixElem<E> retVal = next;
			next = next.next(isColumn);
			return retVal;
		}

		@Override
		public int nonIteratingIndex() {
			return value;
		}
	}
}