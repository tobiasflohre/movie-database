package de.codecentric.moviedatabase.actors.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import de.codecentric.moviedatabase.actors.controller.ActorController;
import de.codecentric.moviedatabase.actors.controller.ActorResourceAssembler;

@Configuration 
@Import(ServiceConfiguration.class)
public class ControllerConfiguration {
	
	@Value("${moviedatabase.navigation.url.base}")
	private String navigationBaseUrl;
	
	@Autowired
	private ServiceConfiguration serviceConfiguration;
	
	@Bean
	public ActorResourceAssembler actorResourceAssembler(){
		return new ActorResourceAssembler();
	}
	
	@Bean
	public ActorController actorController(){
		return new ActorController(serviceConfiguration.actorService(), actorResourceAssembler(), navigationBaseUrl);
	}

}
