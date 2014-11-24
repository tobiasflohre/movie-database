package de.codecentric.moviedatabase.navigation.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration 
@Import(ControllerConfiguration.class)
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {
	
}
