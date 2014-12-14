package de.codecentric.moviedatabase.actors.model;

import java.util.Date;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.web.bind.annotation.RequestMethod;

import de.codecentric.moviedatabase.actors.domain.Actor;

public class ActorForm {
	
	// display data
	private String pageTitle;
	private RequestMethod requestMethod;
	private boolean showAddAnotherActor;
	
	// form data
	private String firstname;
	private String lastname;
	private String biography;
	@DateTimeFormat(iso=ISO.DATE)
	private Date birthDate;
	private boolean addAnotherActor = false;
	private UUID movieId;
	
	public ActorForm(){
		pageTitle = "Add an actor";
		requestMethod = RequestMethod.POST;
		showAddAnotherActor = true;
	}

	public ActorForm(Actor actor){
		pageTitle = "Edit an actor";
		requestMethod = RequestMethod.PUT;
		showAddAnotherActor = false;
		fillDataFromActor(actor);
	}
	
	private void fillDataFromActor(Actor actor){
		this.firstname = actor.getFirstname();
		this.lastname = actor.getLastname();
		this.birthDate = actor.getBirthDate();
		this.biography = actor.getBiography();
	}

	public UUID getMovieId() {
		return movieId;
	}

	public void setMovieId(UUID movieId) {
		this.movieId = movieId;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public String getPageTitle() {
		return pageTitle;
	}

	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	public void setShowAddAnotherActor(boolean showAddAnotherActor) {
		this.showAddAnotherActor = showAddAnotherActor;
	}

	public boolean isShowAddAnotherActor() {
		return showAddAnotherActor;
	}

	public boolean isAddAnotherActor() {
		return addAnotherActor;
	}
	
}
