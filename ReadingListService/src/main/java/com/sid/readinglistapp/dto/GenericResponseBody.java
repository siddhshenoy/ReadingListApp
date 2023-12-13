package com.sid.readinglistapp.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericResponseBody {
	private String status;
	private String message;
	private GenericErrorResponseBody error;
	
	public GenericResponseBody() {
		super();
		this.error = null;
	}
	public GenericResponseBody(String status, String message, GenericErrorResponseBody error) {
		super();
		this.status = status;
		this.message = message;
		this.error = error;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public GenericErrorResponseBody getError() {
		return error;
	}
	public void setError(GenericErrorResponseBody error) {
		this.error = error;
	}
	
	
}
