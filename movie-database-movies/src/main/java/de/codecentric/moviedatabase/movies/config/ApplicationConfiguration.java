package de.codecentric.moviedatabase.movies.config;

import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.templateresolver.UrlTemplateResolver;

import de.codecentric.roca.cors.CorsFilter;

@Configuration 
@Import(ControllerConfiguration.class)
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {
	
	/**
	 * This bean gets picked up automatically by {@link ThymeleafAutoConfiguration}.
	 */
	@Bean
	public UrlTemplateResolver urlTemplateResolver(){
		UrlTemplateResolver urlTemplateResolver = new UrlTemplateResolver();
		urlTemplateResolver.setOrder(20);
		return urlTemplateResolver;
	}
	
	@Bean
	public CorsFilter corsFilter(){
		return new CorsFilter();
	}
	
}
