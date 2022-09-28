package com.myusufalpian.projectecommerce.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.myusufalpian.projectecommerce.dto.PesananRequestDTO;
import com.myusufalpian.projectecommerce.dto.PesananResponseDTO;
import com.myusufalpian.projectecommerce.models.entities.PesananEntity;
import com.myusufalpian.projectecommerce.securities.service.UserDetailsImplementation;
import com.myusufalpian.projectecommerce.services.PesananService;

@RestController
@RequestMapping("/api/orders")
@PreAuthorize("isAuthenticated()")
public class PesananController {
    
    @Autowired
    private PesananService pesananService;

    @PostMapping("/addNewOrder")
    @PreAuthorize("hasAuthority('user')")
    public PesananResponseDTO addNewOrder(@AuthenticationPrincipal UserDetailsImplementation user, @RequestBody PesananRequestDTO request){
        return pesananService.add(user.getUsername(), request);
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAuthority('user')")
    public PesananEntity cancelOrder(@AuthenticationPrincipal UserDetailsImplementation user, @PathVariable String id){
        return pesananService.cancel(id, user.getUsername());
    }

    @PatchMapping("/{id}/receive")
    @PreAuthorize("hasAuthority('user')")
    public PesananEntity receiveOrder(@AuthenticationPrincipal UserDetailsImplementation user, @PathVariable String id){
        return pesananService.receive(id, user.getUsername());
    }

    @PatchMapping("/{id}/confirm")
    @PreAuthorize("hasAuthority('admin')")
    public PesananEntity confirmationPayment(@AuthenticationPrincipal UserDetailsImplementation user, @PathVariable String id){
        return pesananService.confirmationPayment(id, user.getUsername());
    }
    
    @PatchMapping("/{id}/packing")
    @PreAuthorize("hasAuthority('admin')")
    public PesananEntity packing(@AuthenticationPrincipal UserDetailsImplementation user, @PathVariable String id){
        return pesananService.packing(id, user.getUsername());
    }
    
    @PatchMapping("/{id}/sent")
    @PreAuthorize("hasAuthority('admin')")
    public PesananEntity sent(@AuthenticationPrincipal UserDetailsImplementation user, @PathVariable String id){
        return pesananService.sent(id, user.getUsername());
    }

    @GetMapping("/getAllData")
    @PreAuthorize("hasAuthority('user')")
    public List<PesananEntity> getAllData(@AuthenticationPrincipal UserDetailsImplementation user, @RequestParam(name = "page", defaultValue = 0, required = false) Integer page, @RequestParam(name = "limit", defaultValue = 25, required = false) Integer limit){
        return pesananService.findAllPesanan(user.getUsername(), page, limit);
    }

    @GetMapping("/getOrderItems")
    @PreAuthorize("hasAuthority('user')")
    public List<PesananEntity> getOrderItems(@AuthenticationPrincipal  UserDetailsImplementation user, 
        @RequestParam(name = "page", defaultValue = 0, required = false) Integer page, 
        @RequestParam(name = "limit", defaultValue = 25, required = false) Integer limit, 
        @RequestParam(name = "filterText", defaultValue = "", required = false) String filterText){
        return pesananService.search(filterText, page, limit);
    }
}
