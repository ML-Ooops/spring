package org.example.mlooops.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "UserRecord")
public class UserRecordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int RecordId;
    @Column(name = "user_id", nullable = false)
    private int userId;
    @Column(name = "news_id", nullable = false)
    private int newsId;

    @Column(name = "Record_at")
    private LocalDateTime updatedAt;

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
