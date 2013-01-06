package de.codecentric.moviedatabase.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.hateoas.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import de.codecentric.moviedatabase.domain.Movie;
import de.codecentric.moviedatabase.domain.Tag;
import de.codecentric.moviedatabase.model.MovieForm;
import de.codecentric.moviedatabase.service.MovieService;

@RequestMapping(value = "/movies", produces={"application/json", "application/hal+json"}, consumes={"application/json", "application/hal+json"})
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
	public @ResponseBody ResponseEntity<Resource<Movie>> getMovie(@PathVariable UUID id){
		return enableCorsRequests(movieResourceAssembler.toResource(movieService.findMovieById(id)), HttpStatus.OK);
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody ResponseEntity<List<Resource<Movie>>> getMovies(@RequestParam(required = false) String searchString) {
		Set<Tag> tags = new HashSet<Tag>();
		Set<String> searchWords = new HashSet<String>();
		MovieUtil.convertSearchStringToTagsAndSearchWords(searchString, tags, searchWords);
		List<Movie> movies = movieService.findMovieByTagsAndSearchString(tags, searchWords);
		return  enableCorsRequests(movieResourceAssembler.toResource(movies), HttpStatus.OK);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public @ResponseBody ResponseEntity<Resource<Movie>> editMovie(@PathVariable UUID id, @RequestBody MovieForm movieForm) {
		Movie movie = movieService.findMovieById(id);
		movie.setDescription(movieForm.getDescription());
		movie.setStartDate(movieForm.getStartDate());
		movie.setTitle(movieForm.getTitle());
		movieService.updateMovie(movie);
		return getMovie(id);
	}
	
	private <T> ResponseEntity<T> enableCorsRequests(T entity, HttpStatus statusCode) {
		HttpHeaders headers = new HttpHeaders();
		headers.set("Access-Control-Allow-Origin", "*");
		return new ResponseEntity<T>(entity, headers, statusCode);
	}
}
