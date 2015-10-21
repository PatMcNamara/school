import java.util.AbstractSequentialList;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;
/**
 * Linked List used by Graph, Euler and ConnectedComponents.
 * @author pjmcnamara2
 *
 * @param <T>
 */
public class MyList<T> extends AbstractSequentialList<T> implements List<T>,
		Collection<T>, Iterable<T> {
	Node head, tail;
	int size = 0;

	public MyList() {
		/* Create and add sentinel nodes for head and tail*/
		head = new Node();
		tail = new Node();
		head.value = tail.value = null;
		head.prev = tail.next = null;
		head.next = tail;
		tail.prev = head;
	}

	@Override
	public void add(int index, T element) {
		Node next = head.next;
		
		for(; next != tail && index != 0; next = next.next, index--);
		
		Node tmp = new Node();
		tmp.value = element;
		tmp.prev = next.prev;
		tmp.next = next;
		tmp.prev.next = tmp;
		next.prev = tmp;
		size++;
	}
	
	@Override
	public boolean add(T o) {
		Node tmp = new Node();
		tmp.value = o;
		tmp.prev = tail.prev;
		tmp.next = tail;
		tmp.prev.next = tmp;
		tail.prev = tmp;
		size++;
		return true;
	}
	
	@Override
	public boolean addAll(Collection<? extends T> c) {
		boolean result = false;
		for(T i: c)
			result |= add(i);
		return result;
	}
	
	@Override
	public boolean contains(Object o) {
		for(Node tmp = head; tmp.next != tail; tmp = tmp.next)
			if(o == null ? tmp == null : o.equals(tmp))
				return true;
		return false;
	}
	
	@Override
	public T remove(int index) {
		Node tmp = head.next;
		for(int i = 0; tmp != tail && i < index; i++)
			tmp = tmp.next;
		
		tmp.prev = tmp.next;
		tmp.next = tmp.prev;
		size--;
		return tmp.value;
	}
	
	@Override
	public boolean remove(Object o) {
		Node tmp;
		for(tmp = head.next; tmp != tail && !tmp.value.equals(o); tmp = tmp.next);
		
		if(tmp == tail)
			return false;
		tmp.prev.next = tmp.next;
		tmp.next.prev = tmp.prev;
		size--;
		return true;
	}
	
	@Override
	public String toString() {
		Node tmp = head;
		String retVal = "";
		while((tmp = tmp.next) != tail)
			retVal += tmp.value + " ";
		return retVal;
	}
	
	@Override
	public ListIterator<T> listIterator(int index) {
		ListIterator<T> iterator = listIterator();
		while(iterator.nextIndex() != index)
			iterator.next();
		return iterator;
	}
	
	@Override
	public ListIterator<T> listIterator() {
		return new Iterator();
	}
	
	@Override
	public boolean isEmpty() {
		return (size == 0);
	}
	@Override
	public int size() {
		return size;
	}
	/**
	 * Wrapper class for a single node in the list
	 * @author pjmcnamara2
	 *
	 */
	private class Node {
		T value;
		Node next;
		Node prev;
	}
	/**
	 * Private Iterator that implements hasNext, next, nextIndex, hasPrevious,
	 *  previous, previousIndex, and set methods.
	 * @author pjmcnamara2
	 *
	 */
	private class Iterator implements ListIterator<T> {
		Node current = head;
		int position = 0;
		
		public void set(T e) {
			current.value = e;
		}

		public T next() {
			if(!hasNext())
				throw new NoSuchElementException();
			current = current.next;
			position++;
			return current.value;
		}
		public int nextIndex(){
			return position;
		}
		public boolean hasNext() {
			return (current.next != tail);
		}
		
		public T previous() {
			if(!hasPrevious())
				throw new NoSuchElementException();
			current = current.prev;
			position--;
			return current.value;
		}
		public int previousIndex(){
			return position - 1;
		}
		public boolean hasPrevious() {
			return (current.prev != head);
		}
		/**
		 * Remainder of methods are stubs required by interfaces.
		 */
		public void add(T e) {
			throw new UnsupportedOperationException();
		}
		public void remove() {
			throw new UnsupportedOperationException();
		}
	}
	
	@Override
	public void clear() {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean equals(Object o) {
		throw new UnsupportedOperationException();
	}
	@Override
	public int hashCode() {
		throw new UnsupportedOperationException();
	}
	@Override
	public int indexOf(Object o) {
		throw new UnsupportedOperationException();
	}
	@Override
	public int lastIndexOf(Object o) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean removeAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean retainAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	@Override
	public Object[] toArray() {
		throw new UnsupportedOperationException();
	}
	@Override
	public <T> T[] toArray(T[] a) {
		throw new UnsupportedOperationException();
	}
	@Override
	public boolean containsAll(Collection<?> c) {
		throw new UnsupportedOperationException();
	}
	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}
	@Override
	public List<T> subList(int fromIndex, int toIndex) {
		throw new UnsupportedOperationException();
	}
}