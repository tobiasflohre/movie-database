package de.codecentric.moviedatabase.controller;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.hateoas.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.codecentric.moviedatabase.domain.Comment;
import de.codecentric.moviedatabase.domain.Movie;
import de.codecentric.moviedatabase.domain.Tag;
import de.codecentric.moviedatabase.exception.ResourceNotFoundException;
import de.codecentric.moviedatabase.hateoas.ControllerLinkBuilderFactory;
import de.codecentric.moviedatabase.model.MovieForm;
import de.codecentric.moviedatabase.service.MovieService;

public abstract class AbstractMovieController {
	
	protected MovieService movieService;
	protected ControllerLinkBuilderFactory linkBuilderFactory;
	protected TagResourceAssembler tagResourceAssembler;
	protected MovieResourceAssembler movieResourceAssembler;
	
	public AbstractMovieController(MovieService movieService,
			ControllerLinkBuilderFactory linkBuilderFactory,
			TagResourceAssembler tagResourceAssembler,
			MovieResourceAssembler movieResourceAssembler) {
		super();
		this.movieService = movieService;
		this.linkBuilderFactory = linkBuilderFactory;
		this.tagResourceAssembler = tagResourceAssembler;
		this.movieResourceAssembler = movieResourceAssembler;
	}
	
	/**
	 * Logical view names may be different for ajax and normal requests.
	 * Here you define a prefix that's always added to the logical view name.
	 * 
	 * @return prefix for the logical view name.
	 */
	protected abstract String getLogicalViewNamePrefix();
	
	//###################### movies #################################################
	
	@RequestMapping(method = RequestMethod.GET)
	public String getMovies(Model model, @RequestParam(required = false) String searchString) {
		Set<Tag> tags = new HashSet<Tag>();
		Set<String> searchWords = new HashSet<String>();
		convertSearchStringToTagsAndSearchWords(searchString, tags, searchWords);
		List<Movie> movies = movieService.findMovieByTagsAndSearchString(tags, searchWords);
		List<Resource<Movie>> resourceMovies = movieResourceAssembler.toResource(movies);
		model.addAttribute("movies", resourceMovies);
		return getLogicalViewNamePrefix()+"movies";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String getCreateMovie(Model model) {
		model.addAttribute("movieForm", new MovieForm());
		model.addAttribute("actionLink", linkBuilderFactory.linkTo(MovieController.class).withSelfRel());
		model.addAttribute("cancelLink", linkBuilderFactory.linkTo(MovieController.class).withSelfRel());
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
		model.addAttribute("actionLink", resourceMovie.getLink(Relation.SELF.getName()));
		model.addAttribute("cancelLink", resourceMovie.getLink(Relation.SELF.getName()));
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
	
	//################ helper methods ####################################

	private void convertSearchStringToTagsAndSearchWords(String searchString,
			Set<Tag> tags, Set<String> searchWords) {
		// What I do here could probably be done a thousand times more elegant, but it's not the focus of this project...
		if (searchString != null){
			String[] splitAroundTags = searchString.split("tag:");
			for (String token: splitAroundTags){
				if (token.length()!=0){
					if (token.startsWith("'")){
						// It's a tag!
						String[] splitAroundQuotes = token.split("'");
						// first one is empty, the second one is the tag
						tags.add(new Tag(splitAroundQuotes[1]));
						// if there is a rest they must be searchwords
						if (splitAroundQuotes.length > 2){
							for (int i = 2; i<splitAroundQuotes.length;i++){
								// split searchwords by blanks
								for (String searchWord: splitAroundQuotes[i].split(" ")){
									searchWords.add(searchWord.toLowerCase());
								}
							}
						}
					} else {
						// there's no tag
						// split searchwords by blanks
						for (String searchWord: token.split(" ")){
							searchWords.add(searchWord.toLowerCase());
						}
					}
				}
			}
		}
	}
	
}
