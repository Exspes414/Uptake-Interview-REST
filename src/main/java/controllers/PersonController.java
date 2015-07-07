package main.java.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import main.java.models.PersonService;
import main.java.models.Person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST Controller for the Person object
 */
@RestController
@RequestMapping("/person")
public class PersonController {

	/**
	 * The service used to communicate to the database via JPA and Hibernate
	 */
	private final PersonService personService;
	
	@Inject
	public PersonController(final PersonService personService){
		this.personService = personService;
	}
	
	/**
	 * REST API call to create a new Person
	 * @param person The Person to create, auto-generates an ID if one is not provided. Will not create a duplicate ID
	 * @return The created Person, including the generated ID if one was not provided
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Person> createPerson(@RequestBody @Valid final Person person){
		
		Person created = this.personService.save(person);
		
		if( created != null ){
			return new ResponseEntity<Person>(created, HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Person>(HttpStatus.CONFLICT);			
		}
	}

	/**
	 * REST API call to retrieve all Person objects
	 * @return A list of all Person objects in the database
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Person>> getAll(){
		return new ResponseEntity<List<Person>>(this.personService.get(), HttpStatus.OK);
	}
	
	/**
	 * REST API call to retrieve a specific Person object
	 * @param id The Id of the Person object to retrieve
	 * @return The Person object with the specified Id or a 404 if one does not exist
	 */
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.GET)
	public ResponseEntity<Person> getPerson(@PathVariable("id") Long id){
		
		Person retrieved = this.personService.get(id);
		
		if( retrieved != null ){
			return new ResponseEntity<Person>(retrieved, HttpStatus.OK);
		}else{
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * REST API call to update a specific Person object
	 * @param id The Id of the Person object to update
	 * @param person The state to update the specified Person object to
	 * @return The updated person object or a 404 if no Person with the specified ID exists
	 */
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.PUT)
	public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @RequestBody @Valid final Person person){
		
		Person updated = this.personService.update(id, person);
		
		if( updated != null ){
			return new ResponseEntity<Person>(updated, HttpStatus.OK);
		}else{
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * REST API call to delete a specific Person object
	 * @param id The Id of the Person object to delete
	 * @return HTTP 200 if the delete was successful, HTTP 404 if no Person with the specified Id exists
	 */
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.DELETE)
	public ResponseEntity<Person> deletePerson(@PathVariable("id") Long id){
		if( this.personService.delete(id) ){
			return new ResponseEntity<Person>(HttpStatus.OK);
		}else{
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
	}
}
