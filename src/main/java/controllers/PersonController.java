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
public class PersonController {

	private final PersonService personService;
	
	@Inject
	public PersonController(final PersonService personService){
		this.personService = personService;
	}
	
	@RequestMapping(value="/person", method = RequestMethod.POST)
	public Person createPerson(@RequestBody @Valid final Person person){
		return this.personService.save(person);
	}

	@RequestMapping(value="/person", method = RequestMethod.GET)
	public List<Person> getAll(){
		return this.personService.get();
	}
	
	@RequestMapping(value="/person/{id:[\\d]+}", method=RequestMethod.GET)
	public Person getPerson(@PathVariable("id") Long id){
		return this.personService.get(id);
	}
}
