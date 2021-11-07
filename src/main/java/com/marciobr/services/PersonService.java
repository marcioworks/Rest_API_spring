package com.marciobr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.marciobr.converter.DozerConverter;
import com.marciobr.converter.custom.PersonConverter;
import com.marciobr.data.models.Person;
import com.marciobr.data.vo.PersonVO;
import com.marciobr.data.vo.v2.PersonVOV2;
import com.marciobr.exceptions.ResourceNotFoundException;
import com.marciobr.repositories.PersonRepository;

@Service
public class PersonService {

	@Autowired
	private PersonRepository personRepo;
	
	@Autowired
	private PersonConverter converter;
	
	
	public List<PersonVO> findAll(){
		return DozerConverter.parseListObjects(personRepo.findAll(),PersonVO.class) ;
	}
	
	public PersonVO create(PersonVO person) {
		var entity  = DozerConverter.parseObject(person, Person.class);
		PersonVO vo = DozerConverter.parseObject(personRepo.save(entity), PersonVO.class);
		return vo;
	}
	
	public PersonVOV2 createV2(PersonVOV2 person) {
		var entity  = converter.convertVoToEntity(person);
		PersonVOV2 vo = converter.convertEntityToVo(personRepo.save(entity));
		return vo;
	}

	public PersonVO findById(Long id) {
		var entity =  personRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource Not Found With this Id"));
		return  DozerConverter.parseObject(entity, PersonVO.class);
	}


	public PersonVO update(PersonVO person) {
		var entity = personRepo.findById(person.getKey()).orElseThrow(()-> new ResourceNotFoundException("Resource Not Found With this Id"));
		entity.setFirstName(person.getFirstName());	
		entity.setLastName(person.getLastName());
		entity.setAddress(person.getAddress());
		entity.setGender(person.getGender());
		
		var vo = DozerConverter.parseObject(personRepo.save(entity), PersonVO.class);
		return vo;
	}
	
	@Transactional
	public PersonVO disablePerson(Long id) {
		personRepo.disablePerson(id);
		var entity =  personRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource Not Found With this Id"));
		return  DozerConverter.parseObject(entity, PersonVO.class);
	}
	
	public void delete(Long id) {
		Person entity = personRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Resource Not Found With this Id"));
		personRepo.delete(entity);
	}
	
	public Page<PersonVO> findPersonByName(String firstName, Pageable pageable) {
		var page = personRepo.findPersonByName(firstName,pageable);
		return page.map(this::convertToPersonVO) ;
	}


	public Page<PersonVO> findAllByPage(Pageable pageable) {
		var page = personRepo.findAll(pageable);
		return page.map(this::convertToPersonVO) ;
	}

	private PersonVO convertToPersonVO(Person entity) {
		return DozerConverter.parseObject(entity,PersonVO.class);
	}
}
