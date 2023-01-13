package com.myusufalpian.projectecommerce.models.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "tb_produk", schema = "public")
public class ProductEntity implements Serializable {

    public ProductEntity(Integer produkId) {
    }

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid_produk")
    private String uuid;

    @Column(name = "nama_produk", length = 100)
    private String nama;

    @Column(name = "deskripsi_produk",length = 1000)
    private String deskripsi;

    @Column(name = "gambar_produk",length = 255)
    private String gambar;

    @Column(name = "stok_produk")
    private int stok;

    @Column(name = "harga_produk")
    private BigInteger harga;


    @Column(name = "id_category")
    private Integer idCategory;
}
