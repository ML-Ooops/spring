package org.example.mlooops.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Column(nullable = false, unique = true, length = 255)
    private String username;

    @Column(nullable = false, unique = true, length = 255)
    private String email;

    @Column(nullable = false, length = 64)
    private String passwordHash;

    @Column(name = "signupDate", nullable = false)
    private LocalDateTime createdAt;
    // 0을 남자, 1을 여자로 상정

    private String role;

}
