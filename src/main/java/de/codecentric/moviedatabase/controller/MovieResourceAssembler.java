package de.codecentric.moviedatabase.controller;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.util.Assert;

import de.codecentric.moviedatabase.domain.Movie;
import de.codecentric.moviedatabase.hateoas.AbstractResourceAssembler;
import de.codecentric.moviedatabase.hateoas.ControllerLinkBuilderFactory;

public class MovieResourceAssembler extends AbstractResourceAssembler<Movie, Resource<Movie>> {
	
	private ControllerLinkBuilderFactory linkBuilderFactory;
	
	public MovieResourceAssembler(
			ControllerLinkBuilderFactory linkBuilderFactory) {
		super();
		this.linkBuilderFactory = linkBuilderFactory;
	}

	@Override
	public Resource<Movie> toResource(Movie movie) {
		Assert.notNull(movie);
		Link selfLink = linkBuilderFactory.linkTo(AbstractMovieController.class)
				.slash(movie.getId())
				.withRel(Relation.SELF.getName());
		Link moviesLink = linkBuilderFactory.linkTo(AbstractMovieController.class)
				.withRel(Relation.MOVIES.getName());
		Link deleteLink = linkBuilderFactory.linkTo(AbstractMovieController.class)
				.slash(movie.getId())
				.withRel(Relation.DELETE.getName());
		Link editLink = linkBuilderFactory.linkTo(AbstractMovieController.class)
				.slash(movie.getId())
				.slash(PathFragment.EDIT.getName())
				.withRel(Relation.EDIT.getName());
		Link commentsLink = linkBuilderFactory.linkTo(AbstractMovieController.class)
				.slash(movie.getId())
				.slash(PathFragment.COMMENTS.getName())
				.withRel(Relation.COMMENTS.getName());
		Link tagsLink = linkBuilderFactory.linkTo(AbstractMovieController.class)
				.slash(movie.getId())
				.slash(PathFragment.TAGS.getName())
				.withRel(Relation.TAGS.getName());
		return new Resource<Movie>(movie, selfLink, moviesLink, deleteLink, editLink, commentsLink, tagsLink);
	}

}
