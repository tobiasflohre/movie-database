package de.codecentric.moviedatabase.movies.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.codecentric.moviedatabase.movies.service.InMemoryMovieService;
import de.codecentric.moviedatabase.movies.service.MovieService;

@Configuration 
public class ServiceConfiguration {
	
	@Bean
	public MovieService movieService(){
		return new InMemoryMovieService();
	}
	
}
