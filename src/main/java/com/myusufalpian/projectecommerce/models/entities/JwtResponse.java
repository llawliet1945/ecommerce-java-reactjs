package com.myusufalpian.projectecommerce.models.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse implements Serializable{
    private String token;
    private String type = "bearer";
    private String email;
    private String username;
    public JwtResponse(String accessToken, String email, String username) {
        this.token = accessToken;
        this.email = email;
        this.username = username;
    }

    
}
