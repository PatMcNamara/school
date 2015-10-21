package sparseArray;

public class MyMatrixElem implements MatrixElem {
	private int row, column;
	private MyMatrixElem nextRow, nextCol;
	private Object data;
	
	MyMatrixElem(int row, int column, Object data){
		this.row = row;
		this.column = column;
		this.data = data;
	}
	
	public int columnIndex(){
		return column;
	}
	public int rowIndex(){
		return row;
	}
	public Object value(){
		return data;
	}
	public void setValue(Object data){
		this.data = data;
	}
	public MyMatrixElem getNextRow(){
		return nextRow;
	}
	public MyMatrixElem getNextCol(){
		return nextCol;
	}
	public void setNextRow(MyMatrixElem next){
		nextRow = next;
	}
	public void setNextCol(MyMatrixElem next){
		nextCol = next;
	}
}