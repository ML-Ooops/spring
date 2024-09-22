package org.example.mlooops.controller;

import org.example.mlooops.DTOtoFastAPI.Request.ContentSimilarityRequest;
import org.example.mlooops.DTOtoFastAPI.Response.ContentSimilarityResponse;
import org.example.mlooops.DTOtoFastAPI.Response.NewsCategoryResponse;
import org.example.mlooops.dto.NewsRecommendDTO;
import org.example.mlooops.service.CustomUserDetailsService;
import org.example.mlooops.service.NewsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {


    private final NewsService newsService;
    private final CustomUserDetailsService customUserDetailsService;

    public TestController(NewsService newsService, CustomUserDetailsService customUserDetailsService) {
        this.newsService = newsService;
        this.customUserDetailsService = customUserDetailsService;
    }

//    @PostMapping("/test")
//    public List<ContentSimilarityResponse> index(@RequestBody NewsRecommendDTO request) {
//
//
//
//        return newsService.getSimilarityRecommendations(request);
//    }
    @PostMapping("/test")
    public List<ContentSimilarityResponse> index(@RequestBody NewsRecommendDTO request) {
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
}
