package data.hash;

import java.lang.reflect.Array;

public class LinearHash<K,V> extends SkeletonHashMap<K, V>{
	private Entry[] entries;
	private boolean[] deleted;

	public int next(int i){
		return (i+81019) % capacity;
	}
	
	public int find_space(K key){
		int i = hash(key.hashCode()) & (capacity-1);
		int j = -1;
	     while ((entries[i]!=null && !key.equals(entries[i].getKey()))){
	    	 i = next(i);
	    	 if(j==-1 && (entries[i]==null || deleted[i])){
	    		 j = i;
	    	 }
	     }
	     if (entries[i]!=null && !key.equals(entries[i].getKey()))
	    	 i = j;
	     return i;
	} 
	
	public int find(K key){
		int i = hash(key.hashCode()) & (capacity-1);
	     while ((entries[i]!=null && !key.equals(entries[i].getKey())) || deleted[i]){
	    	 i = next(i);
	     }
	     return i;
	}
	

	@Override
	public V put(K key, V value) {
		int i = find_space(key);
		Entry<K,V> e = entries[i];
		entries[i] = new Entry<K, V>(key.hashCode(),key,value);
		deleted[i] = false;
		
		if(e==null)
			++size;
			resize();
		return value;
	}
	
	@Override
	public V get(K key) {
		int i = find(key);
		Entry<K,V> e = entries[i];
		if(e!=null){
			return e.getValue();
		}
		return null;
	}

	@Override
	public V remove(K key) {
		int i = find(key);
		Entry<K,V> e = entries[i];
		
		if(e!=null){
			--size;
			entries[i] = null;
			deleted[i] = true;
			resize();
			return e.getValue();
		}
		return null;
	}

	@Override
	public boolean containsKey(K key) {
		int i = find(key);
		return entries[i]!=null && key.equals(entries[i].getKey());
	}

	@Override
	public boolean containsValue(V value) {
		for(Entry<K,V> e:entries){
			if(e!=null && value.equals(e.getValue())){
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void resize() {
		if(checkLoad())
			return;

//		System.out.println(size + " " + capacity + " " +minThreshold + " " + maxThreshold);
		
		Entry<K,V>[] tempEntries = entries;
		int tempCapacity = capacity;

		while(!checkLoad()){ //Sloow
			capacity = size < minThreshold ? capacity/2 : capacity*2;
			minThreshold = (int) (minLoad*capacity);
			maxThreshold = (int) (maxLoad*capacity);
//			System.out.println("-"+ size + " " + capacity + " " +minThreshold + " " + maxThreshold);
		}
		
		entries = new Entry[capacity];
		deleted = new boolean[capacity];

		for(int i = 0;i<tempCapacity;i++){
			Entry<K,V> e = tempEntries[i];
			if(e!=null){			
				int j = e.getHash() & (capacity-1);
				if(entries[j]==null){
					entries[j] = e;
				} else {
					while(entries[j]!=null){
						j = next(j);
					}
					entries[j] = e;
				}
			}
		}
	}

	
	public LinearHash(){
		this(DEFAULT_CAPACITY);
	}	
	public LinearHash(int capacity){
		this.capacity = nearestPowerOfTwo(capacity);
		
		minLoad = 3.0f / 16.0f; // 3/16
		maxLoad = 3.0f / 4.0f; // 3/4
		minThreshold = (int) (minLoad*capacity);
		maxThreshold = (int) (maxLoad*capacity);
		
		entries = new Entry[capacity];
		deleted = new boolean[capacity];
	}
	

	public static void main(String[] args){
		 LinearHash<String,Integer> test = new LinearHash<String, Integer>();
		 test.put("Test", 10);
		 System.out.println("".hashCode());
		 System.out.println(hash("".hashCode()));
		 
	}
}