package org.example.mlooops.DTOtoFastAPI.Request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RandomRequest {
    @JsonProperty("top_n")
    private int topN;
}
