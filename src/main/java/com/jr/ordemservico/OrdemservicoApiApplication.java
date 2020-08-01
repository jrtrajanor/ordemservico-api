package com.jr.ordemservico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class OrdemservicoApiApplication implements WebMvcConfigurer{

	private final long MAX_AGE_SECS = 3600;
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**").
			allowedOrigins("*").
			allowedMethods("HEAD", "POST", "GET", "PUT", "PATCH", "DELETE", "OPTIONS").
			maxAge(MAX_AGE_SECS);
	}
	
	public static void main(String[] args) {
		SpringApplication.run(OrdemservicoApiApplication.class, args);
	}

}
