package com.example.bankomat.repository;

import com.example.bankomat.entity.KartaMuddat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KartaMuddatRepository extends JpaRepository<KartaMuddat,Integer> {
    boolean existsByBankIdAndKartaTuriIdAndKartaMuddat(Integer bank_id, Integer kartaTuri_id, Integer kartaMuddat);
}
