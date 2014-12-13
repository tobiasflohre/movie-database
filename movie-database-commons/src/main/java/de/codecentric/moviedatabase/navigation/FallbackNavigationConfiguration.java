package de.codecentric.moviedatabase.navigation;

import java.io.InputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration;
import org.springframework.boot.autoconfigure.thymeleaf.ThymeleafProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.TemplateProcessingParameters;
import org.thymeleaf.resourceresolver.IResourceResolver;
import org.thymeleaf.spring4.resourceresolver.SpringResourceResourceResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;
import org.thymeleaf.templateresolver.TemplateResolver;

@Configuration 
public class FallbackNavigationConfiguration {
	
	@Autowired
	private ThymeleafProperties properties;
	
	@Autowired
	private SpringResourceResourceResolver springResourceResourceResolver;

	/**
	 * This bean gets picked up automatically by {@link ThymeleafAutoConfiguration}.
	 */
	@Bean
	public ITemplateResolver navigationTemplateResolver() {
		TemplateResolver resolver = new TemplateResolver();
		resolver.setOrder(30);
		resolver.setResourceResolver(navigationResourceResolver());
		resolver.setPrefix(this.properties.getPrefix());
		resolver.setSuffix(this.properties.getSuffix());
		resolver.setTemplateMode(this.properties.getMode());
		resolver.setCharacterEncoding(this.properties.getEncoding());
		resolver.setCacheable(this.properties.isCache());
		return resolver;
	}
	
	@Bean
	public IResourceResolver navigationResourceResolver(){
		return new IResourceResolver() {
			
			@Override
			public InputStream getResourceAsStream(
					TemplateProcessingParameters templateProcessingParameters,
					String resourceName) {
				if (resourceName.contains("navigation")){
					return springResourceResourceResolver.getResourceAsStream(templateProcessingParameters, "classpath:templates/navigation.html");
				} else {
					return null;
				}
			}
			
			@Override
			public String getName() {
				return "navigationResourceResolver";
			}
		};
	}


}
