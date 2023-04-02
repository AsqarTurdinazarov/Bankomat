package com.example.bankomat.entity.bankomat;

import com.example.bankomat.entity.Manzil;
import com.example.bankomat.entity.enums.Huquqlar;
import com.example.bankomat.entity.template.Abstract;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Xodim extends Abstract implements UserDetails {

    @Column(nullable = false)
    private String fish;
    @Email
    @Column(nullable = false,unique = true)
    private String username;
    @Column(nullable = false)
    private String password;

    private String telRaqam;

    private String emailcode;

    private boolean accountNonExpired = true;
    private boolean accountNonLocked = true;
    private boolean credentialsNonExpired = true;
    private boolean enabled = false;

    @OneToOne
    private Manzil manzil;
    @ManyToOne
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<Huquqlar> huquqlarList = this.role.getHuquqlarList();
        List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
        for (Huquqlar huquqlar : huquqlarList) {
            grantedAuthorityList.add(new GrantedAuthority() {
                @Override
                public String getAuthority() {
                    return huquqlar.name();
                }
            });
        }
        return grantedAuthorityList;
    }

    public Xodim(String fish,String username,String password,boolean enabled,Role role){
        this.fish = fish;
        this.username = username;
        this.password = password;
        this.enabled = enabled;
        this.role = role;
    }
}
