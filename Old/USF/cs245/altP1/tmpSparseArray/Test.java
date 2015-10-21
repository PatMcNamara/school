package tmpSparseArray;

//import sparseArray.MySparseArray;

public class Test {
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		/*MySparseArray sparseArray = new MySparseArray(0, 1, 1, 8);
		System.out.println("Default value: " + sparseArray.defaultValue().toString());
		//sparseArray.setValue(1, 1, 8);
		System.out.println("(1,1) " + sparseArray.elementAt(1, 1));*/
		

		SparseArray a;
		//  Create a new sparse array, fill with values
		RowIterator r = s.iterateRows();
		while (r.hasNext())
		{
			ElemIterator elmItr = r.next();
			while (elmItr.hasNext())
			{
				MatrixElem me = elmItr.next();
				System.out.print("row:" + me.rowIndex() +
						"col:" + me.columnIndex() +
						"val:" + me.value() + " ");
			}
			System.out.println();

		}
	}
}