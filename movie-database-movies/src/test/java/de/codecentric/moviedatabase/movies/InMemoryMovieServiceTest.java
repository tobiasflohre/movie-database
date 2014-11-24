package de.codecentric.moviedatabase.movies;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Test;

import de.codecentric.moviedatabase.movies.domain.Movie;
import de.codecentric.moviedatabase.movies.domain.Tag;
import de.codecentric.moviedatabase.movies.service.InMemoryMovieService;

public class InMemoryMovieServiceTest {

	@Test
	public void testDeleteTags(){
		InMemoryMovieService movieService = new InMemoryMovieService();
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
