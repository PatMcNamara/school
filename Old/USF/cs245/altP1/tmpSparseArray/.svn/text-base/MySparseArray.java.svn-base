package tmpSparseArray;

import java.util.*;

public class MySparseArray implements SparseArray {
	RowColList rowList, colList;
	Object defaultValue;
	
	public MySparseArray(Object defaultValue) {
		this.defaultValue = defaultValue;
		rowList = new RowColList(true);
		colList = new RowColList(false);
	}
	public MySparseArray(Object defaultValue, int row, int col, Object value) {
		this(defaultValue);
		Element tmp = new Element(col, row, value);
		MatrixList tmpList;
		rowList.add(tmpList = new MatrixList());
		tmpList.add(tmp);
		colList.add(tmpList = new MatrixList());
		tmpList.add(tmp);
	}
	
	@Override
	public Object defaultValue() {
		return defaultValue;
	}

	@Override
	public Object elementAt(int row, int col) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ColumnIterator iterateColumns() {
		return (ColumnIterator) colList.iterator();
	}

	@Override
	public RowIterator iterateRows() {
		return (RowIterator) rowList.iterator();
	}

	//TODO this will iterater all the way through the list, even though it is already ordered
	//Large parts of this method could probably be moved into the actual lists as a find method
	@Override
	public void setValue(int row, int col, Object value) {
		// check to see if row and column exist
		MatrixList rml = rowList.find(row), cml = colList.find(col);
		MatrixElem tmp;
		
		//NOTE possible optimization here, if the element is being deleted, you shouldn't create unnecissary elements
		
		// if row doesn't exist, create it
		Iterator iterator = rowList.iterator();
		if(rml == null)
			for(int i=0; ; i++) {
				tmp = (MatrixElem) iterator.next();//NOTE this and next line can be condensed
				if(tmp.rowIndex() > row) {
					rml = new MatrixList();
					rowList.add(i, rml);
					break;
				}
			}
		
		// if column, doesn't exist, create it
		iterator = colList.iterator();
		if(cml == null)
			for(int i=0; ; i++) {
				tmp = (MatrixElem) iterator.next();//NOTE this and next line can be condensed
				if(tmp.columnIndex() > col) {
					rml = new MatrixList();
					colList.add(i, rml);
					break;
				}
			}
		
		MatrixElem matrixElem;
		// see if element exist in the column
		matrixElem = rml.find(col);
		// if the element exists
		if(matrixElem != null) {
			// if value = defaultValue, delete node
			if(value == defaultValue)
				;//TODO delete node
			((Element) matrixElem).setValue(value); //TODO this might be a bit hacky because it doesn't use the interface methods, instead, you could always just make a new object and upgrade all the pointers
		} else {
			//create new element
			matrixElem = new Element(col, row, value);
			// find row of element after this
			iterator = rml.iterator();
			for(int i=0; ; i++) {
				tmp = (MatrixElem) iterator.next();
				if(tmp.columnIndex() > col) {
					rml.add(row, matrixElem);
					break;
				}
			}
			iterator = cml.iterator();
			for (int i=0; ; i++) {
				tmp = (MatrixElem) iterator.next();
				if (tmp.rowIndex() > col) {
					cml.add(i, matrixElem);
					break;
				}
				
			}
		}
		
/*		if(!value.equals(element.value())) {
			if(!value.equals(defaultValue()))
				((Element) element).setValue(value); //TODO this might be a bit hacky because it doesn't use the interface methods, instead, you could always just make a new object and upgrade all the pointers
			else
				;//TODO remove this node
		} // else, value is already set
*/	}

	
	public class Element implements MatrixElem {
		MatrixElem prevCol, prevRow, nextCol, nextRow;
		int column, row;
		Object value;
		
		public Element(int column, int row, Object value) {
			this.column = column;
			this.row = row;
			this.value = value;
		}
/*		public Element(Element oldElement, Object newValue) {
			this.value = newValue;
			column = oldElement.columnIndex();
			row = oldElement.rowIndex();
			oldElement.clone()
			nextCol = oldElement.getNextCol();
			nextRow = oldElement.getNextRow();
			prevCol = oldElement.getPrevCol();
			prevRow = oldElement.getPrevRow();
			
			
		}*/
		@Override
		public int columnIndex() {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public int rowIndex() {
			// TODO Auto-generated method stub
			return 0;
		}
		@Override
		public Object value() {
			// TODO Auto-generated method stub
			return null;
		}
		public void setValue(Object value) {
			this.value = value;
		}
/*		public MatrixElem getPrevCol() {
			return prevCol;
		}
		public MatrixElem getPrevRow() {
			return prevRow;
		}
		public MatrixElem getNextCol() {
			return nextCol;
		}
		public MatrixElem getNextRow() {
			return nextRow;
		}*/
	}
	
	//TODO iterable should return a row or column list (an iterator)
	//TODO you might not need the rowList section, it could just be kept track of in the top class
	public class RowColList extends LinkedList<MatrixList> { //implements Iterable{//<ElemIterator> { // <MatrixList> {//TODO check to see what iterator should be
		private boolean rowList; 
		
		public RowColList(boolean rowList) {
			this.rowList= rowList;
		}
		
		@Override
		public Iterator iterator() {
			if(rowList)
				return null;//TODO make a new row iterator
			return null; //TODO make a new column iterator
		}
		
		public MatrixList find(int index) {//TODO probably be renaimed to get
			for(MatrixList tmp: this)
				if(tmp.element().rowIndex() == index)
					return tmp;
			return null;
		}
		public class CIterator extends ColumnIterator {

			@Override
			public boolean hasNext() {
				// TODO Auto-generated method stub
				return false;
			}

			@Override
			public ElemIterator next() {
				// TODO Auto-generated method stub
				return null;
			}

		}
	}
	
	public class MatrixList extends LinkedList<MatrixElem> { //implements Iterable<MatrixElem> {
		public MatrixElem find(int index) {
			for(MatrixElem tmp: this)
				if(tmp.columnIndex() == index)
					return tmp;
			return null;
		}
	}
}