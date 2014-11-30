package de.codecentric.moviedatabase.actors.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.codecentric.moviedatabase.actors.service.ActorService;
import de.codecentric.moviedatabase.actors.service.InMemoryActorService;

@Configuration 
public class ServiceConfiguration {
	
	@Bean
	public ActorService actorService(){
		return new InMemoryActorService();
	}
	
}
