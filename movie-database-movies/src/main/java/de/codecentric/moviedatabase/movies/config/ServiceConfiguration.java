package de.codecentric.moviedatabase.movies.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.codecentric.moviedatabase.movies.service.InMemoryMovieService;
import de.codecentric.moviedatabase.movies.service.MovieService;

@Configuration 
public class ServiceConfiguration {
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private StringRedisTemplate redisTemplate;
	
	@Bean
	public MovieService movieService(){
		return new InMemoryMovieService(redisTemplate, objectMapper);
	}
	
}
