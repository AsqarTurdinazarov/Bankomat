package com.example.bankomat.repository;

import com.example.bankomat.entity.Bank;
import com.example.bankomat.entity.KartaTuri;
import com.example.bankomat.entity.bankomat.Komissiya;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface KomissiyaRepository extends JpaRepository<Komissiya,Integer> {
    boolean existsByKartaTuriIdAndBankId(Integer kartaTuri_id, Integer bank_id);
    Optional<Komissiya> findByKartaTuriAndBank(KartaTuri kartaTuri, Bank bank);
}
