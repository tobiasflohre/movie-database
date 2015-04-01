package de.codecentric.moviedatabase.movies.events;

import de.codecentric.moviedatabase.movies.domain.Movie;

public class MovieEvent {
	
	private MovieEventType type;
	private Movie movie;
	
	public MovieEvent(){
		super();
	}

	public MovieEvent(MovieEventType type, Movie movie) {
		super();
		this.type = type;
		this.movie = movie;
	}

	public void setType(MovieEventType type) {
		this.type = type;
	}

	public Movie getMovie() {
		return movie;
	}

	public void setMovie(Movie movie) {
		this.movie = movie;
	}

	public MovieEventType getType() {
		return type;
	}

}
