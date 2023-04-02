package com.example.bankomat.controller;

import com.example.bankomat.payload.AddMoneyDto;
import com.example.bankomat.payload.ApiRespons;
import com.example.bankomat.payload.BankomatDto;
import com.example.bankomat.payload.MoneyConvertDto;
import com.example.bankomat.service.LoyihalashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bankomat")
public class BankomatController {

    @Autowired
    LoyihalashService loyihalashService;

    @PostMapping("/addbankomat/{bankId}/{xodimId}")
    public HttpEntity<?> AddBankomat(@RequestBody BankomatDto bankomatDto, @PathVariable Integer bankId,@PathVariable Integer xodimId){
        ApiRespons apiRespons = loyihalashService.addbankomat(bankomatDto,bankId,xodimId);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PutMapping("/takemoney/{bankomatId}/{kartaRaqam}/{pinKod}")
    public HttpEntity<?> PulYechmoq(@RequestBody MoneyConvertDto moneyConvertDto, @PathVariable String kartaRaqam, @PathVariable Integer bankomatId, @PathVariable Integer pinKod){
        ApiRespons apiRespons = loyihalashService.takemoney(moneyConvertDto,kartaRaqam,bankomatId,pinKod);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PutMapping("/addmoney/{bankomatId}/{kartaRaqam}/{summa}")
    public HttpEntity<?> PulQuymoq(@PathVariable double summa, @RequestBody AddMoneyDto addMoneyDto, @PathVariable Integer bankomatId, @PathVariable String kartaRaqam){
        ApiRespons apiRespons = loyihalashService.addmoney(bankomatId,kartaRaqam,summa,addMoneyDto);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }

    @PutMapping("/editbankomatmoney/{bankomatId}")
    public HttpEntity<?> EditBankomat(@PathVariable Integer bankomatId,@RequestBody AddMoneyDto addMoneyDto){
        ApiRespons apiRespons = loyihalashService.editbankomatmoney(bankomatId,addMoneyDto);
        return ResponseEntity.status(apiRespons.isHolat()?200:208).body(apiRespons.getXabar());
    }
}
