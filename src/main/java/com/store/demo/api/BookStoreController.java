package com.store.demo.api;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.demo.entity.Book;
import com.store.demo.service.BookStoreService;

@RestController
@RequestMapping({ "/bookstore" })
public class BookStoreController {

	@Autowired
	private BookStoreService bookStoreService;

	private static final Logger LOGGER = LoggerFactory.getLogger(BookStoreController.class);

	/**
	 * Method to add a new book
	 * 
	 * @param book
	 * @return
	 */
	@PostMapping(path = { "/add" })
	public String addBook(@RequestBody Book book) {

		String returnStr = "";
		try {

			returnStr = bookStoreService.saveBook(book);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return returnStr;

	}

	/**
	 * Method to retrieve an existing book
	 * 
	 * @param isbn
	 * @return
	 */
	@GetMapping(path = { "/get/{isbn}" })
	public String getBook(@PathVariable("isbn") String isbn) {

		try {

			return bookStoreService.getBook(isbn);

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Method to delete a book
	 * 
	 * @param isbn
	 * @return
	 */
	@DeleteMapping(path = { "/delete/{isbn}" })
	public Boolean deleteBook(@PathVariable("isbn") String isbn) {

		Boolean result = null;
		try {

			result = bookStoreService.deleteBook(isbn);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return result;
	}

	/**
	 * Method to update a book
	 * 
	 * @param book
	 * @return
	 */
	@PutMapping(path = { "/update" })
	public Book updateBook(@RequestBody Book book) {

		Book updatedBook = null;
		try {

			updatedBook = bookStoreService.updateBook(book);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return updatedBook;
	}

	/**
	 * Method to checkout books
	 * 
	 * @param books
	 * @param discountCode
	 * @return
	 */
	@PutMapping(path = { "/checkout/{discount}" }, consumes = MediaType.APPLICATION_JSON_VALUE) // ,produces =
																								// MediaType.APPLICATION_JSON_VALUE
	public String checkout(@RequestBody List<Book> books, @PathVariable("discount") String discountCode) {

		try {

			return bookStoreService.checkoutBooks(books, discountCode);
		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * Method to list out all books
	 * 
	 * @return
	 */
	@GetMapping(path = { "/getAll" }, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<Book> getAll() {

		try {

			return bookStoreService.getAllBooks();

		} catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null;
	}

}
