import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
/**
 * Implements a hash table using open hashing.
 * @author pjmcnamara2
 *
 */
public class MyHashTable implements Map<String, Integer> {
	public static final int INIT_ARRAY_SIZE = 16;
	public static final int INITIAL_HEIGHT = 4;
	public static final float LOAD_FACTOR = .75f;
	
	float loadFactor; // number of elements that must be inserted before rehash
	Element[][] table;
	int size = 0;
	
	MyHashTable(){
		this(INIT_ARRAY_SIZE, LOAD_FACTOR);
	}
	MyHashTable(int initialCapacity, float loadFactor) {
		this.loadFactor = loadFactor;
		table = new Element[initialCapacity][INITIAL_HEIGHT];
	}

	public boolean containsKey(Object key) {
		Integer value = get(key);
		return !(value == null);
	}

	public Integer get(Object key) {
		Element tmp = getElement(key);
		return (tmp == null ? null : tmp.value);
	}
	private Element getElement(Object key){
		Element[] tmp = table[Math.abs(key.hashCode() % table.length)];
		for(int i = 0; i < tmp.length && tmp[i] != null; i++)
			if(tmp[i].key.equals(key))
				return tmp[i];
		return null;
	}

	public Integer put(String key, Integer value) {
		// check if rehash will be needed because of this key
		if(!containsKey(key) && ++size > table.length * loadFactor)
			rehash();
		
		// Calculate hash code and get the list associated with that position
		int hashCode = Math.abs(key.hashCode() % table.length);
		Element[] position = table[hashCode];
		int i;
		
		// If key is already in graph, replace value and return old value
		for(i = 0; i < position.length && position[i] != null; i++)
			if(position[i].key.equals(key)){
				Integer val = position[i].value; // change key value
				position[i].value = value;
				return val;
			}

		// Check if this bucket needs expanding
		if(i == position.length) {
			//position = table[hashCode] = Arrays.copyOf(position, position.length * 2); Arrays.copyOf added in Java 1.6
			Element[] tmp = new Element[position.length * 2];
			for(int j = 0; j < position.length; j++)
				tmp[i] = position[i];
			position = table[hashCode] = tmp;
		}
		Element tmp = new Element();
		tmp.key = key;
		tmp.value = value;
		position[i] = tmp;
		return null;
	}
	
	/*
	 * Doubles length of old hash table, then reinserts each element into it's
	 * proper location in the new hash table
	 */
	private void rehash(){
		Element[][] oldArray = table;
		table = new Element[table.length * 2][INITIAL_HEIGHT];
		size = 0;
		for(Element[] i: oldArray)
			for(Element j: i)
				if(j != null)
					put(j.key, j.value);
	}

	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0 ? true : false;
	}
	/**
	 * Wrapper class that holds a single key, value pair.
	 * @author pjmcnamara2
	 *
	 */
	class Element {
		String key;
		Integer value;
	}
	/* The rest of the methods are stubs required by interface */
	public Collection<Integer> values() {
		throw new UnsupportedOperationException();
	}
	public Integer remove(Object key) {
		throw new UnsupportedOperationException();
	}
	public void putAll(Map<? extends String, ? extends Integer> m) {
		throw new UnsupportedOperationException();
	}
	public Set<String> keySet() {
		throw new UnsupportedOperationException();
	}
	public boolean containsValue(Object value) {
		throw new UnsupportedOperationException();
	}
	public Set<java.util.Map.Entry<String, Integer>> entrySet() {
		throw new UnsupportedOperationException();
	}
	public void clear() {
		throw new UnsupportedOperationException();
	}
}
