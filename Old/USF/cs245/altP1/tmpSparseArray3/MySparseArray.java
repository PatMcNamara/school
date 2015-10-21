
public class MySparseArray<E extends Comparable> implements SparseArray {//Comparable<E>
	private E defaultValue;
	AxisList<E> rl, cl;
	
	public MySparseArray(E defaultValue){
		this.defaultValue = defaultValue;
		rl = new RowList<E>();
		cl = new ColList<E>();
	}
	
	@Override
	public E defaultValue() {
		return defaultValue;
	}

	@Override
	public E elementAt(int row, int col) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ColumnIterator iterateColumns() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RowIterator iterateRows() {
		// TODO Auto-generated method stub
		return null;
	}
	//TODO You really should change the object to the type E but then you wouldn't be implementing the interface
	public void setValue(int row, int col, Object value) {
		
	}
	
	public String toString() {
		System.out.println(rl);
		return null;
	}
	
	public static void main(String[] args) {
		MatrixNode<Integer> node1 = new MatrixNode<Integer>(1,1,1);
		MatrixNode<Integer> node2 = new MatrixNode<Integer>(1,2,5);
		MatrixNode<Integer> node3 = new MatrixNode<Integer>(1,3,10);
		MatrixNode<Integer> node4 = new MatrixNode<Integer>(2,1,3);
		System.out.println(node1);
		System.out.println(node2);
		System.out.println(node3);
		System.out.println(node4);
		AxisList<Integer> col = new ColList<Integer>();
		col.add(node1);
	}
}