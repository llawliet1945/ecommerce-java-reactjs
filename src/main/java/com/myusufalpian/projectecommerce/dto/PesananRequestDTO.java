package com.myusufalpian.projectecommerce.dto;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import lombok.Data;

@Data
public class PesananRequestDTO implements Serializable{
    private BigInteger ongkir;
    private String alamatPengiriman;
    private List<CartRequest> items;
}
