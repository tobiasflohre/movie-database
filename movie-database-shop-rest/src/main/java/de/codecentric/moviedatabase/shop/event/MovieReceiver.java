package de.codecentric.moviedatabase.shop.event;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import de.codecentric.moviedatabase.shop.domain.Movie;
import de.codecentric.moviedatabase.shop.repositories.MovieRepository;

public class MovieReceiver {
	
	private MovieRepository movieRepository;
	private ObjectMapper objectMapper;
	
    public MovieReceiver(MovieRepository movieRepository, ObjectMapper objectMapper) {
		super();
		this.movieRepository = movieRepository;
		this.objectMapper = objectMapper;
	}

	private static final Logger LOGGER = LoggerFactory.getLogger(MovieReceiver.class);
	
	public void receive(String message){
		if (LOGGER.isInfoEnabled()){
			LOGGER.info(message);
		}
		JsonNode jsonRoot = null;
	    try {
		    JsonFactory factory = objectMapper.getFactory();
		    JsonParser parser = factory.createParser(message);
			jsonRoot = objectMapper.readTree(parser);
		} catch (IOException e) {
			throw new RuntimeException("Problem with parsing message.",e);
		}
		MovieEventType eventType = MovieEventType.valueOf(jsonRoot.get("type").asText());
		UUID id = UUID.fromString(jsonRoot.get("movie").get("id").asText());
		String title = jsonRoot.get("movie").get("title").asText();
		String description = jsonRoot.get("movie").get("description").asText();
		switch (eventType) {
		case MOVIE_CHANGED:
			updateMovie(id,title,description);
			break;
		case MOVIE_CREATED:
			createMovie(id,title,description);
			break;
		default:
			break;
		}
	}

	private void updateMovie(UUID id, String title, String description) {
		Movie movie = movieRepository.findOne(id);
		if (movie != null){
			movie.setDescription(description);
			movie.setTitle(title);
			movieRepository.save(movie);
		} else {
			// should not happen, if it does it means we missed messages. Better create the entity now than throwing error.
			createMovie(id,title,description);
		}
	}

	private void createMovie(UUID id, String title, String description) {
		Movie movie = new Movie();
		movie.setId(id);
		movie.setDescription(description);
		movie.setTitle(title);
		movie.setQuantity(0);
		movie.setPrice(new BigDecimal(-1));
		movieRepository.save(movie);
	}

}
