package com.myusufalpian.projectecommerce.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myusufalpian.projectecommerce.dto.SearchData;
import com.myusufalpian.projectecommerce.models.entities.ProductEntity;
import com.myusufalpian.projectecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @GetMapping("getAllData")
    public ResponseEntity<String> getAllData() throws JsonProcessingException {
        return productService.findAll();
    }

    @GetMapping("getById")
    public ResponseEntity<String> getById(@RequestParam Integer id) throws JsonProcessingException {
        return productService.findById(id);
    }

    @PostMapping("addNewProduct")
    public ResponseEntity<String> save(@RequestBody ProductEntity param) throws JsonProcessingException {
        return productService.save(param);
    }

    @PutMapping("updateProduct")
    public ResponseEntity<String> updateProduct(@RequestBody ProductEntity param) throws JsonProcessingException {
        return productService.update(param);
    }

    @DeleteMapping(value = "deleteProduct")
    public ResponseEntity<String> deleteData(@RequestParam Integer id) throws JsonProcessingException {
        return productService.removeOne(id);
    }

    @PostMapping(value = "getProductByNama")
    public ResponseEntity<String> getProductByNama(@RequestBody SearchData searchData) throws JsonProcessingException {
        return productService.getProductByNama(searchData.getKeyword());
    }

    @PostMapping(value = "getDataByNama")
    public ResponseEntity<String> getDataByNama(@RequestBody SearchData searchData) throws JsonProcessingException {
        return productService.getDataByNama(searchData.getKeyword());
    }
}
