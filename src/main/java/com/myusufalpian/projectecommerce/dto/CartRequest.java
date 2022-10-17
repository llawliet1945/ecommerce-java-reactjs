package com.myusufalpian.projectecommerce.dto;

import java.io.Serializable;
import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartRequest implements Serializable{
    
    private Integer productId;
    private String productUuid;
    private BigInteger qty;

}
