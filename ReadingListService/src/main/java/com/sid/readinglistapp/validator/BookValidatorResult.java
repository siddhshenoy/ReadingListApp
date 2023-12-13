package com.sid.readinglistapp.validator;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookValidatorResult {
	public boolean finalResult;
	public String ISBNCheckString;
	public String TitleCheckString;
	public String AuthorCheckString;
	public String StatusCheckString;
	
	
}
