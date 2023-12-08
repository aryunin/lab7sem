package com.example.demo.repository;

import com.example.demo.entity.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface MovieRepository extends CrudRepository<Movie, UUID> {
}
