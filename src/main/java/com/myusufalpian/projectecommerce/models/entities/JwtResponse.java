package com.myusufalpian.projectecommerce.models.entities;

import java.io.Serializable;

import lombok.Data;

@Data
public class JwtResponse implements Serializable{
    private String token;
    private String type = "Bearer";
    private String username;
    private String email;

    public JwtResponse(String accessToken, String username, String email) {
        this.token = accessToken;
        this.email = email;
        this.username = username;
    }

    
}
