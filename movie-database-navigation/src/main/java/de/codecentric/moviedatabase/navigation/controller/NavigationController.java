package de.codecentric.moviedatabase.navigation.controller;

import static de.codecentric.roca.core.LinkBuilder.linkTo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/navigation")
public class NavigationController{
	
	private String moviesBaseUrl;
	
	public NavigationController(String moviesBaseUrl) {
		this.moviesBaseUrl = moviesBaseUrl;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getNavigation(Model model, @RequestParam(required = false) String searchUrl) {
		model.addAttribute("searchUrl", searchUrl);
		model.addAttribute("moviesLink", linkTo(moviesBaseUrl).path(NavPathFragment.MOVIES).withRel(NavRelation.MOVIES));
		return "navigation/navigation";
	}
	
}
