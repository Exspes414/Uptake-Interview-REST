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

/**
 * REST API Controller for the Family object
 */
@RestController
@RequestMapping("/family")
public class FamilyController {

	/**
	 * Service used to communicate with the persistence layer
	 */
	private final FamilyService familyService;
	
	@Inject
	public FamilyController(final FamilyService familyService){
		this.familyService = familyService;
	}
	
	/**
	 * POST API call for creating a Family object
	 * @param family The Family object to create
	 * @return The Family object that was created or a HTTP Conflict if one could not be created
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Family> createFamily(@RequestBody @Valid final Family family){
		
		Family created = this.familyService.save(family);
		
		if( created != null ){
			return new ResponseEntity<Family>(created, HttpStatus.CREATED);
		}else{
			return new ResponseEntity<Family>(HttpStatus.CONFLICT);
		}
	}
	
	/**
	 * GET API call to retrieve all Family objects
	 * @return A list of all family objects
	 */
	@RequestMapping(method=RequestMethod.GET)
	public ResponseEntity<List<Family>> getAll(){
		return new ResponseEntity<List<Family>>(this.familyService.get(), HttpStatus.OK);
	}
	
	/**
	 * GET API call to retrieve a specific Family object
	 * @param id The id of the Family object to retrieve
	 * @return The Family object or a 404 if it does not exist
	 */
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.GET)
	public ResponseEntity<Family> get(@PathVariable("id") Long id){
		
		Family retrieved = this.familyService.get(id);
		
		if( retrieved != null ){
			return new ResponseEntity<Family>(retrieved, HttpStatus.OK);
		}else{
			return new ResponseEntity<Family>(HttpStatus.NOT_FOUND);
		}
		
	}
	/**
	 * PUT API call to update a specific family object
	 * @param id The id of the Family object to update
	 * @param family The state to update the Family object to
	 * @return The updated state of the family object
	 */
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.PUT)
	public ResponseEntity<Family> put(@PathVariable("id") Long id, @RequestBody @Valid final Family family){
		
		Family updated = this.familyService.update(id, family);
		
		if( updated != null ){
			return new ResponseEntity<Family>(updated, HttpStatus.OK);
		}else{
			return new ResponseEntity<Family>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * DELETE API call to delete a specific family object
	 * @param id The id of the family object to delete
	 * @return HTTP OK if the Family object was deleted, HTTP 404 if no Family with the specified ID exists
	 */
	@RequestMapping(value="/{id:[\\d]+}", method=RequestMethod.DELETE)
	public ResponseEntity<Family> delete(@PathVariable("id") Long id){
		if(this.familyService.delete(id)){
			return new ResponseEntity<Family>(HttpStatus.OK);
		}else{
			return new ResponseEntity<Family>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * POST API call to add a Person to a family
	 * @param id The Id of the Family to add a Person to
	 * @param person The Person object to add the Family
	 * @return The Person object added to the Family or HTTP 404 if the Family object could not be found
	 */
	@RequestMapping(value="/{familyId:[\\d]+}/members/{personId:[\\d]+}", method=RequestMethod.POST)
	public ResponseEntity<Person> addMember(@PathVariable("familyId") Long id, @PathVariable("personId") @Valid final Long person){
		
		Person addedMember = this.familyService.addMember(id,  person);
		
		if( addedMember != null ){
			return new ResponseEntity<Person>(addedMember, HttpStatus.OK);
		}else{
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
		
	}
	
	/**
	 * DELETE API call to delete a person from a family
	 * @param familyId The Family Id to remove the person from
	 * @param personId The Person Id to remove from the family
	 * @return HTTP OK if the person was successfully removed, HTTP 404 if the Family or Person do not exist
	 */
	@RequestMapping(value="/{familyId:[\\d]+}/members/{personId:[\\d]+}", method=RequestMethod.DELETE)
	public ResponseEntity<Person> removeMember(@PathVariable("familyId") Long familyId, @PathVariable("personId") Long personId){
		if( this.familyService.removeMember(familyId, personId)){
			return new ResponseEntity<Person>(HttpStatus.OK);
		}else{
			return new ResponseEntity<Person>(HttpStatus.NOT_FOUND);
		}
	}
	
}
