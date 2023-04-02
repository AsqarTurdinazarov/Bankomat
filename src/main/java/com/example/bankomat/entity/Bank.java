package com.example.bankomat.entity;

import com.example.bankomat.entity.template.Abstract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Bank extends Abstract {
    @Column(nullable = false)
    private String nomi;

    @OneToOne
    private Manzil manzil;
}
