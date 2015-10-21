package sparseArray;

public class RowElem {
	private int index;
	private RowElem next;
	private ElemIterator data;
	
	public RowElem(int index){
		this.index = index;
		next = null;
		data = new ElementIterator(index, true);
	}
	public int getIndex(){
		return index;
	}
	public RowElem getNext(){
		return next;
	}
	public void setNext(RowElem element){
		next = element;
	}
	public ElemIterator getData(){
		return data;
	}
	public void setData(ElemIterator data){
		this.data = data;
	}
}