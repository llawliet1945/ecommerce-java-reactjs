package com.myusufalpian.projectecommerce.models.repositories;

import com.myusufalpian.projectecommerce.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    List<UserEntity> findByNama(@PathParam("nama") String nama);

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(@PathParam("email") String email);
    public List<UserEntity> findByNamaStartingWith(@PathParam("nama") String nama);

    boolean existsByEmail(String username);
}
