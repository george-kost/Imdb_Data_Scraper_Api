package com.example.imdb_data_scraper_api.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.example.imdb_data_scraper_api.service.MovieService;
import com.example.imdb_data_scraper_api.model.Movie;

import java.io.IOException;

@RestController
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping("/api/movie")
    public ResponseEntity<?> getMovie(@RequestParam String title) {
        try {
            Movie movie = movieService.getMovieByTitle(title);
            return ResponseEntity.ok(movie);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error retrieving movie data");
        }
    }
}