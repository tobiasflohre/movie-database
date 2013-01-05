package de.codecentric.moviedatabase.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration 
@EnableWebMvc
@Import({ViewConfiguration.class, ControllerConfiguration.class})
public class ApplicationConfiguration extends WebMvcConfigurerAdapter {
	
	// Maps resources path to webapp/resources
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations("/resources/");
	}
	
	@Override
	public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.add(jsonHttpMessageConverter());
	}

	@Bean
	public JacksonObjectMapper jacksonObjectMapper() {
		return new JacksonObjectMapper();
	}
	
	@Bean
	public MappingJacksonHttpMessageConverter jsonHttpMessageConverter() {
		MappingJacksonHttpMessageConverter converter = new MappingJacksonHttpMessageConverter();
		converter.setObjectMapper(jacksonObjectMapper());
		return converter;
	}
}
