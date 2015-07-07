package main.java.models;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class PersonServiceImpl implements PersonService {

	private final PersonRepositiory repository;
	
	@Inject
	public PersonServiceImpl(final PersonRepositiory repository){
		this.repository = repository;
	}
	
	@Override
	@Transactional
	public Person save(final Person person) {
		if( person.getId() != null && !this.repository.exists(person.getId())){
			return this.repository.save(person);			
		}else{
			return null;
		}
	}

	@Override
	@Transactional
	public Person get(Long id) {
		if( this.repository.exists(id)){
			return this.repository.getOne(id);			
		}else{
			return null;
		}
	}

	@Override
	@Transactional
	public List<Person> get() {
		return this.repository.findAll();
	}

	@Override
	@Transactional
	public boolean delete(Long id) {
		if(this.repository.exists(id)){
			this.repository.delete(id);
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	@Transactional
	public Person update(Long id, Person person){
		if(this.repository.exists(id)){
			person.setId(id);
			return this.repository.save(person);			
		}else{
			return null;
		}
	}

}
