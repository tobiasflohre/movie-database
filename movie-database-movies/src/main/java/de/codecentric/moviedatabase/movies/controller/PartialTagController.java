package de.codecentric.moviedatabase.movies.controller;

import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.codecentric.moviedatabase.movies.domain.Tag;
import de.codecentric.moviedatabase.movies.service.MovieService;

@RequestMapping(value = "/tags", headers={"X-Requested-With=XMLHttpRequest"}, produces="text/html")
public class PartialTagController {
	
	private MovieService movieService;
	private TagResourceAssembler tagResourceAssembler;
	
	public PartialTagController(MovieService movieService,
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
