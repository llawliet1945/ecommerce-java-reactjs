package com.myusufalpian.projectecommerce.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.myusufalpian.projectecommerce.models.entities.PesananEntity;
import com.myusufalpian.projectecommerce.models.entities.PesananItem;

import java.util.List;
import lombok.Data;

@Data
public class PesananResponseDTO implements Serializable{
    private String id;
    private String nomorPesanan;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Jakarta")
    private Date tanggal;
    private String namaPelanggan;
    private String alamatPengirimaan;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Jakarta")
    private Date waktuPemesanan;
    private BigInteger jumlah;
    private BigInteger ongkosKirim;
    private BigInteger total;
    private List<PesananResponseDTO.Item> items;

    @Data
    public static class Item implements Serializable{
        private Integer productId;
        private String namaProduct;
        private BigInteger qty;
        private BigInteger harga;
        private BigInteger jumlah;
    }

    public PesananResponseDTO(PesananEntity pesananEntity, List<PesananItem> pesananItems){

        this.id = pesananEntity.getId();
        this.nomorPesanan = pesananEntity.getNomor();
        this.tanggal = pesananEntity.getTanggal();
        this.namaPelanggan = pesananEntity.getUser().getNama();
        this.alamatPengirimaan = pesananEntity.getAlamat();
        this.ongkosKirim = pesananEntity.getOngkir();
        this.waktuPemesanan = pesananEntity.getWaktu();
        this.jumlah = pesananEntity.getJumlah();
        this.total = pesananEntity.getTotal();
        
        items = new ArrayList<>();
        
        for (PesananItem pesananItem : pesananItems) {
        
            Item item = new Item();
            item.setProductId(Integer.parseInt(pesananItem.getProduct().getId()));
            item.setNamaProduct(pesananItem.getProduct().getNama());
            item.setQty(pesananItem.getJumlah());
            item.setHarga(pesananItem.getHarga());
            item.setJumlah(pesananItem.getJumlah());
            items.add(item);
        
        }
    }
}
