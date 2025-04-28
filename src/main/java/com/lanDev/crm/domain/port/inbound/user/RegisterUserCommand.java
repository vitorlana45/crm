package com.lanDev.crm.domain.port.inbound.user;

import com.lanDev.crm.domain.model.user.UserRole;

import java.util.UUID;

public record RegisterUserCommand(
    String name,
    String email,
    String password,
    UserRole role,
    Boolean active,
    UUID organizationId
) {}
