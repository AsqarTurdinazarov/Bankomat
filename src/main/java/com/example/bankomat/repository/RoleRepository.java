package com.example.bankomat.repository;

import com.example.bankomat.entity.bankomat.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Integer> {
    Optional<Role> findByNomi(String nomi);
}
