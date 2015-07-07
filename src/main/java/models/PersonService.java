package main.java.models;

import java.util.List;

public interface PersonService {

	Person save(Person person);
	
	List<Person> get();
	
	Person get(Long id);
		
	boolean delete(Long id);
	
	Person update(Long id, Person person);
}
