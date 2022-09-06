package com.myusufalpian.projectecommerce.securities.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.myusufalpian.projectecommerce.models.entities.UserEntity;
import com.myusufalpian.projectecommerce.models.repositories.UserRepository;

import net.bytebuddy.asm.Advice.Return;

@Service
public class UserDetailsServiceImplementation implements UserDetailsService{
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findById(username).orElseThrow(()-> new UsernameNotFoundException("User dengan Username: "+username+ " tidak ditemukan"));
        return UserDetailsImplementation.build(userEntity);
    }
}
