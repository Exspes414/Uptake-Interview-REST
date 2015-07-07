package main.java.models;

import java.util.List;

public interface FamilyService {

	Family save(Family family);
	
	List<Family> get();
	
	Family get(Long id);
	
	void delete(Long id);
	
	Family update(Long id, Family family);
	
	Person addMember(Long familyId, Person person);
	
	boolean removeMember(Long familyId, Long personId);
	
}
