package de.codecentric.moviedatabase.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import de.codecentric.moviedatabase.controller.HtmlMovieController;
import de.codecentric.moviedatabase.controller.HtmlPartialMovieController;
import de.codecentric.moviedatabase.controller.HtmlPartialTagController;
import de.codecentric.moviedatabase.controller.JsonMovieController;
import de.codecentric.moviedatabase.controller.JsonTagController;
import de.codecentric.moviedatabase.controller.MovieResourceAssembler;
import de.codecentric.moviedatabase.controller.TagResourceAssembler;
import de.codecentric.moviedatabase.hateoas.ControllerLinkBuilderFactory;

@Configuration 
@Import(ServiceConfiguration.class)
public class ControllerConfiguration {
	
	@Autowired
	private ServiceConfiguration serviceConfiguration;
	
	@Bean
	public ControllerLinkBuilderFactory controllerLinkBuilderFactory(){
		return new ControllerLinkBuilderFactory();
	}
	
	@Bean
	public TagResourceAssembler tagResourceAssembler(){
		return new TagResourceAssembler(controllerLinkBuilderFactory());
	}
	
	@Bean
	public MovieResourceAssembler movieResourceAssembler(){
		return new MovieResourceAssembler(controllerLinkBuilderFactory());
	}
	
	@Bean
	public HtmlMovieController htmlMovieController(){
		return new HtmlMovieController(serviceConfiguration.movieService(), controllerLinkBuilderFactory(), tagResourceAssembler(), movieResourceAssembler());
	}

	@Bean
	public HtmlPartialMovieController MovieController(){
		return new HtmlPartialMovieController(serviceConfiguration.movieService(), controllerLinkBuilderFactory(), tagResourceAssembler(), movieResourceAssembler());
	}
	
	@Bean
	public HtmlPartialTagController htmlPartialTagController() {
		return new HtmlPartialTagController(serviceConfiguration.movieService(), tagResourceAssembler());
	}

	@Bean
	public JsonMovieController jsonMovieController() {
		return new JsonMovieController(serviceConfiguration.movieService(), movieResourceAssembler(), tagResourceAssembler());
	}
	
	@Bean
	public JsonTagController jsonTagController() {
		return new JsonTagController(serviceConfiguration.movieService(), tagResourceAssembler());
	}

}
