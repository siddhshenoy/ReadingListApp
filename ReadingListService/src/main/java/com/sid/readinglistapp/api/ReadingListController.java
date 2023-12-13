package com.sid.readinglistapp.api;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.MediaType;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.sid.readinglistapp.dto.BookDto;
import com.sid.readinglistapp.dto.GenericErrorResponseBody;
import com.sid.readinglistapp.dto.GenericResponseBody;
import com.sid.readinglistapp.services.BookService;
import com.sid.readinglistapp.validator.BookValidator;
import com.sid.readinglistapp.validator.BookValidatorResult;

@RestController
@RequestMapping(path="/reading_list/v1")
public class ReadingListController {
	@Autowired
	BookService bookService;
	
	@PostMapping(path="/", consumes = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public GenericResponseBody ReadingListPutISBN(@RequestBody BookDto dto) {
		GenericResponseBody responseBody = new GenericResponseBody();
		
		try {
			BookValidator validator = BookValidator.getInstance();
			BookValidatorResult result = validator.validateBook(dto);
			if(result.finalResult) {
				bookService.insertBook(dto);
				responseBody.setStatus("SUCCESS");
				responseBody.setMessage("Book inserted successfully!");
			}
			else {
				
				GenericErrorResponseBody errorBody = new GenericErrorResponseBody();
				errorBody.setCode(10); 		//Validation Error
				errorBody.setMessage("Validation Failure");
				HashMap<Object, Object> extra = new HashMap<>();
				if(result.ISBNCheckString != null)
					extra.put("ISBNValidation", result.ISBNCheckString);
				if(result.TitleCheckString != null)
					extra.put("TitleValidation", result.TitleCheckString);
				if(result.AuthorCheckString != null)
					extra.put("AuthorValidation", result.AuthorCheckString);
				if(result.StatusCheckString != null)
					extra.put("StatusValidation", result.StatusCheckString);
				errorBody.setExtra(extra);
				responseBody.setStatus("ERROR");
				responseBody.setError(errorBody);
			}
		}
		catch(SQLException e) {
			GenericErrorResponseBody errorBody = new GenericErrorResponseBody();
			errorBody.setCode(200);
			errorBody.setMessage("SQL Exception has occurred!");
			errorBody.setDetails("SQL Exception has occurred!");
			responseBody.setStatus("ERROR");
			responseBody.setError(errorBody);
		}
		catch(DuplicateKeyException e) {
		
			GenericErrorResponseBody errorBody = new GenericErrorResponseBody();
			errorBody.setCode(100);
			errorBody.setMessage("Duplicate key error");
			errorBody.setDetails("An attempt was made to insert a book whose ISBN is already present in the database, this should be unique!");
			responseBody.setStatus("ERROR");
			responseBody.setError(errorBody);
		}
		return responseBody;
	}
	
	@DeleteMapping(path="/{isbn_num}")
	@ResponseBody
	public GenericResponseBody ReadingListDeleteISBN(@PathVariable("isbn_num") String ISBN) {
		GenericResponseBody responseBody = new GenericResponseBody();
		try {
			bookService.deleteBook(ISBN);
			responseBody.setStatus("SUCCESS");
			responseBody.setMessage("Book deleted successfully!");
		}
		catch(Exception e) {
			GenericErrorResponseBody errorBody = new GenericErrorResponseBody();
			errorBody.setCode(401);
			errorBody.setMessage(e.getMessage());
			errorBody.setDetails("Book deletion failed!");
			responseBody.setStatus("ERROR");
			responseBody.setError(errorBody);
		}
		return responseBody;
	}
	
	@PutMapping(path="/")
	@ResponseBody
	public GenericResponseBody ReadingListUpdateISBN(@RequestBody BookDto bookDto) {
		GenericResponseBody responseBody = new GenericResponseBody();
		try {
			BookValidator validator = BookValidator.getInstance();
			BookValidatorResult result = validator.validateBook(bookDto);
			if(result.finalResult) {
				BookDto resultDto = bookService.getBook(bookDto.getISBN());
				if(resultDto != null) {
					bookService.updateBook(bookDto);
					responseBody.setStatus("SUCCESS");
					responseBody.setMessage("Book updated successfully!");
				}
				else {
					GenericErrorResponseBody errorBody = new GenericErrorResponseBody();
					errorBody.setCode(20);
					errorBody.setMessage("Book does not exist!");
					responseBody.setStatus("ERROR");
					responseBody.setError(errorBody);
				}
			}
			else {
				
				GenericErrorResponseBody errorBody = new GenericErrorResponseBody();
				errorBody.setCode(10); 		//Validation Error
				errorBody.setMessage("Validation Failure");
				HashMap<Object, Object> extra = new HashMap<>();
				if(result.ISBNCheckString != null)
					extra.put("ISBNValidation", result.ISBNCheckString);
				if(result.TitleCheckString != null)
					extra.put("TitleValidation", result.TitleCheckString);
				if(result.AuthorCheckString != null)
					extra.put("AuthorValidation", result.AuthorCheckString);
				if(result.StatusCheckString != null)
					extra.put("StatusValidation", result.StatusCheckString);
				errorBody.setExtra(extra);
				responseBody.setStatus("ERROR");
				responseBody.setError(errorBody);
			}
		}
		catch(Exception e) {
			GenericErrorResponseBody errorBody = new GenericErrorResponseBody();
			errorBody.setCode(401);
			errorBody.setMessage(e.getMessage());
			errorBody.setDetails("Book update failed!");
			responseBody.setStatus("ERROR");
			responseBody.setError(errorBody);
		}
		return responseBody;
	}
	
	@PatchMapping(path="/{isbn_num}")
	@ResponseBody
	public GenericResponseBody ReadingListPatchISBN(@PathVariable("isbn_num") String ISBN, @RequestBody Map<String, Object> updateObjects) {
		GenericResponseBody responseBody = new GenericResponseBody();
		try {
			bookService.updateBook(ISBN, updateObjects);
			responseBody.setStatus("SUCCESS");
			responseBody.setMessage("Book updated successfully!");
		}
		catch(Exception e) {
			GenericErrorResponseBody errorBody = new GenericErrorResponseBody();
			errorBody.setCode(402);
			errorBody.setMessage(e.getMessage());
			errorBody.setDetails("Book update failed!");
			responseBody.setStatus("ERROR");
			responseBody.setError(errorBody);
		}
		return responseBody;
	}
	
	@GetMapping(path="/")
	@ResponseBody
	public List<BookDto> ReadingListGet() {
		return bookService.getAllBooks();
	}
	
	@GetMapping(path="/{isbn_num}")
	@ResponseBody
	public BookDto ReadingListGetForISBN(@PathVariable("isbn_num") String ISBN) {
		return bookService.getBook(ISBN);
	}
}
