package com.example.bankomat.repository;

import com.example.bankomat.entity.karta.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsersRepository extends JpaRepository<Users,Integer> {
    boolean existsByEmail(String email);
    Optional<Users> findByPasportRaqam(String pasportRaqam);
    boolean existsByPasportRaqam(String pasportRaqam);
}
