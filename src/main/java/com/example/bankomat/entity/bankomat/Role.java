package com.example.bankomat.entity.bankomat;

import com.example.bankomat.entity.enums.Huquqlar;
import com.example.bankomat.entity.template.Abstract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity(name = "Lavozim")
public class Role extends Abstract {
    @Column(nullable = false)
    private String nomi;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<Huquqlar> huquqlarList;
}
