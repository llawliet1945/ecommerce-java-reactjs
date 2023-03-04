package com.myusufalpian.projectecommerce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    public ResponseEntity<String> getAllData() throws JsonProcessingException {
        return categoryService.findAll();
    }

    @GetMapping("getById")
    public ResponseEntity<String> getById(@RequestParam String uuid) throws JsonProcessingException {
        return categoryService.findByUuid(uuid);
    }

    @PostMapping("addNewCategory")
    public ResponseEntity<String> save(@RequestBody CategoryEntity param) throws JsonProcessingException {
        return categoryService.save(param);
    }

    @PutMapping("updateCategory")
    public ResponseEntity<String> updateCategory(@RequestBody CategoryEntity param) throws JsonProcessingException {
        return categoryService.update(param);
    }

    @DeleteMapping(value = "deleteCategory")
    public ResponseEntity<String> deleteData(@RequestParam String id) throws JsonProcessingException {
        return categoryService.removeOne(id);
    }
}
