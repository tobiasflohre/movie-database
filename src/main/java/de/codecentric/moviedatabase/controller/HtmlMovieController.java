package de.codecentric.moviedatabase.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.codecentric.moviedatabase.domain.Tag;
import de.codecentric.moviedatabase.hateoas.ControllerLinkBuilderFactory;
import de.codecentric.moviedatabase.model.MovieForm;
import de.codecentric.moviedatabase.service.MovieService;

@RequestMapping(value = "/movies", headers={"X-Requested-With!=XMLHttpRequest", "Accept=text/html"})
public class HtmlMovieController extends AbstractMovieController{
	
	public HtmlMovieController(MovieService movieService,
			ControllerLinkBuilderFactory linkBuilderFactory,
			TagResourceAssembler tagResourceAssembler,
			MovieResourceAssembler movieResourceAssembler) {
		super(movieService, linkBuilderFactory, tagResourceAssembler, movieResourceAssembler, false);
	}

	//################### side- and searchbar data ###########################
	
	@ModelAttribute("tagsAll")
	public List<Resource<Tag>> getAllTags(){
		List<Tag> tags = movieService.findAllTags();
		return tagResourceAssembler.toResource(tags);
	}
	
	@ModelAttribute("linkHome")
	public Link getLinkHome(){
		return linkBuilderFactory.linkTo(AbstractMovieController.class).withSelfRel();
	}
	
	@ModelAttribute("linkNewMovie")
	public Link getLinkNewMovie(){
		return linkBuilderFactory.linkTo(AbstractMovieController.class)
				.slash(PathFragment.NEW.getName())
				.withRel(Relation.NEW.getName());
	}
	
	@ModelAttribute("linkTagsAll")
	public Link getLinkTagsAll(){
		return linkBuilderFactory.linkTo(HtmlPartialTagController.class).withSelfRel();
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
