package de.codecentric.moviedatabase.shop.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.codecentric.moviedatabase.shop.domain.Movie;
import de.codecentric.moviedatabase.shop.repositories.MovieRepository;

@RestController
@RequestMapping(value = "/movies")
public class ShopRestController {
	
	private MovieRepository movieRepository;
	private String movieSystemBase;
	
	@Autowired
	public ShopRestController(MovieRepository movieRepository,
			@Value("moviedatabase.movies.url.base") String movieSystemBase) {
		super();
		this.movieRepository = movieRepository;
		this.movieSystemBase = movieSystemBase;
	}

	@RequestMapping
	public Iterable<Movie> getAllMovies(){
		return movieRepository.findAll();
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public Movie getMovie(@PathVariable UUID id){
		return movieRepository.findOne(id);
	}

	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public void updateMovie(@PathVariable UUID id, Movie movie){
		Movie persistedMovie = movieRepository.findOne(id);
		persistedMovie.setQuantity(movie.getQuantity());
		persistedMovie.setPrice(movie.getPrice());
		movieRepository.save(persistedMovie);
	}

}
