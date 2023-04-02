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
public class KartaTuri extends Abstract {
    @Column(nullable = false,unique = true)
    private String nomi;
    @Column(nullable = false,length = 4,unique = true)
    private String turKodi;
}
