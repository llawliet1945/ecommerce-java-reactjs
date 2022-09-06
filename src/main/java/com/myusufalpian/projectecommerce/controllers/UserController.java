package com.myusufalpian.projectecommerce.controllers;

import com.myusufalpian.projectecommerce.dto.ResponseData;
import com.myusufalpian.projectecommerce.dto.SearchData;
import com.myusufalpian.projectecommerce.models.entities.UserEntity;
import com.myusufalpian.projectecommerce.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired(required=true)
    private UserService userService;

    @GetMapping("getAllData")
    public Iterable<UserEntity> getAllData(){
        return userService.findAll();
    }

    @GetMapping("getById")
    public UserEntity getById(@RequestParam String id){
        return userService.findById(id);
    }

    @PostMapping("addNewUser")
    public ResponseEntity<ResponseData<UserEntity>> save(@RequestBody UserEntity param, Errors errors) {
        ResponseData<UserEntity> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for (ObjectError objectError : errors.getAllErrors()){
                responseData.getMessages().add(objectError.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setMessages(Collections.singletonList("User sukses ditambahkan!"));
        responseData.setPayload(userService.save(param));
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("updateUser")
    public ResponseEntity<ResponseData<UserEntity>> updateProduct(@RequestBody UserEntity param, Errors errors) {

        ResponseData<UserEntity> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for (ObjectError objectError : errors.getAllErrors()){
                responseData.getMessages().add(objectError.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setMessages(Collections.singletonList("User sukses diubah!"));
        responseData.setPayload(userService.update(param));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping(value = "deleteUser")
    public Iterable<UserEntity> deleteData(@RequestParam String id){
        userService.removeOne(id);
        return userService.findAll();
    }

    @PostMapping(value = "getUserByNama")
    public UserEntity getUserByNama(@RequestBody SearchData searchData){
        return userService.getUserByNama(searchData.getKeyword());
    }

    @PostMapping(value = "getUserByEmail")
    public UserEntity getUserByEmail(@RequestBody SearchData searchData){
        return userService.getUserByEmail(searchData.getKeyword());
    }

    @PostMapping(value = "getDataByNama")
    public List<UserEntity> getDataByNama(@RequestBody SearchData searchData){
        return userService.getDataByNama(searchData.getKeyword());
    }
}
