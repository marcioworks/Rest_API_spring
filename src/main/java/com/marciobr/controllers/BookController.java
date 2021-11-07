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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.marciobr.data.vo.BookVO;
//import com.marciobr.data.vo.v2.BookVOV2;
import com.marciobr.services.BookService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="Book EndPoint", tags= {"BookEndpoint"})
@RestController
@RequestMapping("/api/book")
public class BookController {

	@Autowired
	private BookService bookService;
	
//	@ApiOperation(value = "Find all Books")
//	@GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
//	public List<BookVO> findAll() {
//		List<BookVO> books =  bookService.findAll();
//		books.stream().forEach( p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
//		return books;
//	}		
	
	
	@ApiOperation(value = "Find all Books")
	@GetMapping(produces = {"application/json","application/xml","application/x-yaml"})
	public ResponseEntity<?> findAll(
			@RequestParam(value="page", defaultValue = "0")Integer page,
			@RequestParam(value="limit", defaultValue = "15") Integer limit,
			@RequestParam(value="direction",defaultValue = "asc")String direction,
			PagedResourcesAssembler assembler) {
		
		var sortDirection = "desc".equalsIgnoreCase(direction)? Direction.DESC: Direction.ASC;
		Pageable pageable = PageRequest.of(page, limit, Sort.by(sortDirection,"title"));
		
		Page<BookVO> books =  bookService.findAllByPage(pageable);
		books.stream().forEach( p -> p.add(linkTo(methodOn(BookController.class).findById(p.getKey())).withSelfRel()));
		PagedModel<?> resource = assembler.toModel(books);
		
		return ResponseEntity.ok(resource);
	}		
	
	@ApiOperation(value = "Find a Book by its id")
	@GetMapping(value= "/{id}",produces = {"application/json","application/xml","application/x-yaml"})
	public BookVO findById(@PathVariable("id") Long id) {
		BookVO bookVo = bookService.findById(id);
		bookVo.add(linkTo(methodOn(BookController.class).findById(id)).withSelfRel());
		return bookVo;
	}	
	
	@ApiOperation(value = "Create a Book")
	@PostMapping(produces = {"application/json","application/xml","application/x-yaml"},
			consumes ={"application/json","application/xml","application/x-yaml"})
	public BookVO create(@RequestBody BookVO book) {
		BookVO bookVo = bookService.create(book);
		bookVo.add(linkTo(methodOn(BookController.class).findById(bookVo.getKey())).withSelfRel());
		return bookVo;
	}
	
//	@PostMapping("/v2")
//	public BookVOV2 createV2(@RequestBody BookVOV2 person) {
//		return personService.createV2(person);
//	}
	
	
	@ApiOperation(value = "Update a Book data")
	@PutMapping(produces = {"application/json","application/xml"},
			consumes ={"application/json","application/xml"})
	public BookVO update(@RequestBody BookVO book) {
		BookVO  bookVo =  bookService.update(book);
		bookVo.add(linkTo(methodOn(BookController.class).findById(bookVo.getKey())).withSelfRel());
		return bookVo;
	}	
	
	@ApiOperation(value = "Remove a Book by its id")
	@DeleteMapping("/{id}")
	public ResponseEntity<?> delete(@PathVariable Long id) {
		bookService.delete(id);
		return ResponseEntity.ok().build();
	}
}
