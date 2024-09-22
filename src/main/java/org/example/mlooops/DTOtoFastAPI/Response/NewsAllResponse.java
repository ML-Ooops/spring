package org.example.mlooops.DTOtoFastAPI.Response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class NewsAllResponse {
    @JsonProperty("news_id")
    private String newsId;

    private String title;
    private String content;
    private String hilight;
    private String published_at;
    private String provider;
    private List<String> category;
}
