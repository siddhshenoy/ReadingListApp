package com.sid.readinglistapp.services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;

import com.sid.readinglistapp.dto.BookDto;

class BookRowMapper implements RowMapper<BookDto> {
    @Override
    public BookDto mapRow(ResultSet rs, int rowNum) throws SQLException {
    	BookDto book = new BookDto();
    	book.setISBN(rs.getString("ISBN"));
    	book.setTitle(rs.getString("Title"));
    	book.setAuthor(rs.getString("Author"));
    	book.setBookStatus(rs.getString("BookStatus"));
        return book;
    }
}

class ISBNRowMapper implements RowMapper<String> {
    @Override
    public String mapRow(ResultSet rs, int rowNum) throws SQLException {
    	return rs.getString("ISBN");
    }
}

@Service
public class BookService {
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public void insertBook(BookDto book) throws SQLException, DuplicateKeyException {

		String query = "INSERT INTO BOOKS(ISBN, TITLE, AUTHOR, BOOKSTATUS) VALUES(?, ?, ?, ?)";
		jdbcTemplate.update(query, new Object[] {book.getISBN(), book.getTitle(), book.getAuthor(), book.getBookStatus()});
		
	}
	
	public void deleteBook(String ISBN) {
		String query = "DELETE FROM BOOKS WHERE ISBN = ?";
		jdbcTemplate.update(query, new Object[] {ISBN});
	}
	
	public void updateBook(BookDto book) {
		String query = "UPDATE BOOKS SET TITLE = ?, AUTHOR = ?, BOOKSTATUS = ? WHERE ISBN = ?";
		System.out.println(book.toString());
		jdbcTemplate.update(query, new Object[] {book.getTitle(), book.getAuthor(), book.getBookStatus(), book.getISBN()});
	}
	
	public void updateBook(String ISBN, Map<String, Object> updates) {
		
		String query = "UPDATE BOOKS SET ";
		Object[] paramList = new Object[(updates.size()) + 1];
		int i = 0;
		for(Entry<String, Object> entry : updates.entrySet()) {
			query += entry.getKey() + " = ?, ";
			paramList[i] = entry.getValue();
			i++;
		}
		query = query.substring(0, query.length() - 2);
		query += " WHERE ISBN = ?";
		paramList[i] = ISBN;
		System.out.println("Full query: " + query);
		jdbcTemplate.update(query, paramList);
	}
	
	public void patchBook(BookDto book) {
		
		String query = "UPDATE BOOKS SET TITLE = ?, AUTHOR = ?, STATUS = ? WHERE ISBN = ?";
		jdbcTemplate.update(query, new Object[] {book.getTitle(), book.getAuthor(), book.getBookStatus(), book.getISBN()});
	}
	
	public void updateBook(String ISBN, String Title, String Author, String Status) {
		String query = "UPDATE BOOKS SET TITLE = ?, AUTHOR = ?, STATUS = ? WHERE ISBN = ?";
		jdbcTemplate.update(query, new Object[] {Title, Author, Status, ISBN});
	}
	
	public void updateBookTitle(String ISBN, String Title) {
		String query = "UPDATE BOOKS SET TITLE = ? WHERE ISBN = ?";
		jdbcTemplate.update(query, new Object[] {Title, ISBN});
	}
	
	public void updateBookAuthor(String ISBN, String Author) {
		String query = "UPDATE BOOKS SET AUTHOR = ? WHERE ISBN = ?";
		jdbcTemplate.update(query, new Object[] {Author, ISBN});
	}
	
	public void updateBookStatus(String ISBN, String Status) {
		String query = "UPDATE BOOKS SET STATUS = ? WHERE ISBN = ?";
		jdbcTemplate.update(query, new Object[] {Status, ISBN});
	}
	
	public BookDto getBook(String ISBN) {
		String query = "SELECT * FROM BOOKS WHERE ISBN = ?";
		return (BookDto) jdbcTemplate.queryForObject(query, new BookRowMapper(), new Object[] {ISBN});	
	}
	
	public List<BookDto> getAllBooks() {
		String query = "SELECT * FROM BOOKS";
		return (List<BookDto>) jdbcTemplate.query(query, new BookRowMapper());
		
	}
}
