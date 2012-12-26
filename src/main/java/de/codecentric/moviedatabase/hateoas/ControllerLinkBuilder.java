package de.codecentric.moviedatabase.hateoas;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriTemplate;

public class ControllerLinkBuilder extends UriComponentsLinkBuilder<ControllerLinkBuilder> {
	
	/**
	 * Creates a new {@link ControllerLinkBuilder} using the given {@link UriComponentsBuilder}.
	 * 
	 * @param builder must not be {@literal null}.
	 */
	private ControllerLinkBuilder(UriComponentsBuilder builder) {
		super(builder);
	}

	/**
	 * Creates a new {@link ControllerLinkBuilder} with a base of the mapping annotated to the given controller class.
	 * 
	 * @param controller the class to discover the annotation on, must not be {@literal null}.
	 * @return
	 */
	public static ControllerLinkBuilder linkTo(Class<?> controller) {
		return linkTo(controller, new Object[0]);
	}

	/**
	 * Creates a new {@link ControllerLinkBuilder} with a base of the mapping annotated to the given controller class. The
	 * additional parameters are used to fill up potentially available path variables in the class scop request mapping.
	 * 
	 * @param controller the class to discover the annotation on, must not be {@literal null}.
	 * @param parameters additional parameters to bind to the URI template declared in the annotation, must not be
	 *          {@literal null}.
	 * @return
	 */
	public static ControllerLinkBuilder linkTo(Class<?> controller, Object... parameters) {

		Assert.notNull(controller);

		RequestMapping annotation = AnnotationUtils.findAnnotation(controller, RequestMapping.class);
		String[] mapping = annotation == null ? new String[0] : (String[]) AnnotationUtils.getValue(annotation);

		if (mapping.length > 1) {
			throw new IllegalStateException("Multiple controller mappings defined! Unable to build URI!");
		}

		ControllerLinkBuilder builder = new ControllerLinkBuilder(ServletUriComponentsBuilder.fromCurrentServletMapping());

		if (mapping.length == 0) {
			return builder;
		}

		UriTemplate template = new UriTemplate(mapping[0]);
		return builder.slash(template.expand(parameters));
	}
	
	/* 
	 * (non-Javadoc)
	 * @see org.springframework.hateoas.UriComponentsLinkBuilder#getThis()
	 */
	@Override
	protected ControllerLinkBuilder getThis() {
		return this;
	}

	/* 
	 * (non-Javadoc)
	 * @see org.springframework.hateoas.UriComponentsLinkBuilder#createNewInstance(org.springframework.web.util.UriComponentsBuilder)
	 */
	@Override
	protected ControllerLinkBuilder createNewInstance(UriComponentsBuilder builder) {
		return new ControllerLinkBuilder(builder);
	}
}
