package org.example.mlooops.DTOtoFastAPI.Response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewsCategoryResponse {
    @JsonProperty("news_id")
    private String newsId;

    private List<Float> category_array;
}
