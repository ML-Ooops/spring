package org.example.mlooops.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "UserInterests")
public class UserInterestEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int interestId;

    @Column(name = "user_id", nullable = false)
    private int userId;

    @Column(name = "category_id", nullable = false)
    private int categoryId;

    @Column(name = "category_Double", nullable = false)
    private double categoryDouble;

    public UserInterestEntity(int userId, int categoryId, double categoryDouble) {
        this.userId = userId;
        this.categoryId = categoryId;
        this.categoryDouble = categoryDouble;
    }

    public UserInterestEntity() {

    }
}
