package org.example.mlooops.DTOtoFastAPI.Response;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserUpdateDTO_Res {
    @JsonProperty("news_user_category")
    private List<Float> news_user_category;
}
