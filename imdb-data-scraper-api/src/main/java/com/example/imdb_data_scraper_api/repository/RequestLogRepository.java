package com.example.imdb_data_scraper_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.imdb_data_scraper_api.model.RequestLog;

public interface RequestLogRepository extends JpaRepository<RequestLog, Long> {
}
