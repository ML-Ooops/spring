package org.example.mlooops.DTOtoFastAPI.Request;


import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class NewsByIDRequest {
    private List<String> news_ids;
}
