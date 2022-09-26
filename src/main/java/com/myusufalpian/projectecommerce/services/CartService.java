package com.myusufalpian.projectecommerce.services;

import java.math.BigInteger;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public KeranjangEntity save(String username, String produkId, BigInteger qty){
        Optional<ProductEntity> product = productRepository.findById(produkId);
        product.orElseThrow(()-> new RuntimeException("Product not found!"));
        
        Optional<KeranjangEntity> keranjang = keranjangRepository.findByUsernameAndProdukId(username, produkId);
        
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
            keranjangEntity.setId(UUID.randomUUID().toString());
            keranjangEntity.setProduct(new ProductEntity(produkId));
            keranjangEntity.setKuantitas(qty);
            keranjangEntity.setHarga(product.get().getHarga());
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
}
