package main.java.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import main.java.models.PersonService;
import main.java.models.Person;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/person")
public class PersonController {

	private final PersonService personService;
	
	@Inject
	public PersonController(final PersonService personService){
		this.personService = personService;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<Person> createPerson(@RequestBody @Valid final Person person){
		
		Person created = this.personService.save(person);
		
		if( created != null ){
			return new ResponseEntity<Person>(created, HttpStatus.CREATED);
		}
		return new ResponseEntity<Person>(HttpStatus.CONFLICT);
	}

	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Person>> getAll(){
		return new ResponseEntity<List<Person>>(this.personService.get(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.GET)
	public ResponseEntity<Person> getPerson(@PathVariable("id") Long id){
		
		Person retrieved = this.personService.get(id);
		
		if( retrieved != null ){
			return new ResponseEntity<Person>(retrieved, HttpStatus.OK);
		}else{
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		
		
	}
	
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.PUT)
	public ResponseEntity<Person> updatePerson(@PathVariable("id") Long id, @RequestBody @Valid final Person person){
		
		Person updated = this.personService.update(id, person);
		
		if( updated != null ){
			return new ResponseEntity<Person>(updated, HttpStatus.OK);
		}else{
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.DELETE)
	public ResponseEntity<Person> deletePerson(@PathVariable("id") Long id){
		if( this.personService.delete(id) ){
			return new ResponseEntity<Person>(HttpStatus.OK);
		}else{
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
	}
}
