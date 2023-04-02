package com.example.bankomat.controller;

import com.example.bankomat.entity.bankomat.Komissiya;
import com.example.bankomat.payload.ApiRespons;
import com.example.bankomat.service.LoyihalashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/komissiya")
public class KomissiyaController {

    @Autowired
    LoyihalashService loyihalashService;

    @PostMapping("/addkomissiya/{kartaTuri}/{bank}")
    public HttpEntity<?> Qushish(@PathVariable Integer bank, @PathVariable Integer kartaTuri, @RequestBody Komissiya komissiya){
        ApiRespons apiRespons = loyihalashService.addkomissiya(bank,kartaTuri,komissiya);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }
}
