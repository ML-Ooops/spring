package org.example.mlooops.repository;

import org.example.mlooops.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    UserEntity findByUsername(String username);
}
