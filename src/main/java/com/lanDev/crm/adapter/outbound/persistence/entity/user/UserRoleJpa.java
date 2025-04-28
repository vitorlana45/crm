package com.lanDev.crm.adapter.outbound.persistence.entity.user;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum UserRoleJpa {
    USER(List.of("ROLE_USER")),
    MANAGER(List.of("ROLE_USER","ROLE_ADMIN","ROLE_MANAGER")),
    SUPER_ADMIN(List.of("ROLE_USER", "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_SUPER_ADMIN")),
    ORG_OWNER(List.of("ROLE_USER", "ROLE_ADMIN", "ROLE_MANAGER", "ROLE_ORG_OWNER")),
    ORG_ADMIN(List.of("ROLE_USER", "ROLE_ADMIN", "ROLE_ORG_ADMIN")),
    GUEST(List.of("ROLE_GUEST"));


    private final List<String> authorities;

    UserRoleJpa(List<String> authorities) {
        this.authorities = authorities;
    }

    public List<GrantedAuthority> getAuthorities() {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
