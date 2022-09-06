package com.myusufalpian.projectecommerce.services;

import com.myusufalpian.projectecommerce.exceptions.BadRequestException;
import com.myusufalpian.projectecommerce.exceptions.ResourceNotFoundException;
import com.myusufalpian.projectecommerce.models.entities.UserEntity;
import com.myusufalpian.projectecommerce.models.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserEntity save(UserEntity user){

        if(!StringUtils.hasText(user.getUsername())){
            throw new BadRequestException("username pengguna produk tidak boleh kosong!");
        }

        if(!StringUtils.hasText(user.getNama())){
            throw new BadRequestException("Nama pengguna produk tidak boleh kosong!");
        }

        if(!StringUtils.hasText(user.getEmail())){
            throw new BadRequestException("Email pengguna tidak boleh kosong!");
        }

        if(userRepository.existsById(user.getUsername())) {
            throw new BadRequestException("Username " + user.getUsername() +" telah terdaftar!");
        }

        if(userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("Email " + user.getEmail() + " telah terdaftar!");
        }

        return userRepository.save(user);
    }

    public UserEntity update(UserEntity user){

        if(!StringUtils.hasText(user.getUsername())){
            throw new BadRequestException("username pengguna produk tidak boleh kosong!");
        }

        if(!StringUtils.hasText(user.getNama())){
            throw new BadRequestException("Nama pengguna produk tidak boleh kosong!");
        }

        if(!StringUtils.hasText(user.getEmail())){
            throw new BadRequestException("Email pengguna tidak boleh kosong!");
        }

        return userRepository.save(user);

    }

    public UserEntity findById(String id){
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("pengguna dengan Id: "+
                        id+" tidak ditemukan!"));
    }

    public List<UserEntity> findAll(){
        return userRepository.findAll();
    }

    public void removeOne(String id){
        userRepository.deleteById(id);
    }

    public UserEntity getUserByNama(String nama){
        return userRepository.findByNama(nama);
    }

    public List<UserEntity> getDataByNama(String nama){
        return userRepository.findByNamaStartingWith(nama);
    }

    public UserEntity getUserByEmail(String email){
        return userRepository.findByEmail(email);
    }
}
