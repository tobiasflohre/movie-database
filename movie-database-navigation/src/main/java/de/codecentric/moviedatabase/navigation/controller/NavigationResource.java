package de.codecentric.moviedatabase.navigation.controller;

import de.codecentric.roca.core.ResourceSupport;

public class NavigationResource extends ResourceSupport {
	
	private String active;
	private String searchString;

	public NavigationResource(String active, String searchString) {
		this.active = active;
		this.searchString = searchString;
	}

	public String getActive() {
		return active;
	}

	public String getSearchString() {
		return searchString;
	}
	
}
