package main.java.controllers;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import main.java.models.Family;
import main.java.models.FamilyService;
import main.java.models.Person;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	public ResponseEntity<Family> createFamily(@RequestBody @Valid final Family family){
		
		Family created = this.familyService.save(family);
		
		if( created != null ){
			return new ResponseEntity<Family>(family, HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Family>(HttpStatus.CONFLICT);
		}
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Family>> getAll(){
		return new ResponseEntity<List<Family>>(this.familyService.get(), HttpStatus.OK);
	}
	
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.GET)
	public ResponseEntity<Family> get(@PathVariable("id") Long id){
		
		Family retrieved = this.familyService.get(id);
		
		if( retrieved != null ){
			return new ResponseEntity<Family>(retrieved, HttpStatus.OK);
		}else{
			return new ResponseEntity<Family>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.PUT)
	public ResponseEntity<Family> put(@PathVariable("id") Long id, @RequestBody @Valid final Family family){
		
		Family updated = this.familyService.update(id, family);
		
		if( updated != null ){
			return new ResponseEntity<Family>(updated, HttpStatus.OK);
		}else{
			return new ResponseEntity<Family>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.DELETE)
	public ResponseEntity<Family> delete(@PathVariable("id") Long id){
		if(this.familyService.delete(id)){
			return new ResponseEntity<Family>(HttpStatus.OK);
		}else{
			return new ResponseEntity<Family>(HttpStatus.NOT_FOUND);
		}
	}
	
	@RequestMapping(value="/{id:[\\d]+}/members", method=RequestMethod.POST)
	public ResponseEntity<Person> addMember(@PathVariable("id") Long id, @RequestBody @Valid final Person person){
		
		Person addedMember = this.familyService.addMember(id,  person);
		
		if( addedMember != null ){
			return new ResponseEntity<Person>(addedMember, HttpStatus.OK);
		}else{
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	@RequestMapping(value="/{familyId:[\\d]+}/members/{personId:[\\d]+}", method=RequestMethod.DELETE)
	public ResponseEntity<Person> removeMember(@PathVariable("familyId") Long familyId, @PathVariable("personId") Long personId){
		if( this.familyService.removeMember(familyId, personId)){
			return new ResponseEntity<Person>(HttpStatus.OK);
		}else{
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
	}
	
}
