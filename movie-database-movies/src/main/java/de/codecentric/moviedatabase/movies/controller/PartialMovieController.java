package de.codecentric.moviedatabase.movies.controller;

import static de.codecentric.roca.core.LinkBuilder.linkTo;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.codecentric.moviedatabase.movies.domain.Tag;
import de.codecentric.moviedatabase.movies.model.MovieForm;
import de.codecentric.moviedatabase.movies.service.MovieService;

@RequestMapping(value = "/movies", headers={"X-Requested-With=XMLHttpRequest"}, produces="text/html")
public class PartialMovieController extends AbstractMovieController{
	
	String contextPath;
	
	public PartialMovieController(MovieService movieService,
			TagResourceAssembler tagResourceAssembler,
			MovieResourceAssembler movieResourceAssembler,
			String contextPath) {
		super(movieService, tagResourceAssembler, movieResourceAssembler, true);
		this.contextPath = contextPath;
	}

	//###################### movies #################################################
	
	@RequestMapping(method = RequestMethod.POST)
	public String createMovie(MovieForm movieForm, Model model, HttpServletResponse response) {
		doCreateMovie(movieForm);
		if (movieForm.isAddAnotherMovie()){
			response.setHeader("redirectUrl", linkTo(contextPath).path(MoviePathFragment.MOVIES).path(MoviePathFragment.NEW).withRel(MovieRelation.NEW).getHref());
			return getCreateMovie(model);
		}
		
		return getMovies(model, null);
	}

	//########################### movie #####################################################
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String editMovie(@PathVariable UUID id, MovieForm movieForm, Model model) {
		doEditMovie(id, movieForm);
		return getMovie(id, model);
	}

	//###################### comments #####################################
	
	@RequestMapping(value = "/{id}/comments", method = RequestMethod.POST)
	public String createComment(@PathVariable UUID id,
			@RequestParam String content, Model model) {
		doCreateComment(id, content);
		return getComments(id, model);
	}

	//###################### tags #############################################
	
	@RequestMapping(value = "/{id}/tags", method = RequestMethod.POST)
	public String addTagToMovie(@PathVariable UUID id, @RequestParam Tag tag, Model model) {
		doAddTagToMovie(id, tag);
		return getTags(id, model);
	}

	@RequestMapping(value = "/{id}/tags/{tag}", method = RequestMethod.DELETE)
	public String removeTagFromMovie(@PathVariable UUID id,
			@PathVariable Tag tag, Model model, HttpServletResponse response) {
		doRemoveTagFromMovie(id, tag);
		response.setHeader("redirectUrl", linkTo(contextPath).path(MoviePathFragment.MOVIES).path(id).path(MoviePathFragment.TAGS).withRel(MovieRelation.TAGS).getHref());
		return getTags(id, model);
	}

}
