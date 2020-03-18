package com.newsapi.test.task.client;

import java.util.List;

import com.newsapi.test.task.controller.NewsApiController;
import com.newsapi.test.task.model.Article;
import com.newsapi.test.task.model.Page;

import org.springframework.stereotype.Component;

@Component
public class ClientClass {

    private static final String COUNTRY = "ua"; //The 2-letter ISO 3166-1 code of the country
    private static final String CATEGORY = "general"; //business entertainment general health science sports technology
    private static final int PAGE_SIZE = 5; //int number if result per page between 0 - 100
    private static final String SORT_BY = "publishedAt"; //recent articles first
    private static final String API_KEY = "fd868cb7d74b41d59cb8f6dc708c521c"; //API key

    public void doAllClientWork() {
        NewsApiController controller = new NewsApiController();
        String responseFromApi = controller.getJsonResponseFromApi(COUNTRY, CATEGORY, PAGE_SIZE,
                SORT_BY, API_KEY);
        Page pageFromResponse;
        pageFromResponse = controller.getPageFromRequest(responseFromApi);
        List<Article> articlesFromPage = controller.getArticlesFromPage(pageFromResponse);
        articlesFromPage.forEach(this::print);
    }

    private void print(Article article) {
        System.out.printf("\n%s\n%s\n%s\n", article.getAuthor() == null ? " ~~~ " :
                        article.getAuthor(), article.getTitle(), article.getPublishedAt());
        System.out.println("------------------------------------------------");
    }
}
