package org.example.mlooops.Initializer;

import org.example.mlooops.entity.CategoryDictEntity;
import org.example.mlooops.repository.CategoryDictRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private CategoryDictRepository categoryDictRepository;


    @Override
    public void run(String... args) throws Exception {
        // 데이터가 비어있는 경우에만 초기 데이터 삽입
        if (categoryDictRepository.count() == 0) {
            List<CategoryDictEntity> categories = Arrays.asList(
                    new CategoryDictEntity("IT_과학>과학"),
                    new CategoryDictEntity("IT_과학>보안"),
                    new CategoryDictEntity("IT_과학>모바일"),
                    new CategoryDictEntity("IT_과학>콘텐츠"),
                    new CategoryDictEntity("IT_과학>인터넷_SNS"),
                    new CategoryDictEntity("IT_과학>IT_과학일반")
            );

            categoryDictRepository.saveAll(categories);
            System.out.println("System initialized");
        }
    }
}
