package de.codecentric.moviedatabase.config;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;

public class JacksonObjectMapper extends ObjectMapper {
	
	
	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
	}
	
}
