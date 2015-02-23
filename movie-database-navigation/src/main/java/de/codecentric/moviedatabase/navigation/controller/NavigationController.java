package de.codecentric.moviedatabase.navigation.controller;

import static de.codecentric.roca.core.LinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.List;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import de.codecentric.moviedatabase.navigation.domain.NavigationElement;
import de.codecentric.moviedatabase.navigation.domain.NavigationResponse;
import de.codecentric.roca.core.LinkResolver;

@RequestMapping("/navigation")
public class NavigationController{
	
	private String moviesBaseUrl;
	private String contentDeliveryBaseUrl;
	private RestTemplate restTemplate;
	private LinkResolver linkResolver;
	
	public NavigationController(String moviesBaseUrl, String contentDeliveryBaseUrl, LinkResolver linkResolver) {
		this.moviesBaseUrl = moviesBaseUrl;
		this.contentDeliveryBaseUrl = contentDeliveryBaseUrl;
		this.linkResolver = linkResolver;
		this.restTemplate = new RestTemplate();
	}

	@RequestMapping(method = RequestMethod.GET)
	public String getNavigation(Model model, @RequestParam String searchUrl, @RequestParam String active, @RequestParam(required = false) String searchString) {
		List<NavigationElement> navigationElements = restTemplate.getForObject(contentDeliveryBaseUrl+"/content/navigation/navigation.json", NavigationResponse.class).getNavigationElements();
		List<NavElementModel> navModelList = new ArrayList<>();
		for (NavigationElement element: navigationElements){
			navModelList.add(new NavElementModel(element.getLabel(), linkResolver.resolve(element.getType(), element.getUrl(), element.getApplication()), active.equals(element.getId())));
		}
		NavigationResource navResource = new NavigationResource(searchString != null?searchString:"", navModelList);
		navResource.add(linkTo(moviesBaseUrl).path(NavPathFragment.MOVIES).withRel(NavRelation.MOVIES));
		navResource.add(linkTo(moviesBaseUrl).path(NavPathFragment.LOGOUT).withRel(NavRelation.LOGOUT));
		navResource.add(linkTo(searchUrl).withRel(NavRelation.SEARCH));
		model.addAttribute("navResource", navResource);
		return "navigation/navigation";
	}
	
}
