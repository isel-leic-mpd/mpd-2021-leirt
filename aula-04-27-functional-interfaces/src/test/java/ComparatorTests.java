import data.Address;
import data.Person;
import static org.junit.Assert.*;
import org.junit.Test;

import java.time.LocalDate;
import java.util.Comparator;

import static java.util.Comparator.*;

import java.util.List;

public class ComparatorTests {
	
	static List<Person> db = List.of(
		new Person("Carlos",
			        LocalDate.of(1980, 3, 2),
					new Address("Coimbra", "3050")),
		new Person("Maria",
			LocalDate.of(2005, 10, 25),
			new Address("Coimbra", "2050")),
		new Person("Alice",
			LocalDate.of(1970, 5, 12),
			new Address("Lisboa", "1800")),
		new Person("Alberto",
			LocalDate.of(1080, 3, 2),
			new Address("Faro", "3150")),
		new Person("Rita",
			LocalDate.of(2000, 3, 2),
			new Address("Lisboa", "1900"))
	);

	private Person greater(Person p1, Person p2, Comparator<Person> comparator) {
		return comparator.compare(p1,p2) > 0 ? p1 : p2;
	}

	private Person mygreater(Person p1, Person p2, MyComparator<Person> comparator) {
		return comparator.compare(p1,p2) > 0 ? p1 : p2;
	}

	private static int getPersonAge(Person p) {
		return p.getAge();
	}

	@Test
	public void comparePersonsByNameTest() {
		Person p1 = db.get(0);
		Person p2 = db.get(1);
		//Comparator<Person>  cmp1 = (o1,o2) -> o1.getAge() - o2.getAge();

		//Person pg = greater(p1,p2, comparing(p-> p.getAge()) );
		Person pg = mygreater(p1,p2, MyComparator.comparing(Person::getAge) );
		assertEquals(p1, pg);

	}

	@Test
	public void comparePersonsByAddressTest() {
		Person p1 = db.get(0);
		Person p2 = db.get(1);

		Person pg = greater(p1,p2,
			comparing(Person::getAddress,
				comparing(Address::getCity)
				.thenComparing(Address::getZipCode)
				.reversed()));
		assertEquals(p2, pg);

	}
}
