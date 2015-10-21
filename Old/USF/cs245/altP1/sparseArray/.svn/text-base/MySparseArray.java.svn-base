package sparseArray;

//TODO you could use a MatrixElem for everything instead of column and row elem's (because they have pointers right and down and hold objects)
//TODO should add LinkedList objects, they should return the proper iterator

public class MySparseArray implements SparseArray {
	private Object defaultValue;
	private ColIterator ci = new ColIterator();
	private RowIteratorTmp ri = new RowIteratorTmp();

	public MySparseArray(Object defaultValue){
		this.defaultValue = defaultValue;
	}
	
	@Override
	public Object defaultValue() {
		return defaultValue;
	}

	@Override
	public Object elementAt(int row, int col) {
		ci.restartIterator();
		ElemIterator ei = null;
		do
			ei = ci.next();
		while(ci.hasNext() && ei.nonIteratingIndex() < col);
		if(ei == null || ei.nonIteratingIndex() != col)
			return defaultValue();
		MatrixElem me;
		do
			me = ei.next();
		while(ei.hasNext() && me.rowIndex() < row);
		if(me == null || me.rowIndex() != row)
			return defaultValue;
		return me.value();
	}

	@Override
	public ColumnIterator iterateColumns() {
		ci.restartIterator();
		return ci;
	}

	@Override
	public RowIterator iterateRows() {
		ri.restartIterator();
		return ri;
	}

	@Override
	//TODO this should create the proper Matrix Element its self (and use iteraterater methods to add that element)
	public void setValue(int row, int col, Object value) {
		//TODO this should only be created if the matrix element didn't exist
		MyMatrixElem tmp = new MyMatrixElem(row, col, value);
		
		//find if the column exists
		ci.restartIterator();
		ElementIterator ei = null;
		do
			ei = (ElementIterator) ci.next();
		while(ci.hasNext() && ei.nonIteratingIndex() < col);
		if(ei == null) //no columns exist	TODO if no columns exist, no rows would also exist
			ei = (ElementIterator) ci.add(col).getData();
		else if(ei.nonIteratingIndex() != col)
			ei = (ElementIterator) ci.addBeforeCurrent(col).getData();
		
		//TODO should check to make sure the row exists while keeping the ci iterator at the same spot, this will allow you to have both the row and column element iterators that you will be adding the new element to, just go through the iterator until you reach the spot where it is to be inserted
		//TODO notice that if you did add in the new column, you will have to add a new matrix element
		
		//check to see if the matrix element already exists
		MyMatrixElem me;
		do
			me = (MyMatrixElem) ei.next();
		while(ei.hasNext() && me.rowIndex() < row);
		if(me == null)//TODO this will only be called if you just created a column
			ei.add(tmp);
		else if(me.rowIndex() != row)
			ei.addBeforeCurrent(tmp);
		else
			if(me.value().equals(defaultValue))
				;//TODO remove the matrix element
			else
				me.setValue(value);
		
		//check to see if the row exists
		//TODO also, if you had to add an element, you will have to go through this to check to add the new element into the list, but you will not need to if you just changed the value of the element
		ri.restartIterator();
		ei = null;
		do
			ei = (ElementIterator) ri.next();
		while(ri.hasNext() && ei.nonIteratingIndex() < row);
		if(ei == null)
			ei = (ElementIterator) ri.add(row).getData();
		else if (ei.nonIteratingIndex() != row)
			ei = (ElementIterator) ri.addBeforeCurrent(row).getData();
	}
	
	private class ColIterator extends ColumnIterator {
		ColumnElem head, prev, current;
		public ColIterator(){
			//create dummy node
			head = current = new ColumnElem(0);
			prev = null;
			head.setData(null);
		}
		public boolean hasNext(){
			if(current.getNext() != null)
				return true;
			return false;
		}
		public ElemIterator next(){
			if(!hasNext())
				return null;
			prev = current;
			current = current.getNext();
			return current.getData();
		}
		public ColumnElem add(int index){
			ColumnElem tmp = new ColumnElem(index);
			tmp.setNext(current.getNext());
			current.setNext(tmp);
			return tmp;
		}
		public ColumnElem addBeforeCurrent(int index){
			if(prev == null)
				;//TODO
			ColumnElem tmp = new ColumnElem(index);
			tmp.setNext(current);
			prev.setNext(tmp);
			return tmp;
		}
		@Override
		public void remove(){
			prev.setNext(current.getNext());
		}
		public void restartIterator(){
			prev = null;
			current = head;
		}
	}
	
	private class RowIteratorTmp extends RowIterator{
		RowElem head, prev, current;
		public RowIteratorTmp(){
			//create dummy node
			head = current = new RowElem(0);
			prev = null;
			head.setData(null);
		}
		public boolean hasNext(){
			if(current.getNext() != null)
				return true;
			return false;
		}
		public ElemIterator next(){
			if(!hasNext())
				return null;
			prev = current;
			current = current.getNext();
			return current.getData();
		}
		public RowElem add(int index){
			//TODO do you want to change current to next here?
			RowElem tmp = new RowElem(index);
			tmp.setNext(current.getNext());
			current.setNext(tmp);
			return tmp;
		}
		public RowElem addBeforeCurrent(int index){
			//TODO either update prev or current in all add before methods
			if(prev == null)
				;//TODO
			RowElem tmp = new RowElem(index);
			tmp.setNext(current);
			prev.setNext(tmp);
			return tmp;
		}
		@Override
		public void remove(){
			prev.setNext(current.getNext());
			current.setNext(prev.getNext());
		}
		public void restartIterator(){
			prev = null;
			current = head;
		}
	}
}