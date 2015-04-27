package de.codecentric.moviedatabase.shop.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.UUID;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.codecentric.moviedatabase.shop.domain.Movie;
import de.codecentric.moviedatabase.shop.repositories.MovieRepository;
import org.junit.Before;
import org.junit.Test;

public class ShopRestControllerTest {

	private static final UUID TEST_UUID = UUID.randomUUID();

	private ShopRestController controller;
	private MovieRepository movieRepository;
	private ObjectMapper objectMapper;

	@Before
	public void setUp() throws Exception {
		movieRepository = mock(MovieRepository.class);
		objectMapper = mock(ObjectMapper.class);
		controller = new ShopRestController(movieRepository, "test-base-url", objectMapper);
	}

	@Test
	public void shouldSetUrlsOnMovies() throws Exception {
		when(movieRepository.findOne(TEST_UUID)).thenReturn(new Movie());

		Movie movie = controller.getMovie(TEST_UUID);

		assertThat(movie.getMovieUrl(), equalTo("test-base-url/movies/" + TEST_UUID));
	}

	@Test
	public void shouldUpdateMovies() throws Exception {
		String movieString = "Some content";
		when(objectMapper.readValue(movieString, Movie.class)).thenReturn(newMovie(20, 12.99));

		Movie movieFromRepository = newMovie(5, 9.99);
		when(movieRepository.findOne(TEST_UUID)).thenReturn(movieFromRepository);
		
		controller.updateMovie(TEST_UUID, movieString);

		assertThat(movieFromRepository.getQuantity(), equalTo(20));
		assertThat(movieFromRepository.getPrice(), equalTo(BigDecimal.valueOf(12.99)));
	}
	
	private static Movie newMovie(int quantity, double price) {
		Movie movie = new Movie();
		movie.setQuantity(quantity);
		movie.setPrice(BigDecimal.valueOf(price));
		return movie;
	}
}