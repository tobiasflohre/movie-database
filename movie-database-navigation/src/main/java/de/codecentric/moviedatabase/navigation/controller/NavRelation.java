package de.codecentric.moviedatabase.navigation.controller;

import de.codecentric.roca.core.Relation;


/**
 * This is our internal API for the view. Links are identified by relations. Of course not every entity has a
 * link for every relation listed here, it always depends on the circumstances.
 * 
 * @author tobias.flohre
 */
public enum NavRelation implements Relation {
	
	SELF("self"), 
	MOVIES("movies"),
	ACTORS("actors"),
	SEARCH("search"),
	LOGOUT("logout"), 
	SHOP("shop");
	
	private String name;

	private NavRelation(String name) {
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
