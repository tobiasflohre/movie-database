package de.codecentric.moviedatabase.actors.controller;

import de.codecentric.roca.core.RequestParameter;

public enum ActorRequestParameter implements RequestParameter {
	
	SEARCH_STRING("searchString"),
	SEARCH_URL("searchUrl"), 
	ACTIVE("active"), 
	RETURN_URL("returnUrl");
	
	private String name;

	private ActorRequestParameter(String name) {
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
