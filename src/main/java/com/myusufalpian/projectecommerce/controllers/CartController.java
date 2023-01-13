package com.myusufalpian.projectecommerce.controllers;

import java.math.BigInteger;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.myusufalpian.projectecommerce.dto.CartRequest;
import com.myusufalpian.projectecommerce.models.entities.KeranjangEntity;
import com.myusufalpian.projectecommerce.securities.service.UserDetailsImplementation;
import com.myusufalpian.projectecommerce.services.CartService;

@RestController
@RequestMapping("/api/cart")
@PreAuthorize("isAuthenticated()")
public class CartController {
    
    @Autowired
    private CartService cartService;

    @GetMapping("getAllData")
    public List<KeranjangEntity> findByUserUsername(@AuthenticationPrincipal UserDetailsImplementation user){
        return cartService.findByUserUsername(user.getUsername());
    }

    @PostMapping("/")
    public KeranjangEntity addNewCart(@AuthenticationPrincipal UserDetailsImplementation user, @RequestBody CartRequest cart){
        return cartService.save(user.getUsername(), cart.getProductId(), cart.getQty());
    }

    @PatchMapping("/{productId}/{qty}")
    public KeranjangEntity updateCart(@AuthenticationPrincipal UserDetailsImplementation user,
                                      @PathVariable BigInteger qty, @PathVariable String productId){
        return cartService.updateQty(user.getUsername(), Integer.parseInt(productId), qty);
    }

    @DeleteMapping("/{productId}")
    public void deleteCart(@AuthenticationPrincipal UserDetailsImplementation user, @PathVariable String productId){
        cartService.delete(user.getUsername(), Integer.parseInt(productId));
        cartService.findByUserUsername(user.getUsername());
    }
}
