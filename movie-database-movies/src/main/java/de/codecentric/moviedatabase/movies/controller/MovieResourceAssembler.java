package de.codecentric.moviedatabase.movies.controller;

import static de.codecentric.roca.core.LinkBuilder.linkTo;

import org.springframework.util.Assert;

import de.codecentric.moviedatabase.movies.domain.Movie;
import de.codecentric.roca.core.AbstractResourceAssembler;
import de.codecentric.roca.core.Link;
import de.codecentric.roca.core.Resource;

public class MovieResourceAssembler extends AbstractResourceAssembler<Movie, Resource<Movie>> {
	
	private String actorsBaseUrl;
	private String serverContextPath;
	
	public MovieResourceAssembler(String actorsBaseUrl, String serverContextPath) {
		super();
		this.actorsBaseUrl = actorsBaseUrl;
		this.serverContextPath = serverContextPath;
	}

	@Override
	public Resource<Movie> toResource(Movie movie) {
		Assert.notNull(movie);
		Link selfLink = linkTo(MoviePathFragment.MOVIES).path(movie.getId()).withRel(MovieRelation.SELF);
		Link moviesLink = linkTo(MoviePathFragment.MOVIES).withRel(MovieRelation.MOVIES);
		Link editLink = linkTo(MoviePathFragment.MOVIES).path(movie.getId()).path(MoviePathFragment.EDIT).withRel(MovieRelation.EDIT);
		Link commentsLink = linkTo(MoviePathFragment.MOVIES).path(movie.getId()).path(MoviePathFragment.COMMENTS).withRel(MovieRelation.COMMENTS);
		Link tagsLink = linkTo(MoviePathFragment.MOVIES).path(movie.getId()).path(MoviePathFragment.TAGS).withRel(MovieRelation.TAGS);
		Link actorsLink = linkTo(actorsBaseUrl).path(MoviePathFragment.ACTORS).requestParam(MovieRequestParameter.SEARCH_STRING, "movie:'"+movie.getId()+"'").withRel(MovieRelation.ACTORS);
		Link addActorLink = linkTo(actorsBaseUrl).path(MoviePathFragment.ACTORS).path(MoviePathFragment.NEW).requestParam(MovieRequestParameter.RETURN_URL, serverContextPath+selfLink.getHref()).requestParam(MovieRequestParameter.MOVIE_ID, movie.getId().toString()).withRel(MovieRelation.ADD_ACTOR);
		return new Resource<Movie>(movie, selfLink, moviesLink, editLink, commentsLink, tagsLink, actorsLink, addActorLink);
	}

}
