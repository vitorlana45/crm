package com.lanDev.crm.adapter.inbound.web.mapper.user;

import java.util.UUID;

public record UpdateResponse(
        UUID uuid,
        String name,
        String email,
        String password,
        String role,
        Boolean active
) {
}
