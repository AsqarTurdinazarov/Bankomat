package com.example.bankomat.entity;

import com.example.bankomat.entity.template.Abstract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class KartaMuddat extends Abstract {

    @Column(nullable = false)
    private Integer kartaMuddat;

    @ManyToOne
    private Bank bank;
    @OneToOne
    private KartaTuri kartaTuri;
}
