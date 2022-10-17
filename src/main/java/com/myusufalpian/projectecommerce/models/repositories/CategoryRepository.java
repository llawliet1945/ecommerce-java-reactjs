package com.myusufalpian.projectecommerce.models.repositories;

import com.myusufalpian.projectecommerce.models.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {
    Optional<CategoryEntity> findByUuid(String uuid);

    Optional<CategoryEntity> deleteByUuid(String uuid);
}
