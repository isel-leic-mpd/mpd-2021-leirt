package data;

import java.time.LocalDate;

public class Person  implements Comparable<Person>  {
	private String name;
	private LocalDate birthDate;
	private Address address;

	public Person(String name, LocalDate birthDate, Address address) {
		this.name = name;
		this.birthDate = birthDate;
		this.address = address;
	}

	public String getName() {
		return name;
	}

	public LocalDate getBirthDate() {
		return birthDate;
	}

	public Address getAddress() {
		return address;
	}

	public int getAge() {
		LocalDate now = LocalDate.now();
		int age = now.getYear() - birthDate.getYear();

		if (now.getMonthValue() < birthDate.getMonthValue() ||
			(now.getMonthValue() == birthDate.getMonthValue() &&
				now.getDayOfMonth() < birthDate.getDayOfMonth())
		) age--;
		return age;
	}

	@Override
	public int compareTo(Person p) {
		return name.compareTo(p.getName());
	}
}
