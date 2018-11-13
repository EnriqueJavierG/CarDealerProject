package edu.uprm.cse.datastructures.cardealer;

import java.util.ArrayList;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import edu.uprm.cse.datastructures.cardealer.model.Appointment;
import edu.uprm.cse.datastructures.cardealer.util.LinkedPositionalList;
import edu.uprm.cse.datastructures.cardealer.util.Position;
import edu.uprm.cse.datastructures.cardealer.util.PositionalList;
@Path("/appointment")
public class AppointmentManager {
	private static final PositionalList<Appointment> Monday = new LinkedPositionalList<Appointment>();
	private static final PositionalList<Appointment> Tuesday = new LinkedPositionalList<Appointment>();
	private static final PositionalList<Appointment> Wednesday = new LinkedPositionalList<Appointment>();
	private static final PositionalList<Appointment> Thursday = new LinkedPositionalList<Appointment>();
	private static final PositionalList<Appointment> Friday = new LinkedPositionalList<Appointment>();

	private static final PositionalList<Appointment>[] days =  new PositionalList[] {Monday,Tuesday, Wednesday,Thursday,Friday};

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Appointment[] getAllAppointments() {
		int size = Monday.size() + Tuesday.size() + Wednesday.size() + Thursday.size() + Friday.size();
		Appointment[] allAppointments = new Appointment[size];
		int index = 0;
		for (int i = 0; i < days.length; i++) {
			for(Position<Appointment> app: days[i]) {
				allAppointments[index++] = app.getElement();

			}
		}
		return allAppointments;
	}
	
	@PUT
	@Path("/{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Appointment atu, @PathParam("id") long id ) {

		for (int i = 0; i < days.length; i++) {
			for(Position<Appointment> app: days[i]) {
				if(app.getElement().getAppointmentId() == id)
					days[i].set(app, atu);
				return Response.status(Response.Status.OK).build();
				//Response ok
			}
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}

	@POST
	@Path("/add/day/{day}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addAppointment(Appointment ata,@PathParam("day") String day ) {
		switch(day.toLowerCase()) {
		//Can be upperCase also but will not affect
		case "monday":
			Monday.addLast(ata);
			break;
		case "tuesday":
			Tuesday.addLast(ata);
			break;
		case "wednesday":
			Wednesday.addLast(ata);
			break;
		case "thursday":
			Thursday.addLast(ata);
			break;
		case "friday":
			Friday.addLast(ata);
			break;
		default:
			return Response.status(Response.Status.NOT_FOUND).build();

		}
		return Response.status(Response.Status.CREATED).build();

	}

	/**
	 * Searches all the days for the appointment that has the given id
	 * and removes that appointment from that day
	 * @param id of the appointment 
	 * @return ok if the id was found and removed
	 */
	@DELETE
	@Path("{id}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteAppointment(@PathParam("id") long id) {
		for (int i = 0; i < days.length; i++) {
			for(Position<Appointment> app: days[i]) {
				if(app.getElement().getAppointmentId() == id) {
					days[i].remove(app);
					return Response.status(Response.Status.OK).build();
					//Response ok
				}

			}
		}
		throw new NotFoundException( new JsonError("Error","Car"+id+" not found"));
	}
	//Get all appointments of a given day 
	@GET
	@Path("/day/{day}")// Assuming that all appointments have different ids in the all list
	@Produces(MediaType.APPLICATION_JSON)
	public Appointment[] getAppointmentByDay(@PathParam("day") PositionalList<Appointment> day ) {
		ArrayList<Appointment> list = new ArrayList<>();
		for(Position<Appointment> app : day){
			list.add(app.getElement());
		}

		return list.toArray(new Appointment[list.size()]);
	}

	@GET
	@Path("{id}")// Assuming that all appointments have different ids in the all list
	@Produces(MediaType.APPLICATION_JSON)
	public Appointment getAppointment(@PathParam("id") long id) {
		for (int i = 0; i < days.length; i++) {
			for(Position<Appointment> app: days[i]) {
				if(app.getElement().getAppointmentId() == id)
					return app.getElement();//return appointment
			}
		}
		throw new NotFoundException( new JsonError("Error","Appointment"+id+" not found"));
	}



}
