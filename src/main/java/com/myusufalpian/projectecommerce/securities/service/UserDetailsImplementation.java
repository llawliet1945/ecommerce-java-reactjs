package com.myusufalpian.projectecommerce.securities.service;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.myusufalpian.projectecommerce.models.entities.UserEntity;

import java.util.List;
import java.util.ArrayList;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class UserDetailsImplementation implements UserDetails{

    private String username;
    private String email;
    private String nama;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String role;

    public static UserDetailsImplementation build(UserEntity userEntity){
        return new UserDetailsImplementation(userEntity.getUsername(), userEntity.getEmail(), 
        userEntity.getNama(), 
        userEntity.getPassword(), 
        userEntity.getRole());
    }
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        if(StringUtils.hasText(role)){
            String [] splits = role.replaceAll("", "").split(",");
            for(String string : splits){
                authorities.add(new SimpleGrantedAuthority(string));
            }
        }
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
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
