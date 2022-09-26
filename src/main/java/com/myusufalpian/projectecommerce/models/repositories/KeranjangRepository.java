package com.myusufalpian.projectecommerce.models.repositories;

import com.myusufalpian.projectecommerce.models.entities.KeranjangEntity;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface KeranjangRepository extends JpaRepository<KeranjangEntity, String> {

    // @Query(nativeQuery = true, value = "select * from tb_keranjang tk where product_id=:produkId and user_username=:username")
    Optional<KeranjangEntity> findByUserUsernameAndProductId(String username, String produkId);

    // @Query(nativeQuery = true, value = "select * from tb_keranjang tk where user_username=:username")
    List<KeranjangEntity> findByUserUsername(String username);
}
