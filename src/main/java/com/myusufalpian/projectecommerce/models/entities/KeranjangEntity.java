package com.myusufalpian.projectecommerce.models.entities;

import lombok.*;
import org.springframework.data.jpa.repository.JpaRepository;

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
@Table(name = "tb_keranjang")
public class KeranjangEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @ManyToOne
    @JoinColumn
    private ProductEntity product;

    @ManyToOne
    @JoinColumn
    private UserEntity user;

    private BigInteger kuantitas;

    private BigInteger harga;

    private BigInteger total;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "waktu_dibuat")
    private Date waktu;
}
