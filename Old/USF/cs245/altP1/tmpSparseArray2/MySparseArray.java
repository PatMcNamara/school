package tmpSparseArray2;

import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

public class MySparseArray implements SparseArray {
	Object defaultValue;
	RowColList rl, cl;
	
	public MySparseArray(Object defaultValue) {
		this.defaultValue = defaultValue;
		rl = new RowColList();
		cl = new RowColList();
	}
	
	@Override
	public Object defaultValue() {
		return defaultValue;
	}

	@Override
	public Object elementAt(int row, int col) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public ColumnIterator iterateColumns() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public RowIterator iterateRows() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void setValue(int row, int col, Object value) {
		MatrixElem tmp = new MyMatrixElem(row, col, value);
		RowIterator row = rl.iterator();
		
	}
	
	/**
	 * Indexes should start at 1
	 * @author pat
	 *
	 */
	class RowColList implements SortedSet<Node> {

		Node head, tail;
		
		public RowColList() {
			head = tail = new Node(0, null);
		}
		
		@Override
		public Comparator<? super Node> comparator() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Node first() {
			throw new UnsupportedOperationException();
		}

		@Override
		public SortedSet<Node> headSet(Node toElement) {
			throw new UnsupportedOperationException();
		}

		@Override
		public Node last() {
			return tail;
		}

		@Override
		public SortedSet<Node> subSet(Node fromElement, Node toElement) {
			throw new UnsupportedOperationException();
		}

		@Override
		public SortedSet<Node> tailSet(Node fromElement) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean add(Node e) {
			Node tmp = head;
			while(tmp != null && tmp.compareTo(e) < 0)
				tmp = tmp.next();
			if(tmp == null) {
				e.setNext(tmp);
				e.setPrev(last());
				last().setNext(e);
				tail = e;
				return true;
			}
			if(tmp.equals(e))
				return false;
			System.out.println(tmp.value);
			e.setNext(tmp);
			tmp.prev().setNext(e);
			e.setPrev(tmp.prev());
			tmp.setPrev(e);
			return true;
		}

		@Override
		public boolean addAll(Collection<? extends Node> c) {
			boolean wasChanged = false;
			for(Node e: c)
				wasChanged |= add(e);
			return wasChanged;
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean contains(Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isEmpty() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Iterator<Node> iterator() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int size() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Object[] toArray() {
			throw new UnsupportedOperationException();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			throw new UnsupportedOperationException();
		}
		public String toString() {
			Node tmp = head.next();
			String retVal = "[ ";
			while(tmp != null) {
				retVal += tmp.getValue() + " ";
				tmp = tmp.next();
			}
			retVal += "]";
			return retVal;
		}
	}
	
	class Node implements Comparable {
		int value;
		Node next, prev;
		ElemList list; //Should contain head value (MatrixElem)
		
		public Node(int index, ElemList value) {
			this.value = index;
			list = value;
			next = null;
			prev = null;
		}
		
		public Node next() {
			return next;
		}
		
		public Node prev() {
			return prev;
		}

		public void setPrev(Node prev) {
			this.prev = prev;
		}

		public int getValue() {
			return value;
		}

		public ElemList getList() {
			return list;
		}

		public void setNext(Node next) {
			this.next = next;
		}

		@Override
		public int compareTo(Object o) {
			return this.getValue() - ((Node)o).getValue();
		}
	}
	class ElemList implements SortedSet<MyMatrixElem> { //TODO this would actually be better if it implemented MatrixElem (or have it be a generic that uses <? implements MatirxElem)
		MyMatrixElem head, tail;
		boolean isColumn;
		
		public ElemList(int index, boolean isColumn) {
			this.isColumn = isColumn;
			//create dummy node
			if(isColumn)
				head = tail = new MyMatrixElem(0, index, null);
			else
				head = tail = new MyMatrixElem(index, 0, null); 
		}
		
		@Override
		public Comparator<? super MatrixElem> comparator() {
			throw new UnsupportedOperationException();
		}

		@Override
		public MyMatrixElem first() {
			throw new UnsupportedOperationException();
		}

		@Override
		public SortedSet<MyMatrixElem> headSet(MyMatrixElem toElement) {
			throw new UnsupportedOperationException();
		}

		@Override
		public MyMatrixElem last() {
			throw new UnsupportedOperationException();
		}

		@Override
		public SortedSet<MyMatrixElem> subSet(MyMatrixElem fromElement,
				MyMatrixElem toElement) {
			throw new UnsupportedOperationException();
		}

		@Override
		public SortedSet<MyMatrixElem> tailSet(MyMatrixElem fromElement) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean add(MyMatrixElem e) {
			MyMatrixElem mme = (MyMatrixElem) e;
			if(isColumn)//TODO this could be eleminated if this class extended MatrixElem.  Then you could just have 2 different classes implementing MatrixElem (one for column ops and one for row ops [the might actually require you to use an abstract class as a MatrixNode because you would still need both data sets, but they would each use differant next and prev methods])(another advantage of this is you can get rid of the isColumn element, as you would still use the same methods)
				return addRow(mme);
			else
				return addCol(mme);
		}
		
		private boolean addRow(MyMatrixElem e) {
			MyMatrixElem tmp = head;
			while(tmp != null && tmp.rowIndex() < e.rowIndex())
				tmp = tmp.nextRow();
			if(tmp == null) {
				e.setNextRow(tmp);
				e.setPrevRow(last());
				last().setNextRow(e);
				tail = e;
				return true;
			}
			if(tmp.equals(e))
				return false;
			e.setNextRow(tmp);
			tmp.prevRow().setNextRow(e);
			e.setPrevRow(tmp.prevRow());
			tmp.setPrevRow(e);
			return true;
		}
		
		private boolean addCol(MyMatrixElem e) {
			MyMatrixElem tmp = head;
			while(tmp != null && tmp.columnIndex() < e.columnIndex())
				tmp = tmp.nextCol();
			if(tmp == null) {
				e.setNextCol(tmp);
				e.setPrevCol(last());
				last().setNextCol(e);
				tail = e;
				return true;
			}
			if(tmp.equals(e))
				return false;
			System.out.println(tmp.value);
			e.setNextCol(tmp);
			tmp.prevCol().setNextCol(e);
			e.setPrevCol(tmp.prevCol());
			tmp.setPrevCol(e);
			return true;
		}

		@Override
		public boolean addAll(Collection<? extends MyMatrixElem> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public void clear() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean contains(Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean containsAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isEmpty() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Iterator<MyMatrixElem> iterator() {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean remove(Object o) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean removeAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean retainAll(Collection<?> c) {
			throw new UnsupportedOperationException();
		}

		@Override
		public int size() {
			throw new UnsupportedOperationException();
		}

		@Override
		public Object[] toArray() {
			throw new UnsupportedOperationException();
		}

		@Override
		public <T> T[] toArray(T[] a) {
			throw new UnsupportedOperationException();
		}
		
	}

	class MyMatrixElem implements MatrixElem {
		int column, row;
		Object value;
		
		MyMatrixElem nextCol, prevCol, nextRow, prevRow;
		
		public MyMatrixElem(int row, int column, Object value) {
			this.column = column;
			this.row = row;
			this.value = value;
		}
		
		@Override
		public int columnIndex() {
			return column;
		}

		@Override
		public int rowIndex() {
			return row;
		}

		@Override
		public Object value() {
			return value;
		}

		public MyMatrixElem nextCol() {
			return nextCol;
		}

		public void setNextCol(MyMatrixElem nextCol) {
			this.nextCol = nextCol;
		}

		public MyMatrixElem prevCol() {
			return prevCol;
		}

		public void setPrevCol(MyMatrixElem prevCol) {
			this.prevCol = prevCol;
		}

		public MyMatrixElem nextRow() {
			return nextRow;
		}

		public void setNextRow(MyMatrixElem nextRow) {
			this.nextRow = nextRow;
		}

		public MyMatrixElem prevRow() {
			return prevRow;
		}

		public void setPrevRow(MyMatrixElem prevRow) {
			this.prevRow = prevRow;
		}
		
	}

}
