import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class TestingGenerics {
	
	public class Box<T> {

	    private T t;          

	    public void add(T t) {
	        this.t = t;
	    }

	    public T get() {
	        return t;
	    }

	    public <U extends Number> void inspect(U u){
	        System.out.println("T: " + t.getClass().getName());
	        System.out.println("U: " + u.getClass().getName());
	    }

	    /*public static void main(String[] args) {
	        Box<Integer> integerBox = new Box<Integer>();
	        integerBox.set(new Integer(10));
	        integerBox.inspect("some text"); // error: this is still String!
	    }*/
	}

	
	
	
	public static <T extends Comparable<T>> int countGreaterThan(T[] anArray, T elem) {
	    int count = 0;
	    for (T e : anArray)
	        if (e.compareTo(elem) > 0)
	            ++count;
	    return count;
	}
	
	@Test
	public void testGenericClass1(){
		Integer[] arr= {1, 2, 3, 4, 5, 6};
		int countGreaterThan = countGreaterThan(arr, 2);
		System.out.println(countGreaterThan);
		
		// Integer and Double extends Number
		Box<Number> box = new Box<Number>();
		box.add(new Integer(10));   // OK
		box.add(new Double(10.1)); //ok
		
	}

	
	public interface Pair<K, V> {
	    public K getKey();
	    public V getValue();
	}

	public class OrderedPair<K, V> implements Pair<K, V> {

	    private K key;
	    private V value;

	    public OrderedPair(K key, V value) {
		this.key = key;
		this.value = value;
	    }
	    
	    public OrderedPair() {
		
	    }

	    public K getKey()	{ return key; }
	    public V getValue() { return value; }
	}


	public class Util{
		
		public <K,V> boolean compare(Pair<K,V> pair, Pair<K,V> pair2){
			return pair.getKey().equals(pair2.getKey()) && pair.getValue().equals(pair2.getValue());
			
		}
	}
	
	
	@Test
	public void testGenericClass(){
		Pair<String, String> orderedPair = new OrderedPair<String, String>("hello", "world");
		Pair<String, Integer> intPair = new OrderedPair<String, Integer>("hello", 1);
		
		Pair<String, List<String>> listPair = new OrderedPair<>("hello", Arrays.asList("Test1", "test2"));

		Pair rawPair= new OrderedPair(1L, 2L);
		rawPair= intPair;
		
		orderedPair= rawPair;
		String key = orderedPair.getKey();
		System.out.println("Testing:: " +key);
		
	}
	

	class Shape { /* ... */ }
	class Circle extends Shape { /* ... */ }
	class Rectangle extends Shape { /* ... */ }

	class Node<T> { 
		T t;
		
	public  void add(T t){
		this.t= t;
	}
	/* ... */ }
	
	/**
	 * 
	 * 
	 *
	 * 
	 */
	class Node1<T> implements Comparable<T> {
		public int compareTo(T obj) {
			return 0;
		}
	}
	
	@Test
	public void test(){
		Node<Shape> ns= new Node<>();
		Node<Circle> nc= new Node<>();
		Node<Rectangle> nr= new Node<>();
		Node<Object> no= new Node<>();
		
		// no= nr; not possible
		// nr= nc;
		// Node<Circle> and Node<rectangle> not related
		
	}

	@Test
	public void test2(){
		Node<Shape> ns= new Node<>();
		Node<Circle> nc= new Node<>();
		Node<Rectangle> nr= new Node<>();
		
		//Node<Circle> and Node<rectangle> not related
	}
	
}
