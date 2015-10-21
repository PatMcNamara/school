public class MatrixNode<E extends Comparable> implements MatrixElem, Comparable<MatrixNode<E>> {
	protected MatrixNode<E> prevCol, nextCol, prevRow, nextRow;
	
	public MatrixNode<E> next(boolean isCol){
		if(isCol)
			return nextCol;
		return nextRow;
	}
	public void setNext(MatrixNode<E> next, boolean isCol) {
		if(isCol)
			nextCol = next;
		nextRow = next;
	}
	public MatrixNode<E> prev(boolean isCol){
		if(isCol)
			return prevCol;
		return prevRow;
	}
	public void setPrev(MatrixNode<E> prev, boolean isCol) {
		if(isCol)
			prevCol = prev;
		prevRow = prev;
	}
	
/*	This isn't needed because decendents can directly access the pointers
 * 	protected MatrixNode getNextRow() {return nextRow;}
	protected void setNextRow(MatrixNode nextRow) {this.nextRow = nextRow;}
	protected MatrixNode getPrevRow() {return prevRow;}
	protected void setPrevRow(MatrixNode prevRow) {this.prevRow = prevRow;}
	protected MatrixNode getNextCol() {return nextCol;}
	protected void setNextCol(MatrixNode nextCol) {this.nextCol = nextCol;}
	protected MatrixNode getPrevCol() {return*/
	
	int row, col;
	E value;
	
	public MatrixNode(int row, int col, E value) {
		this.row = row;
		this.col = col;
		this.value = value;
	}
	
	@Override
	public int columnIndex() {
		return col;
	}

	@Override
	public int rowIndex() {
		return row;
	}

	@Override
	public E value() {
		return value;
	}
	
	public String toString() {
		return "(" + row + "," + col + ") " + value;
	}
	
	public int compareTo(MatrixNode<E> node) {
		return value.compareTo(node.value());
	}
}
