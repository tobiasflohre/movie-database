package de.codecentric.moviedatabase.actors.controller;

import static de.codecentric.roca.core.LinkBuilder.linkTo;

import org.springframework.util.Assert;

import de.codecentric.moviedatabase.actors.domain.Actor;
import de.codecentric.roca.core.AbstractResourceAssembler;
import de.codecentric.roca.core.Link;
import de.codecentric.roca.core.Resource;

public class ActorResourceAssembler extends AbstractResourceAssembler<Actor, Resource<Actor>> {
	
	@Override
	public Resource<Actor> toResource(Actor actor) {
		Assert.notNull(actor);
		Link selfLink = linkTo(ActorPathFragment.ACTORS).path(actor.getId()).withRel(ActorRelation.SELF);
		Link actorsLink = linkTo(ActorPathFragment.ACTORS).withRel(ActorRelation.ACTORS);
		Link editLink = linkTo(ActorPathFragment.ACTORS).path(actor.getId()).path(ActorPathFragment.EDIT).withRel(ActorRelation.EDIT);
		return new Resource<Actor>(actor, selfLink, actorsLink, editLink);
	}

}
