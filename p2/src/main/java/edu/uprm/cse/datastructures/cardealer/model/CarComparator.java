/**
 * @author Enrique J. Gonzalez Mendez
 * Implementation of a comparator for the a Car list
 */
package edu.uprm.cse.datastructures.cardealer.model;

import java.util.Comparator;

public class CarComparator implements Comparator<Car> {

	@Override
	public int compare(Car car1, Car car2) {
		if(car1.getCarBrand().compareTo(car2.getCarBrand()) == 0) {
			if(car1.getCarModel().compareTo(car2.getCarModel()) == 0) {
				if(car1.getCarYear() > car2.getCarYear())return 1;
				else if(car1.getCarYear() == car2.getCarYear()) return -0;
				else if(car1.getCarYear() < car2.getCarYear()) return -1;
				return car1.getCarModelOption().compareTo(car2.getCarModelOption());
			}
			return car1.getCarModel().compareTo(car2.getCarModel());
		}
		return car1.getCarBrand().compareTo(car2.getCarBrand());
	}

}
