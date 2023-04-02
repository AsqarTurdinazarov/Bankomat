package com.example.bankomat.controller;

import com.example.bankomat.payload.ApiRespons;
import com.example.bankomat.payload.KartaMuddatDto;
import com.example.bankomat.service.LoyihalashService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kartamuddat")
public class KartaMuddatController {

    @Autowired
    LoyihalashService loyihalashService;

    @PostMapping("/add/{bankId}/{turId}")
    public HttpEntity<?> Joylash(@PathVariable Integer bankId, @PathVariable Integer turId, @RequestBody KartaMuddatDto kartaMuddatDto){
        ApiRespons apiRespons = loyihalashService.addkartamuddat(bankId,turId,kartaMuddatDto);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }
}
