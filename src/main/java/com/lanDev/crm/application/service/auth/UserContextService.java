package com.lanDev.crm.application.service.auth;

import com.lanDev.crm.adapter.inbound.web.security.CustomUserDetails;
import com.lanDev.crm.adapter.outbound.persistence.entity.user.UserRoleJpa;
import com.lanDev.crm.domain.model.user.User;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserContextService {

    public UserRoleJpa getCurrentUserRole() {
        Authentication auth = SecurityContextHolder
                .getContext()
                .getAuthentication();

        if (auth == null || !auth.isAuthenticated()
                || auth instanceof AnonymousAuthenticationToken) {
            throw new AccessDeniedException("Usuário não autenticado");
        }

        Object principal = auth.getPrincipal();
        User domainUser;

        if (principal instanceof CustomUserDetails cud) {
            domainUser = cud.getUser();
        } else if (principal instanceof User u) {
            domainUser = u;
        } else {
            throw new AccessDeniedException("Tipo de usuário inesperado");
        }

        if (domainUser.getRole() == null) {
            throw new AccessDeniedException("Usuário sem papel definido");
        }

        return UserRoleJpa.valueOf(domainUser.getRole().name());
    }

    public UUID getCurrentUserOrganizationId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        CustomUserDetails userDetails = (CustomUserDetails) auth.getPrincipal();
        return userDetails.getUser().getOrganizationId();
    }

    public boolean isSuperAdmin() {
        return getCurrentUserRole() == UserRoleJpa.SUPER_ADMIN;
    }

}
