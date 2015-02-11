package de.codecentric.moviedatabase.cms.controller;

import de.codecentric.roca.core.Relation;


/**
 * This is our internal API for the view. Links are identified by relations. Of course not every entity has a
 * link for every relation listed here, it always depends on the circumstances.
 * 
 * @author tobias.flohre
 */
public enum CmsRelation implements Relation {
	
	NAVIGATION("navigation");
	
	private String name;

	private CmsRelation(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see de.codecentric.moviedatabase.controller.Relation#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
}
