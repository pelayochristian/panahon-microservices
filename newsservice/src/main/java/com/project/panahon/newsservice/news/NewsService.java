package com.project.panahon.newsservice.news;

import com.project.panahon.newsservice.news.source.Category;

import java.util.List;
import java.util.Map;

/**
 * Interface for the NewsService.
 *
 * @author christian
 */
public interface NewsService {
    Map<String, Object> newsAPI(String country, String category);

    Map<String, Object> newsApiSources(String country);

    Map<String, Object> newsApiEverything(String query, String startDate, String endDate);

    List<String> newsCategories();
}
