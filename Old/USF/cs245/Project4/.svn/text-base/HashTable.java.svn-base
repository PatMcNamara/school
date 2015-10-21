public class HashTable {
	public static final int INIT_ARRAY_SIZE = 16;
	public static final float LOAD_FACTOR = .75f;
	
	float loadFactor; // number of elements that must be inserted before rehash
	Key[] table;
	int size = 0;
	int countSize = 0;
	
	HashTable(){
		this(INIT_ARRAY_SIZE, LOAD_FACTOR);
	}
	HashTable(int initialCapacity, float loadFactor) {
		this.loadFactor = loadFactor;
		table = new Key[INIT_ARRAY_SIZE];
	}

	public boolean containsKey(BlockTray key) {
		for(int i = 0; table[Math.abs(key.hashCode() + i^2) % table.length] != null; i++)
			if(table[Math.abs(key.hashCode() + i^2) % table.length].value.equals(key) && !table[Math.abs(key.hashCode() + i^2) % table.length].dead)
				return true;
		return false;
	}
	
	public void printSize(){
		System.err.println("Heap full at " + size);
	}

	public boolean put(BlockTray key) {
		int arrayValue = key.hashCode();
		if(containsKey(key))
			return false;
		if(++size > table.length * loadFactor)
			rehash();
		if(size % 100 == 0 && size >= countSize*100){
			System.err.println("Hash table at size: " + countSize++ * 100);
		}
		int i;
		// Calculate hash code and get the list associated with that position
		for(i = 0; table[Math.abs(key.hashCode() + i^2) % table.length] != null && !table[Math.abs(arrayValue + i^2) % table.length].dead; i++);
		arrayValue = Math.abs(arrayValue + i^2) % table.length;
		
		if(table[arrayValue] == null){
			table[arrayValue] = new Key();
		}
		Key tmp = table[arrayValue];
		tmp.value = key;
		tmp.dead = false;
		return true;
	}
	
	/*
	 * Doubles length of old hash table, then reinserts each element into it's
	 * proper location in the new hash table
	 */
	private void rehash(){
		Key[] oldArray = table;
		table = new Key[table.length*2];
		size = 0;
		for(Key i: oldArray)
			if(i != null && !i.dead)
				put(i.value);
	}

	public int size() {
		return size;
	}
	public boolean isEmpty() {
		return size == 0 ? true : false;
	}
	
	private class Key {
		BlockTray value;
		boolean dead;
	}
}
