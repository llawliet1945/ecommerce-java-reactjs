package com.myusufalpian.projectecommerce.models.repositories;

import com.myusufalpian.projectecommerce.models.entities.KeranjangEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KeranjangRepository extends JpaRepository<KeranjangEntity, Integer> {

    Optional<KeranjangEntity> findByUserUsernameAndProductUuid(String username, String uuid);

    List<KeranjangEntity> findByUserUsername(String username);

    Optional<KeranjangEntity> findByUuid(String uuid);
}
