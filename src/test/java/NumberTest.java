import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import com.journaldev.spring.helper.NumberUtils;
import com.journaldev.spring.model.Person;
import com.journaldev.spring.service.PersonServiceImpl;

import junit.framework.Assert;


public class NumberTest {
	//Generic class
	class MyClass<X> {
			//generic constructor
		  <T> MyClass(T t) {
		    // ...
		  }
		}

	
	
	static <T> T pick(T a1, T a2) { 
		return a2; 
	}
	String result= pick("Hello", "World");
	Serializable s = pick("d", new ArrayList<String>());
	
	//X refers to Integer and T refers to String
	MyClass<Integer> myClass = new MyClass<Integer>("");
	

	@Test
	public void testGenerics(){

		PersonServiceImpl service= new PersonServiceImpl();
		Predicate<Person> namePredicate = p -> "Phil".equalsIgnoreCase(p.getName());
		Predicate<Person> countryPredicate = p -> "AUS".equalsIgnoreCase(p.getCountry());
		List<Person> list= Arrays.asList(new Person(1, "Rishi", "AUS"), new Person(2, "Phil", "AUS"), new Person(3, "Rianna", "IND"));
		
		System.out.println(service.filterByPredicate(list, namePredicate));
		System.out.println(service.filterByPredicate(list, countryPredicate));
	}
	
	
	@Test
	public void testGenerics2(){
		PersonServiceImpl service= new PersonServiceImpl();
		Person p1= new Person(1, "Rishi", "AUS");
		Person p2 = new Person(2, "Phil", "AUS");
		Person p3 = new Person(3, "Rianna", "IND");
		Person[] pArr= {p1, p2, p3};
		PersonServiceImpl.printArr(pArr);
		PersonServiceImpl.printArr(service.exchange(pArr, p1, p3));
	}
	
	@Test
	public void shouldPrint100Primes(){
		int num = 7;
		boolean isPrime = NumberUtils.isPrime(num);
		Assert.assertTrue(isPrime);
		
		System.out.println("Print sqrt of first 100 primes");
		System.out.println(NumberUtils.sqrt100Prime());
		Assert.assertTrue(NumberUtils.sqrt100Prime().size() == 100);
		
	}
	
	
}
