package org.example.mlooops.service;


import org.example.mlooops.DTOtoFastAPI.Request.ContentSimilarityRequest;
import org.example.mlooops.DTOtoFastAPI.Response.ContentSimilarityResponse;
import org.example.mlooops.DTOtoFastAPI.Response.NewsAllResponse;
import org.example.mlooops.DTOtoFastAPI.Response.NewsCategoryResponse;
import org.example.mlooops.dto.NewsRecommendDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class NewsService {
    private final RestTemplate restTemplate;

    @Value("${news.api.url}")
    private String newsApiUrl;

    public NewsService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<NewsAllResponse> getNews() {
        String url = newsApiUrl+"/news";
        NewsAllResponse[] response = restTemplate.getForObject(url, NewsAllResponse[].class);
        return List.of(response);
    }

    public List<NewsCategoryResponse> getNewsCategory() {
        String url = newsApiUrl+"/news_category";
        NewsCategoryResponse[] response = restTemplate.getForObject(url, NewsCategoryResponse[].class);
        return List.of(response);
    }
    public List<ContentSimilarityResponse> getSimilarityRecommendations(ContentSimilarityRequest request) {
        String url = newsApiUrl+"/news/content/similarity_recommend";
        ContentSimilarityResponse[] recommendations = restTemplate.postForObject(url, request, ContentSimilarityResponse[].class);
        return List.of(recommendations);
    }

}
