package com.lanDev.crm.domain.port.inbound.auth;

public interface AuthenticationServicePort {
    AuthResult authenticate(String email, String password);
}