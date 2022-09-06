package com.myusufalpian.projectecommerce.models.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "tb_produk")
public class ProductEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "nama_produk", length = 100)
    private String nama;

    @Column(name = "deskripsi_produk",length = 1000)
    private String deskripsi;

    @Column(name = "gambar_produk",length = 255)
    private String gambar;

    @Column(name = "stok_produk")
    private int stok;

    @Column(name = "harga_produk")
    private BigDecimal harga;

    @ManyToOne
    @JoinColumn
    private CategoryEntity category;
}
