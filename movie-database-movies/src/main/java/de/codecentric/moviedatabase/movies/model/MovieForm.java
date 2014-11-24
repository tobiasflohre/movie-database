package de.codecentric.moviedatabase.movies.model;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.RequestMethod;

import de.codecentric.moviedatabase.movies.domain.Movie;

public class MovieForm {
	
	// display data
	private String pageTitle;
	private RequestMethod requestMethod;
	private boolean showAddAnotherMovie;
	
	// form data
	private String title;
	private String description;
	@DateTimeFormat(iso=ISO.DATE)
	private Date startDate;
	private boolean addAnotherMovie = false;
	
	public MovieForm(){
		pageTitle = "Add a movie";
		requestMethod = RequestMethod.POST;
		showAddAnotherMovie = true;
	}

	public MovieForm(Movie movie){
		pageTitle = "Edit a movie";
		requestMethod = RequestMethod.PUT;
		showAddAnotherMovie = false;
		fillDataFromMovie(movie);
	}
	
	private void fillDataFromMovie(Movie movie){
		this.title = movie.getTitle();
		this.startDate = movie.getStartDate();
		this.description = movie.getDescription();
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public boolean isAddAnotherMovie() {
		return addAnotherMovie;
	}

	public void setAddAnotherMovie(boolean addAnotherMovie) {
		this.addAnotherMovie = addAnotherMovie;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	public boolean isShowAddAnotherMovie() {
		return showAddAnotherMovie;
	}

	
	
}
