package de.codecentric.moviedatabase.controller;

import java.util.UUID;

import org.springframework.hateoas.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.codecentric.moviedatabase.domain.Movie;
import de.codecentric.moviedatabase.service.MovieService;

@RequestMapping(value = "/movies", headers="Accept=application/json")
public class MediaTypeMovieController {

	private MovieService movieService;
	private MovieResourceAssembler movieResourceAssembler;

	public MediaTypeMovieController(MovieService movieService, MovieResourceAssembler movieResourceAssembler) {
		this.movieService = movieService;
		this.movieResourceAssembler = movieResourceAssembler;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Resource<Movie> getMovie(@PathVariable UUID id, Model model){
		return movieResourceAssembler.toResource(movieService.findMovieById(id));
	}
	
}
