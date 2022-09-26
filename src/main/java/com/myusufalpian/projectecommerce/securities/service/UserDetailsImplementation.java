package com.myusufalpian.projectecommerce.securities.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myusufalpian.projectecommerce.models.entities.UserEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class UserDetailsImplementation implements UserDetails{

    private String username;
    private String email;
    private String nama;
    private String alamat;
    private String password;
    @JsonIgnore
    private String role;

    public static UserDetailsImplementation build(UserEntity userEntity){
        return new UserDetailsImplementation(userEntity.getUsername(), userEntity.getEmail(), 
        userEntity.getNama(), 
        userEntity.getAlamat(),
        userEntity.getPassword(), 
        userEntity.getRole());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(StringUtils.hasText(role)){
            String [] splits = role.replaceAll(" ", "").split(",");
            for(String string : splits){
                authorities.add(new SimpleGrantedAuthority(string));
            }
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    
}
