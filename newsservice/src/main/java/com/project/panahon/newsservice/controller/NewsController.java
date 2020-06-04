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
    public ResponseEntity<Map<String, Object>> obtainNewsAPI(@RequestParam String country) {
        return new ResponseEntity<>(newsService.newsAPI(country), HttpStatus.OK);
    }

    @GetMapping(path = "/test")
    public ResponseEntity<Map<String, Object>> test() {
        Map<String, Object> message = new HashMap<>();
        message.put("message", "test");
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
}
