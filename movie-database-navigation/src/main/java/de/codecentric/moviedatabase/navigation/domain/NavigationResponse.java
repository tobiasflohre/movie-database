package de.codecentric.moviedatabase.navigation.domain;

import java.util.List;

public class NavigationResponse {
	
	private String type;
	
	private List<NavigationElement> navigationElements;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<NavigationElement> getNavigationElements() {
		return navigationElements;
	}

	public void setNavigationElements(List<NavigationElement> navigationElements) {
		this.navigationElements = navigationElements;
	}

}
