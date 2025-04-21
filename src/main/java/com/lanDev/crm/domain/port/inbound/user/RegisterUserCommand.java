package com.lanDev.crm.domain.port.inbound.user;

import com.lanDev.crm.domain.model.user.UserRole;

public record RegisterUserCommand(
    String name,
    String email,
    String password,
    UserRole role,
    Boolean active
) {}
