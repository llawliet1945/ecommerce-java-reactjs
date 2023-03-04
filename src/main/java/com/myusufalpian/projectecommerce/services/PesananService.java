package com.myusufalpian.projectecommerce.services;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.myusufalpian.projectecommerce.utilities.GenerateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.myusufalpian.projectecommerce.dto.CartRequest;
import com.myusufalpian.projectecommerce.dto.PesananRequestDTO;
import com.myusufalpian.projectecommerce.dto.PesananResponseDTO;
import com.myusufalpian.projectecommerce.models.entities.PesananEntity;
import com.myusufalpian.projectecommerce.models.entities.PesananItem;
import com.myusufalpian.projectecommerce.models.entities.ProductEntity;
import com.myusufalpian.projectecommerce.models.entities.StatusPesanan;
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
    public ResponseEntity<String> add(String username, PesananRequestDTO request) throws JsonProcessingException {
        PesananEntity pesanan = new PesananEntity();
        pesanan.setUuid(UUID.randomUUID().toString());
        pesanan.setTanggal(new Date());
        pesanan.setNomor(generateNomor());
        pesanan.setUserId(username);
        pesanan.setAlamat(request.getAlamatPengiriman());
        pesanan.setStatus(StatusPesanan.DRAFT);
        pesanan.setWaktu(new Date());
        pesanan.setOngkir(request.getOngkir());

        List<PesananItem> pemesanan = new ArrayList<>();
        for (CartRequest cart : request.getItems()) {
            Optional<ProductEntity> product = productRepository.findById(cart.getProductId());
            if (product.isEmpty()) {
                return GenerateResponse.notFound("Product not found", null);
            }
            
            if(product.get().getStok()<cart.getQty().intValue()){
                return GenerateResponse.badRequest("Stok produk tidak mencukupi, produk hanya tersedia: "+product.get().getStok(), null);
            }

            PesananItem pesananItem = new PesananItem();
            pesananItem.setUuid(UUID.randomUUID().toString());
            pesananItem.setProductId(product.get().getId());
            pesananItem.setHarga(product.get().getHarga());
            pesananItem.setKuantitas(cart.getQty());

            int hrg = pesananItem.getHarga().intValue();
            int qty = pesananItem.getKuantitas().intValue();
            Integer total = hrg * qty;
            pesananItem.setJumlah(BigInteger.valueOf(total));
            pesananItem.setPemesananId(pesanan.getId());
            
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
            ProductEntity product = new ProductEntity(p.getProductId());
            product.setStok(product.getStok()-p.getKuantitas().intValue());
            productRepository.save(product);
            cartService.delete(username, p.getProductId());
        }
        
        pesananLogService.saveLog(username, pesanan, PesananLogService.DRAFT, "Pesanan berhasil dibuat!");

        PesananResponseDTO response = new PesananResponseDTO(save, pemesanan);

        return GenerateResponse.success("Success add new order", response);

    }

    private String generateNomor(){
        return String.format("%016d", System.nanoTime());
    }

    @Transactional
    public ResponseEntity<String> cancel(String uuid, String username) throws JsonProcessingException {
        
        Optional<PesananEntity> pesanan = pesananRepository.findByUuid(uuid);
        if (pesanan.isEmpty()) {
            return GenerateResponse.notFound("Order not found", null);
        }

        if(!username.equals(pesanan.get().getUserId())){
            return GenerateResponse.badRequest("Cancel order just can be process with user", null);
        }

        if(!StatusPesanan.PENGIRIMAN.equals(pesanan.get().getStatus())){
            return GenerateResponse.badRequest("Orders cannot be canceled because they are in the process of being shipped", null);
        }else if(!StatusPesanan.SELESAI.equals(pesanan.get().getStatus())){
            return GenerateResponse.badRequest("OrOrders cannot be canceled because they have been received", null);
        }

        pesanan.get().setStatus(StatusPesanan.DIBATALKAN);
        pesananRepository.save(pesanan.get());

        pesananLogService.saveLog(username, pesanan.get(), PesananLogService.DIBATALKAN, "Pesanan berhasil dibatalkan!");

        return GenerateResponse.success("Success cancel order", null);

    }


    @Transactional
    public ResponseEntity<String> receive(String uuid, String username) throws JsonProcessingException {
        
        Optional<PesananEntity> pesanan = pesananRepository.findByUuid(uuid);
        if (pesanan.isEmpty()) {
            return GenerateResponse.notFound("Order not found", null);
        }

        if(!username.equals(pesanan.get().getUserId())){
            return GenerateResponse.badRequest("Orders can only be canceled by the person concerned", null);
        }

        if(!StatusPesanan.PENGIRIMAN.equals(pesanan.get().getStatus())){
            return GenerateResponse.badRequest("Receiving failed, current order status is: "+ pesanan.get().getStatus(), null);
        }

        pesanan.get().setStatus(StatusPesanan.SELESAI);
        pesananRepository.save(pesanan.get());

        pesananLogService.saveLog(username, pesanan.get(), PesananLogService.SELESAI, "Pesanan telah diterima!");

        return GenerateResponse.success("Order received!", null);

    }

    public ResponseEntity<String> findAllPesanan(String username, int page, int limit) throws JsonProcessingException {
        List<PesananEntity> pesanan = pesananRepository.findByUserId(username, PageRequest.of(page, limit, Sort.by("waktu_pemesanan").descending()));
        if (pesanan.isEmpty()) {
            return GenerateResponse.notFound("Order not found", null);
        }
        return GenerateResponse.success("Get all order success", pesanan);
    }
    
    @Transactional
    public ResponseEntity<String> confirmationPayment(String uuid, String username) throws JsonProcessingException {
        
        Optional<PesananEntity> pesanan = pesananRepository.findByUuid(uuid);
        if (pesanan.isEmpty()){
            return GenerateResponse.notFound("Order not found", null);
        }

        if(!StatusPesanan.DRAFT.equals(pesanan.get().getStatus())){
            return GenerateResponse.notFound("Payment confirmation failed, current order status is: "+ pesanan.get().getStatus(), null);
        }

        pesanan.get().setStatus(StatusPesanan.PEMBAYARAN);

        pesananRepository.save(pesanan.get());

        pesananLogService.saveLog(username, pesanan.get(), PesananLogService.PEMBAYARAN, "Pembayaran sukses dikonfirmasi, terimakasih atas pembayarannya, pesanan akan segera diproses");

        return GenerateResponse.success("Confirmation payment success", null);

    }

    @Transactional
    public ResponseEntity<String> packing(String uuid, String username) throws JsonProcessingException {
        
        Optional<PesananEntity> pesanan = pesananRepository.findByUuid(uuid);
        if (pesanan.isEmpty()) {
            return GenerateResponse.notFound("Order not found", null);
        }

        if(!StatusPesanan.PACKING.equals(pesanan.get().getStatus())){
            return GenerateResponse.badRequest("Order packing has not been done, the current order status is: "+ pesanan.get().getStatus(), null);
        }

        pesanan.get().setStatus(StatusPesanan.PACKING);
        pesananRepository.save(pesanan.get());

        pesananLogService.saveLog(username, pesanan.get(), PesananLogService.PACKING, "Pesanan sedang diproses dan akan segera dikirimkan ke alamat tujuan");

        return GenerateResponse.success("Packing order success", null);

    }

    @Transactional
    public ResponseEntity<String> sent(String uuid, String username) throws JsonProcessingException {
        
        Optional<PesananEntity> pesanan = pesananRepository.findByUuid(uuid);
        if (pesanan.isEmpty()) {
            return GenerateResponse.notFound("Order not found!", null);
        }

        if(!StatusPesanan.PACKING.equals(pesanan.get().getStatus())){
            return GenerateResponse.badRequest("Order delivery has not been made, the current order status is: "+ pesanan.get().getStatus(), null);
        }

        pesanan.get().setStatus(StatusPesanan.DIKIRIM);
        pesananRepository.save(pesanan.get());

        pesananLogService.saveLog(username, pesanan.get(), PesananLogService.PENGIRIMAN, "Pesanan sedang dikirimkan ke alamat tujuan");

        return GenerateResponse.success("Sent order success", null);
    }

    public ResponseEntity<String> search(String filterText, int page, int limit) throws JsonProcessingException {
        List<PesananEntity> pesanan = pesananRepository.search(filterText, PageRequest.of(page, limit, Sort.by("waktu_pemesanan").descending()));
        if (pesanan.isEmpty()){
            return GenerateResponse.notFound("Order not found", null);
        }
        return GenerateResponse.success("Get data success", pesanan);
    }


}
