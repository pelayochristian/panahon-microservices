package com.project.panahon.newsservice.news.factory;

import com.project.panahon.newsservice.news.source.NewsAPI;


/**
 * This will serve a factory class the will <br>
 * return what {@link NewsSourceType} to return.
 *
 * @author christian
 * @since 2020-05-11
 */
public class NewsFactory {

    /**
     * Function use for returning what {@link News} to <br>
     * return.
     *
     * @param sourceType The {@link News}
     * @return The {@link News}
     */
    public static News getNewsDataParser(NewsSourceType sourceType) {
        News news = null;
        switch (sourceType) {
            case NEWS_API:
                news = new NewsAPI();
                break;
            default:
                break;
        }
        return news;
    }
}
