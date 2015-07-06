package main.java.models;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

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
		return this.repository.save(person);
	}

	@Override
	@Transactional
	public Person get(Long id) {
		return this.repository.getOne(id);
	}

	@Override
	@Transactional
	public List<Person> get() {
		return this.repository.findAll();
	}

	@Override
	@Transactional
	public void delete(Long id) {
		this.repository.delete(id);
	}

}
