package main.java.models;

import java.util.List;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
@Validated
public class FamilyServiceImpl implements FamilyService {

	private final FamilyRepository familyRepository;
	
	private final PersonRepositiory personRepository;
	
	@Inject
	public FamilyServiceImpl(final FamilyRepository familyRepository, final PersonRepositiory personRepository) {
		this.familyRepository = familyRepository;
		this.personRepository = personRepository;
	}
	
	@Override
	@Transactional
	public Family save(Family family) {
		return this.familyRepository.save(family);
	}

	@Override
	@Transactional
	public List<Family> get() {
		return this.familyRepository.findAll();
	}

	@Override
	@Transactional
	public Family get(Long id) {
		return this.familyRepository.findOne(id);
	}

	@Override
	@Transactional
	public void delete(Long id) {
		if( this.familyRepository.exists(id)){
			this.familyRepository.delete(id);			
		}
	}
	
	@Override
	@Transactional
	public Family update(Long id, Family family){
		if( this.familyRepository.exists(id)){
			family.setId(id);
			return this.familyRepository.save(family);			
		}else{
			return null;
		}
	}

	@Override
	@Transactional
	public Person addMember(Long familyId, Person person) {
		if(this.familyRepository.exists(familyId)){
			if( !this.personRepository.exists(person.getId())){
				person = this.personRepository.save(person);
			}
			
			Family f = this.familyRepository.getOne(familyId);
			f.getMembers().add(person);
			this.familyRepository.save(f);
			
			return person;
			
		}else{
			return null;
		}
	}
	
	@Override
	@Transactional
	public boolean removeMember(Long familyId, Long personId){
		if( this.familyRepository.exists(familyId) && this.personRepository.exists(personId)){
			Family f = this.familyRepository.getOne(familyId);
			
			for(Person p : f.getMembers()){
				if(p.getId() == personId){
					f.getMembers().remove(p);
					this.familyRepository.save(f);
					return true;
				}
			}
			
			return false;
		}else{
			return false;
		}
	}

}
