package sparseArray;

public class ElementIterator extends ElemIterator {
	private boolean iteratingRow;
	private int index;
	private MyMatrixElem current, prev;

	public ElementIterator(int index, boolean iteratingRow){
		this.iteratingRow = iteratingRow;
		this.index = index;
		prev = null;
		current = new MyMatrixElem(index, -1, null);//dummy head node
	}

	@Override
	public boolean hasNext() {
		if(iteratingRow){
			if(prev == null || current.getNextRow() != null)
				return true;
			return false;
		} else {
			if(prev == null || current.getNextCol() != null)
				return true;
			return false;
		}
	}

	@Override
	public boolean iteratingCol() {
		if(!iteratingRow)
			return true;
		return false;
	}

	@Override
	public boolean iteratingRow() {
		if(iteratingRow)
			return true;
		return false;
	}

	@Override
	public MatrixElem next() {
		if(hasNext())
			return null;
		if(iteratingRow)
			return current = current.getNextRow();
		else
			return current = current.getNextCol();
	}

	@Override
	public int nonIteratingIndex() {
		return index;
	}
	
	public void add(MyMatrixElem tmp){
		//TODO should move the current object to the matrix element just inserted
		if(iteratingRow){
			tmp.setNextCol(current.getNextCol());
			current.setNextCol(tmp);
		} else {
			tmp.setNextCol(current.getNextCol());
			current.setNextCol(tmp);
		}
	}
	public void addBeforeCurrent(MyMatrixElem tmp){
		if(prev == null)
			add(tmp);
		if(iteratingRow){
			tmp.setNextCol(current);
			prev.setNextCol(tmp);
		} else {
			tmp.setNextCol(current);
			current.setNextCol(tmp);
		}
	}
	
/*	public MyMatrixElem add(int index, Object o){
		if(iteratingRow){
			MyMatrixElem tmp = new MyMatrixElem(this.index, index, o);
			tmp.setNextRow(current.getNextRow());
			current.setNextRow(tmp);
			return tmp;
		}
		MyMatrixElem tmp = new MyMatrixElem(index, this.index, o);
		tmp.setNextCol(current.getNextCol());
		current.setNextCol(tmp);
		return tmp;
	}
	public MyMatrixElem addBeforeCurrent(int index, Object o){
		if(prev == null)
			return add(index, o);
		if(iteratingRow){
			MyMatrixElem tmp = new MyMatrixElem(index, this.index, o);
			tmp.setNextCol(current);
			prev.setNextCol(tmp);
			return tmp;
		}
		MyMatrixElem tmp = new MyMatrixElem(index, this.index, o);
		tmp.setNextRow(current);
		current.setNextCol(tmp);
		return tmp;
	}*/
}