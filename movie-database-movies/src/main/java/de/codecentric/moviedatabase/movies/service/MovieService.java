package de.codecentric.moviedatabase.movies.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import de.codecentric.moviedatabase.movies.domain.Comment;
import de.codecentric.moviedatabase.movies.domain.Movie;
import de.codecentric.moviedatabase.movies.domain.Tag;

public interface MovieService {
	
	void createMovie(Movie movie);
	
	void updateMovie(Movie movie);
	
	void deleteMovie(UUID id);
	
	void addCommentToMovie(Comment comment, UUID movieId);
	
	void addTagToMovie(Tag tag, UUID movieId);
	
	void removeTagFromMovie(Tag tag, UUID movieId);

	Movie findMovieById(UUID id);
	
	List<Movie> findMovieByTagsAndSearchString(Set<Tag> tags, Set<String> searchWords);

	List<Tag> findAllTags();
	
}
