package com.example.bankomat.repository;

import com.example.bankomat.entity.bankomat.Xodim;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.Email;
import java.util.Optional;

public interface XodimRepository extends JpaRepository<Xodim,Integer> {
    Optional<Xodim> findByUsername(String username);
    Optional<Xodim> findByUsernameAndEmailcode(@Email String username, String emailcode);
}
