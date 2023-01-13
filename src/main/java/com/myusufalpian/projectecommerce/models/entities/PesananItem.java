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
@Table(name = "tb_pesanan_item", schema = "public")
public class PesananItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uuid;

    @Column(name = "kuantitas_pemesanan_item")
    private BigInteger kuantitas;

    private BigInteger harga;

    @Column(name = "jumlah_pemesanan_item")
    private BigInteger jumlah;
    
    @Column(name = "product_id")
    private Integer productId;
    
    @Column(name = "pemesanan_id")
    private Integer pemesananId;
}
