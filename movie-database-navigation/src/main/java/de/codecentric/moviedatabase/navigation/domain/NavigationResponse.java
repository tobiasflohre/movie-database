package de.codecentric.moviedatabase.navigation.domain;

import java.util.List;

public class NavigationResponse {
	
	private List<NavigationElement> navigationElements;

	public List<NavigationElement> getNavigationElements() {
		return navigationElements;
	}

	public void setNavigationElements(List<NavigationElement> navigationElements) {
		this.navigationElements = navigationElements;
	}

}
