package com.sid.readinglistapp.controller.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.sid.readinglistapp.validator.BookValidator;
import com.sid.readinglistapp.validator.ValidationResult;

public class BookValidatorTests {
	@Test
	public void testISBN_ValidFormat() {
		BookValidator validator = BookValidator.getInstance();
		ValidationResult result = validator.validateISBNNumber("325-4-6436-4343-3");
		assertEquals(result.finalResult, true);
		assertEquals(result.resultString, "");
	}
	@Test
	public void testISBN_InvalidFormat_AlphaNumeric() {
		BookValidator validator = BookValidator.getInstance();
		ValidationResult result = validator.validateISBNNumber("325-4-6436-4A43-3");
		assertEquals(result.finalResult, false);
		assertEquals(result.resultString, "Invalid ISBN, should only contain digits!");
	}
	@Test
	public void testISBN_EmptyISBN() {
		BookValidator validator = BookValidator.getInstance();
		ValidationResult result = validator.validateISBNNumber("");
		assertEquals(result.finalResult, false);
		assertEquals(result.resultString, "ISBN cannot be empty!");
	}
	@Test
	public void testISBN_InvalidISBN_IncorrectLength() {
		BookValidator validator = BookValidator.getInstance();
		ValidationResult result = validator.validateISBNNumber("325-4-643-3-3");
		assertEquals(result.finalResult, false);
		assertEquals(result.resultString, "Invalid ISBN length, should be 10 or 13 digits long and unique");
	}
	@Test
	public void testAuthor_EmptyAuthorName() {
		BookValidator validator = BookValidator.getInstance();
		ValidationResult result = validator.validateAuthor("");
		assertEquals(result.finalResult, false);
		assertEquals(result.resultString, "Author name should not be empty!");
	}
	@Test
	public void testAuthor_EmptyTitle() {
		BookValidator validator = BookValidator.getInstance();
		ValidationResult result = validator.validateTitle("");
		assertEquals(result.finalResult, false);
		assertEquals(result.resultString, "Title should not be empty!");
	}
	
	@Test
	public void testAuthor_EmptyStatus() {
		BookValidator validator = BookValidator.getInstance();
		ValidationResult result = validator.validateBookStatus("");
		assertEquals(result.finalResult, false);
		assertEquals(result.resultString, "Book status should not be empty!");
	}
	
	@Test
	public void testAuthor_InvalidStatus() {
		BookValidator validator = BookValidator.getInstance();
		ValidationResult result = validator.validateBookStatus("test");
		assertEquals(result.finalResult, false);
		assertEquals(result.resultString, "Invalid Book status submitted, should be Unread, InProgress or Finished!");
	}
}
