package com.lanDev.crm.application.service.user;

import com.lanDev.crm.adapter.outbound.persistence.entity.user.UserRoleJpa;
import com.lanDev.crm.application.service.auth.UserContextService;
import com.lanDev.crm.domain.domainExceptions.BusinessException;
import com.lanDev.crm.domain.domainExceptions.EmailAlreadyExistsException;
import com.lanDev.crm.domain.model.user.User;
import com.lanDev.crm.domain.model.user.UserRole;
import com.lanDev.crm.domain.port.inbound.user.RegisterUserCommand;
import com.lanDev.crm.domain.port.inbound.user.UpdateUserCommand;
import com.lanDev.crm.domain.port.inbound.user.UserUseCase;
import com.lanDev.crm.domain.port.outbound.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCase {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserContextService userContextService;

    @Override
    public User register(RegisterUserCommand cmd) {

        UserRoleJpa currentRole = userContextService.getCurrentUserRole();

        UserRoleJpa desiredRole = cmd.role() == null
                ? UserRoleJpa.USER
                : UserRoleJpa.valueOf(cmd.role().name());

        if (currentRole == UserRoleJpa.SUPER_ADMIN && desiredRole == UserRoleJpa.MANAGER) {
            throw new BusinessException(HttpStatus.BAD_REQUEST, "User with role MANAGER cannot be created by ADMIN");
        }

        if (userRepository.existsByEmail(cmd.email())) {
            throw new EmailAlreadyExistsException(cmd.email());
        }

        String hashed = passwordEncoder.encode(cmd.password());
        User toSave = User.newUser(
                cmd.name(),
                cmd.email(),
                hashed,
                UserRole.valueOf(desiredRole.name()),
                cmd.active(),
                cmd.organizationId()
        );
        return userRepository.save(toSave);
    }

    @Override
    public User updateUser(UpdateUserCommand cmd, UUID uuid) {
        Optional<User> userOpt = userRepository.getByUuid(uuid);
        if (userOpt.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "User not found");
        }
        
        User user = userOpt.get();
        hasAccessToOrganization(user.getOrganizationId());

        if (cmd.name() != null) {
            user.setName(cmd.name());
        }
        
        if (cmd.email() != null && !cmd.email().equals(user.getEmail())) {
            if (userRepository.existsByEmail(cmd.email())) {
                throw new EmailAlreadyExistsException(cmd.email());
            }
            user.setEmail(cmd.email());
        }
        
        if (cmd.role() != null) {
            UserRoleJpa currentRole = userContextService.getCurrentUserRole();
            UserRoleJpa desiredRole = UserRoleJpa.valueOf(cmd.role().name());
            
            if (currentRole == UserRoleJpa.ORG_ADMIN && desiredRole == UserRoleJpa.ORG_OWNER) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "User with role ADMIN cannot change a user to MANAGER role");
            }

            if(currentRole == UserRoleJpa.USER && desiredRole == UserRoleJpa.SUPER_ADMIN || desiredRole == UserRoleJpa.ORG_OWNER) {
                throw new BusinessException(HttpStatus.BAD_REQUEST, "User with role USER cannot change a user to MANAGER role");
            }
            
            user.setRole(cmd.role());
        }
        
        if (cmd.password() != null && !cmd.password().isBlank()) {
            String hashed = passwordEncoder.encode(cmd.password());
            user.setPassword(hashed);
        }
        
        if (cmd.active() != null) {
            user.setActive(cmd.active());
        }
        
        return userRepository.save(user);
    }

    @Override
    public void activateUser(UUID id) {
        Optional<User> userOpt = userRepository.getByUuid(id);
        if (userOpt.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "Usuário não encontrado");
        }

        hasAccessToOrganization(userOpt.get().getOrganizationId());
        userRepository.activateUser(id);
    }
    
    @Override
    public void deactivateUser(UUID id) {
        Optional<User> userOpt = userRepository.getByUuid(id);
        if (userOpt.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "User not found");
        }
        
        userRepository.deactivateUser(id);
    }
    
    @Override
    public User getUserById(UUID id) {
        Optional<User> userOpt = userRepository.getByUuid(id);
        if (userOpt.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "User not found");
        }
        
        return userOpt.get();
    }
    
    @Override
    public User getUserByEmail(String email) {
        Optional<User> userOpt = userRepository.getUserByEmail(email);
        if (userOpt.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "User not found with email: " + email);
        }
        
        return userOpt.get();
    }
    
    @Override
    public void deleteUser(UUID id) {
        Optional<User> userOpt = userRepository.getByUuid(id);
        if (userOpt.isEmpty()) {
            throw new BusinessException(HttpStatus.NOT_FOUND, "User not found");
        }
        
        userRepository.deleteById(id);
    }

    private void hasAccessToOrganization(UUID organizationId) {
        // SUPER_ADMIN pode acessar qualquer organização
        if (userContextService.isSuperAdmin()) {
            return;
        }

        // Outros papéis só podem acessar sua própria organização
        UUID currentUserOrgId = userContextService.getCurrentUserOrganizationId();
        if (!currentUserOrgId.equals(organizationId)) {
            throw new BusinessException(HttpStatus.FORBIDDEN, "Access denied to this organization");
        }
    }

}
