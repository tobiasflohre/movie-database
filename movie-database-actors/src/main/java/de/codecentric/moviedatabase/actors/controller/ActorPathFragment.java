package de.codecentric.moviedatabase.actors.controller;

import de.codecentric.roca.core.PathFragment;

public enum ActorPathFragment implements PathFragment {
	
	NEW("new"), 
	EDIT("edit"),
	ACTORS("actors"), 
	NAVIGATION("navigation");
	
	private String name;

	private ActorPathFragment(String name) {
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
