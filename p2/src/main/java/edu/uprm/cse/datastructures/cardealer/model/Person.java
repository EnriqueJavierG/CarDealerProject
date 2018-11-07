package edu.uprm.cse.datastructures.cardealer.model;

public class Person {
	
	private long personId; // internal id of the person
	private String firstName; // first name
	private String lastName; // lastname
	private Integer age; // age
	private char gender; // gender
	private String phone; // phone number
	
	//Constructor
		public Person(long personId, String firstName, String lastName, Integer age, char gender, String phone) {
			super();
			this.personId = personId;
			this.firstName = firstName;
			this.lastName = lastName;
			this.age = age;
			this.gender = gender;
			this.phone = phone;
		}
		public Person() {
			
		}
	//Getters
	public long getPersonId() {
		return personId;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public Integer getAge() {
		return age;
	}
	
	public char getGender() {
		return gender;
	}
	
	public String getPhone() {
		return phone;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((age == null) ? 0 : age.hashCode());
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + gender;
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + (int) (personId ^ (personId >>> 32));
		result = prime * result + ((phone == null) ? 0 : phone.hashCode());
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
		Person other = (Person) obj;
		if (age == null) {
			if (other.age != null)
				return false;
		} else if (!age.equals(other.age))
			return false;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (gender != other.gender)
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (personId != other.personId)
			return false;
		if (phone == null) {
			if (other.phone != null)
				return false;
		} else if (!phone.equals(other.phone))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Person [personId=" + personId + ", firstName=" + firstName + ", lastName=" + lastName + ", age=" + age
				+ ", gender=" + gender + ", phone=" + phone + "]";
	}
	

}
