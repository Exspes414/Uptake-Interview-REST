package main.java.models;

import java.util.List;

/**
 * Interface for communicating with the persistence layer
 */
public interface FamilyService {

	/**
	 * Saves a new family into the persistence layer
	 * @param family The family object to be saved
	 * @return The saved Family or null if provided Family Id was a duplicate
	 */
	Family save(Family family);
	
	/**
	 * Retrieves a list of all Family objects inb the persistence layer
	 * @return A list of all Family objects
	 */
	List<Family> get();
	
	/**
	 * Retrieves a specific Family object with the specified Id
	 * @param id The Id of the Family object to retrieve
	 * @return The Family object with the specified Id or null if no Family object has the specified Id
	 */
	Family get(Long id);
	
	/**
	 * Deletes the family object with the specified Id
	 * @param id The Id of the Family object to delete
	 * @return True if the Family object was deleted, false if no Family object had the specified Id
	 */
	boolean delete(Long id);
	
	/**
	 * Updates a Family object in the persistence layer
	 * @param id The Id of the Family to update
	 * @param family The state to update the Family to
	 * @return The updated state of the Family object or null if no Family object exists with the specified Id
	 */
	Family update(Long id, Family family);
	
	/**
	 * Adds a member to the specified Family object
	 * @param familyId The family to add a person to
	 * @param personId The id of the person object to add to the Family
	 * @return The state of the added Person object or null if no Family exists with the specified Id
	 */
	Person addMember(Long familyId, Long person);
	
	/**
	 * Removes a Person from a Family
	 * @param familyId The Family to remove a member from
	 * @param personId The Person to remove from the family
	 * @return True if the Person was removed from the Family, false if either the Family Id or the Person Id don't exist
	 */
	boolean removeMember(Long familyId, Long personId);
	
}
