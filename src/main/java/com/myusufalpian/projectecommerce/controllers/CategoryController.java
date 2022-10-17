package com.myusufalpian.projectecommerce.controllers;

import com.myusufalpian.projectecommerce.dto.ResponseData;
import com.myusufalpian.projectecommerce.models.entities.CategoryEntity;
import com.myusufalpian.projectecommerce.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/category")
//  localhost:5050/api/category
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("getAllData")
    public List<CategoryEntity> getAllData(){
        return categoryService.findAll();
    }

    @GetMapping("getById")
    public CategoryEntity getById(@RequestParam String uuid){
        return categoryService.findByUuid(uuid);
    }

    @PostMapping("addNewCategory")
    public ResponseEntity<ResponseData<CategoryEntity>> save(@RequestBody CategoryEntity param, Errors errors) {
        ResponseData<CategoryEntity> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for (ObjectError objectError : errors.getAllErrors()){
                responseData.getMessages().add(objectError.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setMessages(Collections.singletonList("Kategori sukses ditambahkan!"));
        responseData.setPayload(categoryService.save(param));
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("updateCategory")
    public ResponseEntity<ResponseData<CategoryEntity>> updateCategory(@RequestBody CategoryEntity param, Errors errors) {

        ResponseData<CategoryEntity> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for (ObjectError objectError : errors.getAllErrors()){
                responseData.getMessages().add(objectError.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setMessages(Collections.singletonList("Kategori sukses diubah!"));
        responseData.setPayload(categoryService.update(param));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping(value = "deleteCategory")
    public Iterable<CategoryEntity> deleteData(@RequestParam String id){
        categoryService.removeOne(id);
        return categoryService.findAll();
    }
}
