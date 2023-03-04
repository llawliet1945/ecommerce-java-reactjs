package com.myusufalpian.projectecommerce.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myusufalpian.projectecommerce.models.entities.UserEntity;
import com.myusufalpian.projectecommerce.models.repositories.UserRepository;
import com.myusufalpian.projectecommerce.utilities.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public ResponseEntity<String> save(UserEntity user) throws JsonProcessingException {

        if(!StringUtils.hasText(user.getUsername())){
            return GenerateResponse.badRequest("Username not found", null);
        }

        if(!StringUtils.hasText(user.getNama())){
            return GenerateResponse.badRequest("Name cannot be null", null);
        }

        if(!StringUtils.hasText(user.getEmail())){
            return GenerateResponse.badRequest("Email cannot be null", null);
        }

        if(userRepository.existsById(user.getUsername())) {
            return GenerateResponse.badRequest("Username already registered", null);
        }

        if(userRepository.existsByEmail(user.getEmail())) {
            return GenerateResponse.badRequest("Email already registered", null);
        }

        userRepository.save(user);

        return GenerateResponse.success("Add data user success", null);
    }

    public ResponseEntity<String> update(UserEntity user) throws JsonProcessingException {

        if(!StringUtils.hasText(user.getUsername())){
            return GenerateResponse.badRequest("Username not found", null);
        }

        if(!StringUtils.hasText(user.getNama())){
            return GenerateResponse.badRequest("Name cannot be null", null);
        }

        if(!StringUtils.hasText(user.getEmail())){
            return GenerateResponse.badRequest("Email cannot be null", null);
        }

        userRepository.save(user);

        return GenerateResponse.success("Update data success", null);

    }

    public ResponseEntity<String> findById(String username) throws JsonProcessingException {
        Optional<UserEntity> user = userRepository.findById(username);
        if (user.isEmpty()) {
            return GenerateResponse.notFound("User not found", null);
        }
        return GenerateResponse.success("Get data success", user.get());
    }

    public ResponseEntity<String> findAll() throws JsonProcessingException {
        return GenerateResponse.success("Get data success", userRepository.findAll());
    }

    public ResponseEntity<String> removeOne(String username) throws JsonProcessingException {
        userRepository.deleteById(username);
        return GenerateResponse.success("Delete data success", null);
    }

    public ResponseEntity<String> getUserByNama(String nama) throws JsonProcessingException {
        List<UserEntity> user = userRepository.findByNama(nama);
        if (user.isEmpty()) {
            return GenerateResponse.notFound("user not found!", null);
        }
        return GenerateResponse.success("Get data success", user);
    }

    public ResponseEntity<String> getDataByNama(String nama) throws JsonProcessingException {
        List<UserEntity> user = userRepository.findByNamaStartingWith(nama);
        if (user.isEmpty()) {
            return GenerateResponse.notFound("user not found!", null);
        }
        return GenerateResponse.success("Get data success", user);
    }

    public ResponseEntity<String> getUserByEmail(String email) throws JsonProcessingException {
        Optional<UserEntity> user = userRepository.findByEmail(email);
        if (user.isEmpty()) {
            return GenerateResponse.notFound("user not found!", null);
        }
        return GenerateResponse.success("Get data success", user.get());
    }
}
