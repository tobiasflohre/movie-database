package de.codecentric.moviedatabase.navigation.controller;

import static de.codecentric.roca.core.LinkBuilder.linkTo;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/navigation")
public class NavigationController{
	
	private String moviesBaseUrl;
	private String actorsBaseUrl;
	private String shopBaseUrl;
	
	public NavigationController(String moviesBaseUrl, String actorsBaseUrl, String shopBaseUrl) {
		this.moviesBaseUrl = moviesBaseUrl;
		this.actorsBaseUrl = actorsBaseUrl;
		this.shopBaseUrl = shopBaseUrl;
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getNavigation(Model model, @RequestParam String searchUrl, @RequestParam String active, @RequestParam(required = false) String searchString) {
		NavigationResource navResource = new NavigationResource(active, searchString != null?searchString:"");
		navResource.add(linkTo(moviesBaseUrl).path(NavPathFragment.MOVIES).withRel(NavRelation.MOVIES));
		navResource.add(linkTo(shopBaseUrl).withRel(NavRelation.SHOP));
		navResource.add(linkTo(actorsBaseUrl).path(NavPathFragment.ACTORS).withRel(NavRelation.ACTORS));
		navResource.add(linkTo(moviesBaseUrl).path(NavPathFragment.LOGOUT).withRel(NavRelation.LOGOUT));
		navResource.add(linkTo(searchUrl).withRel(NavRelation.SEARCH));
		model.addAttribute("navResource", navResource);
		return "navigation/navigation";
	}
	
}
