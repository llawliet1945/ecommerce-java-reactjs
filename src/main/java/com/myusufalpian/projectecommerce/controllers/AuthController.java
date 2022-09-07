package com.myusufalpian.projectecommerce.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myusufalpian.projectecommerce.models.entities.JwtResponse;
import com.myusufalpian.projectecommerce.models.entities.LoginRequest;
import com.myusufalpian.projectecommerce.models.entities.SignUpRequest;
import com.myusufalpian.projectecommerce.models.entities.UserEntity;
import com.myusufalpian.projectecommerce.securities.jwt.JwtUtils;
import com.myusufalpian.projectecommerce.securities.service.UserDetailsImplementation;
import com.myusufalpian.projectecommerce.services.UserService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserService userService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtUtils.generateJwtToken(authentication);
        UserDetailsImplementation userDetailsImplementation = (UserDetailsImplementation) authentication.getPrincipal();
        return ResponseEntity.ok().body(new JwtResponse(token, userDetailsImplementation.getUsername(), userDetailsImplementation.getEmail()));
    }

    @PostMapping("/signup")
    public UserEntity signup(@RequestBody SignUpRequest signUpRequest){
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(signUpRequest.getUsername());
        userEntity.setNama(signUpRequest.getNama());
        userEntity.setEmail(signUpRequest.getEmail());
        userEntity.setRole("user");
        userEntity.setPassword(signUpRequest.getPassword());
        UserEntity add = userService.save(userEntity);
        return add;
    }
}
