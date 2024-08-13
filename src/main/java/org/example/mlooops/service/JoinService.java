package org.example.mlooops.service;

import org.example.mlooops.dto.JoinDTO;
import org.example.mlooops.entity.UserEntity;
import org.example.mlooops.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder= new BCryptPasswordEncoder();
    }

    public int JoinProcess(JoinDTO joinDTO){
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();
        // 0을 남자, 1을 여자로 상정
        Boolean gender = joinDTO.getGender();

        Boolean isExists = userRepository.existsByUsername(username);
        if(isExists){
//            유저이름 중복 테스트
            return 101;
        }
        isExists = userRepository.existsByEmail(email);
        if(isExists){
            //이메일 중복 테스트
            return 102;
        }
        UserEntity data = new UserEntity();
        data.setUsername(username);
        data.setEmail(email);
        data.setPasswordHash(bCryptPasswordEncoder.encode(password));
        data.setCreatedAt(LocalDateTime.now());
        data.setGender(gender);

        userRepository.save(data);

        UserEntity user=userRepository.findByUsername(username);
        System.out.println("userId : "+user.getUserId());
        return 200;

    }

}
