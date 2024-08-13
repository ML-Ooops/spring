package org.example.mlooops.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "CategoryDict")
public class CategoryDictEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int categoryId;

    @Column(name = "category_name", nullable = false, length = 255)
    private String categoryName;


    public CategoryDictEntity(String categoryName) {
        this.categoryName = categoryName;
    }

    public CategoryDictEntity() {

    }
}
