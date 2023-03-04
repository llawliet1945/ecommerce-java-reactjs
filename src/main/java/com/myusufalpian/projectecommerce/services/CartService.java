package com.myusufalpian.projectecommerce.services;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myusufalpian.projectecommerce.utilities.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.myusufalpian.projectecommerce.models.entities.KeranjangEntity;
import com.myusufalpian.projectecommerce.models.entities.ProductEntity;
import com.myusufalpian.projectecommerce.models.entities.UserEntity;
import com.myusufalpian.projectecommerce.models.repositories.KeranjangRepository;
import com.myusufalpian.projectecommerce.models.repositories.ProductRepository;
import com.myusufalpian.projectecommerce.models.repositories.UserRepository;

@Service
public class CartService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KeranjangRepository keranjangRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public ResponseEntity<String> save(String username, Integer productId, BigInteger qty) throws JsonProcessingException {
        
        Optional<ProductEntity> product = productRepository.findById(productId);
        if (product.isEmpty()) {
            return GenerateResponse.notFound("Product not found!", null);
        }
        
        Optional<KeranjangEntity> keranjang = keranjangRepository.findByUserIdAndProductId(username, productId);
        if (keranjang.isEmpty()) {
            return GenerateResponse.notFound("Cart not found!", null);
        }

        Optional<UserEntity> user = userRepository.findByUsername(username);
        if (user.isEmpty()) {
            return GenerateResponse.notFound("User not found!", null);
        }

        KeranjangEntity keranjangEntity;

        if(keranjang.isPresent()){

            keranjangEntity = keranjang.get();

            int kuantitas = keranjang.get().getKuantitas().intValue();
            int quantity = qty.intValue();
            Integer Qty = kuantitas + quantity;
            keranjang.get().setKuantitas(BigInteger.valueOf(Qty));
            
            int tot = keranjang.get().getHarga().intValue();
            int brg = keranjang.get().getKuantitas().intValue();
            Integer total = tot * brg;
            keranjang.get().setTotal(BigInteger.valueOf(total));
            
            keranjangRepository.save(keranjang.get());
        
        }else{
        
            keranjangEntity = new KeranjangEntity();
        
            keranjangEntity.setUuid(UUID.randomUUID().toString());
            keranjangEntity.setProductId(product.get().getId());
            keranjangEntity.setKuantitas(qty);
            keranjangEntity.setHarga(product.get().getHarga());
        
            int tot = keranjangEntity.getHarga().intValue();
            int brg = keranjangEntity.getKuantitas().intValue();
            Integer total = tot * brg;
            keranjangEntity.setTotal(BigInteger.valueOf(total));
        
            keranjangEntity.setWaktu(new Date());
            keranjangEntity.setUserId(user.get().getUsername());
        
            keranjangRepository.save(keranjangEntity);
        
        }

        return GenerateResponse.success("Add new cart success", null);
    
    }
    
    @Transactional
    public ResponseEntity<String> updateQty(String username, Integer productId, BigInteger qty) throws JsonProcessingException {
        Optional<KeranjangEntity> keranjang = keranjangRepository.findByUserIdAndProductId(username, productId);
        if (keranjang.isEmpty()) {
            return GenerateResponse.notFound("Cart not found", null);
        }
        keranjang.get().setKuantitas(qty);
        int tot = keranjang.get().getHarga().intValue();
            int brg = keranjang.get().getKuantitas().intValue();
            Integer total = tot * brg;
            keranjang.get().setTotal(BigInteger.valueOf(total));
        keranjang.get().setTotal(BigInteger.valueOf(total));
        keranjangRepository.save(keranjang.get());
        return GenerateResponse.success("Update cart success", null);
    }

    @Transactional
    public ResponseEntity<String> delete(String username, Integer productId) throws JsonProcessingException {
        Optional<KeranjangEntity> keranjang = keranjangRepository.findByUserIdAndProductId(username, productId);
        if (keranjang.isEmpty()) {
            return GenerateResponse.notFound("Cart not found", null);
        }
        keranjangRepository.delete(keranjang.get());
        return GenerateResponse.success("Delete cart success", null);
    }

    @Transactional
    public ResponseEntity<String> findByUserUsername(String username) throws JsonProcessingException {
        List<KeranjangEntity> keranjang = keranjangRepository.findByUserId(username);
        if (keranjang.isEmpty()) {
            return GenerateResponse.notFound("Cart not found!", null);
        }
        return GenerateResponse.success("Get cart success", keranjang);
    }
}
