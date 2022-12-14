package com.myusufalpian.projectecommerce.services;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myusufalpian.projectecommerce.exceptions.BadRequestException;
import com.myusufalpian.projectecommerce.models.entities.KeranjangEntity;
import com.myusufalpian.projectecommerce.models.entities.ProductEntity;
import com.myusufalpian.projectecommerce.models.entities.UserEntity;
import com.myusufalpian.projectecommerce.models.repositories.KeranjangRepository;
import com.myusufalpian.projectecommerce.models.repositories.ProductRepository;

@Service
public class CartService {
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private KeranjangRepository keranjangRepository;

    @Transactional
    public KeranjangEntity save(String username, String productUuid, BigInteger qty){
        
        ProductEntity product = productRepository.findByUuid(productUuid).orElseThrow(()-> new RuntimeException(
                "Product not " +
                "found!"));
        
        Optional<KeranjangEntity> keranjang = keranjangRepository.findByUserUsernameAndProductUuid(username, productUuid);
        
        KeranjangEntity keranjangEntity;

        if(keranjang.isPresent()){
            
            keranjangEntity = keranjang.get();

            int kuantitas = keranjangEntity.getKuantitas().intValue();
            int quantity = qty.intValue();
            Integer Qty = kuantitas + quantity;
            keranjangEntity.setKuantitas(BigInteger.valueOf(Qty));
            
            int tot = keranjang.get().getHarga().intValue();
            int brg = keranjangEntity.getKuantitas().intValue();
            Integer total = tot * brg;
            keranjangEntity.setTotal(BigInteger.valueOf(total));
            
            keranjangRepository.save(keranjangEntity);
        
        }else{
        
            keranjangEntity = new KeranjangEntity();
        
            keranjangEntity.setUuid(UUID.randomUUID().toString());
            keranjangEntity.setProduct(product);
            keranjangEntity.setKuantitas(qty);
            keranjangEntity.setHarga(product.getHarga());
        
            int tot = keranjangEntity.getHarga().intValue();
            int brg = keranjangEntity.getKuantitas().intValue();
            Integer total = tot * brg;
            keranjangEntity.setTotal(BigInteger.valueOf(total));
        
            keranjangEntity.setWaktu(new Date());
            keranjangEntity.setUser(new UserEntity(username));
        
            keranjangRepository.save(keranjangEntity);
        
        }

        return keranjangEntity;
    
    }
    
    @Transactional
    public KeranjangEntity updateQty(String username, String productUuid, BigInteger qty){
        KeranjangEntity keranjang =
                keranjangRepository.findByUserUsernameAndProductUuid(username, productUuid).orElseThrow(()-> new BadRequestException("Produk tidak ditemukan didalam keranjang anda"));
        keranjang.setKuantitas(qty);
        int tot = keranjang.getHarga().intValue();
            int brg = keranjang.getKuantitas().intValue();
            Integer total = tot * brg;
            keranjang.setTotal(BigInteger.valueOf(total));
        keranjang.setTotal(BigInteger.valueOf(total));
        keranjangRepository.save(keranjang);
        return keranjang;
    }

    @Transactional
    public void delete(String username, String productUuid){
        KeranjangEntity keranjang =
                keranjangRepository.findByUserUsernameAndProductUuid(username, productUuid).orElseThrow(()-> new BadRequestException("Produk tidak ditemukan didalam keranjang anda"));
        keranjangRepository.delete(keranjang);
    }

    @Transactional
    public List<KeranjangEntity> findByUserUsername(String username){
        return keranjangRepository.findByUserUsername(username);
    }
}
