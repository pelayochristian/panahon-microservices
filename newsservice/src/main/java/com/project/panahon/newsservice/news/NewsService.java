package com.project.panahon.newsservice.news;

import java.util.Map;

/**
 * Interface for the NewsService.
 *
 * @author christian
 */
public interface NewsService {
    Map<String, Object> newsAPI(String country);

    Map<String, Object> newsApiSources(String country);

    Map<String, Object> newsApiEverything(String query, String startDate, String endDate);
}
