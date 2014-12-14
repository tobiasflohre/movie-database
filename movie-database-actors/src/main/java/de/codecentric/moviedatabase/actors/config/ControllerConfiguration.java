package de.codecentric.moviedatabase.actors.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import de.codecentric.moviedatabase.actors.controller.ActorController;
import de.codecentric.moviedatabase.actors.controller.ActorResourceAssembler;
import de.codecentric.moviedatabase.actors.controller.PartialActorController;

@Configuration 
@Import(ServiceConfiguration.class)
public class ControllerConfiguration {
	
	@Value("${moviedatabase.navigation.url.base}")
	private String navigationBaseUrl;
	
	@Value("${server.context-path}")
	private String serverContextPath;
	
	@Autowired
	private ServiceConfiguration serviceConfiguration;
	
	@Bean
	public ActorResourceAssembler actorResourceAssembler(){
		return new ActorResourceAssembler();
	}
	
	@Bean
	public ActorController actorController(){
		return new ActorController(serviceConfiguration.actorService(), serverContextPath, actorResourceAssembler(), navigationBaseUrl);
	}

	@Bean
	public PartialActorController partialActorController(){
		return new PartialActorController(serviceConfiguration.actorService(), serverContextPath, actorResourceAssembler(), navigationBaseUrl);
	}

}
