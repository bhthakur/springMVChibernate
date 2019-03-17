
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.journaldev.spring.model.Person;
import com.journaldev.spring.model.Person.Sex;
import com.journaldev.spring.service.CheckPerson;

public class TestingLambda {
	
	
	@Test
	public void test1(){
		List<Person> list = getPersons();
		Person.printPersonsOlderThan(list, 50);
		Person.printPersons(list, new CheckPerson() {
			@Override
			public boolean test(Person p) {
				return p.getAge() < 50 && "NZ".equals(p.getCountry());
			}
		});
		
		Person.printPersonsWithPredicate(list, p -> p.getAge() < 50 && "AUS".equals(p.getCountry()));
		Person.processPersons(list, p -> p.getAge() < 50 && "AUS".equals(p.getCountry()), p -> p.printPerson());
		Person.processPersonsWithFunction(list, p->p.getAge()>50, p->p.getName().toUpperCase(), System.out::println);
		Person.processElements(list, p -> p.getAge() > 50, p-> p.getName().toLowerCase() , System.out::println);
		
		list.stream()
			.filter(p -> p.getAge() >50)
			.map(p -> p.getCountry())
			.forEach(System.out::println);
	}



	private List<Person> getPersons() {
		Person person = new Person(1, "Rishi", "AUS");
		person.setGender(Sex.MALE);
		person.setAge(35);
		Person person2 = new Person(2, "Phil", "NZ");
		person2.setGender(Sex.MALE);
		person2.setAge(65);
		Person person3 = new Person(3, "Rianna", "IND");
		person3.setGender(Sex.FEMALE);
		person3.setAge(55);
		List<Person> list= Arrays.asList(person, person2, person3);
		return list;
	}

	

	@Test
	public void test2(){
	
	}
	
}
