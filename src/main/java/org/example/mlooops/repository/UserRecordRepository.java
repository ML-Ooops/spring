package org.example.mlooops.repository;

import org.example.mlooops.entity.UserEntity;
import org.example.mlooops.entity.UserRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRecordRepository extends JpaRepository<UserRecordEntity, Integer> {
    UserEntity findallByUserId(String userId);


}
