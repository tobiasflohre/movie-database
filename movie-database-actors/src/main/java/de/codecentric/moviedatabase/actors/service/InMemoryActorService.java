package de.codecentric.moviedatabase.actors.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.util.Assert;

import de.codecentric.moviedatabase.actors.domain.Actor;
import de.codecentric.moviedatabase.actors.exception.ResourceNotFoundException;

public class InMemoryActorService implements ActorService {
	
	private Map<UUID, Actor> idToActorMap = new ConcurrentHashMap<UUID, Actor>();
	
	public InMemoryActorService(){
		// let the dummy Actor have always the same ID to test it easily via command line tools / unit tests
		Actor actor = new Actor(UUID.fromString("240342ea-84c8-415f-b1d5-8e4376191aeb"),
				"Harrison","Ford", new Date(),"Ewiger Nebendarsteller");
		actor.getMovieIds().add(UUID.fromString("240342ea-84c8-415f-b1d5-8e4376191aeb"));
		idToActorMap.put(actor.getId(), actor);
	}
	
	@Override
	public void createActor(Actor actor) {
		Assert.notNull(actor);
		idToActorMap.put(actor.getId(), actor);
	}

	@Override
	public void updateActor(Actor actor) {
		// Tags may not be added or removed by this method
		Assert.notNull(actor);
		idToActorMap.put(actor.getId(), actor);
	}

	@Override
	public void deleteActor(UUID id) {
		Assert.notNull(id);
		idToActorMap.remove(id);
	}

	@Override
	public Actor findActorById(UUID id) {
		Assert.notNull(id);
		Actor actor = idToActorMap.get(id);
		if (actor == null){
			throw new ResourceNotFoundException("Actor not found.");
		}
		return actor;
	}

	@Override
	public List<Actor> findActorBySearchStringAndMovieIds(Set<String> searchWords, Set<UUID> movieIds) {
		Set<Actor> actorsFoundByMovieId = new HashSet<>();
		List<Actor> searchResult = new ArrayList<>();
		if (movieIds == null || movieIds.isEmpty()){
			actorsFoundByMovieId = new HashSet<>(idToActorMap.values());
		} else {
			for (UUID movieId: movieIds){
				for (Actor actor: idToActorMap.values()){
					if (actor.getMovieIds().contains(movieId)){
						actorsFoundByMovieId.add(actor);
					}
				}
			}
		}
		searchResult.addAll(actorsFoundByMovieId);
		if (searchWords != null && !searchWords.isEmpty()){
			for (String searchWord: searchWords){
				for (Iterator<Actor> it = searchResult.iterator();it.hasNext();){
					Actor actor = it.next();
					if (!(actor.getFirstname().toLowerCase().contains(searchWord.toLowerCase())) && !(actor.getLastname().toLowerCase().contains(searchWord.toLowerCase()))
							&& !(actor.getBiography() != null && actor.getBiography().toLowerCase().contains(searchWord.toLowerCase()))){
						it.remove();
					}
				}
			}
		} 
		return searchResult;
	}

}
