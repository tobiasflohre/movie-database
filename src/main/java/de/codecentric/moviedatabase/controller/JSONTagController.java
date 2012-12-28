package de.codecentric.moviedatabase.controller;

import java.util.List;

import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import de.codecentric.moviedatabase.domain.Tag;
import de.codecentric.moviedatabase.service.MovieService;

@RequestMapping(value = "/tags", headers="Accept=application/json")
public class JSONTagController {
	
	private MovieService movieService;
	private TagResourceAssembler tagResourceAssembler;
	
	public JSONTagController(MovieService movieService,
			TagResourceAssembler tagResourceAssembler) {
		this.movieService = movieService;
		this.tagResourceAssembler = tagResourceAssembler;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Resource<Tag>> getAllTags(){
		List<Tag> tags = movieService.findAllTags();
		return tagResourceAssembler.toResource(tags);
	}
}
