package de.codecentric.moviedatabase.movies.controller;

import de.codecentric.roca.core.Relation;


/**
 * This is our internal API for the view. Links are identified by relations. Of course not every entity has a
 * link for every relation listed here, it always depends on the circumstances.
 * 
 * @author tobias.flohre
 */
public enum MovieRelation implements Relation {
	
	SEARCH("search"),
	NEW("new"), 
	SELF("self"), 
	COMMENTS("comments"), 
	TAGS("tags"), 
	DELETE("delete"),
	EDIT("edit"), 
	MOVIES("movies"), 
	NAVIGATION("navigation"),
	ACTORS("actors"), 
	ADD_ACTOR("add_actor");
	
	private String name;

	private MovieRelation(String name) {
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
