package de.codecentric.moviedatabase.shop.controller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.codecentric.moviedatabase.shop.domain.Movie;
import de.codecentric.moviedatabase.shop.repositories.MovieRepository;

@RestController
@RequestMapping(value = "/movies")
public class ShopRestController {
	
	private MovieRepository movieRepository;
	private String movieSystemBase;
	private ObjectMapper objectMapper;
	
	@Autowired
	public ShopRestController(MovieRepository movieRepository,
			@Value("${moviedatabase.movies.url.base}") String movieSystemBase,
			ObjectMapper objectMapper) {
		super();
		this.movieRepository = movieRepository;
		this.movieSystemBase = movieSystemBase;
		this.objectMapper = objectMapper;
	}

	@RequestMapping
	public Iterable<Movie> getAllMovies(){
		return movieRepository.findAll();
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Movie getMovie(@PathVariable UUID id){
		Movie movie = movieRepository.findOne(id);
		movie.setMovieUrl(movieSystemBase+"/movies/"+id);
		return movie;
	}

	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void updateMovie(@PathVariable UUID id, @RequestBody String movieString) throws JsonParseException, JsonMappingException, IOException{
		Movie movie = objectMapper.readValue(movieString, Movie.class);
		Movie persistedMovie = movieRepository.findOne(id);
		persistedMovie.setQuantity(movie.getQuantity());
		persistedMovie.setPrice(movie.getPrice());
		movieRepository.save(persistedMovie);
	}

}
