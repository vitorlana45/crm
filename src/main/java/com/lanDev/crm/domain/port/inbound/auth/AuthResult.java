package com.lanDev.crm.domain.port.inbound.auth;

public record AuthResult (
    String name,
    String token,
    Long expirationDate
) { }
