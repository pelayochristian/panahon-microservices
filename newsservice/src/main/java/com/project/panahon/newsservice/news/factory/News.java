package com.project.panahon.newsservice.news.factory;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * This abstract class will be the parent of all <br>
 * related news provider instance.
 *
 * @author christian
 * @since 2020-05-11
 */
public abstract class News {
    public abstract Map<String, Object> parseNewsData(ResponseEntity<Object> newsResponse);
}
