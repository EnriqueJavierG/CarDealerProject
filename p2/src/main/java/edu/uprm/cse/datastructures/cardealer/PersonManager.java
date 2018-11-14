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
import edu.uprm.cse.datastructures.cardealer.model.Person;
import edu.uprm.cse.datastructures.cardealer.model.PersonComparator;
import edu.uprm.cse.datastructures.cardealer.util.CircularSortedDoublyLinkedList;
import edu.uprm.cse.datastructures.cardealer.util.SortedList;
@Path("/person")
public class PersonManager {
	static PersonComparator personComp = new PersonComparator();
	private static final SortedList<Person> personList = new CircularSortedDoublyLinkedList<Person>(personComp);

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Person[] getAllPersons() {
		Person[] allPersons = new Person[personList.size()];
		for (int i = 0; i < personList.size(); i++) {
			allPersons[i] = personList.get(i);
		}
		return allPersons;
	}
	@PUT
	@Path("/{id}/update")
	@Produces(MediaType.APPLICATION_JSON)
	public Response update(Person ptu) {
		for(int i = 0; i< personList.size();i++) {
			if(ptu.getPersonId() == personList.get(i).getPersonId()) {
				personList.remove(i);
				personList.add(ptu);
				return Response.status(Response.Status.OK).build();
			}
		}
		return Response.status(Response.Status.NOT_FOUND).build();
	}
	@POST
	@Path("/add")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addPerson(Person pta) {
		for(int i = 0; i < personList.size(); i ++){
			if(personList.get(i).getPersonId() == pta.getPersonId()){
				// to prevent two persons with the same IDs
				return Response.status(Response.Status.CONFLICT).build();
				//will launch this response if the person already exist in the list
			}	
		}
		personList.add(pta);
		return Response.status(Response.Status.CREATED).build();
		
	}
	@DELETE
	@Path("{id}/delete")
	@Produces(MediaType.APPLICATION_JSON)
	public Response deletePerson(@PathParam("id") long id) {
		for(int i = 0; i < personList.size();i++) {
			if(personList.get(i).getPersonId() == id){
				personList.remove(i);
				return Response.status(Response.Status.OK).build();
			}
		}
		throw new NotFoundException( new JsonError("Error","Car"+id+" not found"));
	}
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person getPerson(@PathParam("id") long id) {
		for (int i = 0; i < personList.size(); i++) {
			if(personList.get(i).getPersonId() == id) {
				return personList.get(i);//will return the  person with the respective ID
			}
		}
		throw new NotFoundException( new JsonError("Error","Car"+id+" not found"));
	}
	
	@GET
	@Path("/lastname/{lastName}")
	@Produces(MediaType.APPLICATION_JSON)
	public Person[] getPersonSameLastName(@PathParam("lastName") String lastName) {
		ArrayList<Person> allPerson = new ArrayList<>();
		for (int i = 0; i < personList.size(); i++) {
			if(personList.get(i).getLastName().toLowerCase().equals(lastName.toLowerCase()) )
			allPerson.add(personList.get(i));
		}
		
		return allPerson.toArray(new Person[allPerson.size()]);
	}

}
