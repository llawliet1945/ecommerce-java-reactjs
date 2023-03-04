package com.myusufalpian.projectecommerce.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myusufalpian.projectecommerce.models.entities.CategoryEntity;
import com.myusufalpian.projectecommerce.models.entities.ProductEntity;
import com.myusufalpian.projectecommerce.models.repositories.CategoryRepository;
import com.myusufalpian.projectecommerce.models.repositories.ProductRepository;
import com.myusufalpian.projectecommerce.utilities.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ResponseEntity<String> save(ProductEntity productEntity) throws JsonProcessingException {

        if(!StringUtils.hasText(productEntity.getNama())){
            return GenerateResponse.badRequest("Username not found", null);
        }

        if(productEntity.getIdCategory()==null){
            return GenerateResponse.badRequest("Kategori tidak boleh kosong!", null);
        }

        Optional<CategoryEntity> category = categoryRepository.findById(productEntity.getIdCategory());
        if (category.isEmpty()) {
            return GenerateResponse.notFound("Kategori tidak ditemukan", null);
        }

        productEntity.setUuid(UUID.randomUUID().toString());
        productRepository.save(productEntity);
        return GenerateResponse.success("Add new product success", null);
    }

    public ResponseEntity<String> update(ProductEntity productEntity) throws JsonProcessingException {
        if(!StringUtils.hasText(productEntity.getId().toString())){
            return GenerateResponse.badRequest("Product id cannot be null", null);
        }

        if(!StringUtils.hasText(productEntity.getNama())){
            return GenerateResponse.badRequest("Product name cannot be null", null);
        }

        if(productEntity.getIdCategory()==null){
            return GenerateResponse.badRequest("Category id cannot be null", null);
        }

        Optional<CategoryEntity> category = categoryRepository.findById(productEntity.getIdCategory());
        if (category.isEmpty()) {
            return GenerateResponse.notFound("Category not found", null);
        }
        return GenerateResponse.badRequest("Update data success", null);
    }

    public ResponseEntity<String> findById(Integer id) throws JsonProcessingException {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return GenerateResponse.notFound("Product not found", null);
        }
        return GenerateResponse.success("Get data success", product.get());
    }

    public ResponseEntity<String> findAll() throws JsonProcessingException {
        List<ProductEntity> product = productRepository.findAll();
        if (product.isEmpty()) {
            return GenerateResponse.notFound("Product not found", null);
        }
        return GenerateResponse.success("Get data success", product);
    }

    public ResponseEntity<String> removeOne(Integer id) throws JsonProcessingException {
        Optional<ProductEntity> product = productRepository.findById(id);
        if (product.isEmpty()) {
            return GenerateResponse.notFound("Product not found", null);
        }
        productRepository.deleteById(id);
        return GenerateResponse.success("Delete data success", null);
    }

    public ResponseEntity<String> getProductByNama(String nama) throws JsonProcessingException {
        List<ProductEntity> product = productRepository.findByNama(nama);
        if (product.isEmpty()) {
            return GenerateResponse.notFound("Product not found", null);
        }
        return GenerateResponse.success("Delete data success", product);
    }

    public ResponseEntity<String> getDataByNama(String nama) throws JsonProcessingException {
        List<ProductEntity> product = productRepository.findByNamaStartingWith(nama);
        if (product.isEmpty()) {
            return GenerateResponse.notFound("Product not found", null);
        }
        return GenerateResponse.success("Get data success", product);
    }

    public ResponseEntity<String> changePicture(Integer id, String gambar) throws JsonProcessingException {
        Optional<ProductEntity> productEntity = productRepository.findById(id);
        productEntity.get().setGambar(gambar);
        productRepository.save(productEntity.get());
        return GenerateResponse.success("Update image product success", null);
    }
}
