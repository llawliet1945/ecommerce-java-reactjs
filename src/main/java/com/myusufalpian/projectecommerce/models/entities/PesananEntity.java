package com.myusufalpian.projectecommerce.models.entities;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Table(name = "tb_pesanan", schema = "public")
public class PesananEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uuid;

    @Column(name = "nomor_pemesanan", length = 100)
    private String nomor;

    @Column(name = "tanggal_pemesanan")
    @Temporal(TemporalType.DATE)
    private Date tanggal;

    @Column(name = "user_id")
    private String userId;

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
