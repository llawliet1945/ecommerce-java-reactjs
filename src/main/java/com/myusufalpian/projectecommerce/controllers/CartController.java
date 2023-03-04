package com.myusufalpian.projectecommerce.controllers;

import java.math.BigInteger;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myusufalpian.projectecommerce.dto.CartRequest;
import com.myusufalpian.projectecommerce.services.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    
    @Autowired
    private CartService cartService;

    @GetMapping("getAllData")
    public ResponseEntity<String> findByUserUsername(@RequestHeader String username) throws JsonProcessingException {
        return cartService.findByUserUsername(username);
    }

    @PostMapping("/")
    public ResponseEntity<String> addNewCart(@RequestHeader String username, @RequestBody CartRequest cart) throws JsonProcessingException {
        return cartService.save(username, cart.getProductId(), cart.getQty());
    }

    @PatchMapping("/{productId}/{qty}")
    public ResponseEntity<String> updateCart(@RequestHeader String username,
                                      @PathVariable BigInteger qty, @PathVariable String productId) throws JsonProcessingException {
        return cartService.updateQty(username, Integer.parseInt(productId), qty);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteCart(@RequestHeader String username, @PathVariable String productId) throws JsonProcessingException {
        return cartService.delete(username, Integer.parseInt(productId));
    }
}
