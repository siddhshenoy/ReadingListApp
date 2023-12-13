package com.sid.readinglistapp.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class GlobalWebConfiguration implements WebMvcConfigurer{
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
//        	.allowedOrigins("http://localhost", "http://localhost:3000", "http://localhost:8081")
        	.allowedOrigins("*")
        	.allowedMethods("*");
    }
}