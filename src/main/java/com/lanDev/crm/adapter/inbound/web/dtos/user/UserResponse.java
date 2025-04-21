package com.lanDev.crm.adapter.inbound.web.dtos.user;

import java.time.LocalDateTime;
import java.util.UUID;


public record UserResponse(
    UUID id,
    String name,
    String email,
    String role,
    boolean active,
    LocalDateTime createdAt

) {}
