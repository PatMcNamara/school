import java.util.SortedSet;


public abstract class AxisList<E extends Comparable> implements SortedSet<ElementList<E>> {
	ElementList<E> head, tail;

	public AxisList() {
		head = tail = new ElementList<E>();
	}
}
