package de.codecentric.moviedatabase.actors.controller;

import de.codecentric.roca.core.Relation;


/**
 * This is our internal API for the view. Links are identified by relations. Of course not every entity has a
 * link for every relation listed here, it always depends on the circumstances.
 * 
 * @author tobias.flohre
 */
public enum ActorRelation implements Relation {
	
	SEARCH("search"),
	NEW("new"), 
	SELF("self"), 
	DELETE("delete"),
	EDIT("edit"), 
	ACTORS("actors"), 
	NAVIGATION("navigation");
	
	private String name;

	private ActorRelation(String name) {
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
