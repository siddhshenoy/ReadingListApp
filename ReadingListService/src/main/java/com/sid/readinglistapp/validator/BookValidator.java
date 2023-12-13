package com.sid.readinglistapp.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sid.readinglistapp.dto.BookDto;

import ch.qos.logback.core.status.Status;


public class BookValidator {
	public static BookValidator validator = null;
	
	public BookValidatorResult validateBook(BookDto dto) {
		BookValidatorResult finalResult = new BookValidatorResult();
		ValidationResult ISBNValidation;
		ValidationResult TitleValidation;
		ValidationResult AuthorValidation;
		ValidationResult StatusValidation;
		ISBNValidation = validateISBNNumber(dto.getISBN());
		AuthorValidation = validateAuthor(dto.getAuthor());
		TitleValidation = validateTitle(dto.getTitle());
		StatusValidation = validateBookStatus(dto.getBookStatus());
		if(ISBNValidation.finalResult && AuthorValidation.finalResult && TitleValidation.finalResult && StatusValidation.finalResult) {
			finalResult.finalResult = true;
		} else {
			if(!ISBNValidation.finalResult)
				finalResult.ISBNCheckString = ISBNValidation.resultString;
			if(!TitleValidation.finalResult)
				finalResult.TitleCheckString = TitleValidation.resultString;
			if(!AuthorValidation.finalResult)
				finalResult.AuthorCheckString = AuthorValidation.resultString;
			if(!StatusValidation.finalResult)
				finalResult.StatusCheckString = StatusValidation.resultString;
		}
		return finalResult;
	}
	public ValidationResult validateISBNNumber(String ISBNString) {
		ValidationResult result = new ValidationResult();
		result.finalResult = false;
		result.resultString = "";
		String ISBN = ISBNString;
		if(ISBN.length() > 0) {
			if(!ISBN.trim().equals("")) {
				ISBN = ISBN.replace("-", "");
				if(ISBN.length() == 10 || ISBN.length() == 13) {
					Pattern pattern = Pattern.compile("^[0-9]*$");
					Matcher matcher = pattern.matcher(ISBN);
					if(matcher.find()) {
						result.finalResult = true;
					}
					else {
						result.resultString = "Invalid ISBN, should only contain digits!";
					}
					
				}
				else {
					result.resultString = "Invalid ISBN length, should be 10 or 13 digits long and unique";
				}
			}
			else {
				result.resultString = "ISBN cannot be empty!";
			}
		}
		else {
			result.resultString = "ISBN cannot be empty!";
		}
		return result;
	}
	public ValidationResult validateTitle(String str) {
		ValidationResult result = new ValidationResult();
		result.finalResult = false;
		result.resultString = "";
		if(str.length() > 0) {
			if(!str.equals("")) {
				result.finalResult = true;
			}
			else {
				result.resultString = "Title should not be empty!";
			}
		}
		else {
			result.resultString = "Title should not be empty!";
		}
		return result;
	}
	public ValidationResult validateAuthor(String str) {
		ValidationResult result = new ValidationResult();
		result.finalResult = false;
		result.resultString = "";
		if(str.length() > 0) {
			if(!str.equals("")) {
				result.finalResult = true;
			}
			else {
				result.resultString = "Author name should not be empty!";
			}
		}
		else {
			result.resultString = "Author name should not be empty!";
		}
		return result;
	}
	public ValidationResult validateBookStatus(String str) {
		ValidationResult result = new ValidationResult();
		result.finalResult = false;
		result.resultString = "";
		if(str.length() > 0) {
			if(!str.equals("")) {
				if(str.equals("Unread") || str.equals("InProgress") || str.equals("Finished"))
					result.finalResult = true;
				else {
					result.resultString = "Invalid Book status submitted, should be Unread, InProgress or Finished!";
				}
			}
			else {
				result.resultString = "Book status should not be empty!";
			}
		}
		else {
			result.resultString = "Book status should not be empty!";
		}
		return result;
	}
	public static BookValidator getInstance() {
		if(validator == null) BookValidator.validator = new BookValidator();
		return BookValidator.validator;
	}
}
