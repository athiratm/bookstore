package com.store.demo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.store.demo.entity.Book;
import com.store.demo.repository.BooksRepository;
import com.store.demo.service.BookStoreService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
public class BookStoreServiceTest {

	@InjectMocks
    BookStoreService bookStoreService;
	
	@Mock
	BooksRepository booksRepository;
	
	ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
	
	
	@Test
	public void testSaveBook() {
		
		Book book = new Book("3333333","testbook","about testbook","abc xyz","COMIC",150.00);
		when(booksRepository.save(any(Book.class))).thenReturn(book);
		String bookISBN = bookStoreService.saveBook(book);
		//assertEquals(book, savedBook);
		assertThat(bookISBN).isEqualTo(book.getIsbn());
		
	}
	
	@Test
	public void testGetBook() {
		
		Book book = new Book("3333333","testbook","about testbook","abc xyz","COMIC",150.00);
		String jsonStr = "";
		try {
			 jsonStr = mapper.writeValueAsString(book);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		when(booksRepository.getById(any(String.class))).thenReturn(book);
		String retrievedBook = bookStoreService.getBook("3333333");
		assertEquals(retrievedBook, jsonStr);
	}
	
//	@Test
//	public void testCheckoutBooks() {
//		
//		List<Book> booksList = new ArrayList<Book>();
//		Book book1 = new Book("3333333","testbook1","about testbook1","abc xyz1","COMIC",150.00);
//		Book book2 = new Book("4444444","testbook2","about testbook2","abc xyz2","HORROR",150.00);
//		booksList.add(book1);
//		booksList.add(book2);
//		
//		String discountCode = "GET_FIFTEEN";
//		
//		when(booksRepository.getById("3333333")).thenReturn(book1);
//		when(booksRepository.getById("4444444")).thenReturn(book2);
//		
//		when(booksRepository.save(book1)).thenReturn(book1);
//		when(booksRepository.save(book2)).thenReturn(book2);
//		
//		String totalStr = bookStoreService.checkoutBooks(booksList, discountCode);
//		//System.out.println(totalStr);
//		assertThat(totalStr).contains("totalPayableAmount");
//		
//	}
	
	@Test
	public void testUpdateBook() {
		
		Book book = new Book("3333333","testbook","about testbook updated","abc xyz","COMIC",150.00);
		when(booksRepository.save(any(Book.class))).thenReturn(book);
		Book updatedBook = bookStoreService.updateBook(book);
		
		assertThat(updatedBook.getIsbn()).isEqualTo(book.getIsbn());
	}
	
	@Test
	public void testDeleteBook() {
		
		Book book = new Book("3333333","testbook","about testbook updated","abc xyz","COMIC",150.00);
		booksRepository.save(book);
		//when(booksRepository.deleteById(book.getIsbn())).thenReturn(Void.class);
		Boolean isDeleted = bookStoreService.deleteBook(book.getIsbn());
		
		assertThat(isDeleted).isEqualTo(true);
	}
	
	
}
