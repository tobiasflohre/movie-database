package de.codecentric.moviedatabase.movies.controller;

import de.codecentric.roca.core.RequestParameter;

public enum MovieRequestParameter implements RequestParameter {
	
	SEARCH_STRING("searchString"),
	SEARCH_URL("searchUrl");
	
	private String name;

	private MovieRequestParameter(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see de.codecentric.moviedatabase.controller.RequestParameter#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
}
