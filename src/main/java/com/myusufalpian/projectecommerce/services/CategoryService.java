package com.myusufalpian.projectecommerce.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myusufalpian.projectecommerce.models.entities.CategoryEntity;
import com.myusufalpian.projectecommerce.models.repositories.CategoryRepository;
import com.myusufalpian.projectecommerce.utilities.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<String> save(CategoryEntity categoryEntity) throws JsonProcessingException {
        categoryEntity.setUuid(UUID.randomUUID().toString());
        categoryRepository.save(categoryEntity);
        return GenerateResponse.success("Add new data success", null);
    }

    public ResponseEntity<String> update(CategoryEntity categoryEntity) throws JsonProcessingException {
        try {
            categoryRepository.save(categoryEntity);
            return GenerateResponse.success("Update category success", null);
        } catch (Exception e) {
            return GenerateResponse.error("Internal server error", e.getLocalizedMessage());
        }
    }

    public ResponseEntity<String> findByUuid(String uuid) throws JsonProcessingException {
        Optional<CategoryEntity> category = categoryRepository.findByUuid(uuid);
        if (category.isEmpty()) {
            return GenerateResponse.notFound("Category not found", null);
        }
        return GenerateResponse.success("Update category success", category.get());
    }

    public ResponseEntity<String> findAll() throws JsonProcessingException {
        List<CategoryEntity> category = categoryRepository.findAll();
        if (category.isEmpty()) {
            return GenerateResponse.notFound("Category not found!", null);
        }
        return GenerateResponse.success("Get data success", category);
    }

    public ResponseEntity<String> removeOne(String uuid) throws JsonProcessingException {
        Optional<CategoryEntity> category = categoryRepository.findByUuid(uuid);
        if (category.isEmpty()) {
            return GenerateResponse.notFound("Category not found", null);
        }
        categoryRepository.deleteByUuid(uuid);
        return GenerateResponse.success("Delete data success", null);
    }

}
