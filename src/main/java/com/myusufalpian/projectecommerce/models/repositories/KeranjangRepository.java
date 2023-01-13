package com.myusufalpian.projectecommerce.models.repositories;

import com.myusufalpian.projectecommerce.models.entities.KeranjangEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KeranjangRepository extends JpaRepository<KeranjangEntity, Integer> {

    Optional<KeranjangEntity> findByUserIdAndProductId(String userId, Integer productId);

    List<KeranjangEntity> findByUserId(String userId);

    Optional<KeranjangEntity> findByUuid(String uuid);
}
