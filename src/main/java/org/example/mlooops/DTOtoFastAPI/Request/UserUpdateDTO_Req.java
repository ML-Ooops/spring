package org.example.mlooops.DTOtoFastAPI.Request;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class UserUpdateDTO_Req {
    @JsonProperty("user_category")
    private List<Float> userCategory;

    @JsonProperty("news_id")
    private String newsId;

}
