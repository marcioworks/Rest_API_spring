package com.marciobr.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.marciobr.data.models.Book;

public interface BookRepository extends JpaRepository<Book, Long>{

}
