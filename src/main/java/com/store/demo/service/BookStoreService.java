package com.store.demo.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.store.demo.api.BookStoreController;
import com.store.demo.entity.Book;
import com.store.demo.repository.BooksRepository;
import com.store.demo.utils.Discount;

@Service
public class BookStoreService {

	private static final Logger LOGGER = LoggerFactory.getLogger(BookStoreController.class);
	ObjectMapper mapper = new ObjectMapper().configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
    
	
	@Autowired
	private BooksRepository booksRepository;

	public String saveBook(Book book) {

		String result = null;
		try {
			Book addedBook = booksRepository.save(book);
			if (addedBook != null) {
				result = String.valueOf(addedBook.getIsbn());
			}
		} catch (Exception e) {
			LOGGER.error("Issue while adding new book" + e.getMessage(), e);
			result = "failure";
		}

		return result;

	}

	public String getBook(String isbn) {

		//mapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
		
		String jsonStr = null;
		try {
			Book book = booksRepository.getById(isbn);
			
			if(book != null) {
				jsonStr = mapper.writeValueAsString(book);
				System.out.println(jsonStr);
			} else {
				jsonStr = "no book exists";
			}
			

		} catch (Exception e) {
			LOGGER.error("Issue while retrieving book with ISBN :" + isbn + e.getMessage(), e);
			jsonStr = "no book exists";
		}

		return jsonStr;

	}

	public boolean deleteBook(String isbn) {

		boolean isDeleted = false;
		try {
			booksRepository.deleteById(isbn);
			isDeleted = true;

		} catch (Exception e) {
			LOGGER.error("Issue while deleting book with ISBN :" + isbn + e.getMessage(), e);

		}

		return isDeleted;

	}

	public Book updateBook(Book book) {

		Book updatedBook = null;
		try {

			updatedBook = booksRepository.save(book);
		} catch (Exception e) {
			LOGGER.error("Issue while updating book" + e.getMessage(), e);
		}
		return updatedBook;
	}

	public String checkoutBooks(List<Book> booksList,String discountCode) {

		List<Book> resultList = new ArrayList<Book>();
		double totalAmount = 0;
		try { 

			for(Book book : booksList) {
				Book bookObj = booksRepository.getById(book.getIsbn());
				if (bookObj != null) {
					Double priceAfterDiscount = getFinalPrice(bookObj.getPrice(), discountCode, bookObj.getType());
					System.out.println(priceAfterDiscount);
					totalAmount += priceAfterDiscount;
					if(priceAfterDiscount != null) {
						bookObj.setPrice(priceAfterDiscount);
						//book.setPrice(priceAfterDiscount);
						book=booksRepository.save(bookObj);
						resultList.add(book);
					}
					
				}
			}

		} catch (Exception e) {
			LOGGER.error("Issue while checkout:" + e.getMessage(), e);
		}
		System.out.println("{totalPayableAmount :"+totalAmount+"}");

		return "{totalPayableAmount :"+totalAmount+"}";

	}

	private Double getFinalPrice(Double price, String discountCode,String type) {

		Double finalPrice = null;
		try {
			Integer discount = Discount.valueOf(discountCode).getValue(); //handle null
			if(type.equalsIgnoreCase("fiction") && discount==10 ||
					type.equalsIgnoreCase("horror") && discount==15|| 
					type.equalsIgnoreCase("adventure") && discount==5) {
				finalPrice = price - ((price * discount) / 100);
			} else {
				finalPrice = price;
			} 
			
		} catch (Exception e) {
			LOGGER.error("Issue while calculating final price:" + e.getMessage(), e);
		}

		return finalPrice;

	}

	public List<Book> getAllBooks() {
		
		try {
			return booksRepository.findAll();
		} catch (Exception e) {
			LOGGER.error("Issue while retrieving books list :" + e.getMessage(), e);
		}
		return null;
	}
}
