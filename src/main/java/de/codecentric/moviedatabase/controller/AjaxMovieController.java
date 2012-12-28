package de.codecentric.moviedatabase.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.codecentric.moviedatabase.domain.Tag;
import de.codecentric.moviedatabase.hateoas.ControllerLinkBuilderFactory;
import de.codecentric.moviedatabase.model.MovieForm;
import de.codecentric.moviedatabase.service.MovieService;

@RequestMapping(value = "/movies", headers="X-Requested-With=XMLHttpRequest")
public class AjaxMovieController extends AbstractMovieController{
	
	private final static Logger logger = LoggerFactory.getLogger(AjaxMovieController.class); 

	public AjaxMovieController(MovieService movieService,
			ControllerLinkBuilderFactory linkBuilderFactory,
			TagResourceAssembler tagResourceAssembler,
			MovieResourceAssembler movieResourceAssembler) {
		super(movieService, linkBuilderFactory, tagResourceAssembler, movieResourceAssembler);
	}

	@Override
	protected String getLogicalViewNamePrefix() {
		logger.debug("ajax get");
		return "ajax/";
	}

	//###################### movies #################################################
	
	@RequestMapping(method = RequestMethod.POST)
	public String createMovie(MovieForm movieForm, Model model, HttpServletResponse response) {
		doCreateMovie(movieForm);
		if (movieForm.isAddAnotherMovie()){
			response.setHeader("redirectUrl", linkBuilderFactory.linkTo(MovieController.class).slash(PathFragment.NEW.getName()).withSelfRel().getHref());
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
			@PathVariable Tag tag, Model model) {
		doRemoveTagFromMovie(id, tag);
		return getTags(id, model);
	}

}
