package com.example.bankomat.token;

import com.example.bankomat.service.ServiceImplement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class Filter extends OncePerRequestFilter {
    @Autowired
    ServiceImplement serviceImplement;
    @Autowired
    TokenGenerator tokenGenerator;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String users = request.getHeader("users");
        if (users != null){
            boolean b = tokenGenerator.TokenTekshirish(users);
            if (b){
                String userName = tokenGenerator.OpenFilter(users);
                if (userName != null){
                    UserDetails userDetails = serviceImplement.loadUserByUsername(userName);

                    if (userDetails!=null){
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        SecurityContextHolder
                                .getContext()
                                .setAuthentication(usernamePasswordAuthenticationToken);
                    }
                    else {
                        System.out.println("UserName topilmadi!!!");
                    }
                }else {
                    System.out.println("UserName yaratilmadi!!!");
                }
            }else {
                System.out.println("Tokenning muddati tugagan!");
            }
        }else {
            System.out.println("Token ololmadi!");
        }
        filterChain.doFilter(request,response);
    }

}
