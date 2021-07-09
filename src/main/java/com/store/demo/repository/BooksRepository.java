package com.store.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.store.demo.entity.Book;

@Repository
public interface BooksRepository extends JpaRepository<Book, String>{



}
