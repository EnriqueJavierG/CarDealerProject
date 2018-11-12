package edu.uprm.cse.datastructures.cardealer.model;

public class CarUnit {
	private long carUnitId; // internal id of the unit
	private long carId; // id of the car object that represents the general for the car. 
                                           // This Car from project 1.
	private String vin; // vehicle identification number
	private String color; // car color
	private String carPlate; // car plate (null until sold)
	private long personId; // id of the person who purchased the car. (null until purchased) 
	
	public CarUnit(long carUnitId, long carId, String vin, String color, String carPlate, long personId) {
		super();
		this.carUnitId = carUnitId;
		this.carId = carId;
		this.vin = vin;
		this.color = color;
		this.carPlate = carPlate;
		this.personId = personId;
	}
	public CarUnit() {
		
	}

	//Getters
	public long getCarUnitId() {
		return carUnitId;
	}

	public long getCarId() {
		return carId;
	}

	public String getVin() {
		return vin;
	}

	public String getColor() {
		return color;
	}

	public String getCarPlate() {
		return carPlate;
	}

	public long getPersonId() {
		return personId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (carId ^ (carId >>> 32));
		result = prime * result + ((carPlate == null) ? 0 : carPlate.hashCode());
		result = prime * result + (int) (carUnitId ^ (carUnitId >>> 32));
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + (int) (personId ^ (personId >>> 32));
		result = prime * result + ((vin == null) ? 0 : vin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CarUnit other = (CarUnit) obj;
		if (carId != other.carId)
			return false;
		if (carPlate == null) {
			if (other.carPlate != null)
				return false;
		} else if (!carPlate.equals(other.carPlate))
			return false;
		if (carUnitId != other.carUnitId)
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (personId != other.personId)
			return false;
		if (vin == null) {
			if (other.vin != null)
				return false;
		} else if (!vin.equals(other.vin))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CarUnit [carUnitId=" + carUnitId + ", carId=" + carId + ", vin=" + vin + ", color=" + color
				+ ", carPlate=" + carPlate + ", personId=" + personId + "]";
	}
                                                 	

}
