package de.codecentric.moviedatabase.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.util.Assert;

import de.codecentric.moviedatabase.domain.Tag;
import de.codecentric.moviedatabase.hateoas.AbstractResourceAssembler;
import de.codecentric.moviedatabase.hateoas.ControllerLinkBuilderFactory;

public class TagResourceAssembler extends AbstractResourceAssembler<Tag, Resource<Tag>> {
	
	private ControllerLinkBuilderFactory linkBuilderFactory;
	
	public TagResourceAssembler(ControllerLinkBuilderFactory linkBuilderFactory) {
		super();
		this.linkBuilderFactory = linkBuilderFactory;
	}

	@Override
	public Resource<Tag> toResource(Tag tag) {
		Assert.notNull(tag);
		Link searchLink = null;
		searchLink = linkBuilderFactory.linkTo(MovieController.class)
			.addRequestParam(RequestParameter.SEARCH_STRING.getName(), "tag:'"+tag.getLabel()+"'").withRel(Relation.SEARCH.getName());
		return new Resource<Tag>(tag, searchLink);
	}
	
	public Resource<Tag> toResource(Tag tag, UUID movieId){
		Assert.notNull(movieId);
		Resource<Tag> resourceTag = toResource(tag);
		Link deleteTagFromMovieLink = linkBuilderFactory.linkTo(MovieController.class).slash(movieId).slash(PathFragment.TAGS.getName()).slash(tag.getLabel()).withSelfRel();
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
