//package com.example.imdb_data_scraper_api.service;
//
//import com.example.imdb_data_scraper_api.ImdbDataScraperApiApplication;
//import com.example.imdb_data_scraper_api.model.Movie;
//import okhttp3.mockwebserver.MockResponse;
//import okhttp3.mockwebserver.MockWebServer;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.test.context.ActiveProfiles;
//
//import java.io.IOException;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest(classes = ImdbDataScraperApiApplication.class)
//@ActiveProfiles("test")
//public class MovieServiceIntegrationTest {
//
//    @Autowired
//    private MovieService movieService;
//
//    @Autowired
//    private JdbcTemplate jdbcTemplate;
//
//    @MockBean
//    private MockWebServer mockWebServer;
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        mockWebServer.start();
//    }
//
//    @AfterEach
//    public void tearDown() throws Exception {
//        mockWebServer.shutdown();
//    }
//
//    @Test
//    public void testFetchMovieDataNotFoundAndLogRequest() throws Exception {
//        mockWebServer.enqueue(new MockResponse().setResponseCode(404));
//
//        // Set the mock server URL
//        String mockUrl = mockWebServer.url("/").toString();
//        System.setProperty("imdb.api.url", mockUrl);
//
//        Exception exception = assertThrows(IOException.class, () -> {
//            movieService.getMovieByTitle("NonExistentMovie");
//        });
//
//        String expectedMessage = "Unexpected code";
//        String actualMessage = exception.getMessage();
//        assertTrue(actualMessage.contains(expectedMessage));
//
//        // Verify request log
//        String sql = "SELECT COUNT(*) FROM request_log WHERE username = ? AND request_data = ? AND http_status_code = ?";
//        int count = jdbcTemplate.queryForObject(sql, Integer.class, "admin", "NonExistentMovie", 404);
//        assertEquals(1, count);
//    }
//}
