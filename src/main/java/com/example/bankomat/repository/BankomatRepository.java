package com.example.bankomat.repository;

import com.example.bankomat.entity.bankomat.Bankomat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankomatRepository extends JpaRepository<Bankomat,Integer> {
}
