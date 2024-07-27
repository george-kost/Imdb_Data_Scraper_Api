package com.example.imdb_data_scraper_api.service;

import com.example.imdb_data_scraper_api.dto.ImdbApiResponse;
import com.example.imdb_data_scraper_api.model.Movie;
import com.example.imdb_data_scraper_api.model.RequestLog;
import com.example.imdb_data_scraper_api.repository.MovieRepository;
import com.example.imdb_data_scraper_api.repository.RequestLogRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class MovieService {

    @Value("${imdb.api.url}")
    private String imdbApiUrl;

    @Value("${imdb.api.key}")
    private String imdbApiKey;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private RequestLogRepository requestLogRepository;

    private final OkHttpClient httpClient = new OkHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    public Movie getMovieByTitle(String title) throws IOException {
        // Check database first
        Movie movie = movieRepository.findByTitle(title);
        if (movie != null) {
            logRequest(title, movie.toString(), 200);
            return movie;
        }

        // Call IMDB API
        Request request = new Request.Builder()
                .url(imdbApiUrl + "/title/find?q=" + title)
                .addHeader("X-RapidAPI-Key", imdbApiKey)
                .build();

        Response response = httpClient.newCall(request).execute();
        if (!response.isSuccessful()) {
            logRequest(title, response.body().string(), response.code());
            throw new IOException("Unexpected code " + response);
        }

        // Parse response and map to Movie object
        String responseBody = response.body().string();
        ImdbApiResponse imdbApiResponse = objectMapper.readValue(responseBody, ImdbApiResponse.class);
        List<ImdbApiResponse.Result> results = imdbApiResponse.getResults();

        if (results == null || results.isEmpty()) {
            throw new IOException("No results found for title: " + title);
        }

        ImdbApiResponse.Result result = results.get(0);
        movie = new Movie();
        movie.setTitle(result.getTitle());
        movie.setYear(result.getYear());
        movie.setLeadActor(result.getPrincipals().get(0).getName());
        movie.setRunningTimeInMinutes(result.getRunningTimeInMinutes());

        // Assuming the first principal is the lead actor
        if (result.getPrincipals() != null && !result.getPrincipals().isEmpty()) {
            movie.setLeadActor(result.getPrincipals().get(0).getName());
        }

        // Save to database
        movieRepository.save(movie);

        // Log the request
        logRequest(title, responseBody, response.code());

        return movie;
    }

    private void logRequest(String requestData, String responseData, int httpStatusCode) {
        RequestLog log = new RequestLog();
        log.setUsername(getCurrentUsername());
        log.setRequestData(requestData);
        log.setResponseData(responseData);
        log.setHttpStatusCode(httpStatusCode);
        log.setTimestamp(LocalDateTime.now());

        requestLogRepository.save(log);
    }

    private String getCurrentUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return ((UserDetails) principal).getUsername();
        } else {
            return principal.toString();
        }
    }
}