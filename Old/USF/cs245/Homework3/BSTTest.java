class BSTTest {


    public static void main(String args[]) {
	
	BinarySearchTree T = new BinarySearchTree();
	int i;

	T.insert(new Integer(8));
	T.insert(new Integer(4));
	T.insert(new Integer(2));
	T.insert(new Integer(1));
	T.insert(new Integer(3));
	T.insert(new Integer(6));
	T.insert(new Integer(5));
	T.insert(new Integer(7));
	T.insert(new Integer(9));
	T.insert(new Integer(10));
	T.insert(new Integer(11));
	T.insert(new Integer(12));
	T.insert(new Integer(13));
	T.insert(new Integer(14));

	T.print();

	for (i=0; i< 14; i++)
	    System.out.println("Element #" + i + " = " + T.ElementAt(i));
	for (i=0; i<14; i++)
	    System.out.println("Tree has " +  T.NumLess(new Integer(i)) + " Elements less than " + i);
	

    }

}
