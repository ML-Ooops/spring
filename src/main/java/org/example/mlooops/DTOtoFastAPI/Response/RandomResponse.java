package org.example.mlooops.DTOtoFastAPI.Response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RandomResponse {
    @JsonProperty("news_id")
    private String newsId;

}
