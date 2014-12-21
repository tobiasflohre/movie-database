package de.codecentric.moviedatabase.movies.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import de.codecentric.moviedatabase.movies.controller.MovieController;
import de.codecentric.moviedatabase.movies.controller.MovieResourceAssembler;
import de.codecentric.moviedatabase.movies.controller.PartialMovieController;
import de.codecentric.moviedatabase.movies.controller.PartialTagController;
import de.codecentric.moviedatabase.movies.controller.TagResourceAssembler;

@Configuration 
@Import(ServiceConfiguration.class)
public class ControllerConfiguration {
	
	@Value("${moviedatabase.navigation.url.base}")
	private String navigationBaseUrl;
	
	@Value("${moviedatabase.actors.url.base}")
	private String actorsBaseUrl;
	
	@Value("${server.context-path}")
	private String serverContextPath;
	
	@Autowired
	private ServiceConfiguration serviceConfiguration;
	
	@Bean
	public TagResourceAssembler tagResourceAssembler(){
		return new TagResourceAssembler();
	}
	
	@Bean
	public MovieResourceAssembler movieResourceAssembler(){
		return new MovieResourceAssembler(actorsBaseUrl, serverContextPath);
	}
	
	@Bean
	public MovieController movieController(){
		return new MovieController(serviceConfiguration.movieService(), tagResourceAssembler(), movieResourceAssembler(), navigationBaseUrl);
	}

	@Bean
	public PartialMovieController partialMovieController(){
		return new PartialMovieController(serviceConfiguration.movieService(), tagResourceAssembler(), movieResourceAssembler(),serverContextPath);
	}
	
	@Bean
	public PartialTagController partialTagController() {
		return new PartialTagController(serviceConfiguration.movieService(), tagResourceAssembler());
	}

}
