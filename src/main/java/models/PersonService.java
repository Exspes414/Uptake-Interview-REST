package main.java.models;

import java.util.List;

/**
 * Interface to communicate with the persistence layer
 */
public interface PersonService {

	/**
	 * Saves a Person object to the Database
	 * @param person The Person object to add to the database
	 * @return The saved state of the Person object or null if a Person with the provided Id already exists
	 */
	Person save(Person person);
	
	/**
	 * Retrieves a list of all Person objects
	 * @return A list of all Person objects in the database
	 */
	List<Person> get();
	
	/**
	 * Retrieves an individual Person object from the database
	 * @param id The id of the Person object to retrieve
	 * @return The Person object with the specified Id or null if none exists
	 */
	Person get(Long id);
		
	/**
	 * Deletes a Person object from the database
	 * @param id The Id of the Person object to delete
	 * @return True if the person object was deleted, false if there was no Person with the specified Id
	 */
	boolean delete(Long id);
	
	/**
	 * Updates a Person object in the database
	 * @param id The Id of the Person object to update
	 * @param person The state of the Person object to update to
	 * @return The updated state of the Person object or null if no Person object matched the provided Id
	 */
	Person update(Long id, Person person);
}
