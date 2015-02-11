package de.codecentric.moviedatabase.navigation.controller;

public class NavElementModel {

	private String label;
	private String url;
	private boolean active;
	
	public NavElementModel(String label, String url, boolean active) {
		super();
		this.label = label;
		this.url = url;
		this.active = active;
	}
	public String getLabel() {
		return label;
	}
	public String getUrl() {
		return url;
	}
	public boolean isActive() {
		return active;
	}


}