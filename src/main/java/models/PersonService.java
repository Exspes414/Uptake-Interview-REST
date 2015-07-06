package main.java.models;

import java.util.List;

public interface PersonService {

	Person save(Person person);
	
	List<Person> get();
	
	Person get(Long id);
		
	void delete(Long id);
}
