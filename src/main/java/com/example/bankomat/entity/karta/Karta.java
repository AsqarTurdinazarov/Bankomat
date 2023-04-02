package com.example.bankomat.entity.karta;

import com.example.bankomat.entity.Bank;
import com.example.bankomat.entity.KartaTuri;
import com.example.bankomat.entity.template.Abstract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Karta extends Abstract {
    @Column(nullable = false,unique = true)
    private String kartaRaqam;
    @Column(nullable = false)
    private Integer pinKod;
    @Column(nullable = false)
    private LocalDate amalMuddat;
    @Column(nullable = false)
    private Double balance;

    @ManyToOne
    private Bank bank;
    @ManyToOne
    private KartaTuri kartaTuri;
    @ManyToOne
    private Users users;
}
