package com.myusufalpian.projectecommerce.controllers;

import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.myusufalpian.projectecommerce.dto.PesananRequestDTO;
import com.myusufalpian.projectecommerce.dto.PesananResponseDTO;
import com.myusufalpian.projectecommerce.models.entities.PesananEntity;
import com.myusufalpian.projectecommerce.services.PesananService;

@RestController
@RequestMapping("/api/orders")
public class PesananController {
    
    @Autowired
    private PesananService pesananService;

    @PostMapping("/addNewOrder")
    public ResponseEntity<String> addNewOrder(@RequestHeader String username, @RequestBody PesananRequestDTO request) throws JsonProcessingException {
        return pesananService.add(username, request);
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<String> cancelOrder(@RequestHeader String username, @PathVariable String id) throws JsonProcessingException {
        return pesananService.cancel(id, username);
    }

    @PatchMapping("/{id}/receive")
    public ResponseEntity<String> receiveOrder(@RequestHeader String username, @PathVariable String id) throws JsonProcessingException {
        return pesananService.receive(id, username);
    }

    @PatchMapping("/{id}/confirm")
    public ResponseEntity<String> confirmationPayment(@RequestHeader String username, @PathVariable String id) throws JsonProcessingException {
        return pesananService.confirmationPayment(id, username);
    }
    
    @PatchMapping("/{id}/packing")
    public ResponseEntity<String> packing(@RequestHeader String username, @PathVariable String id) throws JsonProcessingException {
        return pesananService.packing(id, username);
    }
    
    @PatchMapping("/{id}/sent")
    public ResponseEntity<String> sent(@RequestHeader String username, @PathVariable String id) throws JsonProcessingException {
        return pesananService.sent(id, username);
    }

    @GetMapping("/getAllData")
    public ResponseEntity<String> getAllData(@RequestHeader String username, @RequestParam(name = "page", defaultValue = "0", required = false) Integer page, @RequestParam(name = "limit", defaultValue = "25", required = false) Integer limit) throws JsonProcessingException {
        return pesananService.findAllPesanan(username, page, limit);
    }

    @GetMapping("/getOrderItems")
    public ResponseEntity<String> getOrderItems(@RequestHeader String username,
        @RequestParam(name = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(name = "limit", defaultValue = "25", required = false) Integer limit,
        @RequestParam(name = "filterText", defaultValue = "", required = false) String filterText) throws JsonProcessingException {
        return pesananService.search(filterText, page, limit);
    }
}
