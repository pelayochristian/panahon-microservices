package com.project.panahon.newsservice.controller;

import com.project.panahon.newsservice.news.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(path = "/news")
public class NewsController {

    private NewsService newsService;

    @Autowired
    public NewsController(NewsService newsService) {
        this.newsService = newsService;
    }

    /**
     * REST-End point for retrieving data for <code>newsapi.org</code>
     *
     * @param country {@link String}
     * @return {@link ResponseEntity}
     */
    @GetMapping(path = "/news-api/top-headlines", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> obtainNewsAPIHeadlines(@RequestParam String country,
                                                                      @RequestParam(required = false) String category) {
        return new ResponseEntity<>(newsService.newsAPI(country, category), HttpStatus.OK);
    }

    /**
     * REST-End Point to retrieve sources available from <code>newsapi.org</code>
     *
     * @param country String
     * @return ResponseEntity
     */
    @GetMapping(path = "/news-api/sources", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> obtainNewsApiSources(@RequestParam String country) {
        return new ResponseEntity<>(newsService.newsApiSources(country), HttpStatus.OK);
    }

    /**
     * REST-End Point to retrieve everything available from <code>newsapi.org</code>
     *
     * @param query     String
     * @param startDate String
     * @param endDate   String
     * @return ResponseEntity
     */
    @GetMapping(path = "/news-api/everything", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> obtainNewsApiEverything(@RequestParam String query,
                                                                       @RequestParam String startDate,
                                                                       @RequestParam String endDate) {
        return new ResponseEntity<>(newsService.newsApiEverything(query, startDate, endDate), HttpStatus.OK);
    }

    /**
     * REST-End point to retrieve list of News Categories.
     *
     * @return List
     */
    @GetMapping(path = "/news-api/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<String>> obtainCategoryList() {
        return new ResponseEntity<>(newsService.newsCategories(), HttpStatus.OK);
    }

    @GetMapping(path = "/test")
    public ResponseEntity<Map<String, Object>> test() {
        Map<String, Object> message = new HashMap<>();
        message.put("message", "test");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
