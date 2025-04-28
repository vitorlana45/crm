package com.lanDev.crm.adapter.inbound.web.controllers.user;

public record UpdateUserRequest(
        String name,
        String email,
        String password,
        String role,
        Boolean active
) { }
