package com.example.bankomat.entity.bankomat;

import com.example.bankomat.entity.Bank;
import com.example.bankomat.entity.KartaTuri;
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
public class Komissiya extends Abstract {

    @Column(nullable = false)
    private Double foiz;

    @OneToOne
    private KartaTuri kartaTuri;
    @OneToOne
    private Bank bank;

}
