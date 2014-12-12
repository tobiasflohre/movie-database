package de.codecentric.moviedatabase.movies.controller;

import static de.codecentric.roca.core.LinkBuilder.linkTo;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.codecentric.moviedatabase.movies.domain.Tag;
import de.codecentric.moviedatabase.movies.model.MovieForm;
import de.codecentric.moviedatabase.movies.service.MovieService;
import de.codecentric.roca.core.Link;
import de.codecentric.roca.core.LinkBuilder;
import de.codecentric.roca.core.Resource;

@RequestMapping(value = "/movies", headers={"X-Requested-With!=XMLHttpRequest", "Accept=text/html"})
public class MovieController extends AbstractMovieController{
	
	private String navigationBaseUrl;
	
	public MovieController(MovieService movieService,
			TagResourceAssembler tagResourceAssembler,
			MovieResourceAssembler movieResourceAssembler, String navigationBaseUrl) {
		super(movieService, tagResourceAssembler, movieResourceAssembler, false);
		this.navigationBaseUrl = navigationBaseUrl;
	}

	//################### side- and searchbar data ###########################
	
	@ModelAttribute("tagsAll")
	public List<Resource<Tag>> getAllTags(){
		List<Tag> tags = movieService.findAllTags();
		return tagResourceAssembler.toResource(tags);
	}
	
	@ModelAttribute("linkNewMovie")
	public Link getLinkNewMovie(){
		return linkTo(MoviePathFragment.MOVIES).path(MoviePathFragment.NEW).withRel(MovieRelation.NEW);
	}
	
	@ModelAttribute("linkTagsAll")
	public Link getLinkTagsAll(){
		return linkTo(MoviePathFragment.TAGS).withRel(MovieRelation.SELF);
	}

	@ModelAttribute("linkNavigation")
	public Link getLinkNavigation(@RequestParam(required = false) String searchString, HttpServletRequest request){
		Link searchLink = linkTo(request.getContextPath()).path(MoviePathFragment.MOVIES).withRel(MovieRelation.SEARCH);
		LinkBuilder navigationLinkBuilder = linkTo(navigationBaseUrl).path(MoviePathFragment.NAVIGATION).requestParam(MovieRequestParameter.SEARCH_URL, searchLink.getHref())
				.requestParam(MovieRequestParameter.ACTIVE, "movies");
		if (searchString != null){
			return navigationLinkBuilder.requestParam(MovieRequestParameter.SEARCH_STRING, searchString).withRel(MovieRelation.NAVIGATION);
		} else {
			return navigationLinkBuilder.withRel(MovieRelation.NAVIGATION);
		}
	}

	//################## movies #################################################
	
	@RequestMapping(method = RequestMethod.POST)
	public String createMovie(MovieForm movieForm) {
		doCreateMovie(movieForm);
		if (movieForm.isAddAnotherMovie()){
			return "redirect:/movies/new";
		}
		
		return "redirect:/movies";
	}
	
	//####################### movie ############################################
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String editMovie(@PathVariable UUID id, MovieForm movieForm) {
		doEditMovie(id, movieForm);
		return "redirect:/movies/{id}";
	}
	
	//####################### comments ############################################
	
	@RequestMapping(value = "/{id}/comments", method = RequestMethod.POST)
	public String createComment(@PathVariable UUID id, @RequestParam String content, Model model) {
		doCreateComment(id, content);
		return "redirect:/movies/{id}/comments";
	}
	
	//######################## tags ##############################################
	
	@RequestMapping(value = "/{id}/tags", method = RequestMethod.POST)
	public String addTagToMovie(@PathVariable UUID id, @RequestParam Tag tag) {
		doAddTagToMovie(id, tag);
		return "redirect:/movies/{id}/tags";
	}
	
	@RequestMapping(value = "/{id}/tags/{tag}", method = RequestMethod.DELETE)
	public String removeTagFromMovie(@PathVariable UUID id, @PathVariable Tag tag) {
		doRemoveTagFromMovie(id, tag);
		return "redirect:/movies/{id}/tags";
	}
	
}
