package de.codecentric.roca.core;

import java.util.ArrayList;
import java.util.List;

import org.springframework.util.Assert;

/**
 * Base class for DTOs to collect links.
 * 
 * @author Oliver Gierke
 */
public class ResourceSupport{

	private final List<Link> links;

	public ResourceSupport() {
		this.links = new ArrayList<Link>();
	}

	/**
	 * Adds the given link to the resource.
	 * 
	 * @param link
	 */
	public void add(Link link) {
		Assert.notNull(link, "Link must not be null!");
		this.links.add(link);
	}

	/**
	 * Adds all given {@link Link}s to the resource.
	 * 
	 * @param links
	 */
	public void add(Iterable<Link> links) {
		Assert.notNull(links, "Given links must not be null!");
		for (Link candidate : links) {
			add(candidate);
		}
	}

	/**
	 * Returns whether the resource contains {@link Link}s at all.
	 * 
	 * @return
	 */
	public boolean hasLinks() {
		return !this.links.isEmpty();
	}

	/**
	 * Returns whether the resource contains a {@link Link} with the given rel.
	 * 
	 * @param rel
	 * @return
	 */
	public boolean hasLink(String rel) {
		return getLink(rel) != null;
	}

	/**
	 * Returns all {@link Link}s contained in this resource.
	 * 
	 * @return
	 */
	public List<Link> getLinks() {
		return links;
	}

	/**
	 * Removes all {@link Link}s added to the resource so far.
	 */
	public void removeLinks() {
		this.links.clear();
	}

	/**
	 * Returns the link with the given rel.
	 * 
	 * @param rel
	 * @return the link with the given rel or {@literal null} if none found.
	 */
	public Link getLink(String rel) {

		for (Link link : links) {
			if (link.getRel().equals(rel)) {
				return link;
			}
		}

		return null;
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return String.format("links: %s", links.toString());
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {

		if (this == obj) {
			return true;
		}

		if (obj == null || !obj.getClass().equals(this.getClass())) {
			return false;
		}

		ResourceSupport that = (ResourceSupport) obj;

		return this.links.equals(that.links);
	}

	/* 
	 * (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return this.links.hashCode();
	}
}
