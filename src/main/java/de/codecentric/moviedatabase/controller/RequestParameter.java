package de.codecentric.moviedatabase.controller;

public enum RequestParameter {
	
	SEARCH_STRING("searchString");
	
	private String name;

	private RequestParameter(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
