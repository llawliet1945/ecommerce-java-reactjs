package com.myusufalpian.projectecommerce.models.repositories;

import com.myusufalpian.projectecommerce.models.entities.KeranjangEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface KeranjangRepository extends JpaRepository<KeranjangEntity, String> {

    Optional<KeranjangEntity> findByUserUsernameAndProductId(String username, String produkId);

    List<KeranjangEntity> findByUserUsername(String username);
}
