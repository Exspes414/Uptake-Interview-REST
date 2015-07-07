package main.java.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import main.java.models.Family;
import main.java.models.FamilyService;
import main.java.models.Person;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/family")
public class FamilyController {

	private final FamilyService familyService;
	
	@Inject
	public FamilyController(final FamilyService familyService){
		this.familyService = familyService;
	}
	
	@RequestMapping(method=RequestMethod.POST)
	public Family createFamily(@RequestBody @Valid final Family family){
		return this.familyService.save(family);		
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public List<Family> getAll(){
		return this.familyService.get();
	}
	
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.GET)
	public Family get(@PathVariable("id") Long id){
		return this.familyService.get(id);
	}
	
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.PUT)
	public Family put(@PathVariable("id") Long id, @RequestBody @Valid final Family family){
		return this.familyService.update(id, family);
	}
	
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.DELETE)
	public void delete(@PathVariable("id") Long id){
		this.familyService.delete(id);
	}
	
	@RequestMapping(value="/{id:[\\d]+}/members", method=RequestMethod.POST)
	public Person addMember(@PathVariable("id") Long id, @RequestBody @Valid final Person person){
		return this.familyService.addMember(id, person);
	}
	
}
