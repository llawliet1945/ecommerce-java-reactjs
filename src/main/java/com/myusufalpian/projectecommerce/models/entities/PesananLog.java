package com.myusufalpian.projectecommerce.models.entities;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Data
@Entity
@Table(name = "tb_pesanan_log")
public class PesananLog implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String uuid;

    private Integer logType;

    private String logMessage;

    @Temporal(TemporalType.TIMESTAMP)
    private Date waktu;

    @ManyToOne
    @JoinColumn
    private PesananEntity pemesanan;

    @ManyToOne
    @JoinColumn
    private UserEntity user;

}
