package com.lanDev.crm.adapter.inbound.web.dtos.auth;


public record LoginRequest (
    String email,
    String password
) {}
