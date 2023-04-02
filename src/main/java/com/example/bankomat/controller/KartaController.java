package com.example.bankomat.controller;

import com.example.bankomat.entity.karta.Users;
import com.example.bankomat.payload.ApiRespons;
import com.example.bankomat.payload.Dto;
import com.example.bankomat.payload.PasportDto;
import com.example.bankomat.repository.UsersRepository;
import com.example.bankomat.service.LoyihalashService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/karta")
public class KartaController {

    @Autowired
    LoyihalashService loyihalashService;
    @Autowired
    UsersRepository usersRepository;


    @PostMapping("/addwithuser")
    public HttpEntity<?> Qushish(@RequestBody PasportDto pasportDto){
        Optional<Users> byPasportRaqam = usersRepository.findByPasportRaqam(pasportDto.getPasportRaqam());
        if (byPasportRaqam.isPresent()){ return ResponseEntity.status(HttpStatus.OK).body(byPasportRaqam.get()); }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Mijoz ro'yhatdan o'tmagan");
    }

    @PostMapping("/addnotuser/{kartaMuddatID}")
    public HttpEntity<?> Post(@RequestBody Dto dto,@PathVariable Integer kartaMuddatID){
        ApiRespons apiRespons = loyihalashService.addnotuser(dto,kartaMuddatID);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }
}
