package de.codecentric.moviedatabase.navigation.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.codecentric.moviedatabase.navigation.controller.NavigationController;

@Configuration 
public class ControllerConfiguration {
	
	@Value("${moviedatabase.movies.url.base}")
	private String moviesBaseUrl;
	
	@Bean
	public NavigationController navigationController(){
		return new NavigationController(moviesBaseUrl);
	}

}
