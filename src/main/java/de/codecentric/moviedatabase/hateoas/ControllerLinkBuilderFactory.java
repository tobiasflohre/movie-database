package de.codecentric.moviedatabase.hateoas;

import org.springframework.hateoas.LinkBuilderFactory;

public class ControllerLinkBuilderFactory implements LinkBuilderFactory<ControllerLinkBuilder>{

	@Override
	public ControllerLinkBuilder linkTo(Class<?> target) {
		return ControllerLinkBuilder.linkTo(target);
	}

	@Override
	public ControllerLinkBuilder linkTo(Class<?> target, Object... parameters) {
		return ControllerLinkBuilder.linkTo(target, parameters);
	}

}
