package com.sid.readinglistapp.validator;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ValidationResult {
	public boolean finalResult;
	public String resultString = null;
}