package com.example.bankomat.entity.bankomat;

import com.example.bankomat.entity.template.MoneyCount;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Kupyura extends MoneyCount {
    private String tarif;
}
