package com.newsapi.test.task.service;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsapi.test.task.model.Article;
import com.newsapi.test.task.model.Page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class NewsServiceImpl extends MappingJackson2HttpMessageConverter implements NewsService {

    private static final String SITE = "http://newsapi.org/v2/top-headlines";
    private final RestTemplate restTemplate;
    private final Logger logger = LogManager.getLogger(NewsServiceImpl.class);

    public NewsServiceImpl() {
        this.restTemplate = new RestTemplate();
    }

    @Override
    public String getResponseFromApi(String country, String category, int pageSize,
                                     String sortBy, String apiKey) throws HttpServerErrorException {
        String NEWS_API_URL =
                String.format("%s?country=%s&category=%s&pageSize=%d&sortBy=%s&apiKey=%s",
                        SITE, country, category, pageSize, sortBy, apiKey);
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(NEWS_API_URL, String.class);
            return response.getBody();
        } catch (HttpServerErrorException e) {
            logger.error("Get get response from API " + e.getStatusCode() + ". " + e);
            throw new HttpServerErrorException(e.getStatusCode());
        }
    }

    @Override
    public Page getPageFromResponse(String response) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(response, Page.class);
    }

    @Override
    public List<Article> getArticlesFromPage(Page page) throws HttpServerErrorException {
        List<Article> articles = page.getArticles();
        if (articles != null) {
            return articles;
        }
        logger.error("Page is empty " + HttpStatus.NO_CONTENT);
        throw new HttpServerErrorException(HttpStatus.NO_CONTENT);
    }
}
