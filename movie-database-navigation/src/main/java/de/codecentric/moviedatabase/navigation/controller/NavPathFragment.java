package de.codecentric.moviedatabase.navigation.controller;

import de.codecentric.roca.core.PathFragment;

public enum NavPathFragment implements PathFragment {
	
	MOVIES("movies"),
	ACTORS("actors"),
	LOGOUT("logout");
	
	private String name;

	private NavPathFragment(String name) {
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
