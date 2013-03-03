package de.codecentric.moviedatabase.config;

import javax.annotation.PostConstruct;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.DeserializationConfig;

public class JacksonObjectMapper extends ObjectMapper {
	
	
	@PostConstruct
	public void afterPropertiesSet() throws Exception {
		configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS, false);
	}
	
}
