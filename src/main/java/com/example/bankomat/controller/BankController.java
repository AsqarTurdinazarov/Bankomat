package com.example.bankomat.controller;

import com.example.bankomat.payload.ApiRespons;
import com.example.bankomat.payload.BankDto;
import com.example.bankomat.service.LoyihalashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bank")
public class BankController {

    @Autowired
    LoyihalashService loyihalashService;

    @PostMapping("/addbank")
    public HttpEntity<?> Joylash(@RequestBody BankDto bankDto){
        ApiRespons apiRespons = loyihalashService.addbank(bankDto);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }
}
