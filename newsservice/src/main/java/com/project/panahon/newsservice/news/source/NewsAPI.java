package com.project.panahon.newsservice.news.source;

import com.project.panahon.newsservice.news.factory.News;
import org.springframework.http.ResponseEntity;

import java.util.*;

/**
 * This abstract class extends {@link News}. This <br>
 * is use to parse the incoming response came from the <br>
 * source.
 *
 * @author christian
 * @since 2020-05-11
 */
public class NewsAPI extends News {

    /**
     * Override method form {@link News} abstract class.
     *
     * @param newsResponse {@link ResponseEntity}
     * @return {@link Map}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> parseNewsData(ResponseEntity<Object> newsResponse, String jsonAttribute) {
        Map<String, Object> newsMap = new HashMap<>();

        // Get the list list of articles from the news response.
        List<LinkedHashMap<String, Object>> bodyListResponse = (List<LinkedHashMap<String, Object>>)
                ((LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap<String, Object>>>)
                        Objects.requireNonNull(newsResponse.getBody())).get(jsonAttribute);

        newsMap.put(this.getClass().getSimpleName(), bodyListResponse);

        return newsMap;
    }
}
