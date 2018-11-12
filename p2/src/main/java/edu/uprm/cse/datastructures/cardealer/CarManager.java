/**
 * @author Enrique J. Gonzalez Mendez
 * This class implements CRUD, Json, Curl and Maven to create the backend methods of a server that 
 * will have a car list
 */
package edu.uprm.cse.datastructures.cardealer;

import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import edu.uprm.cse.datastructures.cardealer.*;

import java.util.ArrayList;
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
@Path("/cars")
public class CarManager {
	static CarComparator carComp = new CarComparator();
	private static final SortedList<Car> carList = new CircularSortedDoublyLinkedList<Car>(carComp);

	@GET
	@Path("")
	@Produces(MediaType.APPLICATION_JSON)
	public Car[] getAllCars() {
		Car[] allCars = new Car[carList.size()];
		for (int i = 0; i < carList.size(); i++) {
			allCars[i] = carList.get(i);
		}
		return allCars;
	}
	@GET
	@Path("/brand/{carBrand}")
	@Produces(MediaType.APPLICATION_JSON)
	public Car[] getCarsSameBrand(@PathParam("carBrand") String brand) {
		ArrayList<Car> allCars = new ArrayList<>();
		for (int i = 0; i < carList.size(); i++) {
			if(carList.get(i).getCarBrand().equals(brand))
			allCars.add(carList.get(i));
		}
		
		return allCars.toArray(new Car[allCars.size()]);
	}
	
	@GET
	@Path("/year/{year}")
	@Produces(MediaType.APPLICATION_JSON)
	public Car[] getCarsSameYear(@PathParam("year") long year) {
		ArrayList<Car> allCars = new ArrayList<>();
		for (int i = 0; i < carList.size(); i++) {
			if(carList.get(i).getYear() == year)
			allCars.add(carList.get(i));
		}
		
		return allCars.toArray(new Car[allCars.size()]);
	}
	
	
	@PUT
	@Path("/{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Car ctu) {
		if(ctu.getCarBrand() == null|| ctu.getCarBrand().length() == 0 || ctu.getCarModel() == null || ctu.getCarModel().length() ==0 || ctu.getCarPrice() < 0 
				|| ctu.getCarModelOption() == null || ctu.getCarModelOption().length() == 0){
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		} 
		
		for(int i = 0; i< carList.size();i++) {
			if(ctu.getCarId() == carList.get(i).getCarId()) {
				carList.remove(i);
				carList.add(ctu);
				return Response.status(Response.Status.OK).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addCar(Car cta) {
		
		if(cta.getCarBrand() == null|| cta.getCarBrand().length() == 0 || cta.getCarModel() == null || cta.getCarModel().length() ==0 || cta.getCarPrice() < 0 
				|| cta.getCarModelOption() == null || cta.getCarModelOption().length() == 0){
			return Response.status(Response.Status.NOT_ACCEPTABLE).build();
		} 
		
		carList.add(cta);
		return Response.status(Response.Status.CREATED).build();
		
	}
	@DELETE
	@Path("{id}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deleteCar(@PathParam("id") long id) {
		for(int i = 0; i < carList.size();i++) {
			if(carList.get(i).getCarId() == id){
				carList.remove(i);
				return Response.status(Response.Status.OK).build();
			}
		}
		throw new NotFoundException( new JsonError("Error","Car"+id+" not found"));
	}
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Car getCar(@PathParam("id") long id) {
		for (int i = 0; i < carList.size(); i++) {
			if(carList.get(i).getCarId() == id) {
				return carList.get(i);
			}
		}
		throw new NotFoundException( new JsonError("Error","Car"+id+" not found"));
	}
}



