package com.newsapi.test.task.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newsapi.test.task.model.Article;
import com.newsapi.test.task.model.Page;

import org.springframework.web.client.HttpServerErrorException;

public interface NewsService {

    String getResponseFromApi(String country, String category, int pageSize,
                              String sortBy, String apiKey) throws HttpServerErrorException;

    Page getPageFromResponse(String response) throws JsonProcessingException;

    List<Article> getArticlesFromPage(Page page) throws HttpServerErrorException;
}
