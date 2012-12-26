package de.codecentric.moviedatabase.controller;

public enum PathFragment {
	
	NEW("new"), 
	COMMENTS("comments"), 
	TAGS("tags"), 
	EDIT("edit");
	
	private String name;

	private PathFragment(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
