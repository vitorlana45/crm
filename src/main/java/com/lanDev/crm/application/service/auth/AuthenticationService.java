package com.lanDev.crm.application.service.auth;

import com.lanDev.crm.adapter.inbound.web.security.CustomUserDetails;
import com.lanDev.crm.adapter.inbound.web.security.TokenService;
import com.lanDev.crm.domain.domainExceptions.BusinessException;
import com.lanDev.crm.domain.model.user.User;
import com.lanDev.crm.domain.port.inbound.auth.AuthenticationServicePort;
import com.lanDev.crm.domain.port.inbound.auth.AuthResult;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServicePort {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResult authenticate(String email, String password) throws UsernameNotFoundException {
        var usernamePassword = new UsernamePasswordAuthenticationToken(email, password);

        Authentication auth = null;

        try {
            System.out.println("antes");
            auth = this.authenticationManager.authenticate(usernamePassword);
        } catch (RuntimeException ex) {
           throw new BusinessException(HttpStatus.BAD_REQUEST, "Credenciais inválidas. Verifique o e-mail e senha informados.");
        }

        CustomUserDetails details  = (CustomUserDetails) auth.getPrincipal();
        if (details == null) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "Error: Verifique suas credenciais");
        }

        User domainUser = details.getUser();
        String token = this.tokenService.generateToken(domainUser);
        long expirationDate = this.tokenService.getExpirationToken(token);


        return new AuthResult(domainUser.getName(),token, expirationDate);
    }

}