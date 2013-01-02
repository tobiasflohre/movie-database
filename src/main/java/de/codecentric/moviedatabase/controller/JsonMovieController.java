package de.codecentric.moviedatabase.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.codecentric.moviedatabase.domain.Movie;
import de.codecentric.moviedatabase.domain.Tag;
import de.codecentric.moviedatabase.service.MovieService;

@RequestMapping(value = "/movies", headers="Accept=application/json")
public class JsonMovieController {

	private MovieService movieService;
	private MovieResourceAssembler movieResourceAssembler;

	public JsonMovieController(MovieService movieService,
			MovieResourceAssembler movieResourceAssembler,
			TagResourceAssembler tagResourceAssembler) {
		this.movieService = movieService;
		this.movieResourceAssembler = movieResourceAssembler;
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Resource<Movie> getMovie(@PathVariable UUID id){
		return movieResourceAssembler.toResource(movieService.findMovieById(id));
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Resource<Movie>> getMovies(@RequestParam(required = false) String searchString) {
		Set<Tag> tags = new HashSet<Tag>();
		Set<String> searchWords = new HashSet<String>();
		MovieUtil.convertSearchStringToTagsAndSearchWords(searchString, tags, searchWords);
		List<Movie> movies = movieService.findMovieByTagsAndSearchString(tags, searchWords);
		return  movieResourceAssembler.toResource(movies);
	}
	
}
