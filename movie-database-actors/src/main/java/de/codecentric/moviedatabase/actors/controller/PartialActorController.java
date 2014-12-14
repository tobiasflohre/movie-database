package de.codecentric.moviedatabase.actors.controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.codecentric.moviedatabase.actors.service.ActorService;

@RequestMapping(value = "/actors", headers={"X-Requested-With=XMLHttpRequest"}, produces="text/html")
public class PartialActorController extends ActorController {

	public PartialActorController(ActorService actorService, String serverContextPath,
			ActorResourceAssembler actorResourceAssembler,
			String navigationBaseUrl) {
		super(actorService, serverContextPath, actorResourceAssembler, navigationBaseUrl);
	}

	@Override
	@RequestMapping(method = RequestMethod.GET)
	public String getActors(Model model, String searchString) {
		super.getActors(model, searchString);
		return "actor/partial/actors";
	}
	
}
