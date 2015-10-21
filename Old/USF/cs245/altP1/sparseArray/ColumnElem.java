package sparseArray;

public class ColumnElem {
	private int index;
	private ColumnElem next;
	private ElemIterator data;
	
	public ColumnElem(int index){
		this.index = index;
		next = null;
		data = new ElementIterator(index, false);
	}
	public int getIndex(){
		return index;
	}
	public ColumnElem getNext(){
		return next;
	}
	public void setNext(ColumnElem element){
		next = element;
	}
	public ElemIterator getData(){
		return data;
	}
	public void setData(ElemIterator data){
		this.data = data;
	}
}