package com.example.bankomat.service;

import com.example.bankomat.entity.KartaTuri;
import com.example.bankomat.entity.bankomat.Komissiya;
import com.example.bankomat.payload.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;

public interface LoyihalashService {
    UserDetails loadUserByUsername(String userName);

    ApiRespons addbank(BankDto bankDto);

    ApiRespons addkartaturi(KartaTuri kartaTuri);

    ApiRespons addkartamuddat(Integer bankId, Integer turId, KartaMuddatDto kartaMuddatDto);

    ApiRespons addnotuser(Dto dto, Integer kartaMuddatID);

    ApiRespons login(LoginDto loginDto);

    ApiRespons register(XodimDto xodimDto);

    ApiRespons faollashtirish(String email, String emailCode);

    ApiRespons addkomissiya(Integer bank, Integer kartaTuri, Komissiya komissiya);

    ApiRespons addbankomat(BankomatDto bankomatDto, Integer bankId, Integer xodimId);

    ApiRespons takemoney(MoneyConvertDto moneyConvertDto, String kartaRaqam, Integer bankomatId, Integer pinKod);

    ApiRespons addmoney(Integer bankomatId, String kartaRaqam, double summa, AddMoneyDto addMoneyDto);

    ApiRespons editbankomatmoney(Integer bankomatId, AddMoneyDto addMoneyDto);
}
