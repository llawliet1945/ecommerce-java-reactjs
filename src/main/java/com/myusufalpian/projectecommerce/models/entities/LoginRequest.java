package com.myusufalpian.projectecommerce.models.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest implements Serializable{
    private String username;
    private String password;
}
