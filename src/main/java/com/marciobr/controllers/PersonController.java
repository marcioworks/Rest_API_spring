package com.marciobr.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marciobr.data.vo.PersonVO;
import com.marciobr.data.vo.v2.PersonVOV2;
import com.marciobr.services.PersonService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "Person EndPoint", tags = { "PersonEndpoint" })
@RestController
@RequestMapping("/api/person")
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@Autowired
	private PagedResourcesAssembler<PersonVO> assembler;

//	@CrossOrigin(origins = "http://localhost:8080")
//	@ApiOperation(value = "Find All Persons")
//	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
//	public List<PersonVO> findAll() {
//		List<PersonVO> persons = personService.findAll();
//		persons.stream()
//				.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
//		return persons;
//	}

	@ApiOperation(value = "Find All Persons by name")
	@GetMapping(value="/findPersonByName/{firstName}",produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<?> findPersonByName(
			@PathVariable("firstName") String firstName,
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit,
			@RequestParam(value= "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC: Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit,Sort.by(sortDirection,"firstName"));
		Page<PersonVO> persons = personService.findPersonByName(firstName,pageable);
		persons.stream()
				.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		
		PagedModel<?> resources = assembler.toModel(persons);
		return  ResponseEntity.ok(resources);
	}
	
	@ApiOperation(value = "Find All Persons by Page")
	@GetMapping(produces = { "application/json", "application/xml", "application/x-yaml" })
	public ResponseEntity<?> findAll(
			@RequestParam(value = "page", defaultValue = "0") Integer page,
			@RequestParam(value = "limit", defaultValue = "12") Integer limit,
			@RequestParam(value= "direction", defaultValue = "asc") String direction) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction) ? Direction.DESC: Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit,Sort.by(sortDirection,"firstName"));
		Page<PersonVO> persons = personService.findAllByPage(pageable);
		persons.stream()
				.forEach(p -> p.add(linkTo(methodOn(PersonController.class).findById(p.getKey())).withSelfRel()));
		
		PagedModel<?> resources = assembler.toModel(persons);
		return  ResponseEntity.ok(resources);
	}

//	@CrossOrigin(origins = {"http://localhost:8080","teste.com.br"})
	@ApiOperation(value = "Find a Persons by its id")
	@GetMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO findById(@PathVariable("id") Long id) {
		PersonVO personVo = personService.findById(id);
		personVo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVo;
	}

	@ApiOperation(value = "Create a Person")
	@PostMapping(produces = { "application/json", "application/xml", "application/x-yaml" }, consumes = {
			"application/json", "application/xml", "application/x-yaml" })
	public PersonVO create(@RequestBody PersonVO person) {
		PersonVO personVo = personService.create(person);
		personVo.add(linkTo(methodOn(PersonController.class).findById(personVo.getKey())).withSelfRel());
		return personVo;
	}

	@PostMapping("/v2")
	public PersonVOV2 createV2(@RequestBody PersonVOV2 person) {
		return personService.createV2(person);
	}

	@ApiOperation(value = "Update a Person data")
	@PutMapping(produces = { "application/json", "application/xml" }, consumes = { "application/json",
			"application/xml" })
	public PersonVO update(@RequestBody PersonVO person) {
		PersonVO personVo = personService.update(person);
		personVo.add(linkTo(methodOn(PersonController.class).findById(personVo.getKey())).withSelfRel());
		return personVo;
	}

	@ApiOperation(value = "disable a Persons by its id")
	@PatchMapping(value = "/{id}", produces = { "application/json", "application/xml", "application/x-yaml" })
	public PersonVO disablePerson(@PathVariable("id") Long id) {
		PersonVO personVo = personService.disablePerson(id);
		personVo.add(linkTo(methodOn(PersonController.class).findById(id)).withSelfRel());
		return personVo;
	}

	@ApiOperation(value = "remove A Person by its id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		personService.delete(id);
		return ResponseEntity.ok().build();
	}
}
