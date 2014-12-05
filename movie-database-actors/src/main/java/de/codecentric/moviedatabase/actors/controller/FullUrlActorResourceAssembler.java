package de.codecentric.moviedatabase.actors.controller;

import static de.codecentric.roca.core.LinkBuilder.linkTo;

import org.springframework.util.Assert;

import de.codecentric.moviedatabase.actors.domain.Actor;
import de.codecentric.roca.core.AbstractResourceAssembler;
import de.codecentric.roca.core.Link;
import de.codecentric.roca.core.Resource;

public class FullUrlActorResourceAssembler extends AbstractResourceAssembler<Actor, Resource<Actor>> {
	
	private String actorsBaseUrl;
	
	public FullUrlActorResourceAssembler(String actorsBaseUrl) {
		super();
		this.actorsBaseUrl = actorsBaseUrl;
	}

	@Override
	public Resource<Actor> toResource(Actor actor) {
		Assert.notNull(actor);
		Link selfLink = linkTo(actorsBaseUrl).path(ActorPathFragment.ACTORS).path(actor.getId()).withRel(ActorRelation.SELF);
		Link actorsLink = linkTo(actorsBaseUrl).path(ActorPathFragment.ACTORS).withRel(ActorRelation.ACTORS);
		Link editLink = linkTo(actorsBaseUrl).path(ActorPathFragment.ACTORS).path(actor.getId()).path(ActorPathFragment.EDIT).withRel(ActorRelation.EDIT);
		return new Resource<Actor>(actor, selfLink, actorsLink, editLink);
	}

}
