package com.myusufalpian.projectecommerce.services;

import java.math.BigInteger;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myusufalpian.projectecommerce.dto.CartRequest;
import com.myusufalpian.projectecommerce.dto.PesananRequestDTO;
import com.myusufalpian.projectecommerce.dto.PesananResponseDTO;
import com.myusufalpian.projectecommerce.exceptions.BadRequestException;
import com.myusufalpian.projectecommerce.exceptions.ResourceNotFoundException;
import com.myusufalpian.projectecommerce.models.entities.PesananEntity;
import com.myusufalpian.projectecommerce.models.entities.PesananItem;
import com.myusufalpian.projectecommerce.models.entities.ProductEntity;
import com.myusufalpian.projectecommerce.models.entities.StatusPesanan;
import com.myusufalpian.projectecommerce.models.entities.UserEntity;
import com.myusufalpian.projectecommerce.models.repositories.PesananItemRepository;
import com.myusufalpian.projectecommerce.models.repositories.PesananRepository;
import com.myusufalpian.projectecommerce.models.repositories.ProductRepository;

@Service
public class PesananService {

    @Autowired
    private ProductRepository productRepository;
    
    @Autowired
    private PesananRepository pesananRepository;
    
    @Autowired
    private PesananItemRepository pesananItemRepository;
    
    @Autowired
    private CartService cartService;

    @Autowired
    private PesananLogService pesananLogService;


    @Transactional
    public PesananResponseDTO add(String username, PesananRequestDTO request){
        PesananEntity pesanan = new PesananEntity();
        pesanan.setId(UUID.randomUUID().toString());
        pesanan.setTanggal(new Date());
        pesanan.setNomor(generateNomor());
        pesanan.setUser(new UserEntity(username));
        pesanan.setAlamat(request.getAlamatPengiriman());
        pesanan.setStatus(StatusPesanan.DRAFT);
        pesanan.setWaktu(new Date());
        pesanan.setOngkir(request.getOngkir());

        List<PesananItem> pemesanan = new ArrayList<>();
        for (CartRequest cart : request.getItems()) {
            ProductEntity product = productRepository.findById(cart.getProductId()).orElseThrow(()-> new BadRequestException("Product dengan id: "+cart.getProductId()+" tidak ditemukan"));
            
            if(product.getStok()<cart.getQty().intValue()){
                throw new BadRequestException("Stok produk tidak mencukupi, produk hanya tersedia: "+product.getStok());
            }

            PesananItem pesananItem = new PesananItem();
            pesananItem.setId(UUID.randomUUID().toString());
            pesananItem.setProduct(product);
            pesananItem.setHarga(product.getHarga());
            pesananItem.setKuantitas(cart.getQty());
            int hrg = pesananItem.getHarga().intValue();
            int qty = pesananItem.getKuantitas().intValue();
            Integer total = hrg * qty;
            pesananItem.setJumlah(BigInteger.valueOf(total));
            pesananItem.setPemesanan(pesanan);
            pemesanan.add(pesananItem);
        
        }

        BigInteger jumlah = BigInteger.ZERO;
        for (PesananItem p : pemesanan) {
            jumlah = jumlah.add(p.getJumlah());
        }

        pesanan.setJumlah(jumlah);
        pesanan.setOngkir(request.getOngkir());
        pesanan.setTotal(pesanan.getJumlah().add(pesanan.getOngkir()));

        PesananEntity save = pesananRepository.save(pesanan);

        for (PesananItem p : pemesanan) {
            pesananItemRepository.save(p);
            ProductEntity product = new ProductEntity();
            product.setStok(product.getStok()-p.getKuantitas().intValue());
            productRepository.save(product);
            cartService.delete(username, product.getId());
        }

        pesananLogService.saveLog(username, pesanan, PesananLogService.DRAFT, "Pesanan berhasil dibuat!");

        PesananResponseDTO response = new PesananResponseDTO(save, pemesanan);

        return response;

    }

    private String generateNomor(){
        return String.format("%016d", System.nanoTime());
    }

    public PesananEntity cancel(String id, String username){
        
        PesananEntity pesanan = pesananRepository.findById(id).orElseThrow(()-> new ResourceNotFoundException("Pesanan dengan id: "+id+" tidak ditemukan!"));

        if(!username.equals(pesanan.getUser().getUsername())){
            throw new BadRequestException("Pesanan hanya dapat dibatalkan oleh yang bersangkutan!");
        }

        if(!StatusPesanan.PENGIRIMAN.equals(pesanan.getStatus())){
            throw new BadRequestException("Pesanan tidak dapat dibatalkan karena sedang dalam proses pengiriman!");
        }else if(!StatusPesanan.SELESAI.equals(pesanan.getStatus())){
            throw new BadRequestException("Pesanan tidak dapat dibatalkan karena pesanan sudah diterima!");
        }

        pesanan.setStatus(StatusPesanan.DIBATALKAN);
        PesananEntity save = pesananRepository.save(pesanan);

        pesananLogService.saveLog(username, pesanan, PesananLogService.DIBATALKAN, "Pesanan berhasil dibatalkan!");

        return save;

    }
}
