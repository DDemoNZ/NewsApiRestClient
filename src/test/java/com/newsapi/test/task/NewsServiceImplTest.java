package com.newsapi.test.task;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.newsapi.test.task.model.Article;
import com.newsapi.test.task.service.NewsServiceImpl;
import com.newsapi.test.task.model.Page;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@RunWith(SpringRunner.class)
class NewsServiceImplTest {


    private static final String SITE = "http://newsapi.org/v2/top-headlines";
    private static final String COUNTRY = "ua";
    private static final String CATEGORY = "general";
    private static final int PAGE_SIZE = 5;
    private static final String SORT_BY = "publishedAt";
    private static final String API_KEY = "fd868cb7d74b41d59cb8f6dc708c521c";

    private static RestTemplate restTemplate;
    private static ObjectMapper objectMapper;
    private static NewsServiceImpl newsService;

    @BeforeEach
    void setUp() {
        restTemplate = new RestTemplate();
        objectMapper = new ObjectMapper();
        newsService = new NewsServiceImpl();
    }

    @Test
    void statusOkOfValidRequest() {
        String responseFromApi = newsService.getResponseFromApi(COUNTRY, CATEGORY, PAGE_SIZE, SORT_BY, API_KEY);
        Page page = new Page();
        try {
            page = objectMapper.readValue(responseFromApi, Page.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        Assert.isTrue(HttpStatus.OK.getReasonPhrase().equalsIgnoreCase(page.getStatus()),
                "Response status is - " + page.getStatus());
    }

    @Test
    void statusUnauthorizedOfNonValidRequest() {
//        RestTemplate restTemplate = new RestTemplate();
        String nonValidApiKey = "fd868cb7d74b41d59cb8f6dcddddc52c";
        String NEWS_API_URL = String.format("%s?country=%s&category=%s&pageSize=%d&sortBy=%s&apiKey=%s",
                SITE, COUNTRY, CATEGORY, PAGE_SIZE, SORT_BY, nonValidApiKey);
        try {
            ResponseEntity<String> response = restTemplate.getForEntity(NEWS_API_URL,
                    String.class);
        } catch (HttpClientErrorException.Unauthorized e) {
            Assert.isTrue(HttpStatus.UNAUTHORIZED.equals(e.getStatusCode()));
        }
    }

    @Test
    void getPageFromValidRequest() {
        String NEWS_API_URL = String.format("%s?country=%s&category=%s&pageSize=%d&sortBy=%s&apiKey=%s",
                SITE, COUNTRY, CATEGORY, PAGE_SIZE, SORT_BY, API_KEY);
        ResponseEntity<String> response = restTemplate.getForEntity(NEWS_API_URL, String.class);
        ObjectMapper mapper = new ObjectMapper();
        Page page;
        try {
            page = mapper.readValue(Objects.requireNonNull(response.getBody()), Page.class);
        } catch (IOException e) {
            page = null;
        }
        Assert.isTrue(page != null);
        Assert.isTrue(page.getArticles() != null);
        Assert.isTrue(page.getArticles().size() == 5);
    }

    @Test
    void getPageFromRequestWithNegativePageSize() {
        int size = -5;
        String url = String.format("%s?country=%s&category=%s&pageSize=%d&sortBy=%s&apiKey=%s",
                SITE, COUNTRY, CATEGORY, size, SORT_BY, API_KEY);
        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        ObjectMapper mapper = new ObjectMapper();
        Page page;
        try {
            page = mapper.readValue(Objects.requireNonNull(response.getBody()), Page.class);
        } catch (IOException e) {
            page = null;
        }
        Assert.isTrue(page != null);
        Assert.isTrue(page.getArticles().size() == 0);
    }

    @Test
    void getArticlesFromPage() {
        String NEWS_API_URL = String.format("%s?country=%s&category=%s&pageSize=%d&sortBy=%s&apiKey=%s",
                SITE, COUNTRY, CATEGORY, PAGE_SIZE, SORT_BY, API_KEY);
        ResponseEntity<String> response = restTemplate.getForEntity(NEWS_API_URL, String.class);
        ObjectMapper mapper = new ObjectMapper();
        Page page;
        try {
            page = mapper.readValue(Objects.requireNonNull(response.getBody()), Page.class);
        } catch (IOException e) {
            page = null;
        }
        Assert.isTrue(page != null);
        List<Article> articles = page.getArticles();
        Assert.notNull(articles, articles.size() + " articles on page.");
        Assert.notNull(articles.get(2));
    }
}
