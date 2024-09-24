package org.example.mlooops.controller;

import org.example.mlooops.DTOtoFastAPI.Request.ContentSimilarityRequest;
import org.example.mlooops.DTOtoFastAPI.Request.RandomRequest;
import org.example.mlooops.DTOtoFastAPI.Response.NewsAllResponse;
import org.example.mlooops.DTOtoFastAPI.Response.NewsCategoryResponse;
import org.example.mlooops.dto.NewsRecommendDTO;
import org.example.mlooops.dto.RecordDTO;
import org.example.mlooops.service.CustomUserDetailsService;
import org.example.mlooops.service.NewsService;
import org.example.mlooops.service.ResponseData;
import org.example.mlooops.service.UserRecordService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NewsController {
    private final UserRecordService userRecordService;
    private final NewsService newsService;
    private final CustomUserDetailsService customUserDetailsService;

    public NewsController(UserRecordService userRecordService, NewsService newsService, CustomUserDetailsService customUserDetailsService) {
        this.userRecordService = userRecordService;
        this.newsService = newsService;
        this.customUserDetailsService = customUserDetailsService;
    }

    @PostMapping("/news/record")
    public ResponseEntity<ResponseData> RecordProcess(@RequestBody RecordDTO recordDTO) {
        System.out.println(1);
        System.out.println("newID: " + recordDTO.getNewsId());
        System.out.println("newID: " + recordDTO.getEmail());
        System.out.println(1);
        int result = userRecordService.addUserRecord(recordDTO);
        ResponseData data;
        switch (result) {
            case 400:
                data = new ResponseData("Error Store");
                return new ResponseEntity<>(data, HttpStatus.CONFLICT); // 409 Conflict
            case 200:
                data = new ResponseData("Complete join");
                return new ResponseEntity<>(data, HttpStatus.OK); // 200 OK
            default:
                data = new ResponseData("Occure other fail ");
                return new ResponseEntity<>(data, HttpStatus.INTERNAL_SERVER_ERROR); // 500 Internal Server Error
        }
    }


    @GetMapping("/news/all")
    public List<NewsAllResponse> ShowAllRecords() {
        return newsService.getNews();
    }

    @GetMapping("/news/category")
    public List<NewsCategoryResponse> ShowAllCategoryRecords() {
        return newsService.getNewsCategory();
    }

    @PostMapping("/news/recommendation/content")
    public List<NewsAllResponse> RecommendContent(@RequestBody NewsRecommendDTO request) {
        float[] temp=customUserDetailsService.getUserCategory(request.getEmail());
        ContentSimilarityRequest contentSimilarityRequest=new ContentSimilarityRequest();
        contentSimilarityRequest.setCategoryArray(temp);
        contentSimilarityRequest.setTopN(request.getTopN());
        return newsService.getSimilarityRecommendations(contentSimilarityRequest);
    }

    @PostMapping("/news/recommendation/random")
    public List<NewsAllResponse> RecommendRandom(@RequestBody RandomRequest request) {
        return newsService.getRandomNews(request);
    }



}
