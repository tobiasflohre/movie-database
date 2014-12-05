package de.codecentric.moviedatabase.actors.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class Actor {
	
	private UUID id;
	private String firstname;
	private String lastname;
	private Date birthDate;
	private String biography;
	private Set<UUID> movieIds = new HashSet<>();

	public Actor() {
		// pass
	}
	
	public Actor(UUID id, String firstname, String lastname, Date birthDate,
			String biography) {
		super();
		this.id = id;
		this.firstname = firstname;
		this.lastname = lastname;
		this.birthDate = birthDate;
		this.biography = biography;
	}

	public Actor(String firstname, String lastname, Date birthDate,
			String biography) {
		this(UUID.randomUUID(), firstname, lastname, birthDate, biography);
	}

	public UUID getId() {
		return id;
	}

	public String getFirstname() {
		return firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public String getBiography() {
		return biography;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public Set<UUID> getMovieIds() {
		return movieIds;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((biography == null) ? 0 : biography.hashCode());
		result = prime * result
				+ ((birthDate == null) ? 0 : birthDate.hashCode());
		result = prime * result
				+ ((firstname == null) ? 0 : firstname.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result
				+ ((lastname == null) ? 0 : lastname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Actor other = (Actor) obj;
		if (biography == null) {
			if (other.biography != null)
				return false;
		} else if (!biography.equals(other.biography))
			return false;
		if (birthDate == null) {
			if (other.birthDate != null)
				return false;
		} else if (!birthDate.equals(other.birthDate))
			return false;
		if (firstname == null) {
			if (other.firstname != null)
				return false;
		} else if (!firstname.equals(other.firstname))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (lastname == null) {
			if (other.lastname != null)
				return false;
		} else if (!lastname.equals(other.lastname))
			return false;
		return true;
	}


}
