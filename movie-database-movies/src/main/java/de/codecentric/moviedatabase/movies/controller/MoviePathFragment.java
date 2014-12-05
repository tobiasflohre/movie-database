package de.codecentric.moviedatabase.movies.controller;

import de.codecentric.roca.core.PathFragment;

public enum MoviePathFragment implements PathFragment {
	
	NEW("new"), 
	COMMENTS("comments"), 
	TAGS("tags"), 
	EDIT("edit"),
	MOVIES("movies"), 
	NAVIGATION("navigation"),
	ACTORS("actors");
	
	private String name;

	private MoviePathFragment(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see de.codecentric.moviedatabase.controller.PathFragment#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
}
