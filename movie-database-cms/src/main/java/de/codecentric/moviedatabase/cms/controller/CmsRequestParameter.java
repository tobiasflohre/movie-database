package de.codecentric.moviedatabase.cms.controller;

import de.codecentric.roca.core.RequestParameter;

public enum CmsRequestParameter implements RequestParameter {
	
	SEARCH_URL("searchUrl"), 
	ACTIVE("active"); 
	
	private String name;

	private CmsRequestParameter(String name) {
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
