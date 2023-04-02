package com.example.bankomat.repository;

import com.example.bankomat.entity.Manzil;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManzilRepository extends JpaRepository<Manzil,Integer> {
    boolean existsByViloyatAndTumanAndKucha(String viloyat, String tuman, String kucha);
}
