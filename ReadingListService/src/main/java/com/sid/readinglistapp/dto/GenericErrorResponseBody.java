package com.sid.readinglistapp.dto;

import java.util.HashMap;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GenericErrorResponseBody {
	private int code;
	private String message;
	private String details;
	private HashMap<Object, Object> extra;
	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetails() {
		return details;
	}
	public void setDetails(String details) {
		this.details = details;
	}
	public HashMap<Object, Object> getExtra() {
		return extra;
	}
	public void setExtra(HashMap<Object, Object> extra) {
		this.extra = extra;
	}
}
