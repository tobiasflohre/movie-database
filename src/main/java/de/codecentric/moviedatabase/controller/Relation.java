package de.codecentric.moviedatabase.controller;

import org.springframework.hateoas.Link;

/**
 * This is our internal API for the view. Links are identified by relations. Of course not every entity has a
 * link for every relation listed here, it always depends on the circumstances.
 * 
 * @author tobias.flohre
 */
public enum Relation {
	
	SEARCH("search"),
	NEW("new"), 
	SELF(Link.REL_SELF), 
	COMMENTS("comments"), 
	TAGS("tags"), 
	DELETE("delete"),
	EDIT("edit"), 
	MOVIES("movies");
	
	private String name;

	private Relation(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
