package com.example.bankomat.entity.karta;

import com.example.bankomat.entity.Manzil;
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
public class Users extends Abstract {

    @Column(nullable = false)
    private String fish;
    @Column(nullable = false,unique = true)
    private String email;
    @Column(nullable = false,unique = true)
    private String pasportRaqam;

    @OneToOne
    private Manzil manzil;
}
