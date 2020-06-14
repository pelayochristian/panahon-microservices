package com.project.panahon.newsservice.news.source;

public enum Category {
    BUSINESS("Business"),
    TECHNOLOGY("Technology"),
    ENTERTAINMENT("Entertainment"),
    HEALTH("Health"),
    SCIENCE("Science"),
    SPORTS("Sports");

    private String action;

    public String getAction() {
        return this.action;
    }

    Category(String action) {
        this.action = action;
    }
}
