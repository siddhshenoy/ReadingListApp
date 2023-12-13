package com.sid.readinglistapp.controller.tests;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.google.gson.Gson;
import com.sid.readinglistapp.AppConfig;
import com.sid.readinglistapp.ReadingListServiceApplication;
import com.sid.readinglistapp.api.ReadingListController;
import com.sid.readinglistapp.dto.BookDto;
import com.sid.readinglistapp.services.BookService;

@SpringBootTest(classes = {ReadingListServiceApplication.class, ReadingListController.class, AppConfig.class, BookService.class})
@AutoConfigureMockMvc
@TestPropertySource(value = "classpath:application.properties")
public class ReadingListControllerTests {
	@Autowired
	MockMvc mvc;
	


	public String RandomISBNGenerator() {
		Random r = new Random();
//		int first = (r.nextInt() % 900) + 100;
//		int second = r.nextInt() % 10;
//		int third = (r.nextInt() % 9000) + 1000;
//		int fourth = (r.nextInt() % 9000) + 1000;
//		int check = r.nextInt() % 10;
		int first = r.nextInt(100, 999);
		int second = r.nextInt(0, 9);
		int third = r.nextInt(1000,9999);
		int fourth = r.nextInt(1000,9999);
		int check = r.nextInt(0,9);
		return first + "-" + second + "-" + third + "-" + fourth + "-" + check;

	}
	@Test
	public void getAllReadingLists_200() throws Exception {
		mvc.perform(get("/reading_list/v1/")
				.contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON));
	}

	@Test
	public void postReadingLists_200() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setISBN(RandomISBNGenerator());
		bookDto.setAuthor("Dummy Author");
		bookDto.setTitle("Dummy Title");
		bookDto.setBookStatus("Unread");
		Gson gson = new Gson();
		String toSendJson = gson.toJson(bookDto);
		System.out.println("Json generated: " + toSendJson);
		mvc.perform(post("/reading_list/v1/")
				.content(toSendJson).contentType(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andExpect(jsonPath("$.status").value("SUCCESS"))
		.andExpect(jsonPath("$.message").value("Book inserted successfully!"));
	}
	@Test
	public void postReadingLists_ErrorUniqueKey() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setISBN("792-8-5385-8471-9");
		bookDto.setAuthor("Dummy Author");
		bookDto.setTitle("Dummy Title");
		bookDto.setBookStatus("Unread");
		Gson gson = new Gson();
		String toSendJson = gson.toJson(bookDto);
		System.out.println("Json generated: " + toSendJson);
		mvc.perform(
				post("/reading_list/v1/")
				.content(toSendJson).
				contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		 .andExpect(jsonPath("$.status").value("ERROR"))
		 .andExpect(jsonPath("$.error.code").value("100"));
	}
	@Test
	public void postReadingLists_InvalidISBNFormat() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setISBN("792-8-5385-847A-7");
		bookDto.setAuthor("Dummy Author");
		bookDto.setTitle("Dummy Title");
		bookDto.setBookStatus("Unread");
		Gson gson = new Gson();
		String toSendJson = gson.toJson(bookDto);
		System.out.println("Json generated: " + toSendJson);
		mvc.perform(
				post("/reading_list/v1/")
				.content(toSendJson).
				contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		 .andExpect(jsonPath("$.status").value("ERROR"))
		 .andExpect(jsonPath("$.error.code").value("10"))
		 .andExpect(jsonPath("$.error.extra.ISBNValidation").value("Invalid ISBN, should only contain digits!"));
	}
	@Test
	public void postReadingLists_EmptyTitle() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setISBN("792-8-5385-8473-7");
		bookDto.setAuthor("Dummy Author");
		bookDto.setTitle("");
		bookDto.setBookStatus("Unread");
		Gson gson = new Gson();
		String toSendJson = gson.toJson(bookDto);
		System.out.println("Json generated: " + toSendJson);
		mvc.perform(
				post("/reading_list/v1/")
				.content(toSendJson).
				contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		 .andExpect(jsonPath("$.status").value("ERROR"))
		 .andExpect(jsonPath("$.error.code").value("10"))
		 .andExpect(jsonPath("$.error.extra.TitleValidation").value("Title should not be empty!"));
	}
	@Test
	public void postReadingLists_EmptyAuthor() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setISBN("792-8-5385-8473-7");
		bookDto.setAuthor("");
		bookDto.setTitle("Dummy Title");
		bookDto.setBookStatus("Unread");
		Gson gson = new Gson();
		String toSendJson = gson.toJson(bookDto);
		System.out.println("Json generated: " + toSendJson);
		mvc.perform(
				post("/reading_list/v1/")
				.content(toSendJson).
				contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		 .andExpect(jsonPath("$.status").value("ERROR"))
		 .andExpect(jsonPath("$.error.code").value("10"))
		 .andExpect(jsonPath("$.error.extra.AuthorValidation").value("Author name should not be empty!"));
	}
	@Test
	public void postReadingLists_EmptyStatus() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setISBN("792-8-5385-8473-7");
		bookDto.setAuthor("Dummy Author");
		bookDto.setTitle("Dummy Title");
		bookDto.setBookStatus("");
		Gson gson = new Gson();
		String toSendJson = gson.toJson(bookDto);
		System.out.println("Json generated: " + toSendJson);
		mvc.perform(
				post("/reading_list/v1/")
				.content(toSendJson).
				contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		 .andExpect(jsonPath("$.status").value("ERROR"))
		 .andExpect(jsonPath("$.error.code").value("10"))
		 .andExpect(jsonPath("$.error.extra.StatusValidation").value("Book status should not be empty!"));
	}
	@Test
	public void postReadingLists_InvalidISBN() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setISBN("792-8-5385");
		bookDto.setAuthor("Dummy Author");
		bookDto.setTitle("Dummy Title");
		bookDto.setBookStatus("Unread");
		Gson gson = new Gson();
		String toSendJson = gson.toJson(bookDto);
		System.out.println("Json generated: " + toSendJson);
		mvc.perform(
				post("/reading_list/v1/")
				.content(toSendJson).
				contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		 .andExpect(jsonPath("$.status").value("ERROR"))
		 .andExpect(jsonPath("$.error.code").value("10"))
		 .andExpect(jsonPath("$.error.extra.ISBNValidation").value("Invalid ISBN length, should be 10 or 13 digits long and unique"));
	}
	@Test
	public void postReadingLists_EmptyISBN() throws Exception {
		BookDto bookDto = new BookDto();
		bookDto.setISBN("");
		bookDto.setAuthor("Dummy Author");
		bookDto.setTitle("Dummy Title");
		bookDto.setBookStatus("Unread");
		Gson gson = new Gson();
		String toSendJson = gson.toJson(bookDto);
		System.out.println("Json generated: " + toSendJson);
		mvc.perform(
				post("/reading_list/v1/")
				.content(toSendJson).
				contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		 .andExpect(jsonPath("$.status").value("ERROR"))
		 .andExpect(jsonPath("$.error.code").value("10"))
		 .andExpect(jsonPath("$.error.extra.ISBNValidation").value("ISBN cannot be empty!"));
	}
	@Test
	public void deleteReadingList() throws Exception {
		mvc.perform(
				delete("/reading_list/v1/792-8-5385-8471-7")
				.contentType(MediaType.APPLICATION_JSON)
		).andExpect(status().isOk())
		 .andExpect(jsonPath("$.status").value("SUCCESS"))
		 .andExpect(jsonPath("$.message").value("Book deleted successfully!"));
	}
}
