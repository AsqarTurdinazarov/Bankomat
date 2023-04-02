package com.example.bankomat.entity.bankomat;

import com.example.bankomat.entity.KartaTuri;
import com.example.bankomat.entity.template.Abstract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class BankomatKartaTuri extends Abstract {

    @ManyToOne
    private Bankomat bankomat;
    @ManyToOne
    private KartaTuri kartaTuri;
}
