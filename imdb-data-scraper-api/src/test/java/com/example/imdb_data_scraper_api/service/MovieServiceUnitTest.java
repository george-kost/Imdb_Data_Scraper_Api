//package com.example.imdb_data_scraper_api.service;
//
//import com.example.imdb_data_scraper_api.dto.ImdbApiResponse;
//import com.example.imdb_data_scraper_api.model.Movie;
//import com.example.imdb_data_scraper_api.repository.MovieRepository;
//import com.example.imdb_data_scraper_api.repository.RequestLogRepository;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import okhttp3.OkHttpClient;
//import okhttp3.Protocol;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//
//import java.io.IOException;
//import java.util.Collections;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//public class MovieServiceUnitTest {
//
//    @InjectMocks
//    private MovieService movieService;
//
//    @Mock
//    private MovieRepository movieRepository;
//
//    @Mock
//    private RequestLogRepository requestLogRepository;
//
//    @Mock
//    private OkHttpClient httpClient;
//
//    @Mock
//    private ObjectMapper objectMapper;
//
//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }
//
//    @Test
//    public void testGetMovieByTitleFromDatabase() throws IOException {
//        Movie mockMovie = new Movie();
//        mockMovie.setTitle("Inception");
//
//        when(movieRepository.findByTitle("Inception")).thenReturn(mockMovie);
//
//        Movie movie = movieService.getMovieByTitle("Inception");
//
//        assertNotNull(movie);
//        assertEquals("Inception", movie.getTitle());
//        verify(requestLogRepository, times(1)).save(any());
//    }
//
//    @Test
//    public void testGetMovieByTitleFromApi() throws Exception {
//        when(movieRepository.findByTitle("NonExistentMovie")).thenReturn(null);
//
//        // Creating a real Response object
//        String jsonResponse = "{jsonResponse}";
//        ResponseBody responseBody = ResponseBody.create(jsonResponse, okhttp3.MediaType.parse("application/json"));
//        Response mockResponse = new Response.Builder()
//                .request(new Request.Builder().url("http://localhost").build())
//                .protocol(Protocol.HTTP_1_1)
//                .code(200)
//                .message("OK")
//                .body(responseBody)
//                .build();
//
//        ImdbApiResponse mockApiResponse = mock(ImdbApiResponse.class);
//        ImdbApiResponse.Result mockResult = mock(ImdbApiResponse.Result.class);
//
//        when(httpClient.newCall(any(Request.class)).execute()).thenReturn(mockResponse);
//        when(objectMapper.readValue(jsonResponse, ImdbApiResponse.class)).thenReturn(mockApiResponse);
//        when(mockApiResponse.getResults()).thenReturn(Collections.singletonList(mockResult));
//
//        // Assuming that mockResult.getTitle() and other methods return valid values
//        when(mockResult.getTitle()).thenReturn("NonExistentMovie");
//        when(mockResult.getYear()).thenReturn(2010);
//        when(mockResult.getRunningTimeInMinutes()).thenReturn(120);
//        when(mockResult.getPrincipals()).thenReturn(Collections.emptyList());
//
//        Movie movie = movieService.getMovieByTitle("NonExistentMovie");
//
//        assertNotNull(movie);
//        verify(movieRepository, times(1)).save(any(Movie.class));
//        verify(requestLogRepository, times(1)).save(any());
//    }
//}
