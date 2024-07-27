package com.example.imdb_data_scraper_api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.imdb_data_scraper_api.model.Movie;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    Movie findByTitle(String title);
}
