package de.codecentric.moviedatabase.cms.controller;

import de.codecentric.roca.core.PathFragment;

public enum CmsPathFragment implements PathFragment {
	
	NAVIGATION("navigation");
	
	private String name;

	private CmsPathFragment(String name) {
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
