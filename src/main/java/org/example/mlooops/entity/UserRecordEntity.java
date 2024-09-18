package org.example.mlooops.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "UserNewsRecord")
public class UserRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int RecordId;
    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "news_id", nullable = false)
    private String newsId;

    @Column(name = "Record_at")
    private LocalDateTime updatedAt;

}
