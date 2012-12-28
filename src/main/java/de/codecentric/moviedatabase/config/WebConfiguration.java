package de.codecentric.moviedatabase.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring3.SpringTemplateEngine;
import org.thymeleaf.spring3.view.ThymeleafViewResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import de.codecentric.moviedatabase.controller.HtmlAjaxMovieController;
import de.codecentric.moviedatabase.controller.JsonMovieController;
import de.codecentric.moviedatabase.controller.JsonTagController;
import de.codecentric.moviedatabase.controller.HtmlMovieController;
import de.codecentric.moviedatabase.controller.MovieResourceAssembler;
import de.codecentric.moviedatabase.controller.TagResourceAssembler;
import de.codecentric.moviedatabase.hateoas.ControllerLinkBuilderFactory;
import de.codecentric.moviedatabase.service.InMemoryMovieService;
import de.codecentric.moviedatabase.service.MovieService;

@Configuration 
@EnableWebMvc
public class WebConfiguration extends WebMvcConfigurerAdapter {
	
	// Maps resources path to webapp/resources
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}

	@Bean 
	public ServletContextTemplateResolver templateResolver() {
		ServletContextTemplateResolver resolver = new ServletContextTemplateResolver();
		resolver.setPrefix("/WEB-INF/templates/");
		resolver.setSuffix(".html");
		resolver.setTemplateMode("HTML5");
		resolver.setOrder(1);
		return resolver;
	}
	
	@Bean 
	public SpringTemplateEngine templateEngine() {
		SpringTemplateEngine engine = new SpringTemplateEngine();
		engine.setTemplateResolver(templateResolver());
		return engine;
	}
	
	@Bean 
	public ThymeleafViewResolver thymeleafViewResolver() {
		ThymeleafViewResolver resolver = new ThymeleafViewResolver();
		resolver.setTemplateEngine(templateEngine());
		return resolver;
	}
	
	@Bean
	public MovieService movieService(){
		return new InMemoryMovieService();
	}
	
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
	public HtmlMovieController movieController(){
		return new HtmlMovieController(movieService(), controllerLinkBuilderFactory(), tagResourceAssembler(), movieResourceAssembler());
	}

	@Bean
	public HtmlAjaxMovieController ajaxMovieController(){
		return new HtmlAjaxMovieController(movieService(), controllerLinkBuilderFactory(), tagResourceAssembler(), movieResourceAssembler());
	}
	
	@Bean
	public JsonMovieController jsonMovieController() {
		return new JsonMovieController(movieService(), movieResourceAssembler(), tagResourceAssembler());
	}
	
	@Bean
	public JsonTagController jsonTagController() {
		return new JsonTagController(movieService(), tagResourceAssembler());
	}

}
