package org.example.mlooops.repository;


import org.example.mlooops.entity.CategoryDictEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryDictRepository extends JpaRepository<CategoryDictEntity, Integer> {
//    CategoryDictRepository findByCategoryID(Integer categoryID);
}
