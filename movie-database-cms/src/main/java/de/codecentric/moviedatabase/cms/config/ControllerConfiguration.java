package de.codecentric.moviedatabase.cms.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.codecentric.moviedatabase.cms.controller.CmsController;

@Configuration 
public class ControllerConfiguration {
	
	@Value("${moviedatabase.contentdelivery.url.base}")
	private String contentDeliveryBaseUrl;
	
	@Value("${moviedatabase.navigation.url.base}")
	private String navigationBaseUrl;
	
	@Bean
	public CmsController cmsController(){
		return new CmsController(contentDeliveryBaseUrl, navigationBaseUrl);
	}

}
