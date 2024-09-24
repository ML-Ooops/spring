package org.example.mlooops.controller;

import org.example.mlooops.DTOtoFastAPI.Request.ContentSimilarityRequest;
import org.example.mlooops.DTOtoFastAPI.Response.ContentSimilarityResponse;
import org.example.mlooops.DTOtoFastAPI.Response.NewsAllResponse;
import org.example.mlooops.DTOtoFastAPI.Response.NewsCategoryResponse;
import org.example.mlooops.dto.NewsRecommendDTO;
import org.example.mlooops.jwt.LoginFilter;
import org.example.mlooops.service.CustomUserDetailsService;
import org.example.mlooops.service.JwtBlacklistService;
import org.example.mlooops.service.NewsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
public class TestController {


    private final NewsService newsService;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtBlacklistService jwtBlacklistService;
    private final RestTemplate restTemplate;

    public TestController(NewsService newsService, CustomUserDetailsService customUserDetailsService, JwtBlacklistService jwtBlacklistService, RestTemplate restTemplate) {
        this.newsService = newsService;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtBlacklistService = jwtBlacklistService;
        this.restTemplate = restTemplate;
    }

//    @PostMapping("/test")
//    public List<ContentSimilarityResponse> index(@RequestBody NewsRecommendDTO request) {
//
//
//
//        return newsService.getSimilarityRecommendations(request);
//    }
    @PostMapping("/test")
    public List<NewsAllResponse> index(@RequestBody NewsRecommendDTO request) {
        float[] temp=customUserDetailsService.getUserCategory(request.getEmail());
        ContentSimilarityRequest contentSimilarityRequest=new ContentSimilarityRequest();
        contentSimilarityRequest.setCategoryArray(temp);
        contentSimilarityRequest.setTopN(request.getTopN());


        return newsService.getSimilarityRecommendations(contentSimilarityRequest);
    }

    @GetMapping("/")
    public String mainP() {
        return "main controller";
    }

    @GetMapping("/sample")
    public String sampleApi() {

        return "Hello, Spring REST Docs!";
    }
    @GetMapping("/blacklist")
    public String blackLsit() {
        System.out.println(jwtBlacklistService.getBlacklistedTokens());
        return "Hello, Spring REST Docs!";
    }

}
