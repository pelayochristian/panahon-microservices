package com.project.panahon.newsservice.news;

import com.project.panahon.newsservice.cache.RedisCacheManager;
import com.project.panahon.newsservice.config.ServiceConfig;
import com.project.panahon.newsservice.news.factory.News;
import com.project.panahon.newsservice.news.factory.NewsFactory;
import com.project.panahon.newsservice.news.factory.NewsSourceType;
import com.project.panahon.newsservice.news.source.NewsAPI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * Service class that implements method form {@link NewsService} <br>
 * interface. This will handle the scraping of data from different <br>
 * sources.
 *
 * @author christian
 * @since 2020-05-11
 */
@Service
public class NewsServiceImplementation implements NewsService {

    private RedisCacheManager redisCacheManager;
    private RestTemplate restTemplate;
    private ServiceConfig serviceConfig;
    private static Logger logger = LoggerFactory.getLogger(NewsServiceImplementation.class);
    private int newsAPiCounter = 1;

    @Autowired
    public NewsServiceImplementation(RedisCacheManager redisCacheManager,
                                     RestTemplate restTemplate,
                                     ServiceConfig serviceConfig) {
        this.redisCacheManager = redisCacheManager;
        this.serviceConfig = serviceConfig;
        this.restTemplate = restTemplate;
    }

    /**
     * Service function use for scraping data form <tt>https://newsapi.org/</tt>.
     *
     * @param country {@link String}
     * @return {@link Map}
     */
    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> newsAPI(String country) {

        // Get the cache
        Map<String, Object> cache = redisCacheManager.obtainCache(country, NewsAPI.class.getSimpleName(), Map.class);

        // Check if exist
        if (cache != null) {
            logger.info("Data is already in the cache.");
            return cache;
        } else { // Else call the API to fetch data.
            return callNewsAPI(country, redisCacheManager);
        }
    }

    public Map<String, Object> callNewsAPI(String country, RedisCacheManager cacheManager) {
        newsAPiCounter++;

        // Response holder
        Map<String, Object> jsonResponse = new HashMap<>();

        // Construct URL for retrieving from API
        String url = String.format("http://newsapi.org/v2/top-headlines?country=%s&apiKey=%s", country, "5003cc84786d46838137513086f998cc");

        try {

            logger.info("Start Retrieving data from the source.");

            // Get the Response entity via rest-template.
            ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);

            // Get the instance for NEWS_API.
            News news = NewsFactory.getNewsDataParser(NewsSourceType.NEWS_API);

            logger.info("Data fetched.");

            // Get the filtered data.
            jsonResponse = news.parseNewsData(responseEntity);

            logger.info("Save the data to cache.");

            // Add to cache
            cacheManager.putCache(country, NewsAPI.class.getSimpleName(), jsonResponse);

            // Set expiration
            cacheManager.obtainExpire(country);
        } catch (Exception e) {
            switch (newsAPiCounter) {
                case 2:
                    break;
                default:
                    logger.error("Error in data retrieval. {}", e.getMessage());
                    jsonResponse.put(NewsAPI.class.getSimpleName(), e.toString());
                    break;
            }
        }
        return jsonResponse;
    }
}
