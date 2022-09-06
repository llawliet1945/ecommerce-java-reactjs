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
@Table(name = "tb_pesanan")
public class PesananEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String id;

    @Column(name = "nomor_pemesanan", length = 100)
    private String nomor;

    @Column(name = "tanggal_pemesanan")
    @Temporal(TemporalType.DATE)
    private Date tanggal;

    @ManyToOne
    @JoinColumn
    private UserEntity user;

    @Column(name = "alamat_pengiriman", length = 300)
    private String alamat;

    @Column(name = "jumlah_pemesanan")
    private BigInteger jumlah;

    @Column(name = "ongkir_pemesanan")
    private BigInteger ongkir;

    @Column(name = "total_pembayaran")
    private BigInteger total;

    @Column(name = "status_pemesanan")
    @Enumerated(EnumType.STRING)
    private StatusPesanan status;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "waktu_pemesanan")
    private Date waktu;
}
