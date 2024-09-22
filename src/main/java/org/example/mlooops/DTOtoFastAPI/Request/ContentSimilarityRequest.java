package org.example.mlooops.DTOtoFastAPI.Request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ContentSimilarityRequest {
    @JsonProperty("category_array")
    private float[] categoryArray;

    @JsonProperty("top_n")
    private int topN;
}
