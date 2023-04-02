package com.example.bankomat.repository;

import com.example.bankomat.entity.karta.Karta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KartaRepository extends JpaRepository<Karta,Integer> {
    Optional<Karta> findByKartaRaqam(String kartaRaqam);
}
