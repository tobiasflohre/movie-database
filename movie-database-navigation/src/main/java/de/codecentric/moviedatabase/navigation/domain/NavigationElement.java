package de.codecentric.moviedatabase.navigation.domain;

public class NavigationElement {

	private String id;
	private String label;
	private int order;
	private String url;
	private String type;
	private String application;
	private String authorizedGroups;

	public int getOrder() {
		return order;
	}

	public void setOrder(int order) {
		this.order = order;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAuthorizedGroups() {
		return authorizedGroups;
	}

	public void setAuthorizedGroups(String authorizedGroups) {
		this.authorizedGroups = authorizedGroups;
	}

	@Override
	public String toString() {
		return "NavigationElement [id=" + id + ", label=" + label + ", order="
				+ order + ", url=" + url + ", type=" + type + ", application="
				+ application + ", authorizedGroups=" + authorizedGroups + "]";
	}

}