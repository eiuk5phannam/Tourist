package com.corelambda.tourist.datamodel;


import java.util.Map;

public class QueryResponse {
    public Map<Integer, WikipediaPage> getPages() {
        return pages;
    }

    public void setPages(Map<Integer, WikipediaPage> pages) {
        this.pages = pages;
    }

    private Map<Integer,WikipediaPage> pages;
}
