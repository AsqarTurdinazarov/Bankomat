package com.example.bankomat.repository;

import com.example.bankomat.entity.bankomat.BankomatKartaTuri;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BankomatKartaTuriRepository extends JpaRepository<BankomatKartaTuri,Integer> {
    Optional<BankomatKartaTuri> findByBankomatIdAndKartaTuriId(Integer bankomat_id, Integer kartaTuri_id);
}
