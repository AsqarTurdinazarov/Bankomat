package com.example.bankomat.entity.template;

import com.example.bankomat.entity.bankomat.Role;
import com.example.bankomat.entity.bankomat.Xodim;
import com.example.bankomat.entity.enums.Huquqlar;
import com.example.bankomat.repository.RoleRepository;
import com.example.bankomat.repository.XodimRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

import static com.example.bankomat.entity.enums.Huquqlar.*;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    XodimRepository xodimRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

    @Value(value = "${spring.sql.init.mode}")
    private String boshqaruv;


    @Override
    public void run(String... args) throws Exception {
        if (boshqaruv.equals("always")){
            Huquqlar[] huquqlars = Huquqlar.values();
            Role direktor = roleRepository.save(new Role("direktor", Arrays.asList(huquqlars)));
            Role xodim = roleRepository.save(new Role("xodim", Arrays.asList(PUTMONEY,WRITEMONEY)));

            xodimRepository.save(new Xodim("Turdinazarov Asqar Baxodirovich","turdinazarovasqar355@gmail.com",passwordEncoder.encode("1234"),true,direktor));
            xodimRepository.save(new Xodim("Valiyev Ali Muminovich","turdinazarovasqar@gmail.com", passwordEncoder.encode("1111"),true,xodim));
        }
    }
}
