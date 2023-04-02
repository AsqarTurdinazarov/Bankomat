package com.example.bankomat.controller;

import com.example.bankomat.payload.ApiRespons;
import com.example.bankomat.payload.LoginDto;
import com.example.bankomat.payload.XodimDto;
import com.example.bankomat.service.LoyihalashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/xodim")
public class XodimController {

    @Autowired
    LoyihalashService loyihalashService;

    @PostMapping("/logIn")
    public HttpEntity<?> LogIn(@RequestBody LoginDto loginDto){
        ApiRespons apiRespons = loyihalashService.login(loginDto);
        return ResponseEntity.status(apiRespons.isHolat()?200:409).body(apiRespons.getXabar());
    }

    @PostMapping("/register")
    public HttpEntity<?> Registration(@Valid @RequestBody XodimDto xodimDto){
        ApiRespons apiRespons = loyihalashService.register(xodimDto);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @GetMapping("/tasdiqlash")
    public HttpEntity<?> Tastiqlash(@RequestParam String email, @RequestParam String emailCode){
        ApiRespons apiRespons = loyihalashService.faollashtirish(email,emailCode);
        return ResponseEntity.status(apiRespons.isHolat()?200:409).body(apiRespons.getXabar());
    }

}
