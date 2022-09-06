package com.myusufalpian.projectecommerce.services;

import com.myusufalpian.projectecommerce.exceptions.BadRequestException;
import com.myusufalpian.projectecommerce.exceptions.ResourceNotFoundException;
import com.myusufalpian.projectecommerce.models.entities.ProductEntity;
import com.myusufalpian.projectecommerce.models.repositories.CategoryRepository;
import com.myusufalpian.projectecommerce.models.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    public ProductEntity save(ProductEntity productEntity){

        if(!StringUtils.hasText(productEntity.getNama())){
            throw new BadRequestException("Nama produk tidak boleh kosong!");
        }

        if(productEntity.getCategory()==null){
            throw new BadRequestException("Kategori tidak boleh kosong!");
        }

        if(!StringUtils.hasText(productEntity.getCategory().getId())){
            throw new BadRequestException("Id kategori tidak boleh kosong!");
        }

        categoryRepository.findById(productEntity.getCategory().getId())
                .orElseThrow(() -> new BadRequestException(
                        "Kategori dengan id: "+productEntity.getCategory().getId()+" tidak ditemukan!"
                ));

        productEntity.setId(UUID.randomUUID().toString());
        return productRepository.save(productEntity);
    }

    public ProductEntity update(ProductEntity productEntity){
        if(!StringUtils.hasText(productEntity.getId())){
            throw new BadRequestException("Id produk tidak boleh kosong!");
        }

        if(!StringUtils.hasText(productEntity.getNama())){
            throw new BadRequestException("Nama produk tidak boleh kosong!");
        }

        if(productEntity.getCategory()==null){
            throw new BadRequestException("Kategori tidak boleh kosong!");
        }

        if(!StringUtils.hasText(productEntity.getCategory().getId())){
            throw new BadRequestException("Id kategori tidak boleh kosong!");
        }

        categoryRepository.findById(productEntity.getCategory().getId())
                .orElseThrow(() -> new BadRequestException(
                        "Kategori dengan id: "+productEntity.getCategory().getId()+" tidak ditemukan!"
                ));
        return productRepository.save(productEntity);
    }

    public ProductEntity findById(String id){
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Produk dengan Id: "+
                        id+" tidak ditemukan!"));
    }

    public List<ProductEntity> findAll(){
        return productRepository.findAll();
    }

    public void removeOne(String id){
        productRepository.deleteById(id);
    }

    public ProductEntity getProductByNama(String nama){
        return productRepository.findByNama(nama);
    }

    public List<ProductEntity> getDataByNama(String nama){
        return productRepository.findByNamaStartingWith(nama);
    }

    public ProductEntity changePicture(String id, String gambar){
        ProductEntity productEntity = findById(id);
        productEntity.setGambar(gambar);
        return productRepository.save(productEntity);
    }
}
