import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;


public class ElementList<E extends Comparable> implements SortedSet<E> {
	MatrixNode<E> head, tail;
	boolean isColumn;
	
	public ElementList() {
		
	}

	@Override
	public Comparator<? super E> comparator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E first() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<E> headSet(E toElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public E last() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<E> subSet(E fromElement, E toElement) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SortedSet<E> tailSet(E fromElement) {
		// TODO Auto-generated method stub
		return null;
	}
	public boolean add(E e) {
		throw new UnsupportedOperationException();
	}

	public boolean add(MatrixNode<E> e) {
		MatrixNode<E> tmp = head;
		while(tmp != null && tmp.compareTo(e) < 0)
			tmp = tmp.next(isColumn);
		if(tmp == null) {
			e.setNext(tmp, isColumn);
			e.setPrev(tail, isColumn);
			tail.setNext(e, isColumn);
			tail = e;
			return true;
		}
		if(tmp.equals(e))
			return false;
		System.out.println(tmp.value);
		e.setNext(tmp, isColumn);
		tmp.prev(isColumn).setNext(e, isColumn);
		e.setPrev(tmp.prev(isColumn), isColumn);
		tmp.setPrev(e, isColumn);
		return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean contains(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Iterator<E> iterator() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int size() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Object[] toArray() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T> T[] toArray(T[] a) {
		// TODO Auto-generated method stub
		return null;
	}

/*	public String toString() {
		
	}*/
	
}
