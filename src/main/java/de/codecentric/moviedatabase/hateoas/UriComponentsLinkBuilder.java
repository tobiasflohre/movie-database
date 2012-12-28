package de.codecentric.moviedatabase.hateoas;

import java.net.URI;

import org.springframework.hateoas.Identifiable;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.LinkBuilder;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Base class to implement {@link LinkBuilder}s based on a Spring MVC {@link UriComponentsBuilder}.
 * 
 * @author Ricardo Gladwell
 * @author Oliver Gierke
 * @author Tobias Flohre (extended for request parameters)
 * @param <T> The LinkBuilder Typ
 */
public abstract class UriComponentsLinkBuilder<T extends LinkBuilder> implements LinkBuilder {

	private final UriComponents uriComponents;

	/**
	 * Creates a new {@link UriComponentsLinkBuilder} using the given {@link UriComponentsBuilder}.
	 * 
	 * @param builder must not be {@literal null}.
	 */
	public UriComponentsLinkBuilder(UriComponentsBuilder builder) {

		Assert.notNull(builder);
		this.uriComponents = builder.build();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.hateoas.LinkBuilder#slash(java.lang.Object)
	 */
	@Override
	public T slash(Object object) {

		if (object == null) {
			return getThis();
		}

		String[] segments = StringUtils.tokenizeToStringArray(object.toString(), "/");
		return createNewInstance(UriComponentsBuilder.fromUri(uriComponents.toUri()).pathSegment(segments));
	}

	public T addRequestParam(String paramName, String paramValue) {

		if (paramName == null || paramValue == null) {
			return getThis();
		}

		return createNewInstance(UriComponentsBuilder.fromUri(uriComponents.toUri()).queryParam(paramName, paramValue));
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.hateoas.LinkBuilder#slash(org.springframework.hateoas.Identifiable)
	 */
	@Override
	public LinkBuilder slash(Identifiable<?> identifyable) {

		if (identifyable == null) {
			return this;
		}

		return slash(identifyable.getId());
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.hateoas.LinkBuilder#toUri()
	 */
	@Override
	public URI toUri() {
		return uriComponents.encode().toUri();
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.hateoas.LinkBuilder#withRel(java.lang.String)
	 */
	@Override
	public Link withRel(String rel) {
		return new Link(this.toString(), rel);
	}

	/*
	 * (non-Javadoc)
	 * @see org.springframework.hateoas.LinkBuilder#withSelfRel()
	 */
	@Override
	public Link withSelfRel() {
		return new Link(this.toString());
	}

	/*
	 * (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toUri().normalize().toASCIIString();
	}

	/**
	 * Returns the current concrete instance.
	 * 
	 * @return
	 */
	protected abstract T getThis();

	/**
	 * Creates a new instance of the sub-class.
	 * 
	 * @param builder will never be {@literal null}.
	 * @return
	 */
	protected abstract T createNewInstance(UriComponentsBuilder builder);
}
