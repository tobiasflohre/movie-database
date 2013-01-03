package de.codecentric.moviedatabase.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.codecentric.moviedatabase.domain.Tag;
import de.codecentric.moviedatabase.service.MovieService;

@RequestMapping(value = "/tags", headers={"X-Requested-With=XMLHttpRequest", "Accept=text/html"})
public class HtmlPartialTagController {
	
	private MovieService movieService;
	private TagResourceAssembler tagResourceAssembler;
	
	public HtmlPartialTagController(MovieService movieService,
			TagResourceAssembler tagResourceAssembler) {
		this.movieService = movieService;
		this.tagResourceAssembler = tagResourceAssembler;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getAllTags(Model model){
		List<Tag> tags = movieService.findAllTags();
		model.addAttribute("tagsAll", tagResourceAssembler.toResource(tags));
		return "tag/partial/tags";
	}

}
