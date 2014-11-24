package de.codecentric.moviedatabase.movies.controller;

import static de.codecentric.roca.core.LinkBuilder.linkTo;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.codecentric.moviedatabase.movies.domain.Comment;
import de.codecentric.moviedatabase.movies.domain.Movie;
import de.codecentric.moviedatabase.movies.domain.Tag;
import de.codecentric.moviedatabase.movies.exception.ResourceNotFoundException;
import de.codecentric.moviedatabase.movies.model.MovieForm;
import de.codecentric.moviedatabase.movies.service.MovieService;
import de.codecentric.roca.core.Resource;

@RequestMapping("/movies")
public abstract class AbstractMovieController {
	
	protected MovieService movieService;
	protected TagResourceAssembler tagResourceAssembler;
	protected MovieResourceAssembler movieResourceAssembler;
	private boolean partial;
	
	public AbstractMovieController(MovieService movieService,
			TagResourceAssembler tagResourceAssembler,
			MovieResourceAssembler movieResourceAssembler,
			boolean partial) {
		super();
		this.movieService = movieService;
		this.tagResourceAssembler = tagResourceAssembler;
		this.movieResourceAssembler = movieResourceAssembler;
		this.partial = partial;
	}
	
	/**
	 * Logical view names may be different for ajax and normal requests.
	 * Here you define a prefix that's always added to the logical view name.
	 * 
	 * @return prefix for the logical view name.
	 */
	protected String getLogicalViewNamePrefix(){
		return "movie/"+ (partial ? "partial/": "");
	}
	
	//###################### movies #################################################
	
	@RequestMapping(method = RequestMethod.GET)
	public String getMovies(Model model, @RequestParam(required = false) String searchString) {
		Set<Tag> tags = new HashSet<Tag>();
		Set<String> searchWords = new HashSet<String>();
		MovieUtil.convertSearchStringToTagsAndSearchWords(searchString, tags, searchWords);
		List<Movie> movies = movieService.findMovieByTagsAndSearchString(tags, searchWords);
		List<Resource<Movie>> resourceMovies = movieResourceAssembler.toResource(movies);
		model.addAttribute("movies", resourceMovies);
		model.addAttribute("searchString", searchString);
		return getLogicalViewNamePrefix()+"movies";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String getCreateMovie(Model model) {
		model.addAttribute("movieForm", new MovieForm());
		model.addAttribute("actionLink", linkTo(MoviePathFragment.MOVIES).withRel(MovieRelation.SELF));
		model.addAttribute("cancelLink", linkTo(MoviePathFragment.MOVIES).withRel(MovieRelation.SELF));
		return getLogicalViewNamePrefix()+"movie_edit";
	}
	
	protected void doCreateMovie(MovieForm movieForm) {
		Movie movie = new Movie(movieForm.getTitle(), movieForm.getDescription(), movieForm.getStartDate());
		movieService.createMovie(movie);
	}
	
	//########################### movie #####################################################
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getMovie(@PathVariable UUID id, Model model){
		model.addAttribute("movie", movieResourceAssembler.toResource(movieService.findMovieById(id)));
		return getLogicalViewNamePrefix()+"movie";
	}
	
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String getEditMovie(@PathVariable UUID id, Model model) {
		Movie movie = movieService.findMovieById(id);
		model.addAttribute("movieForm", new MovieForm(movie));
		Resource<Movie> resourceMovie = movieResourceAssembler.toResource(movie);
		model.addAttribute("actionLink", resourceMovie.getLink(MovieRelation.SELF.getName()));
		model.addAttribute("cancelLink", resourceMovie.getLink(MovieRelation.SELF.getName()));
		return getLogicalViewNamePrefix()+"movie_edit";
	}
	
	protected void doEditMovie(@PathVariable UUID id, MovieForm movieForm) {
		Movie movie = movieService.findMovieById(id);
		movie.setDescription(movieForm.getDescription());
		movie.setStartDate(movieForm.getStartDate());
		movie.setTitle(movieForm.getTitle());
		movieService.updateMovie(movie);
	}
	
	//###################### comments #####################################
	
	@RequestMapping(value = "/{id}/comments", method = RequestMethod.GET)
	public String getComments(@PathVariable UUID id, Model model) {
		model.addAttribute("movie", movieResourceAssembler.toResource(movieService.findMovieById(id)));
		return getLogicalViewNamePrefix()+"comments";
	}
	
	protected void doCreateComment(@PathVariable UUID id, @RequestParam String content) {
		Movie movie = movieService.findMovieById(id);
		movie.getComments().add(new Comment(new Date(), content));
	}
	
	//###################### tags #############################################
	
	@RequestMapping(value = "/{id}/tags", method = RequestMethod.GET)
	public String getTags(@PathVariable UUID id, Model model) {
		model.addAttribute("tags", tagResourceAssembler.toResource(movieService.findMovieById(id).getTags(),id));
		model.addAttribute("movie", movieResourceAssembler.toResource(movieService.findMovieById(id)));
		return getLogicalViewNamePrefix()+"tags";
	}
	
	protected void doAddTagToMovie(@PathVariable UUID id, @RequestParam Tag tag) {
		movieService.addTagToMovie(tag, id);
	}
	
	protected void doRemoveTagFromMovie(@PathVariable UUID id, @PathVariable Tag tag) {
		movieService.removeTagFromMovie(tag, id);
	}
	
	//################### exception handling ###############################
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handle(ResourceNotFoundException e) {
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
	}
	
	
}
