package edu.uprm.cse.datastructures.cardealer.model;

public class Appointment {
	
	private long appointmentId; // internal id of the appointment
	private long carUnitId; // id of the car to be serviced
	private String job; // description of the job to be done (i.e.: “oil change”)
	private double bill; // cost of the service (initially 0).
	
	//Constructor
	public Appointment(long appointmentId, long carUnitId, String job, double bill) {
		super();
		this.appointmentId = appointmentId;
		this.carUnitId = carUnitId;
		this.job = job;
		this.bill = bill;
	}
	//Empty constructor
	public Appointment(){
		
	}

	
	public long getAppointmentId() {
		return appointmentId;
	}


	public long getCarUnitId() {
		return carUnitId;
	}


	public String getJob() {
		return job;
	}


	public double getBill() {
		return bill;
	}
	
	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (appointmentId ^ (appointmentId >>> 32));
		long temp;
		temp = Double.doubleToLongBits(bill);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (int) (carUnitId ^ (carUnitId >>> 32));
		result = prime * result + ((job == null) ? 0 : job.hashCode());
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
		Appointment other = (Appointment) obj;
		if (appointmentId != other.appointmentId)
			return false;
		if (Double.doubleToLongBits(bill) != Double.doubleToLongBits(other.bill))
			return false;
		if (carUnitId != other.carUnitId)
			return false;
		if (job == null) {
			if (other.job != null)
				return false;
		} else if (!job.equals(other.job))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Appointment [appointmentId=" + appointmentId + ", carUnitId=" + carUnitId + ", job=" + job + ", bill="
				+ bill + "]";
	}

}
