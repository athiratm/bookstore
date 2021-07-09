package com.store.demo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.runner.JUnitPlatform;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.store.demo.api.BookStoreController;
import com.store.demo.entity.Book;
import com.store.demo.repository.BooksRepository;
import com.store.demo.service.BookStoreService;

@ExtendWith(MockitoExtension.class)
@RunWith(JUnitPlatform.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class BookStoreControllerTest {

	@InjectMocks
	BookStoreController bookStoreController;

	@Mock
	BookStoreService bookStoreService;

	@Mock
	BooksRepository booksRepository;

	String jsonStr;

	ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

	Book book = new Book("3333333", "testbook", "about testbook", "abc xyz", "COMIC", 150.00);
	Book book1 = new Book("3333333", "testbook update", "about testbook", "abc xyz", "COMIC", 150.00);
	
	@BeforeEach
	void setMockOutput() throws JsonProcessingException {

		
		jsonStr = mapper.writeValueAsString(book);
		when(bookStoreService.saveBook(book)).thenReturn(book.getIsbn());
		when(bookStoreService.getBook("3333333")).thenReturn(jsonStr);
		when(bookStoreService.updateBook(book1)).thenReturn(book1);
		when(bookStoreService.deleteBook(book1.getIsbn())).thenReturn(true);

	}

	@Test
	public void testAddBook() {

		String isbn = bookStoreController.addBook(book);
		assertThat(isbn).isEqualTo(book.getIsbn());
	}

	@Test
	public void testGetBook() {

		String bookStr = bookStoreController.getBook("3333333");
		assertEquals(jsonStr, bookStr);
	}
	
	@Test
	public void testUpdateBook() {

		Book updatedBook = bookStoreController.updateBook(book1);
		assertEquals(updatedBook.getIsbn(), book.getIsbn());
	}
	
	
	@Test
	public void testDeleteBook() {

		Boolean isDeleted = bookStoreController.deleteBook(book1.getIsbn());
		assertEquals(isDeleted, true);
	}
}
