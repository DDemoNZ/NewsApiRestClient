package com.newsapi.test.task.controller;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.newsapi.test.task.model.Article;
import com.newsapi.test.task.model.Page;
import com.newsapi.test.task.service.NewsService;
import com.newsapi.test.task.service.NewsServiceImpl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpServerErrorException;

@RestController
public class NewsApiController {

    private final Logger logger = LogManager.getLogger(NewsApiController.class);
    private final NewsService newsService;

    public NewsApiController() {
        this.newsService = new NewsServiceImpl();
    }

    public String getJsonResponseFromApi(String country, String category, int pageSize,
                                         String sortBy, String apiKey) {
        return newsService.getResponseFromApi(country, category, pageSize, sortBy, apiKey);
    }

    public Page getPageFromRequest(String response) {
        try {
            return newsService.getPageFromResponse(response);
        } catch (JsonProcessingException e) {
            logger.error(HttpStatus.BAD_REQUEST.getReasonPhrase() + ". " + e);
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST);
        }
    }

    public List<Article> getArticlesFromPage(Page page) {
        return newsService.getArticlesFromPage(page);
    }
}
