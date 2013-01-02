package de.codecentric.moviedatabase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.codecentric.moviedatabase.service.InMemoryMovieService;
import de.codecentric.moviedatabase.service.MovieService;

@Configuration 
public class ServiceConfiguration {
	
	@Bean
	public MovieService movieService(){
		return new InMemoryMovieService();
	}
	
}
