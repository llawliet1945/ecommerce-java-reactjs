package com.myusufalpian.projectecommerce.models.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "tb_keranjang", schema = "public")
public class KeranjangEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uuid;

    @Column(name = "product_id")
    private Integer productId;
    
    @Column(name = "user_id")
    private String userId;

    private BigInteger kuantitas;

    private BigInteger harga;

    private BigInteger total;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "waktu_dibuat")
    private Date waktu;
}
