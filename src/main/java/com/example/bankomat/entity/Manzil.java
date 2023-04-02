package com.example.bankomat.entity;

import com.example.bankomat.entity.template.Abstract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Manzil extends Abstract {
    @Column(nullable = false)
    private String viloyat;
    @Column(nullable = false)
    private String tuman;
    @Column(nullable = false)
    private String kucha;
}
