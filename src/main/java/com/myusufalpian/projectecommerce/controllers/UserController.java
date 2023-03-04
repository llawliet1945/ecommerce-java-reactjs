package com.myusufalpian.projectecommerce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myusufalpian.projectecommerce.dto.SearchData;
import com.myusufalpian.projectecommerce.models.entities.UserEntity;
import com.myusufalpian.projectecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired(required=true)
    private UserService userService;

    @GetMapping("getAllData")
    public ResponseEntity<String> getAllData() throws JsonProcessingException {
        return userService.findAll();
    }

    @GetMapping("getById")
    public ResponseEntity<String> getById(@RequestParam String id) throws JsonProcessingException {
        return userService.findById(id);
    }

    @PostMapping("addNewUser")
    public ResponseEntity<String> save(@RequestBody UserEntity param) throws JsonProcessingException {
        return userService.save(param);
    }

    @PutMapping("updateUser")
    public ResponseEntity<String> updateProduct(@RequestBody UserEntity param) throws JsonProcessingException {
        return userService.update(param);
    }

    @DeleteMapping(value = "deleteUser")
    public ResponseEntity<String> deleteData(@RequestParam String id) throws JsonProcessingException {
        return userService.removeOne(id);
    }

    @PostMapping(value = "getUserByNama")
    public ResponseEntity<String> getUserByNama(@RequestBody SearchData searchData) throws JsonProcessingException {
        return userService.getUserByNama(searchData.getKeyword());
    }

    @PostMapping(value = "getUserByEmail")
    public ResponseEntity<String> getUserByEmail(@RequestBody SearchData searchData) throws JsonProcessingException {
        return userService.getUserByEmail(searchData.getKeyword());
    }

    @PostMapping(value = "getDataByNama")
    public ResponseEntity<String> getDataByNama(@RequestBody SearchData searchData) throws JsonProcessingException {
        return userService.getDataByNama(searchData.getKeyword());
    }
}
