package com.myusufalpian.projectecommerce.services;

import com.myusufalpian.projectecommerce.exceptions.ResourceNotFoundException;
import com.myusufalpian.projectecommerce.models.entities.CategoryEntity;
import com.myusufalpian.projectecommerce.models.repositories.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class CategoryService {

    @Autowired
    private CategoryRepository categoryRepository;

    public CategoryEntity save(CategoryEntity categoryEntity){
        categoryEntity.setUuid(UUID.randomUUID().toString());
        return categoryRepository.save(categoryEntity);
    }

    public CategoryEntity update(CategoryEntity categoryEntity){
        return categoryRepository.save(categoryEntity);
    }

    public CategoryEntity findByUuid(String uuid){
        return categoryRepository.findByUuid(uuid)
                .orElseThrow(() -> new ResourceNotFoundException("Kategori tidak ditemukan!"));
    }

    public List<CategoryEntity> findAll(){
        return categoryRepository.findAll();
    }

    public void removeOne(String uuid){
        categoryRepository.deleteByUuid(uuid);
    }

}
