package org.example.mlooops.repository;


import org.example.mlooops.entity.UserInterestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInterestRepositoty extends JpaRepository<UserInterestEntity, Integer> {
    UserInterestEntity[] findAllByUserId(int userId);

    Boolean existsByUserId(int userId);

}
