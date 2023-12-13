package com.sid.readinglistapp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@PropertySource("classpath:/application.properties")
public class AppConfig {
	
	@Value("${spring.datasource.url}")
	private String dbUrl;
	
	@Value("${spring.datasource.username}")
	private String dbUsername;
	
	@Value("${spring.datasource.password}")
	private String dbPassword;
	
    @Bean
    DataSource dataSource() {
    	if(dbUrl == null) { 
    		dbUrl = "jdbc:mysql://192.168.0.104:3306/reading_lists_app";
    	}
    	if(dbUsername == null) {
    		dbUsername = "root";
    	}
    	if(dbPassword == null) {
    		dbPassword = "root123";
    	}
    	System.out.println(dbUrl + ", " + dbUsername + ", " + dbPassword);
		return new DriverManagerDataSource(dbUrl, dbUsername, dbPassword);
	}
	@Bean
	public JdbcTemplate applicationDatabaseConnection() {
		System.out.println("Generating new JDBC Template...");
		return new JdbcTemplate(dataSource());
	}	
}
