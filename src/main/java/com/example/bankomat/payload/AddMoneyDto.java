package com.example.bankomat.payload;

import lombok.Data;

@Data
public class AddMoneyDto {
    private int ikkiyuz;
    private int yuz;
    private int ellik;
    private int yigirma;
    private int un;
    private int besh;
}
