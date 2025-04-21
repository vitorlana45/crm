package com.lanDev.crm.adapter.inbound.web.dtos.auth;


public record LoginResponse (
        String name,
        String token,
        Long expirationDate
){}