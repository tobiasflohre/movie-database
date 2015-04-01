package de.codecentric.moviedatabase.shop.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
