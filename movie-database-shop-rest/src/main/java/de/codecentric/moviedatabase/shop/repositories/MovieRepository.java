package de.codecentric.moviedatabase.shop.repositories;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.codecentric.moviedatabase.shop.domain.Movie;

public interface MovieRepository extends CrudRepository<Movie, UUID> {
}
