package com.example.bankomat.entity.bankomat;

import com.example.bankomat.entity.Bank;
import com.example.bankomat.entity.KartaTuri;
import com.example.bankomat.entity.Manzil;
import com.example.bankomat.entity.template.Abstract;
import com.example.bankomat.entity.template.MoneyCount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Bankomat extends MoneyCount {

    @Column(nullable = false)
    private double pulSigim;
    @Column(nullable = false)
    private double balance;
    @Column(nullable = false)
    private double maxKirimChiqim;
    @Column(nullable = false)
    private double minKirimChiqim;
    @Column(nullable = false)
    private double chegara;

    @ManyToOne
    private Bank bank;
    @OneToOne
    private Manzil manzil;
    @OneToOne
    private Xodim xodim;
    @OneToOne
    private Kupyura kupyura;
}
