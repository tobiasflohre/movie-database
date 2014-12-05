package de.codecentric.moviedatabase.actors.controller;

import static de.codecentric.roca.core.LinkBuilder.linkTo;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.codecentric.moviedatabase.actors.domain.Actor;
import de.codecentric.moviedatabase.actors.exception.ResourceNotFoundException;
import de.codecentric.moviedatabase.actors.model.ActorForm;
import de.codecentric.moviedatabase.actors.service.ActorService;
import de.codecentric.roca.core.AbstractResourceAssembler;
import de.codecentric.roca.core.Link;
import de.codecentric.roca.core.LinkBuilder;
import de.codecentric.roca.core.Resource;

@RequestMapping(value = "/actors", headers={"X-Requested-With!=XMLHttpRequest", "Accept=text/html"})
public class ActorController {
	
	private String navigationBaseUrl;
	private ActorService actorService;
	private AbstractResourceAssembler<Actor, Resource<Actor>> actorResourceAssembler;
	
	public ActorController(ActorService actorService,
			AbstractResourceAssembler<Actor, Resource<Actor>> actorResourceAssembler, String navigationBaseUrl) {
		this.navigationBaseUrl = navigationBaseUrl;
		this.actorService = actorService;
		this.actorResourceAssembler = actorResourceAssembler;
	}

	//################### side- and searchbar data ###########################
	
	@ModelAttribute("linkNewActor")
	public Link getLinkNewActor(){
		return linkTo(ActorPathFragment.ACTORS).path(ActorPathFragment.NEW).withRel(ActorRelation.NEW);
	}
	
	@ModelAttribute("linkNavigation")
	public Link getLinkNavigation(@RequestParam(required = false) String searchString){
		Link searchLink = linkTo(ActorPathFragment.ACTORS).withRel(ActorRelation.SEARCH);
		LinkBuilder navigationLinkBuilder = linkTo(navigationBaseUrl).path(ActorPathFragment.NAVIGATION).requestParam(ActorRequestParameter.SEARCH_URL, searchLink.getHref())
				.requestParam(ActorRequestParameter.ACTIVE, "actors");
		if (searchString != null){
			return navigationLinkBuilder.requestParam(ActorRequestParameter.SEARCH_STRING, searchString).withRel(ActorRelation.NAVIGATION);
		} else {
			return navigationLinkBuilder.withRel(ActorRelation.NAVIGATION);
		}
	}

	//################## actors #################################################
	
	@RequestMapping(method = RequestMethod.GET)
	public String getActors(Model model, @RequestParam(required = false) String searchString) {
		Set<String> searchWords = new HashSet<String>();
		Set<UUID> movieIds = new HashSet<>();
		ActorsUtil.convertSearchStringToSearchWords(searchString, searchWords, movieIds);
		List<Actor> actors = actorService.findActorBySearchStringAndMovieIds(searchWords, movieIds);
		List<Resource<Actor>> resourceActors = actorResourceAssembler.toResource(actors);
		model.addAttribute("actors", resourceActors);
		model.addAttribute("searchString", searchString);
		return "actor/actors";
	}

	@RequestMapping(value = "/new", method = RequestMethod.GET)
	public String getCreateActor(Model model) {
		model.addAttribute("actorForm", new ActorForm());
		model.addAttribute("actionLink", linkTo(ActorPathFragment.ACTORS).withRel(ActorRelation.SELF));
		model.addAttribute("cancelLink", linkTo(ActorPathFragment.ACTORS).withRel(ActorRelation.SELF));
		return "actor/actor_edit";
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public String createActor(ActorForm actorForm) {
		Actor actor = new Actor(actorForm.getFirstname(), actorForm.getLastname(), actorForm.getBirthDate(), actorForm.getBiography());
		actorService.createActor(actor);
		if (actorForm.isAddAnotherActor()){
			return "redirect:/actors/new";
		}
		return "redirect:/actors";
	}
	
	//########################### actor #####################################################
	
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public String getActor(@PathVariable UUID id, Model model){
		model.addAttribute("actor", actorResourceAssembler.toResource(actorService.findActorById(id)));
		return "actor/actor";
	}
	
	@RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
	public String getEditActor(@PathVariable UUID id, Model model) {
		Actor actor = actorService.findActorById(id);
		model.addAttribute("actorForm", new ActorForm(actor));
		Resource<Actor> resourceActor = actorResourceAssembler.toResource(actor);
		model.addAttribute("actionLink", resourceActor.getLink(ActorRelation.SELF.getName()));
		model.addAttribute("cancelLink", resourceActor.getLink(ActorRelation.SELF.getName()));
		return "actor/actor_edit";
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public String editActor(@PathVariable UUID id, ActorForm actorForm) {
		Actor actor = actorService.findActorById(id);
		actor.setFirstname(actorForm.getFirstname());
		actor.setLastname(actorForm.getLastname());
		actor.setBiography(actorForm.getBiography());
		actor.setBirthDate(actorForm.getBirthDate());
		actorService.updateActor(actor);
		return "redirect:/actors/{id}";
	}
	
	//################### exception handling ###############################
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<String> handle(ResourceNotFoundException e) {
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.NOT_FOUND);
	}

}
