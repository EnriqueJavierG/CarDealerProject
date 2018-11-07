package edu.uprm.cse.datastructures.cardealer.model;

import java.util.Comparator;

public class PersonComparator implements Comparator<Person> {

	@Override
	public int compare(Person p1, Person p2) {
		if(p1.getLastName().compareTo(p2.getLastName())== 0) {
			return p1.getFirstName().compareTo(p2.getFirstName());
		}
		return p1.getLastName().compareTo(p2.getLastName());
	}

}
