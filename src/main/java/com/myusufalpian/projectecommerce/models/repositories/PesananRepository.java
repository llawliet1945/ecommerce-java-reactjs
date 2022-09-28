package com.myusufalpian.projectecommerce.models.repositories;

import com.myusufalpian.projectecommerce.models.entities.PesananEntity;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PesananRepository extends JpaRepository<PesananEntity, String> {

    List<PesananEntity> findByUserUsername(String username, Pageable pageable);

    @Query(nativeQuery = true, value = "SELECT p FROM tb_pesanan p WHERE LOWER(p.nomor_pemesanan) LIKE %:filterText OR LOWER(p.user.nama) LIKE %:filterText")
    List<PesananEntity> search(String filterText, Pageable pageable);
}
