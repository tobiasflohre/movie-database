package de.codecentric.moviedatabase.movies.controller;

import static de.codecentric.roca.core.LinkBuilder.linkTo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.util.Assert;

import de.codecentric.moviedatabase.movies.domain.Tag;
import de.codecentric.roca.core.AbstractResourceAssembler;
import de.codecentric.roca.core.Link;
import de.codecentric.roca.core.Resource;

public class TagResourceAssembler extends AbstractResourceAssembler<Tag, Resource<Tag>> {
	
	@Override
	public Resource<Tag> toResource(Tag tag) {
		Assert.notNull(tag);
		Link searchLink = linkTo(MoviePathFragment.MOVIES).requestParam(MovieRequestParameter.SEARCH_STRING,"tag:'"+tag.getLabel()+"'").withRel(MovieRelation.SEARCH);
		return new Resource<Tag>(tag, searchLink);
	}
	
	public Resource<Tag> toResource(Tag tag, UUID movieId){
		Assert.notNull(movieId);
		Resource<Tag> resourceTag = toResource(tag);
		Link deleteTagFromMovieLink = linkTo(MoviePathFragment.MOVIES).path(movieId).path(MoviePathFragment.TAGS).path(tag.getLabel()).withRel(MovieRelation.SELF);
		resourceTag.add(deleteTagFromMovieLink);
		return resourceTag;
	}

	public List<Resource<Tag>> toResource(Collection<Tag> tags, UUID movieId){
		Assert.notNull(tags);
		List<Resource<Tag>> resourceList = new ArrayList<Resource<Tag>>();
		for (Tag tag: tags){
			resourceList.add(toResource(tag, movieId));
		}
		return resourceList;
	}
}
