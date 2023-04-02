package com.example.bankomat.entity.bankomat;

import com.example.bankomat.entity.Bank;
import com.example.bankomat.entity.template.Abstract;
import com.example.bankomat.entity.template.MoneyCount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Hisobot extends MoneyCount {

    @Column(nullable = false)
    private double tuldirilganPul;
    @Column(nullable = false)
    private Timestamp tuldirilganVaqt;

    @OneToOne
    private Bank bank;
    @OneToOne
    private Xodim xodim;
}
