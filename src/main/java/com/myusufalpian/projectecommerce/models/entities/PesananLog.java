package com.myusufalpian.projectecommerce.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "tb_pesanan_log", schema = "public")
public class PesananLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String uuid;

    private Integer logType;

    private String logMessage;

    @Temporal(TemporalType.TIMESTAMP)
    private Date waktu;

    @Column(name = "pemesanan_id")
    private Integer pemesanan;

    @Column(name = "user_id")
    private String userId;

}
