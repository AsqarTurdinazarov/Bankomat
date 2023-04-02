package com.example.bankomat.payload;

import com.example.bankomat.entity.Manzil;
import com.example.bankomat.entity.bankomat.Kupyura;
import lombok.Data;

import java.util.List;

@Data
public class BankomatDto {
    private double pulSigim;
    private double balance;
    private double maxKirimChiqim;
    private double minKirimChiqim;

    private int ikkiyuz;
    private int yuz;
    private int ellik;
    private int yigirma;
    private int un;
    private int besh;

    private Manzil manzil;
    private Kupyura kupyura;
    private double chegara;

    private List<Integer> kartaTuriList;
}
