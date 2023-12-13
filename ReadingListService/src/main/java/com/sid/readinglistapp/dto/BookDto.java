package com.sid.readinglistapp.dto;

import com.google.gson.annotations.SerializedName;

public class BookDto {
	@SerializedName(value="isbn")
	private String ISBN;
	@SerializedName(value="title")
	private String Title;
	@SerializedName(value="author")
	private String Author;
	@SerializedName(value="bookStatus")
	private String BookStatus;
	
	
	
	public BookDto() {
		super();
	}
	public BookDto(
			String ISBN,
			String Title,
			String Author
		){
		super();
		this.ISBN = ISBN;
		this.Title = Title;
		this.Author = Author;
	}
	public String getISBN() {
		return ISBN;
	}
	public void setISBN(String iSBN) {
		ISBN = iSBN;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getAuthor() {
		return Author;
	}
	public void setAuthor(String author) {
		Author = author;
	}

	public String getBookStatus() {
		return BookStatus;
	}
	public void setBookStatus(String status) {
		BookStatus = status;
	}
	@Override
	public String toString() {
		return ("ISBN: " + ISBN + ", Title: " + Title + ", Author: " + Author + ", Status: " + BookStatus);
		
	}
	
}
