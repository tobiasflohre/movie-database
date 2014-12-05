package de.codecentric.moviedatabase.actors.service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import de.codecentric.moviedatabase.actors.domain.Actor;

public interface ActorService {
	
	public void createActor(Actor actor);
	
	public void updateActor(Actor actor);
	
	public void deleteActor(UUID id);
	
	public Actor findActorById(UUID id);
	
	public List<Actor> findActorBySearchStringAndMovieIds(Set<String> searchWords, Set<UUID> movieIds);

}
