package de.codecentric.moviedatabase.shop.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.codecentric.moviedatabase.shop.event.MovieReceiver;
import de.codecentric.moviedatabase.shop.repositories.MovieRepository;

@Configuration
public class ApplicationConfiguration {
	
	@Autowired
	private MovieRepository movieRepository;
	@Autowired
	private ObjectMapper objectMapper;
	
	@Bean
	public MovieReceiver receiver(){
		return new MovieReceiver(movieRepository, objectMapper);
	}

	@Bean
	public RedisMessageListenerContainer container(RedisConnectionFactory connectionFactory,
			MessageListenerAdapter listenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.addMessageListener(listenerAdapter, new PatternTopic("movieChannel"));
		return container;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(MovieReceiver receiver) {
		return new MessageListenerAdapter(receiver, "receive");
	}

}
