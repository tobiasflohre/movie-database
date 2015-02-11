package de.codecentric.moviedatabase.contentdelivery.config;

import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.MimeMappings;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CustomMimeMapper implements EmbeddedServletContainerCustomizer {
	  @Override
	  public void customize(ConfigurableEmbeddedServletContainer container) {
	    MimeMappings mappings = new MimeMappings(MimeMappings.DEFAULT);
	    mappings.add("json", "application/json");
	    container.setMimeMappings(mappings);
	  }
	}