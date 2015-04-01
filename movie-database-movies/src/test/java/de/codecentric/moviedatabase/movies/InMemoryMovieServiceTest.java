package de.codecentric.moviedatabase.movies;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.core.StringRedisTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.codecentric.moviedatabase.movies.domain.Movie;
import de.codecentric.moviedatabase.movies.domain.Tag;
import de.codecentric.moviedatabase.movies.service.InMemoryMovieService;

public class InMemoryMovieServiceTest {
	
	private ObjectMapper objectMapper;
	private StringRedisTemplate redisTemplate;
	
	@Before
	public void setup(){
		objectMapper = mock(ObjectMapper.class);
		redisTemplate = mock(StringRedisTemplate.class);
	}

	@Test
	public void testDeleteTags(){
		InMemoryMovieService movieService = new InMemoryMovieService(redisTemplate, objectMapper);
		Set<Tag> tags = new HashSet<>();
		tags.add(new Tag("Science Fiction"));
		List<Movie> movies = movieService.findMovieByTagsAndSearchString(tags, null);
		assertThat(movies.size(),is(1));
		movieService.removeTagFromMovie(new Tag("Science Fiction"), movies.iterator().next().getId());
		Movie movie = movieService.findMovieById(movies.iterator().next().getId());
		assertThat(movie.getTags().size(),is(0));
		movies = movieService.findMovieByTagsAndSearchString(tags, null);
		assertThat(movies.size(),is(0));
	}
	
}
