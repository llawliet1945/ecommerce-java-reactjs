package com.myusufalpian.projectecommerce.models.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class SignUpRequest implements Serializable{
    private String username;
    private String email;
    private String nama;
    private String alamat;
    private String password;
}
