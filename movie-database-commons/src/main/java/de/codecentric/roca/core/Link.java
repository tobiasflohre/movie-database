package de.codecentric.roca.core;

public class Link {
	
	private String href;
	private String rel;
	
	public Link(String href, String rel) {
		this.href = href;
		this.rel = rel;
	}

	public String getHref() {
		return href;
	}

	public String getRel() {
		return rel;
	}

}
