package edu.uprm.cse.datastructures.cardealer;
import javax.ws.rs.Path;

import javax.ws.rs.core.MediaType;
import edu.uprm.cse.datastructures.cardealer.*;
import java.util.Iterator;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;   

import edu.uprm.cse.datastructures.cardealer.model.*;
import edu.uprm.cse.datastructures.cardealer.util.*;
@Path("/carunit")
public class CarUnitManager {
	static CarUnitComparator carUnitComp = new CarUnitComparator();
	private static final SortedList<CarUnit> carList = new CircularSortedDoublyLinkedList<CarUnit>(carUnitComp);
	
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public CarUnit[] getAllCarUnits() {
		CarUnit[] allCarUnits = new CarUnit[carList.size()];
		for (int i = 0; i < carList.size(); i++) {
			allCarUnits[i] = carList.get(i);//adds carUnit to the array
		}
		return allCarUnits;//return all cars
	}
	@PUT
	@Path("/{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(CarUnit ctu) {
		for(int i = 0; i< carList.size();i++) {
			if(ctu.getCarUnitId() == carList.get(i).getCarUnitId()) {
				carList.remove(i);
				carList.add(ctu);
			
				return Response.status(Response.Status.OK).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	/**
	 * Adds the given carUnit to the list
	 * @param cta carUnit to add
	 * @return ok if added
	 */
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCarUnit(CarUnit cta) {
		for(int i = 0; i < carList.size(); i++){
			if(carList.get(i).getCarUnitId() == cta.getCarUnitId())
				throw new IllegalArgumentException("a car unit with this id already exists");// to prevent two carUnits with the same IDs
		}
		carList.add(cta);
		return Response.status(Response.Status.CREATED).build();
	}
	
	@DELETE
	@Path("{id}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCar(@PathParam("id") long id) {
		if(carList.isEmpty())throw new NotFoundException( new JsonError("Error","list empty"));
		for(int i = 0; i < carList.size();i++) {
			if(carList.get(i).getCarUnitId() == id){
				carList.remove(i);//remove from list
				return Response.status(Response.Status.OK).build();
			}
		}
		throw new NotFoundException( new JsonError("Error","Car"+id+" not found"));
	}
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CarUnit getCar(@PathParam("id") long id) {
		for (int i = 0; i < carList.size(); i++) {
			if(carList.get(i).getCarUnitId() == id) {
				return carList.get(i);
			}
		}
		throw new NotFoundException( new JsonError("Error","Car"+id+" not found"));
	}
	
}
