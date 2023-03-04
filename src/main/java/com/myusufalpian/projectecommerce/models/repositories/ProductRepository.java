package com.myusufalpian.projectecommerce.models.repositories;

import com.myusufalpian.projectecommerce.models.entities.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {
    List<ProductEntity> findByNama(@PathParam("nama") String nama);

    List<ProductEntity> findByNamaStartingWith(@PathParam("nama") String nama);

    Optional<ProductEntity> findByUuid(String uuid);
}
