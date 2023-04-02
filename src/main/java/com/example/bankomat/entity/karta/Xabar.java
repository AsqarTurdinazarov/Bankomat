package com.example.bankomat.entity.karta;

import com.example.bankomat.entity.template.Abstract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Xabar extends Abstract {

    @Column(nullable = false)
    private Float yechilganPul;
    @Column(nullable = false)
    private Float qolganPul;
    @Column(nullable = false)
    private Date pulYechilganVaqt;
}
