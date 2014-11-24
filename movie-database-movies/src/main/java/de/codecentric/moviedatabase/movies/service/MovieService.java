package de.codecentric.moviedatabase.movies.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import de.codecentric.moviedatabase.movies.domain.Comment;
import de.codecentric.moviedatabase.movies.domain.Movie;
import de.codecentric.moviedatabase.movies.domain.Tag;

public interface MovieService {
	
	public void createMovie(Movie movie);
	
	public void updateMovie(Movie movie);
	
	public void deleteMovie(UUID id);
	
	public void addCommentToMovie(Comment comment, UUID movieId);
	
	public void addTagToMovie(Tag tag, UUID movieId);
	
	public void removeTagFromMovie(Tag tag, UUID movieId);

	public Movie findMovieById(UUID id);
	
	public List<Movie> findMovieByTagsAndSearchString(Set<Tag> tags, Set<String> searchWords);

	public List<Tag> findAllTags();
	
}
