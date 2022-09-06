package com.myusufalpian.projectecommerce.models.repositories;

import com.myusufalpian.projectecommerce.models.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.websocket.server.PathParam;
import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, String> {
    public UserEntity findByNama(@PathParam("nama") String nama);

    public UserEntity findByEmail(@PathParam("email") String email);
    public List<UserEntity> findByNamaStartingWith(@PathParam("nama") String nama);

    boolean existsByEmail(String username);
}
