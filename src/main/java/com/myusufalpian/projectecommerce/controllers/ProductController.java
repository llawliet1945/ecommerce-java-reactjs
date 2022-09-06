package com.myusufalpian.projectecommerce.controllers;

import com.myusufalpian.projectecommerce.dto.ResponseData;
import com.myusufalpian.projectecommerce.dto.SearchData;
import com.myusufalpian.projectecommerce.models.entities.ProductEntity;
import com.myusufalpian.projectecommerce.models.repositories.CategoryRepository;
import com.myusufalpian.projectecommerce.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/api/product")
//  localhost:5050/api/product
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("getAllData")
    public Iterable<ProductEntity> getAllData(){
        return productService.findAll();
    }

    @GetMapping("getById")
    public ProductEntity getById(@RequestParam String id){
        return productService.findById(id);
    }

    @PostMapping("addNewProduct")
    public ResponseEntity<ResponseData<ProductEntity>> save(@RequestBody ProductEntity param, Errors errors) {
        ResponseData<ProductEntity> responseData = new ResponseData<>();
        if(errors.hasErrors()){
            for (ObjectError objectError : errors.getAllErrors()){
                responseData.getMessages().add(objectError.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setMessages(Collections.singletonList("Produk sukses ditambahkan!"));
        responseData.setPayload(productService.save(param));
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("updateProduct")
    public ResponseEntity<ResponseData<ProductEntity>> updateProduct(@RequestBody ProductEntity param, Errors errors) {

        ResponseData<ProductEntity> responseData = new ResponseData<>();

        if(errors.hasErrors()){
            for (ObjectError objectError : errors.getAllErrors()){
                responseData.getMessages().add(objectError.getDefaultMessage());
            }
            responseData.setStatus(false);
            responseData.setPayload(null);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseData);
        }
        responseData.setStatus(true);
        responseData.setMessages(Collections.singletonList("Produk sukses diubah!"));
        responseData.setPayload(productService.update(param));
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping(value = "deleteProduct")
    public Iterable<ProductEntity> deleteData(@RequestParam String id){
        productService.removeOne(id);
        return productService.findAll();
    }

    @PostMapping(value = "getProductByNama")
    public ProductEntity getProductByNama(@RequestBody SearchData searchData){
        return productService.getProductByNama(searchData.getKeyword());
    }

    @PostMapping(value = "getDataByNama")
    public List<ProductEntity> getDataByNama(@RequestBody SearchData searchData){
        return productService.getDataByNama(searchData.getKeyword());
    }
}
