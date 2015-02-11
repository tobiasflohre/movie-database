package de.codecentric.moviedatabase.navigation.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import de.codecentric.moviedatabase.navigation.controller.NavigationController;
import de.codecentric.roca.core.LinkResolver;

@Configuration 
public class ControllerConfiguration {
	
	@Autowired
	private Environment env;
	
	@Value("${moviedatabase.movies.url.base}")
	private String moviesBaseUrl;
	
	@Value("${moviedatabase.contentdelivery.url.base}")
	private String contentDeliveryBaseUrl;
	
	@Bean
	public NavigationController navigationController(){
		return new NavigationController(moviesBaseUrl,contentDeliveryBaseUrl, linkResolver());
	}
	
	@Bean
	public LinkResolver linkResolver(){
		return new LinkResolver(env);
	}

}
