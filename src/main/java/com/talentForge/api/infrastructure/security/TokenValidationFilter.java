package com.talentForge.api.infrastructure.security;

import com.talentForge.api.application.service.TokenService;
import com.talentForge.api.infrastructure.persistence.repository.JpaUserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenValidationFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService service;

    @Autowired
    private JpaUserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = fetchToken(request);

        if (token != null){
            String email = service.validateToken(token);
            var user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new EntityNotFoundException("Não existe um Usuário com esse email!"));

            var authentication = new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        doFilter(request,response,filterChain);
    }

    private String fetchToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization");

        if (token != null){
            return token.replace("Bearer ","");
        }

        return null;
    }
}
