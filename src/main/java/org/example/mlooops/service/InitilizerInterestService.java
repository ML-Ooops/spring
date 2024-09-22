package org.example.mlooops.service;

import org.example.mlooops.dto.InitialInterestDTO;
import org.example.mlooops.entity.UserEntity;
import org.example.mlooops.entity.UserInterestEntity;
import org.example.mlooops.repository.UserInterestRepositoty;
import org.example.mlooops.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class InitilizerInterestService {
    private final UserInterestRepositoty userInterestRepositoty;
    private final UserRepository userRepository;

    public InitilizerInterestService(UserInterestRepositoty userInterestRepositoty, UserRepository userRepository) {
        this.userInterestRepositoty=userInterestRepositoty;
        this.userRepository=userRepository;
    }

    public int IntInterestServie(InitialInterestDTO interestDTO){
        String email=interestDTO.getEmail();
        UserEntity userEntity=userRepository.findByEmail(email);
        int userId=userEntity.getUserId();
        float[] interest=interestDTO.getCategoryInterest();

        Boolean isExist=userInterestRepositoty.existsByUserId(userId);
        if(isExist){
            //이미 최초 업도르 완료.
            return 103;
        }
        List<UserInterestEntity> categories = new ArrayList<>(); // ArrayList 생성

        for (int i = 0; i < interest.length; i++) {
            categories.add(new UserInterestEntity(userId, i + 1, interest[i])); // 리스트에 추가
        }
        userInterestRepositoty.saveAll(categories);



        //최초 업로드 완료
        return 200;
    }

}
