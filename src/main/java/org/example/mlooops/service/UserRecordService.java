package org.example.mlooops.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.mlooops.DTOtoFastAPI.Request.UserUpdateDTO_Req;
import org.example.mlooops.DTOtoFastAPI.Response.RandomResponse;
import org.example.mlooops.DTOtoFastAPI.Response.UserUpdateDTO_Res;
import org.example.mlooops.dto.RecordDTO;
import org.example.mlooops.entity.UserEntity;
import org.example.mlooops.entity.UserInterestEntity;
import org.example.mlooops.entity.UserRecordEntity;
import org.example.mlooops.repository.UserInterestRepositoty;
import org.example.mlooops.repository.UserNewsRecordRepository;
import org.example.mlooops.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserRecordService {
    private final RestTemplate restTemplate;

    @Value("${news.api.url}")
    private String newsApiUrl;


    private final CustomUserDetailsService customUserDetailsService;
    private UserNewsRecordRepository userRecordRepository;
    private UserRepository UserRepository;
    private UserInterestRepositoty userInterestRepositoty;

    public UserRecordService(RestTemplate restTemplate, UserNewsRecordRepository userRecordRepository, UserRepository userRepository, CustomUserDetailsService customUserDetailsService, UserInterestRepositoty userInterestRepositoty) {
        this.restTemplate = restTemplate;
        this.userRecordRepository = userRecordRepository;
        this.UserRepository = userRepository;
        this.customUserDetailsService = customUserDetailsService;
        this.userInterestRepositoty = userInterestRepositoty;
    }

    public int addUserRecord(RecordDTO recordDTO) {
        System.out.println("기능 시작");
        String email = recordDTO.getEmail();
        String newId=recordDTO.getNewsId();
        UserEntity userEntity= UserRepository.findByEmail(email);
        int user_id = userEntity.getUserId();

        if (userEntity == null) {
            throw new EntityNotFoundException("User not found with ID: ");
        }

        
        float[] interest = customUserDetailsService.getUserCategory(email);
        List<Float> floatList = new ArrayList<>();
        for (float f : interest) {
            floatList.add(f); // autoboxing을 통해 float에서 Float로 변환
        }
        UserUpdateDTO_Req userUpdateDTO_Req = new UserUpdateDTO_Req();
        userUpdateDTO_Req.setNewsId(newId);
        userUpdateDTO_Req.setUserCategory(floatList);

        System.out.println("사용자 정보세팅");
        try {
            UserRecordEntity userRecord = new UserRecordEntity();
            userRecord.setEmail(email);
            userRecord.setNewsId(newId);
            userRecord.setUpdatedAt(LocalDateTime.now());
            userRecordRepository.save(userRecord);

            String url = newsApiUrl+"/user_category_update";
            UserUpdateDTO_Res userUpdateDTORes = restTemplate.postForObject(url, userUpdateDTO_Req, UserUpdateDTO_Res.class);

            List<Float> temp =userUpdateDTORes.getNews_user_category();
            
            System.out.println("temp 추출 후");
            UserInterestEntity[] userInterests = userInterestRepositoty.findAllByUserId(user_id);
            for (int i = 0; i < temp.size(); i++) {
                Float tempValue = temp.get(i);
                for (UserInterestEntity userInterest : userInterests) {
                    if (userInterest.getCategoryId() == i+1) {
                        // 값이 일치하면 업데이트
                        userInterest.setCategoryDouble(tempValue); // 필요한 필드를 업데이트
                        userInterestRepositoty.save(userInterest); // 변경된 엔티티 저장
                    }
                }
            }



        }catch (Exception e){
            return 400;
        }
        return 200;

    }


}
