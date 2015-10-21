/**
 * Implementation of the MatrixElem interface, represents a single element within
 * the sparse array.  It is also a doubly linked linked list in 2 ways (vertically
 * and horizantaly.
 * @author pjmcnamara2
 *
 * @param <E> Type of element contained in the matrix node
 */
public class MatrixNode<E> implements MatrixElem {
	MatrixNode<E> prevCol, nextCol, prevRow, nextRow;
	int row, col;
	E value;
	
	public MatrixNode(int row, int col, E value) {
		this.row = row;
		this.col = col;
		this.value = value;
	}
	public int columnIndex() {
		return col;
	}
	public int rowIndex() {
		return row;
	}
	public E value() {
		return value;
	}
	public MatrixNode<E> next(boolean isCol){
		if(isCol) {return nextRow;}
		return nextCol;
	}
	public MatrixNode<E> prev(boolean isCol){
		if(isCol) {return prevRow;}
		return prevCol;
	}
	public void setNext(MatrixNode<E> next, boolean isCol) {
		if(isCol) {nextRow = next;}
		else {nextCol = next;}
	}
	public void setPrev(MatrixNode<E> prev, boolean isCol) {
		if(isCol) {prevRow = prev;}
		else {prevCol = prev;}
	}
	public String toString() {
		return "	(" + row + "," + col + ") " + value + "\n";
	}
	public int compareTo(int index, boolean isCol) {
		if(isCol) {return row - index;}
		return col - index;
	}
	public int compareTo(MatrixNode<E> node, boolean isCol) {
		if(isCol) {return compareTo(node.row, isCol);}
		return compareTo(node.col, isCol);
	}
}