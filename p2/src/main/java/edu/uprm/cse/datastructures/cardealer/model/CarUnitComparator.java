package edu.uprm.cse.datastructures.cardealer.model;

import java.util.Comparator;

public class CarUnitComparator implements Comparator<CarUnit> {

	@Override
	public int compare(CarUnit cu1, CarUnit cu2) {
		return cu1.getVin().compareTo(cu2.getVin());
	}

}
