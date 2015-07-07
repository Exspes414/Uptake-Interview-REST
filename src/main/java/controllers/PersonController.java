package main.java.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import main.java.models.PersonService;
import main.java.models.Person;

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
	public Person createPerson(@RequestBody @Valid final Person person){
		return this.personService.save(person);
	}

	@RequestMapping(method = RequestMethod.GET)
	public List<Person> getAll(){
		return this.personService.get();
	}
	
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.GET)
	public Person getPerson(@PathVariable("id") Long id){
		return this.personService.get(id);
	}
	
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.PUT)
	public Person updatePerson(@PathVariable("id") Long id, @RequestBody @Valid final Person person){
		return this.personService.update(id, person);
	}
	
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.DELETE)
	public void deletePerson(@PathVariable("id") Long id){
		this.personService.delete(id);
	}
}
