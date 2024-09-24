package org.example.mlooops.service;


import org.example.mlooops.DTOtoFastAPI.Request.ContentSimilarityRequest;
import org.example.mlooops.DTOtoFastAPI.Request.NewsByIDRequest;
import org.example.mlooops.DTOtoFastAPI.Request.RandomRequest;
import org.example.mlooops.DTOtoFastAPI.Response.ContentSimilarityResponse;
import org.example.mlooops.DTOtoFastAPI.Response.NewsAllResponse;
import org.example.mlooops.DTOtoFastAPI.Response.NewsCategoryResponse;
import org.example.mlooops.DTOtoFastAPI.Response.RandomResponse;
import org.example.mlooops.dto.NewsRecommendDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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

    public List<NewsAllResponse> getRandomNews(RandomRequest randomRequest) {
        String url = newsApiUrl + "/news/content/random_recommend";
        RandomResponse[] recommendations = restTemplate.postForObject(url, randomRequest, RandomResponse[].class);

        return fetchNewsByIds(extractNewsIds(recommendations));
    }

    public List<NewsAllResponse> getSimilarityRecommendations(ContentSimilarityRequest request) {
        String url = newsApiUrl + "/news/content/similarity_recommend";
        ContentSimilarityResponse[] recommendations = restTemplate.postForObject(url, request, ContentSimilarityResponse[].class);

        return fetchNewsByIds(extractNewsIds(recommendations));
    }

    private List<String> extractNewsIds(Object[] recommendations) {
        List<String> newsIds = new ArrayList<>();
        for (Object recommendation : recommendations) {
            if (recommendation instanceof RandomResponse) {
                newsIds.add(((RandomResponse) recommendation).getNewsId());
            } else if (recommendation instanceof ContentSimilarityResponse) {
                newsIds.add(((ContentSimilarityResponse) recommendation).getNewsId());
            }
        }
        return newsIds;
    }

    private List<NewsAllResponse> fetchNewsByIds(List<String> newsIds) {
        NewsByIDRequest newsByIDRequest = new NewsByIDRequest();
        newsByIDRequest.setNews_ids(newsIds);

        String url = newsApiUrl + "/news/news_ids";
        NewsAllResponse[] response = restTemplate.postForObject(url, newsByIDRequest, NewsAllResponse[].class);
        System.out.println(response);

        return List.of(response);
    }


}
