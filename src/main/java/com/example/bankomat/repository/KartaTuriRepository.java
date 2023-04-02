package com.example.bankomat.repository;

import com.example.bankomat.entity.KartaTuri;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KartaTuriRepository extends JpaRepository<KartaTuri,Integer> {
    boolean existsByNomi(String nomi);
//    Optional<KartaTuri> findByNomi(String nomi);
}
