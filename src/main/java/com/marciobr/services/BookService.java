package com.marciobr.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.marciobr.converter.DozerConverter;
import com.marciobr.data.models.Book;
import com.marciobr.data.models.Person;
import com.marciobr.data.vo.BookVO;
import com.marciobr.data.vo.PersonVO;
import com.marciobr.exceptions.ResourceNotFoundException;
import com.marciobr.repositories.BookRepository;

@Service
public class BookService {

	@Autowired
	private BookRepository bookRepo;

	public List<BookVO> findAll() {
		return DozerConverter.parseListObjects(bookRepo.findAll(), BookVO.class);
	}

	public BookVO findById(Long id) {
		var entity = bookRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found With this Id"));
		return DozerConverter.parseObject(entity, BookVO.class);
	}

	public BookVO create(BookVO bookVO) {
		var entity = DozerConverter.parseObject(bookVO, Book.class);
		BookVO vo = DozerConverter.parseObject(bookRepo.save(entity), BookVO.class);
		return vo;
	}

	public void delete(Long id) {
		Book entity = bookRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found With this Id"));
		bookRepo.delete(entity);
	}

	public BookVO update(BookVO book) {
		var entity = bookRepo.findById(book.getKey())
				.orElseThrow(() -> new ResourceNotFoundException("Resource Not Found With this Id"));
		entity.setAuthor(book.getAuthor());
		entity.setLaunchDate(book.getLaunchDate());
		entity.setPrice(book.getPrice());
		entity.setTitle(book.getTitle());

		BookVO vo = DozerConverter.parseObject(bookRepo.save(entity), BookVO.class);
		return vo;

	}

	public Page<BookVO> findAllByPage(Pageable pageable) {
		var page = bookRepo.findAll(pageable);
		return page.map(this::convertToBookVO) ;
	}

	private BookVO convertToBookVO(Book entity) {
		return DozerConverter.parseObject(entity,BookVO.class);
	}
	

}
