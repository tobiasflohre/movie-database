package de.codecentric.moviedatabase.navigation.controller;

import java.util.List;

import de.codecentric.roca.core.ResourceSupport;

public class NavigationResource extends ResourceSupport {
	
	private String searchString;
	private List<NavElementModel> navElements;

	public NavigationResource(String searchString, List<NavElementModel> navElements) {
		this.searchString = searchString;
		this.navElements = navElements;
	}

	public List<NavElementModel> getNavElements() {
		return navElements;
	}

	public String getSearchString() {
		return searchString;
	}
	
}
