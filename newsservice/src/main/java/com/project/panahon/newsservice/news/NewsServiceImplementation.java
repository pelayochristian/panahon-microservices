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
    private int newsAPISources = 1;
    private int newsAPIEverything = 1;
    private static final String NEWS_API_HEADLINE_ARTICLES = "articles";
    private static final String NEWS_API_SOURCES = "sources";


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
    @Override
    public Map<String, Object> newsAPI(String country) {

        // Get the cache
        Map<String, Object> cache = redisCacheManager.obtainCache(country, NewsAPI.class.getSimpleName()
                + "callNewsAPI", Map.class);

        // Check if exist
        if (cache != null) {
            logger.info("Data is already in the cache.");
            return cache;
        } else { // Else call the API to fetch data.
            return callNewsAPI(country, redisCacheManager);
        }
    }

    @Override
    public Map<String, Object> newsApiSources(String country) {
        // Get the cache
        Map<String, Object> cache = redisCacheManager.obtainCache(country, NewsAPI.class.getSimpleName()
                + "callNewsApiSources", Map.class);

        // Check if exist
        if (cache != null) {
            logger.info("Data is already in the cache.");
            return cache;
        } else { // Else call the API to fetch data.
            return callNewsApiSources(country, redisCacheManager);
        }
    }

    @Override
    public Map<String, Object> newsApiEverything(String query, String startDate, String endDate) {
        // Get the cache
        Map<String, Object> cache = redisCacheManager.obtainCache(query, NewsAPI.class.getSimpleName()
                + "newsApiEverything", Map.class);

        // Check if exist
        if (cache != null) {
            logger.info("Data is already in the cache.");
            return cache;
        } else { // Else call the API to fetch data.
            return callNewsApiEverything(query, startDate, endDate, redisCacheManager);
        }
    }

    /**
     * Method to query a data from the <code>newsapi.org</code> api everything.
     *
     * @param query        String
     * @param startDate    String
     * @param endDate      String
     * @param cacheManager Cache
     * @return Map
     */
    private Map<String, Object> callNewsApiEverything(String query, String startDate, String endDate, RedisCacheManager cacheManager) {
        newsAPIEverything++;

        Map<String, Object> jsonResponse = new HashMap<>();
        String url = String.format(serviceConfig.getNewsAPIEverything(), query, startDate, endDate, serviceConfig.getNewsAPIToken());

        try {
            logger.info("Start Retrieving data from {}", url);

            // Get the Response entity via rest-template.
            ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
            News news = NewsFactory.getNewsDataParser(NewsSourceType.NEWS_API);

            logger.info("Data fetched from {}", url);

            // Get the filtered data.
            jsonResponse = news.parseNewsData(responseEntity, NEWS_API_HEADLINE_ARTICLES);

            logger.info("Save the data to cache {}", url);

            cacheManager.putCache(query, NewsAPI.class.getSimpleName() + "callNewsApiSources", jsonResponse);
            cacheManager.obtainExpire(query);
        } catch (Exception e) {
            if (newsAPIEverything != 2) {
                logger.error("Error in data retrieval. {}", e.getMessage());
                jsonResponse.put(NewsAPI.class.getSimpleName(), e.toString());
            }
        }
        return jsonResponse;
    }

    /**
     * Service function to call the API and retrieve form <code>newsapi.org</code> sources.
     *
     * @param country      String
     * @param cacheManager cache
     * @return Map
     */
    public Map<String, Object> callNewsApiSources(String country, RedisCacheManager cacheManager) {
        newsAPISources++;

        Map<String, Object> jsonResponse = new HashMap<>();
        String url = String.format(serviceConfig.getNewsAPISources(), country, serviceConfig.getNewsAPIToken());

        try {
            logger.info("Start Retrieving data from {}", url);

            // Get the Response entity via rest-template.
            ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
            News news = NewsFactory.getNewsDataParser(NewsSourceType.NEWS_API);

            logger.info("Data fetched from {}", url);

            // Get the filtered data.
            jsonResponse = news.parseNewsData(responseEntity, NEWS_API_SOURCES);

            logger.info("Save the data to cache.");

            cacheManager.putCache(country, NewsAPI.class.getSimpleName() + "callNewsApiSources", jsonResponse);
            cacheManager.obtainExpire(country);
        } catch (Exception e) {
            if (newsAPISources != 2) {
                logger.error("Error in data retrieval. {}", e.getMessage());
                jsonResponse.put(NewsAPI.class.getSimpleName(), e.toString());
            }
        }
        return jsonResponse;
    }


    public Map<String, Object> callNewsAPI(String country, RedisCacheManager cacheManager) {

        newsAPiCounter++;

        // Response holder
        Map<String, Object> jsonResponse = new HashMap<>();

        // Construct URL for retrieving from API
        String url = String.format(serviceConfig.getNewsAPIURL(), country, serviceConfig.getNewsAPIToken());

        try {

            logger.info("Start Retrieving data from the source.");

            // Get the Response entity via rest-template.
            ResponseEntity<Object> responseEntity = restTemplate.getForEntity(url, Object.class);
            News news = NewsFactory.getNewsDataParser(NewsSourceType.NEWS_API);

            logger.info("Data fetched.");

            // Get the filtered data.
            jsonResponse = news.parseNewsData(responseEntity, NEWS_API_HEADLINE_ARTICLES);

            logger.info("Save the data to cache.");

            cacheManager.putCache(country, NewsAPI.class.getSimpleName() + "callNewsAPI", jsonResponse);
            cacheManager.obtainExpire(country);
        } catch (Exception e) {
            if (newsAPiCounter != 2) {
                logger.error("Error in data retrieval. {}", e.getMessage());
                jsonResponse.put(NewsAPI.class.getSimpleName(), e.toString());
            }
        }
        return jsonResponse;
    }
}
