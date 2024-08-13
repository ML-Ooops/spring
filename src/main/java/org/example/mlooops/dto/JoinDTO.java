package org.example.mlooops.dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class JoinDTO {
    private String username;

    private String password;

    private String email;


    // 0을 남자, 1을 여자로 상정
    private Boolean gender;
}