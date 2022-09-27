package com.myusufalpian.projectecommerce.services;

import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.myusufalpian.projectecommerce.models.entities.PesananEntity;
import com.myusufalpian.projectecommerce.models.entities.PesananLog;
import com.myusufalpian.projectecommerce.models.entities.UserEntity;
import com.myusufalpian.projectecommerce.models.repositories.PesananRepository;

@Service
public class PesananLogService {
    
    @Autowired
    private PesananRepository pesananRepository;

    public final static int DRAFT = 0;
    public final static int PEMBAYARAN = 10;
    public final static int PACKING = 20;
    public final static int PENGIRIMAN = 30;
    public final static int SELESAI = 40;
    public final static int DIBATALKAN = 90;

    public void saveLog(String username, PesananEntity pesanan, int type, String message){
        PesananLog p = new PesananLog();
        p.setId(UUID.randomUUID().toString());
        p.setLogMessage(message);
        p.setLogType(type);
        p.setPemesanan(pesanan);
        p.setUser(new UserEntity(username));
        p.setWaktu(new Date());
    }

}
