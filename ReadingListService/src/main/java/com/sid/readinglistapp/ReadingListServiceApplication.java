package com.sid.readinglistapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.sid.readinglistapi.api", "com.sid.readinglistapi.services", "com.sid.readinglistapi.config"})
public class ReadingListServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(ReadingListServiceApplication.class, args);
	}
}
