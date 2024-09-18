package org.example.mlooops.service;

import jakarta.persistence.EntityNotFoundException;
import org.example.mlooops.dto.RecordDTO;
import org.example.mlooops.entity.UserEntity;
import org.example.mlooops.entity.UserRecordEntity;
import org.example.mlooops.repository.UserNewsRecordRepository;
import org.example.mlooops.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class UserRecordService {
    private UserNewsRecordRepository userRecordRepository;
    private UserRepository UserRepository;

    public UserRecordService(UserNewsRecordRepository userRecordRepository, UserRepository userRepository) {
        this.userRecordRepository = userRecordRepository;
        this.UserRepository = userRepository;
    }

    public int addUserRecord(RecordDTO recordDTO) {
        UserEntity userEntity= UserRepository.findByEmail(recordDTO.getEmail());
        if (userEntity == null) {
            throw new EntityNotFoundException("User not found with ID: ");
        }
        String email=userEntity.getEmail();
        String newId=recordDTO.getNewsId();
        try {
            UserRecordEntity userRecord = new UserRecordEntity();
            userRecord.setEmail(email);
            userRecord.setNewsId(newId);
            userRecord.setUpdatedAt(LocalDateTime.now());
            userRecordRepository.save(userRecord);
        }catch (Exception e){
            return 400;
        }
        return 200;

    }


}
