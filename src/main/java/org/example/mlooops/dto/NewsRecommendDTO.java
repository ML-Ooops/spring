package org.example.mlooops.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class NewsRecommendDTO {
    @JsonProperty("email")
    private String email;

    @JsonProperty("top_n")
    private int topN;
}
